package com.openlab.hotel.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/5
 */
@Setter
@Getter
@Builder
public class UserRoleVo implements Serializable {
    private String name; // 角色名称
    private String code; // 角色代码
}
