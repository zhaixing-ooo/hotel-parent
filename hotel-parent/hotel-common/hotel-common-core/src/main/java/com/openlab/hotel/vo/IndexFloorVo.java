package com.openlab.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndexFloorVo implements Serializable {
    private Long id;
    private String floorName;
    private List<IndexRoomVo> roomList;
}
