package com.openlab.hotel.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.openlab.hotel.entity.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 生成 Token 字符串
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/5
 */
public final class HutoolJWTUtil {
    /** 生成系统用户token */
    public static String createToken(SysUser sysUser) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10); // Token 有效期为 10 分钟
        Map<String,Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 载荷
        payload.put("username", sysUser.getUsername());
        payload.put("userId", sysUser.getId());
        String key = "openlab.com"; // 盐
        return JWTUtil.createToken(payload, key.getBytes());
    }

    /** 解析token */
    public static Long parseToken(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        String userId = jwt.getPayload("userId").toString();
        return Long.parseLong(userId);
    }
}
