package com.liang.delivery.Dao;

import com.liang.delivery.Entity.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestDao extends JpaRepository<RequestEntity,Integer> {
    List<RequestEntity> findRequestEntitiesByGroupId(int groupId);
    List<RequestEntity> findRequestEntitiesByUserId(int userId);
    Page<RequestEntity> findAllByGroupId(int groupId,Pageable pageable);
    Page<RequestEntity> findAllByGroupIdAndOperation(int groupId,int operation,Pageable pageable);
    Page<RequestEntity> findAllByUserId(int userId, Pageable pageable);
    Page<RequestEntity> findAllByUserIdAndOperation(int userId,int operation, Pageable pageable);
    List<RequestEntity> findRequestEntitiesByUserIdAndOperationAndAndResult(int userId,int operation,int result);
    RequestEntity findRequestEntityByGroupIdAndUserIdAndResult(int groupId,int userId,int result);
}
