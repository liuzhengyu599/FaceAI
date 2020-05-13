package com.ayu.domain;

public class Room {
    private String idcard;      //身份证
    private int num;            //房间号
    private int type;           //类型,单人间0双人间1多人间2
    private double price;       //价格
    private int status;         //状态,0和1分别是已预订和空闲

    public Room() {
    }

    public Room(String idcard, int num, int type, double price, int status) {
        this.idcard = idcard;
        this.num = num;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
