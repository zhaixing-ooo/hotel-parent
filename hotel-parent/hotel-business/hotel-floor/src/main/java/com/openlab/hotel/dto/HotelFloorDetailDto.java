package com.openlab.hotel.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/8
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFloorDetailDto implements Serializable {
    private Long id;
    private Integer floorNo;
    private String floorName;
    private String remarks;
}
