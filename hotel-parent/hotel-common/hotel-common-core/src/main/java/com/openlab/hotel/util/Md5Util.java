package com.openlab.hotel.util;

import java.security.MessageDigest;

/**
 * @Description Md5 加密工具类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/5
 */
public final class Md5Util {
    public static String Md5(String s){
        // 用来将字节转换成16进制表示的字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获取MD5摘要算法的MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获的密文完成哈希计算，产生128为的长整数
            byte[] md = mdInst.digest();
            // 把密文转成十六进制的字符串形式
            int j = md.length;
            // 每个字节用16进制表示的话，使用两个字符
            char[] str = new char[j * 2];
            // 表示转换结果中对应的字符位置
            int k = 0;
            // 从第一个字节开始，对每一个字节转换成16进制字符的转换
            for (int i = 0; i < j; i++) {
                // 获取第i个字节
                byte byte0 = md[i];
                // 取字节中高4位的数字转换，>>>为逻辑右移，将符号一起右移
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                // 取字节中低4位的数字转换
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(Md5Util.Md5("123456"));
    }
}
