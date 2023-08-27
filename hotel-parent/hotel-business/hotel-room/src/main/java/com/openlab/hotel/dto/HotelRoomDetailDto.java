package com.openlab.hotel.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelRoomDetailDto implements Serializable {
    private Long id;
    private String roomName; // 房间名称
    private String roomNumber; // 房间号
    private String bedType; // 床型
    private String broadband; // 宽带
    private Integer standardPrice; // 标准价
    private Integer memberPrice; // 会员价
    private Long roomTypeId; // 房间类型名称
    private Long floorId; // 楼层名称
    private String coverImg; // 房间封面图
    private String remarks; // 备注
}
