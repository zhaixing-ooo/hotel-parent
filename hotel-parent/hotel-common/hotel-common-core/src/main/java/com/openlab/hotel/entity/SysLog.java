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

/**
 * @Description 系统日志实体类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_log")
public class SysLog extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String area;
    private String browser;
    private String city;
    @TableField("class_method")
    private String classMethod;
    private String exception;
    @TableField("http_method")
    private String httpMethod;
    private String isp;
    private String params;
    private String province;
    @TableField("remote_addr")
    private String remoteAddr;
    @TableField("request_uri")
    private String requestUri;
    private String response;
    @TableField("session_id")
    private String sessionId;
    private String title;
    private String type;
    @TableField("use_time")
    private Long useTime;
}
