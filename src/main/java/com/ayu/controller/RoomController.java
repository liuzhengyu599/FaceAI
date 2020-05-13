package com.ayu.controller;

import com.ayu.domain.Room;
import com.ayu.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomController {
    ObjectMapper objectMapper=new ObjectMapper();
    @RequestMapping(value = "/getRoomList",method = RequestMethod.GET)
    @ResponseBody
    public String getRoomList() throws JsonProcessingException {
        List<Room> roomList = RoomService.list;
        RoomService.freshList();
        return objectMapper.writeValueAsString(roomList);
    }
    @RequestMapping(value = "/getRoomListByIdcard",method = RequestMethod.POST)
    @ResponseBody
    public String getRoomListByIdcard(@RequestBody Map<String, Object> params1) throws JsonProcessingException {
        String idcard = (String) params1.get("idcard");
        RoomService.getRoomListByIdcard(idcard);
        return objectMapper.writeValueAsString( RoomService.myList);
    }

    @RequestMapping(value = "/bookRoom",method = RequestMethod.POST)
    @ResponseBody
    //如果要取类型为 x-www-form-urlencoded的请求参数，那么用@RequestBody即可
    //由于前台发送的是application/json，因为在后端要用@RequestBody接收一个map来取其中的键值对数据
    public String bookRoom(@RequestBody Map<String, Object> params1) throws JsonProcessingException {
        System.out.println(params1.get("idcard"));
        Room room = new Room();
        room.setIdcard((String) params1.get("idcard"));
        room.setStatus((Integer)params1.get("status"));
        room.setNum((Integer)params1.get("num"));
        RoomService.bookRoom(room);
        RoomService.freshList();
        return objectMapper.writeValueAsString(RoomService.list);
    }

    @RequestMapping(value = "/getRoomListByIdcardAndroid")
    @ResponseBody
    public String getRoomListByIdcardAndroid(String id) throws JsonProcessingException {
        RoomService.getRoomListByIdcard(id);
        return objectMapper.writeValueAsString(RoomService.myList);
    }

    @RequestMapping(value = "/bookRoomAndroid")
    @ResponseBody
    public String bookRoomAndroid(String id, String status, String num) throws JsonProcessingException {
        Room room = new Room();
        room.setIdcard(id);
        room.setStatus(Integer.parseInt(status));
        room.setNum(Integer.parseInt(num));
        RoomService.bookRoom(room);
        RoomService.freshList();
        return objectMapper.writeValueAsString(RoomService.list);
    }

}
