package com.liang.delivery.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.liang.delivery.Dao.InformationDao;
import com.liang.delivery.Entity.InformationEntity;
import com.liang.delivery.Service.GroupService;
import com.liang.delivery.Service.InformationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationServiceImplTest {

    @Resource
    private InformationService informationService;
    @Resource
    private InformationDao informationDao;
    @Resource
    private GroupService groupService;
    @Test
    public void saveAllCount() {
        List<InformationEntity> infoList = new ArrayList<InformationEntity>();
        for(int i=0;i<600;i++){
            InformationEntity info = new InformationEntity();
            info.setEnclosure("info:"+i+1);
            info.setId(i+1);
            infoList.add(info);
        }
        informationService.saveAllCount(infoList);
    }

    @Test
    public void saveInfo() {
        List<InformationEntity> infoList = new ArrayList<InformationEntity>();
        for(int i=0;i<600;i++){
            InformationEntity info = new InformationEntity();
            info.setGroupId(1);
            info.setInfoTitle("test");
            info.setInfoBody("test");
            info.setEnclosure("info:"+i);
            info.setId(i+1);
            infoList.add(info);
        }
        informationService.saveInfo(infoList);
    }
    @Test
    public void save(){
        InformationEntity info = new InformationEntity();
        info.setGroupId(1);
        info.setInfoTitle("test");
        info.setInfoBody("test");
        info.setEnclosure("info:"+123);
        info.setId(601);
        info.setTime(new Timestamp(new Date().getTime()));
        informationService.save(info);
    }
    @Test
    public void read(){
        InformationEntity info = informationService.findOne(601);
        System.out.println(info.getTime().toString());
    }
    /*分页测试*/
    @Test
    public void TestPage(){
        List<Integer> id_list = new ArrayList<Integer>();
        id_list.add(2);
        Page<InformationEntity> infoPage = informationService.getNewInfo(id_list,0,8);
       System.out.println(JSON.toJSONString(infoPage));
       /* //创建容器存储这几条信息的id
        List<Integer> list = new ArrayList<Integer>();
        List<InformationEntity> infoList = infoPage.getContent();
        //loop把id存到容器里面
        for(InformationEntity info : infoList){
            list.add(info.getId());
        }
        //查询数据库把组信息提取出来
        List<GroupsEntity> groups = groupService.findGroupsByIdIn(list);
        Map<Integer,String> groupsName = new HashMap<Integer,String>();
        //loop把信息附加到Page里面
        for(GroupsEntity group : groups){
            groupsName.put(group.getId(),group.getGroupName());
        }*/

    }
}