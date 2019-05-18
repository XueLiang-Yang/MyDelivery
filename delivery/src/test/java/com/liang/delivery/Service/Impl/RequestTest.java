package com.liang.delivery.Service.Impl;

import com.liang.delivery.Entity.RequestEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.RequestService;
import com.liang.delivery.Service.UserGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestTest {

    @Resource
    private RequestService requestService;
    @Resource
    private UserGroupService userGroupService;

    /*保存请求测试*/
    @Test
    public void addRequest(){
        RequestEntity req = new RequestEntity();
        for(int i=0;i<3;i++){
            req.setUserId(26);
            req.setGroupId(i+2);
            req.setGroupName("group"+i+1);
            req.setOperation(1);
            req.setResult(0);
            req.setTime(new Timestamp(new Date().getTime()));
            String result = requestService.addRequest(req);
            System.out.println(result);
        }
    }

    /*分页测试*/
    @Test
    public void PageTest(){
        Map page = requestService.getPage("user",26,0,3);
        System.out.println(page.get("UserToGroup").toString());
    }

    /*处理请求接口测试*/
    @Test
    public void handleRequestTest(){
        System.out.println(requestService.handleRequest(5,-1));
    }

    /*模拟整个请求过程1*/
    @Test
    public void PreHandleTest(){
        System.out.println("开始发送请求---------------------------------");
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUserId(24);
        requestEntity.setGroupId(4);
        requestEntity.setGroupName("group3");
        requestEntity.setResult(0);
        requestEntity.setOperation(0);
        requestEntity.setTime(new Timestamp(new Date().getTime()));
        requestService.addRequest(requestEntity);
    }
    /*模拟整个请求过程2
    * 结果会被回滚*/
    @Test
    @Transactional
    public void NextHandleTest(){
        System.out.println("模拟同意后的操作-------------------------");
        boolean res = false;
        int result = 1;
        UserGroupEntity userGroupEntity = new UserGroupEntity(24,4,0);
        System.out.println(userGroupEntity);
        //RequestEntity request = requestService.handleRequest(6,1);
        //如果请求被同意 则把用户放到用户组里面

        if(result==1){
          //  权重默认为0 (普通用户)

            res = userGroupService.addGroup(userGroupEntity);
        }
    }
}
