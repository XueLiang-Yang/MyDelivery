package com.liang.delivery.Controller;

import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.GroupService;
import com.liang.delivery.Service.UserGroupService;
import com.liang.delivery.Tools.FtpClient;
import com.liang.delivery.Tools.page.userPage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/group")
public class GroupController {
    private final static int pageSize = 8;
    @Resource
    private GroupService groupService;
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private FtpClient ftpClient;

    /*根据组id把该组的所有用户分页查询出来*/
    /*此处page从1开始*/
    @RequestMapping("/userList")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public userPage getUserList(@RequestBody Map map){
        if(map.get("id")==null||map.get("page")==null){
            return null;
        }

        int id = Integer.parseInt((String)map.get("id"));
        int page = (int)map.get("page");
        //获取用户组对象
        GroupsEntity group = groupService.findGroupsById(id);
        //根据一对多关系获取所有的组内用户
        List<UserEntity> users = group.getGroupUsers();
        //创建一个容器存储当前页需要的用户
        List<UserEntity> userList = new ArrayList<UserEntity>();
        //获取总页数
        int totalPage = 0;
        if(users.size()%pageSize!=0){
            totalPage = users.size()/pageSize+1;
        }else{
            totalPage = users.size()/pageSize;
        }
        int min = page*pageSize;
        int max = (page+1)*pageSize;
        //防止溢出
        if(max >users.size()){
            max = users.size();
        }else if(page<0){
            page = 0;
        }
        for(int i=min;i<max;i++){
            userList.add(users.get(i));
        }
        return new userPage(userList,totalPage,page);
    }

    /*根据用户组昵称查询用户组*/
    @RequestMapping("/getGroupsByName")
    @ResponseBody
    @CrossOrigin
    public Page<GroupsEntity> getGroupsByName(@RequestBody Map map){
        if(map.get("name")==null||map.get("page")==null){
            return null;
        }
        int page = (int) map.get("page");
        String name =(String) map.get("name");
        name = '%'+name+'%';
        return groupService.findGroupsByNickName(name,page,pageSize);
    }

    /*新建用户组*/
    @RequestMapping("/createNewGroup")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public boolean createNewGroup(@RequestParam(value="file",required = false) MultipartFile file,@RequestParam(value="groupName") String groupName,
                                  @RequestParam(value="groupInfo") String groupInfo,HttpSession session){
      /*  if(map.get("groupName")==null||map.get("groupInfo")==null||map.get("file")==null){
            return false;
        }
        String groupName = (String)map.get("groupName");
        String groupInfo = (String)map.get("groupInfo");
        MultipartFile file = (MultipartFile) map.get("file");*/
        //新建一个Map用于存储文件属性及信息
        Map uploadFile = new HashMap();
        //没有上传图片的话 使用默认的图片作为用户组头像
        if(file == null){
            File defalutPic = new File("src/test/resources/static/img/1.jpg");
            uploadFile.put("File",defalutPic);
        }else{//有上传图片的情况
            uploadFile.put("MultipartFile",file);
        }
        /*获取创建者的学生姓名*/
        UserEntity user = (UserEntity) session.getAttribute("USER_SESSION");
        GroupsEntity group = new GroupsEntity(groupName,groupInfo,user.getStuName());
        GroupsEntity newGroup = groupService.save(group);
        /*默认新建用户组的用户是管理员身份  权重为1*/
        UserGroupEntity userGroupEntity = new UserGroupEntity(user.getId(),newGroup.getId(),1);
        userGroupService.addGroup(userGroupEntity);
        if(newGroup==null){
            return false;
        }
        //把用户组id设为文件名
        String fileName = newGroup.getId()+".jpg";
        //把用户头像放到ftp服务器的headPortrait文档里面
        ftpClient.UploadToFtp(uploadFile,"headPortrait/",fileName);
        return true;
    }

    /*修改用户组信息*/
    @ResponseBody
    @Transactional
    @CrossOrigin
    @RequestMapping("/updataGroupInfo")
    public boolean updateGroupInfo(@RequestParam(name = "groupName") String groupName,@RequestParam(name = "groupInfo")String groupInfo,
                                   @RequestParam(name = "groupId")int groupId,@RequestParam(name = "newHeadPic",required = false) MultipartFile file ){
        GroupsEntity groupsEntity = new GroupsEntity(groupId,groupName,groupInfo);
        /*上传新头像*/
        if(file!=null){
            Map uploadFile = new HashMap();
            uploadFile.put("MultipartFile",file);
            //把用户组id设为文件名
            String fileName = groupId+".jpg";
            //把用户头像放到ftp服务器的headPortrait文档里面
            ftpClient.UploadToFtp(uploadFile,"headPortrait/",fileName);
        }
        GroupsEntity group = groupService.updateGroupInfo(groupsEntity);
        if(group!=null){
            return true;
        }else{
            return false;
        }
    }
}
