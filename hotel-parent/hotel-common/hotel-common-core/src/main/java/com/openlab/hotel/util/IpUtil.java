package com.openlab.hotel.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @Description 获取IP地址的工具类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
public class IpUtil {
    /**
     * 获取客户端IP
     * @param request 请求对象
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (Objects.isNull(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Objects.isNull(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (Objects.isNull(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Objects.isNull(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (Objects.isNull(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (Objects.nonNull(ip) && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return !StringUtils.hasLength(checkString) || "unknown".equalsIgnoreCase(checkString);
    }
}
