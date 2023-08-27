package com.openlab.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.hotel.base.Result;
import com.openlab.hotel.dto.HotelFloorDetailDto;
import com.openlab.hotel.dto.HotelFloorListDto;
import com.openlab.hotel.entity.HotelFloor;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.service.HotelFloorService;
import com.openlab.hotel.util.PageUtil;
import com.openlab.hotel.vo.HotelFloorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/7
 */
@RestController
@RequestMapping("/floor")
public class HotelFloorController {
    @Resource
    private HotelFloorService hotelFloorService;

    @Operation(summary = "获取楼层列表数据接口")
    @Parameters({
            @Parameter(name = "searchValue", in = ParameterIn.QUERY, description = "搜索关键字"),
            @Parameter(name = "pageSize", in = ParameterIn.QUERY, description = "每页显示行数", required = true),
            @Parameter(name = "pageIndex", in = ParameterIn.QUERY, description = "当前页码", required = true)
    })
    @GetMapping
    public Result<Object> getFloorList(@RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
                                       @RequestParam(value = "pageSize", required = true, defaultValue = "10") Long pageSize,
                                       @RequestParam(value = "pageIndex", required = true, defaultValue = "1") Long pageIndex) {
        IPage<HotelFloor> page = new Page<>(pageIndex, pageSize);

        LambdaQueryWrapper<HotelFloor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!searchValue.isEmpty(), HotelFloor::getFloorName, searchValue)
                .or()
                .eq(!searchValue.isEmpty(), HotelFloor::getFloorNo, searchValue);
        hotelFloorService.page(page, queryWrapper);

        PageUtil<HotelFloorListDto> list = new PageUtil<>(
                page.getCurrent(),
                page.getTotal(),
                page.getRecords().stream().map(f -> {
                    return HotelFloorListDto.builder()
                            .id(f.getId())
                            .floorNo(f.getFloorNo())
                            .floorName(f.getFloorName())
                            .createTime(f.getCreateTime())
                            .build();
                }).collect(Collectors.toList()));

        return Result.ok().data(list);
    }

    @Operation(summary = "添加楼层接口")
    @Parameter(name = "hotelFloor", in = ParameterIn.QUERY, description = "楼层数据对象", required = true)
    @PostMapping
    public Result<Object> addFloor(@RequestBody @Validated HotelFloor hotelFloor) {
        LambdaQueryWrapper<HotelFloor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HotelFloor::getFloorName,hotelFloor.getFloorName());
        HotelFloor dbHotelFloor = hotelFloorService.getOne(queryWrapper);
        if (!Objects.isNull(dbHotelFloor)) {
            return Result.fail().message(hotelFloor.getFloorName() + " 已经存在！");
        }
        boolean flag = hotelFloorService.save(hotelFloor);
        if (flag) return Result.ok().message("楼层添加成功！");
        return Result.fail().message("楼层添加失败！");
    }

    @Operation(summary = "查询楼层详情接口")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "楼层编号", required = true)
    @GetMapping("/{id}")
    public Result<Object> getFloor(@PathVariable("id") Long id) {
        HotelFloor hotelFloor = hotelFloorService.getById(id);
        if (Objects.isNull(hotelFloor)) return Result.fail().message("楼层不存在！");
        return Result.ok().data(HotelFloorDetailDto.builder()
                .id(hotelFloor.getId())
                .floorNo(hotelFloor.getFloorNo())
                .floorName(hotelFloor.getFloorName())
                .remarks(hotelFloor.getRemarks())
                .build());
    }

    @Operation(summary = "更新楼层接口")
    @Parameter(name = "hotelFloor", in = ParameterIn.QUERY, description = "楼层数据对象", required = true)
    @PutMapping
    public Result<Object> editFloor(@RequestBody @Validated HotelFloor hotelFloor) {
        boolean flag = hotelFloorService.updateById(hotelFloor);
        if (flag) return Result.ok().message("更新楼层成功！");
        return Result.fail().message("更新楼层失败！");
    }

    @Operation(summary = "删除楼层接口")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "楼层编号", required = true)
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) throw new BadRequestException("楼层编号不能为空！");
        boolean flag = hotelFloorService.removeById(id);
        if (flag) return Result.ok().message("删除楼层成功！");
        return Result.fail().message("删除楼层失败！");
    }

    @Operation(summary = "查询所有楼层接口", description = "查询所有楼层，用于添加房间列表时作为下拉框填充数据使用。")
    @GetMapping("/all")
    public List<HotelFloorVo> floors() {
        List<HotelFloor> list = hotelFloorService.list();
        return list.stream().map(f -> HotelFloorVo.builder().id(f.getId()).name(f.getFloorName()).build()).collect(Collectors.toList());
    }

}
