package com.openlab.hotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.openlab.hotel.entity.HotelRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Company: 西安欧鹏
 * @Author: 姚臣伟
 * @Version: 1.0.0
 * @Date: 2023/8/10
 */
public interface HotelRoomMapper extends BaseMapper<HotelRoom> {
    /**
     * 根据条件分页查询房间列表数据
     * @param searchValue 搜索关键字
     * @param offset 当前页码
     * @param pageSize 每页显示行数
     * @return 返回询房间列表数据
     */
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "hotelRoomType.typeName", column = "type_name"),
            @Result(property = "hotelFloor.floorName", column = "floor_name")
    })
    @Select("""
    <script>
    SELECT room.id, room.room_name, room.room_number,
    room.create_time, room.broadband, room.cover_img,
    room.bed_type, room.member_price, room.standard_price,
    floor.floor_name, type.type_name
    FROM hotel_room room JOIN hotel_room_type type ON room.room_type_id = type.id
    JOIN hotel_floor floor ON room.floor_id = floor.id
    <if test="searchValue != null and searchValue != ''">
    WHERE room.room_name LIKE concat('%', #{searchValue}, '%') OR room.room_number=#{searchValue}
    </if>
    ORDER BY room.id DESC
    LIMIT #{offset}, #{pageSize}
    </script>
    """)
    List<HotelRoom> listByPage(@Param("searchValue") String searchValue, @Param("offset") Long offset, @Param("pageSize") Long pageSize);
}
