package com.ayu.domain;

public class User {
    private String idcard;          //身份证
    private String name;            //姓名
    private Integer gender;         //性别
    private String picPath;         //图片路径
    private String phoneNum;        //电话号码
    private String birthday;        //生日
    private byte[] faceFeature;     //脸部信息

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }

    @Override
    public String toString() {
        return "用户信息{" +"\n"+
                "姓名:" + name + "\n" +
                "性别=" + ((gender==0)?"男":"nv") +"\n"+
                "电话号码：" + ((phoneNum!=null)?phoneNum:"空") + "\n" +
                "生日:"   +birthday +
                '}';
    }
}
