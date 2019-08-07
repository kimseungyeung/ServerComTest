package com.server.servercomtest.Interface;

import com.server.servercomtest.Data.UserData;

import java.util.ArrayList;

public interface RepositoryService {

    public UserData getdata();

    public void AddData(UserData sd);

    public void SetData(UserData sdl);
}
