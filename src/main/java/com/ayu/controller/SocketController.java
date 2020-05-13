package com.ayu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Controller
@RequestMapping("/Socket")
public class SocketController {
    ObjectMapper objectMapper = new ObjectMapper();
    ServerSocket server = null;
    // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
    Socket socket = null;
    private static final String[] command = {
            "Get_TempAndHumi", "Get_Door_Status", "Get_Light_Intensity", "Get_Rain_Intensity",
            "Set_Door_Open", "Set_Door_Close", "Set_Light_Open", "Set_Light_Close",
            "Set_Heater_Open", "Set_Heater_Close"
    };

    @RequestMapping("/Init")
    @ResponseBody
    public String InitSocket() throws IOException {
        // 为了简单起见，所有的异常信息都往外抛
        int port = 12138;
        // 定义一个ServerSocket监听在端口12138上
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            socket = server.accept();
            // 每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Task(socket)).start();
        }
        // return objectMapper.writeValueAsString("刷新等待设备");
    }

    @ResponseBody
    @RequestMapping(value = "/getTemp", method = RequestMethod.GET)
    public String getTemp() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[0].getBytes());
        // 3.使用Socket对象中的方法getInputStream()获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        // 4.使用网络字节输入流InputStream对象中的方法read,读取客户端发送的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        return objectMapper.writeValueAsString(new String(bytes, 0, len, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/isConnected", method = RequestMethod.GET)
    public String isConnected() throws JsonProcessingException {
        try {
            socket.sendUrgentData(0xFF);
            return objectMapper.writeValueAsString("连接状态");
        } catch (Exception e) {
            return objectMapper.writeValueAsString("断开状态");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/getDoorStatus", method = RequestMethod.GET)
    public String getDoorStatus() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[1].getBytes());
        // 3.使用Socket对象中的方法getInputStream()获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        // 4.使用网络字节输入流InputStream对象中的方法read,读取客户端发送的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        return objectMapper.writeValueAsString(new String(bytes, 0, len, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/getLightStatus", method = RequestMethod.GET)
    public String getLightStatus() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[2].getBytes());
        // 3.使用Socket对象中的方法getInputStream()获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        // 4.使用网络字节输入流InputStream对象中的方法read,读取客户端发送的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        return objectMapper.writeValueAsString(new String(bytes, 0, len, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/getRainStatus", method = RequestMethod.GET)
    public String getRainStatus() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[3].getBytes());
        // 3.使用Socket对象中的方法getInputStream()获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        // 4.使用网络字节输入流InputStream对象中的方法read,读取客户端发送的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        return objectMapper.writeValueAsString(new String(bytes, 0, len, "utf-8"));
    }

    @ResponseBody
    @RequestMapping(value = "/closeDoor", method = RequestMethod.GET)
    public String closeDoor() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[5].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @ResponseBody
    @RequestMapping(value = "/openDoor", method = RequestMethod.GET)
    public String openDoor() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[4].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @ResponseBody
    @RequestMapping(value = "/closeLight", method = RequestMethod.GET)
    public String closeLight() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[7].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @ResponseBody
    @RequestMapping(value = "/openLight", method = RequestMethod.GET)
    public String openLight() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[6].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @ResponseBody
    @RequestMapping(value = "/closeHeater", method = RequestMethod.GET)
    public String closeHeater() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[9].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @ResponseBody
    @RequestMapping(value = "/openHeater", method = RequestMethod.GET)
    public String openHeater() throws IOException {
        socket.sendUrgentData(0xFF);
        // 5.使用Socket对象中的方法getOutputStream()获取网络字节输出流OutputStream对象
        OutputStream os = socket.getOutputStream();
        // 6.使用网络字节输出流OutputStream对象中的方法write,给客户端回写数据
        os.write(command[8].getBytes());
        return objectMapper.writeValueAsString("指令发送成功，但是不代表执行成功");
    }

    @RequestMapping("/control")
    public String goControl() {
        return "DEVICE";
    }

    static class Task implements Runnable {
        private Scanner sc = new Scanner(System.in);
        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                System.out.println("已连接");
                handleSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 跟客户端Socket进行通信
         *
         * @throws Exception
         */
        private void handleSocket() throws Exception {
        }
    }
}
