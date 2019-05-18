package com.liang.delivery.Tools.page;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;

import java.util.List;

public class userPage {
    public List<UserEntity> userList;
    public int TotalPage;
    public int CurrPage;
    public List<String> managers;

    public userPage(List<UserEntity> userList, int totalPage, int currPage) {
        this.userList = userList;
        TotalPage = totalPage;
        CurrPage = currPage;
        /*剔除多对多关系*/
        for(UserEntity user : userList){
            user.setGroups(null);
        }
    }

    public userPage(List<UserEntity> userList, int totalPage, int currPage, List<String> managers) {
        this.userList = userList;
        TotalPage = totalPage;
        CurrPage = currPage;
        this.managers = managers;
    }
}
