package com.server.servercomtest.Interface;

import com.server.servercomtest.Data.UserData;

public interface Network {

    public void signup(UserData ud);
    public void signin(UserData ud);
    public String gettoken(String id,String password);
}
