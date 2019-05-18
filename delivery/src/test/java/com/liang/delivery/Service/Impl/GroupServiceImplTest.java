package com.liang.delivery.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.UserEntity;
//import com.liang.delivery.Redis.RedisClient;
import com.liang.delivery.Service.GroupService;
import org.apache.tomcat.util.buf.UEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceImplTest {
    @Resource
    private GroupService groupService;
   /* @Resource
    private RedisClient redisClient;*/
    @Test
    public void updateGroupInfo() {
    }

    @Test
    @Transactional
    public void findGroupsByNickName() {
        Page<GroupsEntity> groups = groupService.findGroupsByNickName("%ou%",0,5);
        for(GroupsEntity g:groups.getContent()){
            System.out.println(g);
        }
    }

    @Test
    public void findGroupsById() {
        GroupsEntity groupsEntity = groupService.findGroupsById(4);
        System.out.println(groupsEntity);
       // System.out.println(groupsEntity.getGroupUsers());
    }

    @Test
    public void save() {
        GroupsEntity groupsEntity = new GroupsEntity();
        for(int i=1;i<4;i++){
            groupsEntity.setGroupName("group"+i);
            groupsEntity.setGroupInfo("This is group"+i);
            groupsEntity.setGroupCreater("杨学樑");
            groupService.save(groupsEntity);
        }
    }
    //获取用户组里面的用户并传到schedule任务队列发送邮件
/*    @Test
    public void GroupAndRedisTest(){
        GroupsEntity groupsEntity = groupService.findGroupsById(3);
        List<UserEntity> users = groupsEntity.getGroupUsers();
        //任务队列 用Set去重
        Set<String> mailSet = new HashSet<String>();
        for(UserEntity u:users){
            mailSet.add(u.geteMail());
        }
        //把任务存进Redis
        redisClient.LPUSH(groupsEntity.getGroupName(),mailSet);
        //把该用户组的任务放到Redis受Schedule监控的队列中
        redisClient.LPUSH(RedisClient.MailQueue,groupsEntity.getGroupName());
    }*/
    @Test
    public void delete() {
    }
}
