package com.openlab.hotel.feign;

import com.openlab.hotel.vo.RoomTypesVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description 远程调用 hotel-type 模块下的
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
@FeignClient(value = "hotel-type-service", path = "/roomType")
public interface HotelRoomTypeService {
    @GetMapping("/all")
    List<RoomTypesVo> getTypeList();
}
