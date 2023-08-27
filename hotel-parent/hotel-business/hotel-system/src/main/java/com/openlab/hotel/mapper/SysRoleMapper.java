package com.openlab.hotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.hotel.entity.SysRole;
import org.apache.ibatis.annotations.Select;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("""
    SELECT id,name,code,create_time
    FROM sys_role 
    WHERE name like #{name}
    """)
    SysRole roleList(String name);
}
