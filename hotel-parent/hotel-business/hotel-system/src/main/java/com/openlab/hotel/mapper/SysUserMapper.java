package com.openlab.hotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.hotel.entity.SysUser;
import com.openlab.hotel.vo.UserRoleVo;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Result(property = "name", column = "name")
    @Select("""
    SELECT name,code FROM sys_role WHERE id=#{roleId};
    """)
    UserRoleVo getRoleByUserId(Long roleId);

    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "realName", column = "real_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "userIcon", column = "user_icon"),
            @Result(property = "userRoleVo", column = "role_id",
                    many = @Many(select = "com.openlab.hotel.mapper.SysUserMapper.getRoleByUserId", fetchType = FetchType.LAZY))
    })
    @Select("""
    SELECT su.id id, su.username, su.real_name, email,sex, su.create_time, user_icon, su.password, su.status, su.role_id
    FROM sys_user su
    WHERE su.username=#{username}
    """)
    SysUser login(String username);
}
