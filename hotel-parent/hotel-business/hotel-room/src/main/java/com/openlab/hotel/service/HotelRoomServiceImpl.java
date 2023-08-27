package com.openlab.hotel.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.hotel.dto.HotelRoomListDto;
import com.openlab.hotel.entity.HotelRoom;
import com.openlab.hotel.mapper.HotelRoomMapper;
import com.openlab.hotel.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
@Service
public class HotelRoomServiceImpl extends ServiceImpl<HotelRoomMapper, HotelRoom> implements HotelRoomService {
    /**
     * 根据条件查询房间分页数据列表
     *
     * @param searchValue 搜索关键字
     * @param pageSize    每页显示行数
     * @param pageIndex   当前页码
     * @return 返回分页房间列表
     */
    @Override
    public PageUtil<HotelRoomListDto> listByPage(String searchValue, Long pageSize, Long pageIndex) {
        // 1. 对提交的数据进行验证
        if (pageIndex <= 1L) pageIndex = 1L;
        //if (pageSize < 1) throw new BadRequestException("每页显示行数不能小于1！");
        if (pageSize < 1) pageSize = 10L;

        // 2. 定义查询条件包裹器
        LambdaQueryWrapper<HotelRoom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(searchValue), HotelRoom::getRoomName, searchValue)
                .or()
                .eq(StringUtils.hasLength(searchValue), HotelRoom::getRoomNumber, searchValue);

        // 3. 查询总数据
        Long count = baseMapper.selectCount(queryWrapper);

        // 4. 计算总页数
        long pages = count % pageSize == 0L ? count / pageSize : count / pageSize + 1L;

        // 5. 对总页数进行判断
        if (pageIndex > pages) pageIndex = pages;
        if (pageIndex <= 1) pageIndex = 1L;

        // 6. 计算分页数据的起始位置
        long offset = (pageIndex - 1) * pageSize;

        // 7. 查询数据
        List<HotelRoom> roomList = baseMapper.listByPage(searchValue, offset, pageSize);

        // 8. 转换数据
        List<HotelRoomListDto> result = roomList.stream().map(room -> {
            return HotelRoomListDto.builder()
                    .id(room.getId())
                    .roomName(room.getRoomName())
                    .roomNumber(room.getRoomNumber())
                    .bedType(room.getBedType())
                    .broadband(room.getBroadband())
                    .standardPrice(room.getStandardPrice())
                    .memberPrice(room.getMemberPrice())
                    .roomStatus(room.getRoomStatus())
                    .roomTypeName(room.getHotelRoomType().getTypeName())
                    .floorName(room.getHotelFloor().getFloorName())
                    .coverImg(room.getCoverImg())
                    .createTime(room.getCreateTime())
                    .build();
        }).collect(Collectors.toList());

        // 9. 返回结果
        return new PageUtil<>(pageIndex, count, result);
    }
}
