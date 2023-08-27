package com.openlab.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.hotel.entity.SysRole;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.mapper.SysRoleMapper;
import com.openlab.hotel.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public SysRole roleList(String name) {
        if (StringUtils.hasLength(name)) {
            return baseMapper.roleList(name);
        }
        throw new BadRequestException("登录账号不能为空！");
    }
}
