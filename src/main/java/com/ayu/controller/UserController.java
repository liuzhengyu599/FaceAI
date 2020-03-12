package com.ayu.controller;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.ayu.domain.ErrorCode;
import com.ayu.domain.User;
import com.ayu.service.UserService;
import com.ayu.utils.ArcsoftUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.codec.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 注册的方法，从表单中获取数据
     *
     * @param name
     * @param idcard
     * @param phoneNum
     * @param registerFile
     * @param request
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/Register")
    public String register(String name,
                           String idcard,
                           String phoneNum,
                           MultipartFile registerFile,
                           HttpServletRequest request,
                           Model model,
                           String img
    ) throws IOException {
        //先获取要上传的文件目录
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        //创建File对象，一会向该路径下上传文件
        File file = new File(path);
        //判断目录是否存在，如果不存在则创建
        if (file.exists() == false) {
            file.mkdir();
        }
        //创建要上传的图片文件，还不存在
        System.out.println(img);
        File picture=null;
        try{
            if(registerFile.isEmpty()==false){
                System.out.println("文件模式");
                //获取原文件名
                String filename = registerFile.getOriginalFilename();
                //创建要上传的图片文件，还不存在
                picture = new File(file, filename);
                //写入文件
                registerFile.transferTo(picture);
            }else{
                byte[] decode = Base64.decode(base64Process(img));
                String filename=UUID.randomUUID().toString()+".png";
                picture = new File(path,"img.png");
                FileOutputStream fileOutputStream = new FileOutputStream(picture);
                fileOutputStream.write(decode);
                fileOutputStream.close();
            }
        }catch (Exception e){
            return "Nophoto";
        }
        //通过图片来创建面部识别类
        ArcsoftUtils arcsoftUtils = new ArcsoftUtils(picture);
        //获取数据库中的用户数组
        //List<User> userList = UserService.getUserList();
        for (User user : UserService.list) {
            //如果用户相似度大于0.85则认为是同一个人，或者身份证相同
            if (arcsoftUtils.compareWithFeature(user.getFaceFeature()) > 0.85 || user.getIdcard().equals(idcard)) {
                //写入返回的错误信息
                model.addAttribute("msg", ErrorCode.EXIST.getMsg());
                model.addAttribute("name",user.getName());
                //卸载引擎
                arcsoftUtils.close();
                //返回页面
                return "registeFailed";
            }
        }
        //如果执行到这说明没有错，创建新用户
        User user = new User();
        //给用户属性赋值
        user.setIdcard(idcard);
        user.setFaceFeature(arcsoftUtils.getFaceFeature().getFeatureData());
        user.setGender(arcsoftUtils.getGender());
        user.setName(name);
        user.setPhoneNum(phoneNum);
        user.setPicPath(picture.getAbsolutePath());
        user.setBirthday(idcard.substring(6, 10) + "-" + idcard.substring(10, 12) + "-" + idcard.substring(12, 14));
        //把用户添加到数据库
        UserService.addUser(user);
        //卸载引擎
        arcsoftUtils.close();
        //返回注册成功
        model.addAttribute("msg", "注册成功");
        //返回页面
        return "registeSuccess";
    }

    /**
     * 登录的方法，登录的结果会携带信息跳转
     *
     * @param request
     * @param loginFile
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/Login")
    public String testFileUpload(HttpServletRequest request,
                                 MultipartFile loginFile,
                                 Model model
    ) throws Exception {
        //获取文件名并生成文件
        String filename = loginFile.getOriginalFilename();
        File picture = new File(filename);
        //写入图片数据，生成面部识别类
        loginFile.transferTo(picture);
        ArcsoftUtils arcsoftUtils = new ArcsoftUtils(picture);
        //从数据库获取用户
        //List<User> userList = UserService.getUserList();
        //搜寻是否有此用户
        for (User user : UserService.list) {
            //如果有匹配率大于85%的，注入用户信息，返回，关闭引擎
            if (arcsoftUtils.compareWithFeature(user.getFaceFeature()) > 0.85) {
                model.addAttribute("msg", user.toString());
                arcsoftUtils.close();
                //删除临时文件
                picture.delete();
                return "success";
            }
        }
        //删除临时文件，关闭引擎，添加数据返回
        picture.delete();
        arcsoftUtils.close();
        model.addAttribute("msg", "没有此用户");
        return "success";
    }

   /* @RequestMapping("/takePhotoRegister")
    public String takePhotoRegister(String name,
                            String idcard,
                            String phoneNum,
                            String img,
                            HttpServletRequest request,
                            Model model
    ) throws IOException {
        byte[] decode = Base64.decode(base64Process(img));
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        File dir = new File(path);
        //判断目录是否存在，如果不存在则创建
        if (dir.exists() == false) {
            dir.mkdir();
        }
        File file = new File(dir,"img.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        //for (int i = 0; i <decode.length ; i++) {

        fileOutputStream.write(decode);
        if(file==null){
            return "Nophoto";
        }

        ArcsoftUtils arcsoftUtils;
        try{
            arcsoftUtils = new ArcsoftUtils(file);
        }catch (Exception e){
            return "Nophoto";
        }
        for (User user : UserService.list) {
            if(arcsoftUtils.compareWithFeature(user.getFaceFeature())>0.85){
                //写入返回的错误信息
                model.addAttribute("msg", ErrorCode.EXIST.getMsg());
                model.addAttribute("name",user.getName());
                //卸载引擎
                arcsoftUtils.close();
                return "takephotoSucess";
            }
        }
        return "takephotoFailed";
    }*/
    private String base64Process(String base64Str) {
        if (!StringUtils.isEmpty(base64Str)) {
            String photoBase64 = base64Str.substring(0, 30).toLowerCase();
            int indexOf = photoBase64.indexOf("base64,");
            if (indexOf > 0) {
                base64Str = base64Str.substring(indexOf + 7);
            }
            return base64Str;
        } else {
            return "";
        }
    }
}