package com.openlab.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description 系统角色实体类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0
 * @Date: 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "系统角色表")
@TableName("sys_role")
public class SysRole extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空！")
    private String name;

    @Schema(description = "角色编号")
    @NotBlank(message = "角色编号不能为空！")
    private String code;
}
