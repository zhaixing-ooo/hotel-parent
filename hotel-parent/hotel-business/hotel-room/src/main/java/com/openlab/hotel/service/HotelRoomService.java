package com.openlab.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.hotel.dto.HotelRoomListDto;
import com.openlab.hotel.entity.HotelRoom;
import com.openlab.hotel.util.PageUtil;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
public interface HotelRoomService extends IService<HotelRoom> {
    /**
     * 根据条件查询房间分页数据列表
     * @param searchValue 搜索关键字
     * @param pageSize 每页显示行数
     * @param pageIndex 当前页码
     * @return 返回分页房间列表
     */
    PageUtil<HotelRoomListDto> listByPage(String searchValue, Long pageSize, Long pageIndex);
}
