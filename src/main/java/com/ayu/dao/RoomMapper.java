package com.ayu.dao;

import com.ayu.domain.Room;

import java.util.List;

public interface RoomMapper {
    List<Room> getRoomList();
    void changeStatus(Room room);
    List<Room> getRoomListByIdcard(String idcard);
}
