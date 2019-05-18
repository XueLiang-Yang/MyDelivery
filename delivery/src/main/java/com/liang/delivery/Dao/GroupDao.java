package com.liang.delivery.Dao;

import com.liang.delivery.Entity.GroupsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupDao extends JpaRepository<GroupsEntity,Integer> {
    List<GroupsEntity> findGroupsEntitiesByGroupNameLike(String nickName);
    Page<GroupsEntity> findAllByGroupNameLike(String nickName, Pageable pageable);
}
