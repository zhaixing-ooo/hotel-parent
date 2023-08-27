package com.openlab.hotel.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description 用于后端向前端传输数据
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/8
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelFloorListDto implements Serializable {
    private Long id;
    private Integer floorNo;
    private String floorName;
    private LocalDateTime createTime;
}
