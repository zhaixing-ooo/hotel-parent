package com.openlab.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFloorVo implements Serializable {
    private Long id;
    private String name;
}
