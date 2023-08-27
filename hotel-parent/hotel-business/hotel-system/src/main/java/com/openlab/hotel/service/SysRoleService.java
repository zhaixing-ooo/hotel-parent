package com.openlab.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.openlab.hotel.entity.SysRole;

public interface SysRoleService extends IService<SysRole> {
    SysRole roleList(String name);
}
