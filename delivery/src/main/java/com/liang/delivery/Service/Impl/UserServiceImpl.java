package com.liang.delivery.Service.Impl;

import com.liang.delivery.Dao.UserDao;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Service.UserService;
import com.liang.delivery.Tools.Encode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /*根据用户id查询用户信息
    *   已绑定多对多关系
    *   懒加载模式 只要调用getGroups方法即可获取用户拥有的用户组*/
    @Override
    public UserEntity findOne(int id) {
        return userDao.findById(id).get();
    }

    /*用户登录*/
    @Override
    public List<UserEntity> login(String nickname, String password) {
        return userDao.findUserEntityByNicknameEqualsAndPasswordEquals(nickname,password);
    }

    /*用户注册*/
    @Override
    public boolean register(UserEntity user) {
        UserEntity u = null;
        u = userDao.save(user);
        if(u!=null){
            return true;
        }
        return false;
    }

    /*检测用户账号是否存在*/
    @Override
    public boolean checkUserName(String username) {
        UserEntity u = null;
        u = userDao.findUserEntityByNicknameEquals(username);
        if(u!=null){
            return true;
        }
        return false;
    }

    /*根据学生姓名模拟查询用户*/
    @Override
    public List<UserEntity> getUsersByStuName(String stuName) {
        return userDao.findUsersByStuName(stuName);
    }

    /*根据学生姓名分页查询用户*/
    @Override
    public Page<UserEntity> getUsersByStuNameAndSort(String stuName,int page,int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.ASC,"id");
        stuName = '%'+stuName+'%';
        return userDao.findAllByStuNameLike(stuName,pageable);
    }

    /*修改个人信息*/
    @Override
    @Modifying
    @Transactional
    public UserEntity alertUserInfo(UserEntity userEntity) {
        try{
            UserEntity user = userDao.findById(userEntity.getId()).get();
            /*可修改的只有学生学号、姓名、性别、邮箱、班级、和密码*/
            if(userEntity.getStuName()!=null){
                user.setStuName(userEntity.getStuName());
            }else if(userEntity.getStuNum()!=0){
                user.setStuNum(userEntity.getStuNum());
            }else if(userEntity.getStuSex()!=null){
                user.setStuSex(userEntity.getStuSex());
            }else if(userEntity.geteMail()!=null){
                user.seteMail(userEntity.geteMail());
            }else if(userEntity.getClassName()!=null){
                user.setClassName(userEntity.getClassName());
            }else if(userEntity.getPassword()!=null){
                user.setPassword(Encode.MD5EncodeUtf8(userEntity.getPassword()));
            }
            return user;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*查询list里面id的用户*/
    @Override
    public List<UserEntity> findUsersByIdIn(List list) {
        return userDao.findUserEntitiesByIdIn(list);
    }
}
