package com.openlab.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.openlab.hotel.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Description 酒店预订信息表
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("hotel_reserve")
public class HotelReserve extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("order_number")
    private String orderNumber; // 订单号
    @TableField("room_id")
    private Long roomId; // 房间号
    private int status; // 订单状态（0已预订待付款，1已付款待入住，2已入住待退房，3已退订，4已完成）
    @TableField("checkin_date")
    private LocalDate checkinDate; // 入住时间
    @TableField("reserve_days")
    private Long reserveDays; // 预订天数
    @TableField("checkout_date")
    private LocalDate checkoutDate; // 退房时间
    @TableField("amount_money")
    private Integer amountMoney; // 预订金额
}
