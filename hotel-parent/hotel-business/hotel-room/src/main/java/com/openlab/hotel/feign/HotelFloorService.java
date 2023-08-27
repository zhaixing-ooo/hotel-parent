package com.openlab.hotel.feign;

import com.openlab.hotel.vo.HotelFloorVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/11
 */
@FeignClient(value = "hotel-floor-service", path = "/floor")
public interface HotelFloorService {
    @GetMapping("/all")
    List<HotelFloorVo> floors();
}
