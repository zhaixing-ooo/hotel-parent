package com.openlab.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.openlab.hotel.base.BaseEntity;
import com.openlab.hotel.vo.UserRoleVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 系统用户实体类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user")
@Schema(description = "系统用户表")
public class SysUser extends BaseEntity {
    @Schema(description = "用户编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "登录用户名")
    @NotBlank(message = "登录名不能为空!")
    private String username;

    @Schema(description = "登录密码")
    //@NotBlank(message = "登录密码不能为空！")
    private String password;

    @Schema(description = "真实姓名")
    @TableField("real_name")
    @NotBlank(message = "真实姓名不能为空！")
    private String realName;

    @Schema(description = "用户性别")
    private String sex;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用户头像")
    @TableField("user_icon")
    private String userIcon;

    @Schema(description = "角色编号")
    @TableField("role_id")
    private Long roleId;

    // 此字段不存在数据库中
    @TableField(exist = false)
    private UserRoleVo userRoleVo;
}
