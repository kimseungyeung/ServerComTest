package com.server.servercomtest.Interface;

import com.server.servercomtest.Data.UserData;

import org.json.JSONObject;

import java.io.IOException;

public interface NetworkService  {
    public String connect(String username,String password);
    public String signup(String token, JSONObject json);
    public void signup(UserData ud);
    public void signin(String username,String password);
    public String gettoken(String id,String password);
    public String authorize(String sessionId) throws IOException ;
    public String token(String sessionId, String code) throws IOException;
}
