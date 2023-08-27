package com.openlab.hotel.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @Description 存储分页数据的工具类
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/4
 */
@Getter
@AllArgsConstructor
public class PageUtil<T> {
    private Long pageIndex;
    private Long total;
    private List<T> content;
    private PageUtil(){}
}
