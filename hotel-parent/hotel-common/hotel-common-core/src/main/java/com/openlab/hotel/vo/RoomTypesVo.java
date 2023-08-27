package com.openlab.hotel.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTypesVo implements Serializable {
    private Long id;
    private String name;
}
