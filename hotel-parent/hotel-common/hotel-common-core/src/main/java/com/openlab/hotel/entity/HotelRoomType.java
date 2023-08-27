package com.openlab.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.openlab.hotel.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 酒店房间类型表
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "酒店房间类型表")
@TableName("hotel_room_type")
public class HotelRoomType extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "typeName", description = "类型名称")
    @TableField("type_name")
    //@NotBlank(message = "房间类型名称不能为空")
    private String typeName; // 名称

    @Schema(name = "typeSort", description = "类型排序")
    @TableField("type_sort")
    private int typeSort; // 排序
}
