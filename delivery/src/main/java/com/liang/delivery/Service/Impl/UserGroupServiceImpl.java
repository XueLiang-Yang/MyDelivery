package com.liang.delivery.Service.Impl;

import com.liang.delivery.Dao.UserGroupDao;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.UserGroupService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final static int MANAGER_WEIGHT = 1;
    @Resource
    private UserGroupDao userGroupDao;

    @Override
    @Modifying
    @Transactional
    public boolean addGroup(UserGroupEntity userGroupEntity) {
        UserGroupEntity temp = null;
        temp = userGroupDao.save(userGroupEntity);
        if(temp == null){
            return false;
        }else{
            return true;
        }
    }

    /*查找带权重的用户*/
    @Override
    public List<UserGroupEntity> findManager(int groupId) {
        return userGroupDao.findUsersHaveWeight(groupId);
    }

    /*查找用户带的组*/
    @Override
    public List<UserGroupEntity> findUserGroupByUserIdAndWeight(int userId, int weight) {
        return userGroupDao.findUserGroupEntitiesByUserIdAndWeight(userId,weight);
    }

    /*设置管理员权限*/
    @Override
    @Modifying
    @Transactional
    public UserGroupEntity setManager(int groupId, int userId) {
        UserGroupEntity userGroupEntity = userGroupDao.findUserGroupEntityByGroupIdAndUserId(groupId,userId);
        userGroupEntity.setWeight(MANAGER_WEIGHT);
        return userGroupEntity;
    }

    /*退出用户组*/
    @Override
    @Modifying
    @Transactional
    public boolean quitGroup(int groupId, int userId) {
        int result = userGroupDao.deleteByGroupIdAndUserId(groupId,userId);
        if(result ==0){
            return false;
        }
        return true;
    }


}
