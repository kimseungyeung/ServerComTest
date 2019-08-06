package com.server.servercomtest.Data;

import android.graphics.Bitmap;

import lombok.Data;

@Data
public class UserData {
    String username;
    String password;
    String realname;
    String email;
    String mobile;
    public UserData(String uname,String pw,String rname,String em,String p){
        this.email=em;
        this.password=pw;
        this.realname=rname;
        this.email=em;
        this.password=p;

    }
    public UserData(){

    }
}
