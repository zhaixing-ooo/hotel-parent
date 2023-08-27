package com.openlab.hotel;

import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/11
 */
public class StringTest {
    @Test
    public void test01() {
        String str = "http://192.168.72.130:9000/hotel/2023-08-11/81337989-5043-4a86-a814-70d55c24662d.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=COV8f8mQXsAjkpw6S3xP%2F20230811%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230811T063824Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9d661450156c27b753c08d2b7f67ce4c2881345b60c2ab05c2b650f808c48c21";

        System.out.println(str.contains("2023-08-11/81337989-5043-4a86-a814-70d55c24661d.jpg"));
    }
}
