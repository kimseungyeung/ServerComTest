package com.server.servercomtest.Data;

import android.graphics.Bitmap;

import lombok.Data;

@Data
public class UserData {
    Bitmap profileimage;
    String nickname;
    String email;
    String phonenum;
    public UserData(Bitmap i, String em, String nick,String phone){
        this.profileimage=i;
        this.email=em;
        this.nickname=nick;
        this.phonenum=phone;

    }
    public UserData(){

    }
}
