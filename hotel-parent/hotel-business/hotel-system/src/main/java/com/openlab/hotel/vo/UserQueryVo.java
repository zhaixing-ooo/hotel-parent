package com.openlab.hotel.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/5
 */
@Setter
@Getter
public class UserQueryVo implements Serializable {
    private String searchValue; // 搜索内容
    private int status; // 用户状态： -1，全部； 0，封禁； 1， 正常
    private int pageSize; // 每页显示记录数
    private int pageIndex; // 当前页码
}
