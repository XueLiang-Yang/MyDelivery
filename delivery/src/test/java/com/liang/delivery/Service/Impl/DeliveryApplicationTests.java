package com.liang.delivery.Service.Impl;

import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Service.GroupService;
import com.liang.delivery.Service.Impl.GroupServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryApplicationTests {

    @Resource
    private GroupService groupService;
    @Test
    public void GroupSaveTest() {
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setGroupCreater("liang");
        groupsEntity.setGroupInfo("这只是个测试");
        groupsEntity.setGroupName("测试用户组");
        groupService.save(groupsEntity);
    }

    @Test
    public void GroupUpdateTest(){
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setId(1);
        groupsEntity.setGroupCreater("xueliang");
        groupsEntity.setGroupInfo("修改测试");
        groupsEntity.setGroupName("测试用户组");
        System.out.println(groupService.updateGroupInfo(groupsEntity));
    }

    @Test
    public void DeleteTest(){
        groupService.delete(2);
    }

    @Test
    public void FindTest(){
        GroupsEntity group = groupService.findGroupsById(1);
        System.out.println(group);
/*        List<GroupsEntity> g1 = groupService.findGroupsByNickName("测试");
        for(GroupsEntity g2 : g1){
            System.out.println(g2);
        }*/

    }

}
