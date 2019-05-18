package com.liang.delivery.Service.Impl;

import com.liang.delivery.Dao.GroupDao;
import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupDao groupDao;

    //修改用户组
    @Modifying
    @Transactional
    @Override
    public GroupsEntity updateGroupInfo(GroupsEntity groupsEntity) {
        GroupsEntity group = groupDao.findById(groupsEntity.getId()).get();
        if(groupsEntity.getGroupName()!=null){
            group.setGroupName(groupsEntity.getGroupName());
        }
        if(groupsEntity.getGroupInfo()!=null){
            group.setGroupInfo(groupsEntity.getGroupInfo());
        }
        return group;
    }
    //根据用户组昵称查找用户组
    @Override
    public Page<GroupsEntity> findGroupsByNickName(String nickName, int page, int pageSize) {
        Pageable pageable =  PageRequest.of(page,pageSize, Sort.Direction.ASC,"id");
        return groupDao.findAllByGroupNameLike('%'+nickName+'%',pageable);
    }
    //根据用户组id查找用户组
    @Override
    public GroupsEntity findGroupsById(int id) {
        return groupDao.findById(id).get();
    }

    /*返回list里面的id的用户组*/
    @Override
    public List<GroupsEntity> findGroupsByIdIn(List<Integer> list) {
        return  groupDao.findAllById(list);
    }

    //添加用户组
    @Override
    public GroupsEntity save(GroupsEntity groupsEntity){
        GroupsEntity group = groupDao.saveAndFlush(groupsEntity);
        return group;
    }

    //删除用户组
    public void delete(int id){
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setId(id);
        groupDao.delete(groupsEntity);
    }
}
