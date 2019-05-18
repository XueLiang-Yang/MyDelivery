package com.liang.delivery.Controller;

import com.alibaba.fastjson.JSON;
import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.InformationEntity;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Redis.RedisClient;
import com.liang.delivery.Service.GroupService;
import com.liang.delivery.Service.InformationService;
import com.liang.delivery.Tools.FtpClient;
import com.liang.delivery.Tools.page.InfoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/info")
public class InfoHandleController {
    private static final Logger logger = LoggerFactory.getLogger(InfoHandleController.class);

    //总页数
    private final static int pageSize = 8;
    //FTP服务器
    @Resource
    private FtpClient ftpClient;
    //Redis服务器
    @Resource
    private RedisClient redisClient;
    @Resource
    private InformationService informationService;
    @Resource
    private GroupService groupService;

    /*RestFul跳转页面*/
    @RequestMapping("/{page}")
    public String toPage(@PathVariable("page") String page){
        return "info/"+page;
    }

    /*信息发布操作*/
    @RequestMapping("/deliveryNewInfo")
    @ResponseBody
    public String testUpload(@RequestBody InformationEntity info){
        //InformationEntity info = new InformationEntity(infoContent,infoTitle,1,fileName,fileRealName,new Timestamp(new Date().getTime()));

        info.setTime(new Timestamp(new Date().getTime()));
        //把新消息存进数据库
        boolean result = informationService.save(info);

     //   获取该用户组的信息
     /* 由于GroupEntity与UserEntity进行了双向绑定
     * 所以该操作可以把该用户组下面的用户信息获取出来
     */
        GroupsEntity groupsEntity = groupService.findGroupsById(info.getGroupId());
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
        if(result){
            return "success";
        }else {
            return "false";
        }
    }

    /*上传文件*/
    @RequestMapping("/toUpload")
    @ResponseBody
    @CrossOrigin
    public Map<String,String> toUpload(@RequestParam(name = "uploadFile") MultipartFile file,@RequestParam(name = "groupId") int groupId){
        Map<String,String> res = new HashMap<String, String>();
      /*  if(groupId == 0){
            res.put("result","上传失败，请先确认选择好了要发布的用户组");
            return res;
        }*/
        String fileName = file.getOriginalFilename();
        //ftp服务器ftpuser文件夹内的相对路径
        String relativePath = "groupFile/" + groupId + "/";
        //获取文件名后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String relativeFileName = UUID.randomUUID().toString()+suffix;
        //新建一个Map存放文件属性以及文件对象
        Map uploadFile = new HashMap();
        uploadFile.put("MultipartFile",file);
        String result = ftpClient.UploadToFtp(uploadFile,relativePath,relativeFileName);

        res.put("result",result);
        res.put("relateFileName",relativeFileName);
        return res;
    }

    /*根据用户组id获取最新信息
    * 此处的id_list是一个数组 一个存储groupId的数组*/
    @RequestMapping("/getNewInfo")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public InfoPage getNewInfo(@RequestBody Map map){
        if(map.get("page")==null||map.get("id_list")==null){
            return null;
        }
        List<Integer> id_list = JSON.parseArray((String) map.get("id_list"),Integer.class);
        int page = (int) map.get("page");
        Page<InformationEntity> infoPage = informationService.getNewInfo(id_list,page,pageSize);
        List<InformationEntity> infoList = infoPage.getContent();
        //查询数据库把组信息提取出来
        List<GroupsEntity> groups = groupService.findGroupsByIdIn(id_list);
        Map<Integer,String> groupsName = new HashMap<Integer,String>();
        //loop把信息附加到Page里面
        for(GroupsEntity group : groups){
            groupsName.put(group.getId(),group.getGroupName());
        }
        InfoPage returnPage = new InfoPage(infoList,groupsName,page,infoPage.getTotalPages());
        return returnPage;
    }
}
