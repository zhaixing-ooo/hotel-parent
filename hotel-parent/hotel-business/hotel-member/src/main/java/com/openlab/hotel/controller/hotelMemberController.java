package com.openlab.hotel.controller;

import com.openlab.hotel.base.Result;
import com.openlab.hotel.service.HotelMemberService;
import com.openlab.hotel.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
public class hotelMemberController {
    @Resource
    private HotelMemberService hotelMemberService;
    @Operation(summary = "后台获取会员列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(@RequestParam(value = "searchValue", defaultValue = "", required = false) String searchValue,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
                                          @RequestParam(value = "pageIndex", defaultValue = "1") Long pageIndex)
    PageUtil<HotelRoomListDto> list = hotelRoomService.listByPage(searchValue, pageSize, pageIndex);

        list.getContent().forEach(room -> room.setCoverImg(getRoomCoverImage(room.getCoverImg())));

        return Result.ok().data(list);
    }
}
