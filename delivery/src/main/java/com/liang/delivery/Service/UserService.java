package com.liang.delivery.Service;

import com.liang.delivery.Entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserEntity findOne(int id);
    List<UserEntity> login(String nickname,String password);
    boolean register(UserEntity user);
    boolean checkUserName(String username);
    List<UserEntity> getUsersByStuName(String stuName);
    Page<UserEntity> getUsersByStuNameAndSort(String stuName,int page,int size);
    UserEntity alertUserInfo(UserEntity userEntity);
    /*根据id查询多个用户*/
    List<UserEntity> findUsersByIdIn(List list);
}
