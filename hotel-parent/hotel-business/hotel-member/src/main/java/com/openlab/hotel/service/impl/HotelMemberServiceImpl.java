package com.openlab.hotel.service.impl;

//import cn.hutool.db.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openlab.hotel.entity.HotelMember;
import com.openlab.hotel.mapper.HotelMemberMapper;
import com.openlab.hotel.service.HotelMemberService;
import org.springframework.stereotype.Service;

@Service
public class HotelMemberServiceImpl extends ServiceImpl<HotelMemberMapper, HotelMember> implements HotelMemberService {
}
