package com.liang.delivery.Service;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;

import java.util.List;

/*
* 用户组和用户的中间表*/
public interface UserGroupService {
    //添加信息
     boolean addGroup(UserGroupEntity userGroupEntity);
    //查找带权重的用户
     List<UserGroupEntity> findManager(int groupId);
     //查找用户管理的组
    List<UserGroupEntity> findUserGroupByUserIdAndWeight(int userId,int weight);
    //设置管理员权限
    UserGroupEntity setManager(int groupId,int userId);
    //退出用户组
    boolean quitGroup(int groupId,int userId);
}
