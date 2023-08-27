package com.openlab.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexRoomVo implements Serializable {
    private Long id;
    private String roomName;
    private String roomNumber;
    private Integer memberPrice;
    private String coverImg;
}
