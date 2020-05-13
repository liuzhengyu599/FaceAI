package com.ayu.domain;

import java.util.UUID;

public class androidUser {
    private String name;
    private String id;
    private String tel;
    private String pass;
    private String sessionid;

    public androidUser() {
    }

    public androidUser(User user) {
        this.id = user.getIdcard();
        this.name = user.getName();
        this.pass = user.getPass();
        this.tel = user.getPhoneNum();
        this.sessionid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
