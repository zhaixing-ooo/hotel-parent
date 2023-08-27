package com.openlab.hotel.controller;

import com.openlab.hotel.base.Result;
import com.openlab.hotel.entity.SysUser;
import com.openlab.hotel.mapper.SysUserMapper;
import com.openlab.hotel.service.SysUserService;
import com.openlab.hotel.util.HutoolJWTUtil;
import com.openlab.hotel.util.Md5Util;
import com.openlab.hotel.vo.UserRoleVo;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/5
 */
//@CrossOrigin //TODO 解决跨域问题，如果切换为网关后，此注解要去掉
@RestController
public class LoginController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserMapper sysUserMapper;

    @PostMapping("login")
    public Result<Object> login(@RequestBody SysUser sysUser) {
        SysUser dbSysUser = sysUserService.login(sysUser.getUsername());
        UserRoleVo userRoleVo = sysUserMapper.getRoleByUserId(sysUser.getRoleId());
        if (Objects.isNull(dbSysUser)) {
            return Result.fail().code(HttpStatus.BAD_REQUEST.value()).message("登录失败，用户名错误！");
        } else if (!Objects.equals(Md5Util.Md5(sysUser.getPassword()), dbSysUser.getPassword())) {
            return Result.fail().code(HttpStatus.BAD_REQUEST.value()).message("登录失败，密码错误！");
        } else if (dbSysUser.getStatus() == 0) {
            return Result.fail().code(HttpStatus.BAD_REQUEST.value()).message("登录失败，账号被封禁！");
        } else {
            // 生成 Token 字符串
            String token = HutoolJWTUtil.createToken(sysUser);

            // 把Token放到Redis中 //TODO 生成Token存放到 Redis 中进行缓存

            // 返回登录用户信息
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("username", dbSysUser.getUsername());
            resultMap.put("realName", dbSysUser.getRealName());
            resultMap.put("token", token);
            resultMap.put("email", dbSysUser.getEmail());
            resultMap.put("sex", dbSysUser.getSex());
            resultMap.put("createTime", dbSysUser.getCreateTime());
            resultMap.put("userIcon", dbSysUser.getUserIcon());
//            resultMap.put("name",userRoleVo.getName());
//            resultMap.put("code",userRoleVo.getCode());
            resultMap.put("userRoleVo",userRoleVo);
//            resultMap.put("roleCode", dbSysUser.getUserRoleVo().stream().map(UserRoleVo::getCode).collect(Collectors.toList()));
//            resultMap.put("roleName", dbSysUser.getUserRoleVo().stream().map(UserRoleVo::getName).collect(Collectors.toList()));
            System.out.println(resultMap.get("userRoleVo"));
            return Result.ok().message("登录成功").data(resultMap);
        }
    }
}
