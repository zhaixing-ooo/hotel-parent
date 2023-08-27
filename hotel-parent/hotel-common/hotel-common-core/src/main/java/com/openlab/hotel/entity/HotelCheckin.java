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
 * @Description 入住信息表
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("hotel_checkin")
public class HotelCheckin extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("order_number")
    private String orderNumber; // 订单号
    @TableField("room_id")
    private Long roomId; // 房间编号
    @TableField("member_name")
    private String memberName; // 会员姓名
    @TableField("id_card")
    private String idCard; // 身份证
    @TableField("member_number")
    private int memberNumber; // 入住人数
    private String birthday; // 出生日期
    private String gender; // 性别
    private String province; // 省份
    private String city; // 城市
    private String address; // 家庭住址
    private String phone; // 手机号
    private int status; // 入住状态：0已入住未退房，1已退房
    @TableField("checkin_date")
    private LocalDate checkinDate; // 入住时间
    @TableField("checkout_date")
    private LocalDate checkoutDate; // 退房时间
    @TableField("amount_money")
    private Integer amountMoney; // 金额
}
