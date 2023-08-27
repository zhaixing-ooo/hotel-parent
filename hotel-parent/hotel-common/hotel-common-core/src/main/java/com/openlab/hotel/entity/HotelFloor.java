package com.openlab.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.openlab.hotel.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 酒店楼层表
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "酒店楼层表")
@TableName("hotel_floor")
public class HotelFloor extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "floorNo", description = "楼层编号")
    @TableField("floor_no")
    private Integer floorNo;

    @NotBlank(message = "楼层名称不能为空！")
    @Schema(name = "floorName", description = "楼层名称")
    @TableField("floor_name")
    private String floorName;
}
