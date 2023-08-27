package com.openlab.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.hotel.base.Result;
import com.openlab.hotel.dto.HotelRoomDetailDto;
import com.openlab.hotel.dto.HotelRoomListDto;
import com.openlab.hotel.entity.HotelRoom;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.feign.HotelFloorService;
import com.openlab.hotel.feign.HotelRoomTypeService;
import com.openlab.hotel.service.HotelRoomService;
import com.openlab.hotel.util.MinioUtil;
import com.openlab.hotel.util.PageUtil;
import com.openlab.hotel.vo.HotelFloorVo;
import com.openlab.hotel.vo.IndexFloorVo;
import com.openlab.hotel.vo.IndexRoomVo;
import com.openlab.hotel.vo.RoomTypesVo;
import io.minio.MinioClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
@Slf4j
@Tag(name = "酒店房间接口", description = "对酒店房间作相关操作")
@RestController
@RequestMapping("/room")
public class HotelRoomController {
    @Resource
    private HotelRoomService hotelRoomService;

    @Resource
    private MinioClient minioClient;

    @Resource
    private HotelRoomTypeService hotelRoomTypeService;

    @Resource
    private HotelFloorService hotelFloorService;

    @Operation(summary = "获取房间分页数据接口")
    @Parameters({
            @Parameter(name = "searchValue", in = ParameterIn.QUERY, description = "搜索关键字"),
            @Parameter(name = "pageSize", in = ParameterIn.QUERY, description = "每页显示行数", required = true),
            @Parameter(name = "pageIndex", in = ParameterIn.QUERY, description = "当前页码", required = true),
    })
    @GetMapping
    public Result<Object> list(@RequestParam(value = "searchValue", defaultValue = "", required = false) String searchValue,
                               @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
                               @RequestParam(value = "pageIndex", defaultValue = "1") Long pageIndex) {

        PageUtil<HotelRoomListDto> list = hotelRoomService.listByPage(searchValue, pageSize, pageIndex);

        list.getContent().forEach(room -> room.setCoverImg(getRoomCoverImage(room.getCoverImg())));

        return Result.ok().data(list);
    }

    @Operation(summary = "添加房间接口")
    @Parameter(name = "hotelRoom", in = ParameterIn.QUERY, description = "房间对象，用于接收前端提交的表单数据", required = true)
    @PostMapping
    public Result<Object> addRoom(@RequestBody @Validated HotelRoom hotelRoom) {
        boolean flag = hotelRoomService.save(hotelRoom);
        if (flag) return Result.ok().message("房间添加成功！");
        return Result.fail().message("房间添加失败！");
    }

