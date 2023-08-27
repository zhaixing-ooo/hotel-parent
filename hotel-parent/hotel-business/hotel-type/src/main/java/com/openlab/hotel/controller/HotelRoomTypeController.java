package com.openlab.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.hotel.base.Result;
import com.openlab.hotel.entity.HotelRoomType;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.service.HotelRoomTypeService;
import com.openlab.hotel.util.PageUtil;
import com.openlab.hotel.vo.RoomTypesVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/8
 */
@RestController
@RequestMapping("/roomType")
public class HotelRoomTypeController {
    @Resource
    private HotelRoomTypeService hotelRoomTypeService;


    @Operation(summary = "获取房间类型列表数据")
    @Parameters({
            @Parameter(name = "pageIndex", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页显示记录行数"),
            @Parameter(name = "searchValue", description = "搜索关键字")
    })
    @GetMapping
    public Result<Object> list(@RequestParam("pageIndex") Integer pageIndex,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam(value = "searchValue", defaultValue = "") String searchValue) {
        LambdaQueryWrapper<HotelRoomType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(searchValue), HotelRoomType::getTypeName, searchValue);

        IPage<HotelRoomType> page = new Page<>(pageIndex, pageSize);
        hotelRoomTypeService.page(page, queryWrapper);

        PageUtil<HotelRoomType> pageUtil = new PageUtil<>(page.getCurrent(), page.getTotal(), page.getRecords());

        return Result.ok().data(pageUtil);
    }

    @Operation(summary = "添加房间类型")
    @Parameter(name = "hotelRoomType", description = "房间类型对象")
    @PostMapping
    public Result<Object> add(@RequestBody HotelRoomType hotelRoomType) {
        hotelRoomType.setCreateBy(1L);
        hotelRoomType.setUpdateBy(1L);
        boolean flag = hotelRoomTypeService.save(hotelRoomType);
        if (flag) return Result.ok().message("房间类型添加成功！");
        return Result.fail().message("房间类型添加失败！");
    }

    @Operation(summary = "查询房间类型详情")
    @Parameter(name = "id", description = "房间类型编号")
    @GetMapping("/{id}")
    public Result<Object> getById(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) throw new BadRequestException("请求参数不对!");
        HotelRoomType hotelRoomType = hotelRoomTypeService.getById(id);
        return Result.ok().data(hotelRoomType);
    }

    @PutMapping
    public Result<Object> edit(@RequestBody HotelRoomType hotelRoomType) {
        if (Objects.isNull(hotelRoomType)) throw new BadRequestException("更新对象不能为空!");
        boolean flag = hotelRoomTypeService.updateById(hotelRoomType);
        if (flag) return Result.ok().message("更新房间类型成功!");
        return Result.fail().message("更新房间类型失败!");
    }

    @Operation(summary = "删除房间类型")
    @Parameter(name = "id", description = "房间类型编号")
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) throw new BadRequestException("请求参数不对!");
        boolean flag = hotelRoomTypeService.removeById(id);
        if (flag) return Result.ok().message("删除房间类型成功!");
        return Result.fail().message("删除房间类型失败!");
    }

    @Operation(summary = "远程获取列表接口")
    @GetMapping("/all")
    public List<RoomTypesVo> all() {
        List<HotelRoomType> list = hotelRoomTypeService.list();
        return list.stream().map(t -> {
            return RoomTypesVo.builder().id(t.getId()).name(t.getTypeName()).build();
        }).collect(Collectors.toList());
    }

}
