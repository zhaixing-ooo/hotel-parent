package com.openlab.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.hotel.entity.SysUser;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 实现登录
     * @param username 登录账号
     * @return 返回登录成功后的用户信息
     */
    SysUser login(String username);
}
