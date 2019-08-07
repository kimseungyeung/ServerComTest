package com.server.servercomtest.Data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class UserData implements Parcelable {
    String username;
    String password;
    String realname;
    String email;
    String mobile;
    public UserData(String uname,String pw,String rname,String em,String p){
        this.username=uname;
        this.password=pw;
        this.realname=rname;
        this.email=em;
        this.mobile=p;

    }
    public UserData(Parcel p){
        this.username=p.readString();
        this.password=p.readString();
        this.realname=p.readString();
        this.email=p.readString();
        this.mobile=p.readString();
    }
    public UserData(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString("");
        dest.writeString(this.realname);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new UserData[size];
        }
    };

}
