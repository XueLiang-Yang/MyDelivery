package com.liang.delivery.Service.Impl;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.UserGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGroupTest {

    @Resource
    private UserGroupService userGroupService;
/*    @Test
    public void FindUserTest() {
        List<UserEntity> users = userGroupService.findUserEntitiesByGroupId(1);
        for(UserEntity u : users){
            System.out.println(u);
        }
    }*/
    @Test
    public void addGroup(){
       /* for(int i=1;i<21;i++){
            userGroupService.addGroup(2+i,2,0);
        }
        for(int i=21;i<41;i++){
            userGroupService.addGroup(2+i,3,0);
        }
        for(int i=41;i<61;i++){
            userGroupService.addGroup(2+i,4,0);
        }*/
       UserGroupEntity userGroupEntity = new UserGroupEntity(24,4,0);
        userGroupService.addGroup(userGroupEntity);
    }
    @Test
    public void TestFindManager(){
        List<UserGroupEntity> managers =  userGroupService.findManager(2);
        for (UserGroupEntity manager:managers){
            System.out.println(manager);
        }
    }

    @Test
    public void TestQuitGroup(){
        boolean result = userGroupService.quitGroup(3,32);
        System.out.println(result);
    }


}