    @Operation(summary = "获取房间详情接口", description = "根据房间编号查询房间详细信息，用于更新房间时使用")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "房间编号", required = true)
    @GetMapping("/{id}")
    public Result<Object> detail(@PathVariable("id") Long id) {
        HotelRoom hotelRoom = hotelRoomService.getById(id);
        return Result.ok().data(HotelRoomDetailDto.builder()
                        .id(hotelRoom.getId())
                        .roomName(hotelRoom.getRoomName())
                        .roomNumber(hotelRoom.getRoomNumber())
                        .remarks(hotelRoom.getRemarks())
                        .standardPrice(hotelRoom.getStandardPrice())
                        .broadband(hotelRoom.getBroadband())
                        .bedType(hotelRoom.getBedType())
                        .coverImg(getRoomCoverImage(hotelRoom.getCoverImg()))
                        .roomTypeId(hotelRoom.getRoomTypeId())
                        .floorId(hotelRoom.getFloorId())
                        .memberPrice(hotelRoom.getMemberPrice())
                .build());
    }

    @Operation(summary = "更新房间接口")
    @Parameter(name = "hotelRoom", in = ParameterIn.QUERY, description = "房间对象，用于接收前端提交的表单数据", required = true)
    @PutMapping
    public Result<Object> editRoom(@RequestBody @Validated HotelRoom hotelRoom) {
        if (Objects.isNull(hotelRoom)) throw new BadRequestException("更新房间数据不能为空！");

        HotelRoom dbHotelRoom = hotelRoomService.getById(hotelRoom.getId());
        if (Objects.isNull(dbHotelRoom)) throw new BadRequestException("您修改的房间信息不存在！");


        if (hotelRoom.getCoverImg().contains(dbHotelRoom.getCoverImg())) {
            // 如果没有更换封面就不删除
            hotelRoom.setCoverImg(dbHotelRoom.getCoverImg());
        } else {
            // 删除Minio 服务器中的图片
            MinioUtil.remove(minioClient, "hotel", dbHotelRoom.getCoverImg());
        }

        boolean flag = hotelRoomService.updateById(hotelRoom);
        if (flag) return Result.ok().message("房间更新成功!");
        return Result.fail().message("房间更新失败!");
    }

    @Operation(summary = "删除房间接口", description = "根据房间编号来删除房间信息，同时还需要删除Minio服务器中的封面")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "房间编号", required = true)
    @DeleteMapping("/{id}")
    public Result<Object> delRoom(@PathVariable("id") Long id) {
        HotelRoom dbHotelRoom = hotelRoomService.getById(id);
        if (Objects.isNull(dbHotelRoom)) throw new BadRequestException("您修改的房间信息不存在！");

        // 删除Minio 服务器中的图片
        MinioUtil.remove(minioClient, "hotel", dbHotelRoom.getCoverImg());

        // 删除房间信息
        boolean flag = hotelRoomService.removeById(id);
        if (flag) return Result.ok().message("房间删除成功!");
        return Result.fail().message("房间删除失败!");
    }


    @Operation(summary="获取房间类型列表", description ="获取所有房间类型列表，用于添加和修改房间时填充下拉列表数据")
    @GetMapping("/types")
    public Result<Object> types() {
        List<RoomTypesVo> all = hotelRoomTypeService.getTypeList();
        return Result.ok().data(all);
    }

    @Operation(summary = "获取楼层列表", description = "获取所有楼层列表，用于添加和修改房间时填充下拉列表数据")
    @GetMapping("/floors")
    public Result<Object> floors() {
        List<HotelFloorVo> floors = hotelFloorService.floors();
        return Result.ok().data(floors);
    }

    @Operation(summary = "文件上传接口", description = "处理前端的文件上传请求，会把文件上传到 minio 服务器中，然后返回文件名称")
    @Parameter(name = "fileResource", description = "处理文件上传的对象", in = ParameterIn.QUERY, required = true)
    @PostMapping("/upload")
    public Result<Object> upload(MultipartFile fileResource) {
        // 1. 判断是否有文件上传
        if (fileResource.isEmpty()) throw new BadRequestException("上传的文件不能为空！");

        // 2. 获取文件上传类型
        String mimeType = fileResource.getContentType(); // image/jpeg, image/png, image/gif
        if (!StringUtils.hasLength(mimeType) || !Arrays.asList("image/jpeg", "image/png", "image/gif").contains(mimeType)) {
            throw new BadRequestException("上传图片的格式必须为 jpg, png 和 gif");
        }

        // 3. 获取文件的原始名称
        //String originalFilename = fileResource.getOriginalFilename();

        // 4. 给文件重新命名
        //String fileName = System.currentTimeMillis() + (new Random().nextInt(9000) + 1000) + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 5. 调用 MinioClient 中的方法来实现文件上传
        String fileName = MinioUtil.upload(minioClient, "hotel", fileResource);

        // 6. 返回文件名称给前端
        return Result.ok().data(fileName);
    }





    /**
     * 根据数据库中的封面图名称获取 MinIO 服务器中的封面路径
     * @param coverImg 数据库中的封面图名称
     * @return 返回MinIO 服务器中的封面路径
     */
    private String getRoomCoverImage(String coverImg) {
        return MinioUtil.preview(minioClient, "hotel", coverImg);
    }

    @Operation(summary = "首页楼层数据接口")
    @GetMapping("/floorList")
    public Result<Object> indexFloorAndRoom() {
        // 查询所有楼层信息
        List<HotelFloorVo> floors = hotelFloorService.floors();

        // 转换首页所需要的数据
        List<IndexFloorVo> IndexFloors = floors.stream().map(f -> {
            // 根据楼层编号查询房间
            LambdaQueryWrapper<HotelRoom> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HotelRoom::getFloorId, f.getId());
            IPage<HotelRoom> roomPages = new Page<>(1, 4);
            hotelRoomService.page(roomPages, queryWrapper);

            // 封装某个楼层下的四个房间信息
            List<IndexRoomVo> indexRooms = roomPages.getRecords().stream()
                    .map(r -> IndexRoomVo.builder()
                            .id(r.getId())
                            .roomName(r.getRoomName())
                            .roomNumber(r.getRoomNumber())
                            .memberPrice(r.getMemberPrice())
                            .coverImg(getRoomCoverImage(r.getCoverImg()))
                            .build()).collect(Collectors.toList());

            // 返回最终所需要的数据
            return IndexFloorVo.builder()
                    .id(f.getId())
                    .floorName(f.getName())
                    .roomList(indexRooms)
                    .build();
        }).collect(Collectors.toList());

        return Result.ok().data(IndexFloors);
    }











}
