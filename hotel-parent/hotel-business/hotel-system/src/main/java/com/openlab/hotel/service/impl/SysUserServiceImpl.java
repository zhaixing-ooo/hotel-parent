package com.openlab.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.hotel.entity.SysUser;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.mapper.SysUserMapper;
import com.openlab.hotel.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public SysUser login(String username) {
        if (StringUtils.hasLength(username)) {
            return baseMapper.login(username);
        }
        throw new BadRequestException("登录账号不能为空！");
    }
}
