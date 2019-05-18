package com.liang.delivery.Service;

import com.liang.delivery.Entity.GroupsEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {

    //新建用户组
    GroupsEntity save(GroupsEntity groupsEntity);
    //删除用户组
    void delete(int id);
    //修改组信息
    GroupsEntity updateGroupInfo(GroupsEntity groupsEntity);
    //根据用户组昵称查找用户组
    Page<GroupsEntity> findGroupsByNickName(String nickName, int page, int pageSize);
    //根据用户组的id查找用户组
    GroupsEntity findGroupsById(int id);
    //根据多个用户组的id查询用户组
    List<GroupsEntity> findGroupsByIdIn(List<Integer> list);
}
