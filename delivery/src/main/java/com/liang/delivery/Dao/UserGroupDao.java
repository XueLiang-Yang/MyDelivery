package com.liang.delivery.Dao;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupDao extends JpaRepository<UserGroupEntity,Integer> {
    @Query(value = "select u from UserEntity u inner join UserGroupEntity ug on u.id=ug.userId and ug.groupId=?1")
    List<UserEntity> findUserByGroupId(int group_id);

    /*查找带权重的用户*/
    @Query(value="select ug from UserGroupEntity ug where ug.groupId=?1 and ug.weight=1")
    List<UserGroupEntity> findUsersHaveWeight(int groupId);

    /*根据用户id查询用户管理的用户组*/
    List<UserGroupEntity> findUserGroupEntitiesByUserIdAndWeight(int userId,int weight);

    /*根据用户组和用户的id查询实体类*/
    UserGroupEntity findUserGroupEntityByGroupIdAndUserId(int groupId,int userId);

    /*根据用户组和用户的id删除一条信息(退出用户组)*/
    int deleteByGroupIdAndUserId(int groupId,int userId);
}
