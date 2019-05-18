package com.liang.delivery.Service.Impl;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Service.UserService;
import com.liang.delivery.Tools.Encode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private UserService userService;
    @Test
    @Transactional
    public void FindUserTest() {
        UserEntity user = userService.findOne(1);
        System.out.println(user);
        System.out.println(user.getGroups());
    }

    @Test
    @Transactional
    public void Login(){
        List<UserEntity> user = userService.login("user24",Encode.MD5EncodeUtf8("liang"));
        System.out.println(user);
    }
    /*用户注册*/
    @Test
    public void register(){
        UserEntity user = new UserEntity();
        for(int i=1;i<31;i++){
            user.setPassword(Encode.MD5EncodeUtf8("liang"));
            user.setClassName("外包152");
            user.seteMail("406171296@qq.com");
            user.setNickname("user"+i);
            user.setStuName("stu"+i);
            user.setStuNum(150050220+i);
            user.setStuSex("男");
            userService.register(user);
        }
    }

    /*学生姓名模糊查询*/
    @Test
    @Transactional
    public void getUsersByStuName(){
      //List<UserEntity> users = userService.getUsersByStuName("u1");
        Page<UserEntity> users = userService.getUsersByStuNameAndSort("u1",0,3);
      for(Object user:users){
          System.out.println(user);
      }
    }

    /*用户信息修改接口测试*/
    @Test
    public void alertUserInfoTest(){
        UserEntity user = new UserEntity();
        user.setId(3);
        user.seteMail("406171296@qq.com");
       userService.alertUserInfo(user);
    }
}
