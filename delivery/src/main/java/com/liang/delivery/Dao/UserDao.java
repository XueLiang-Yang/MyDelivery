package com.liang.delivery.Dao;

import com.liang.delivery.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity,Integer> {
    UserEntity findUserEntityByNicknameEquals(String username);
    List<UserEntity> findUserEntityByNicknameEqualsAndPasswordEquals(String nickname, String password);
    @Query(value = "select * from user where instr(stu_name,?1)>0",nativeQuery = true)
    List<UserEntity> findUsersByStuName(String stuName);
    List<UserEntity> findUserEntitiesByIdIn(List list);
    Page<UserEntity> findAllByStuNameLike(String stuName, Pageable pageable);
}
