package com.openlab.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HotelRoomApplication8040 {
    public static void main(String[] args) {
        SpringApplication.run(HotelRoomApplication8040.class, args);
    }
}