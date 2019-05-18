package com.liang.delivery.Controller;

import com.alibaba.fastjson.JSON;
import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.UserGroupService;
import com.liang.delivery.Service.UserService;
import com.liang.delivery.Tools.Encode;
import com.liang.delivery.Tools.MyScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final int pageSize = 8;
    @Resource
    private UserService userService;
    @Resource
    private UserGroupService userGroupService;

    /*只用于接口测试*/
    /*RestFul跳转页面*/
    @RequestMapping("/{page}")
    public String toPage(@PathVariable("page") String page){
        return "user/"+page;
    }

    /*用户登录*/
    @RequestMapping(value="/doLogin")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public Map doLogin(@RequestBody Map map, HttpSession session){
        Map responseMap = new HashMap();
        String username = (String)map.get("username");
        /*密码使用MD5进行加密*/
        String password = Encode.MD5EncodeUtf8((String)map.get("password"));
        List<UserEntity> users = userService.login(username,password);
        if(users.size()!=0){
            UserEntity user = users.get(0);
            session.setAttribute("USER_SESSION",user);
            logger.info(((UserEntity)session.getAttribute("USER_SESSION")).toString());
            responseMap.put("USER_SESSION",user);
            //根据用户的id查询该用户拥有管理权限的用户组   权重1为管理员，0为普通组员
            List<UserGroupEntity> list = userGroupService.findUserGroupByUserIdAndWeight(user.getId(),1);
            responseMap.put("USER_WEIGHT",list);
            return responseMap;
        }
        return null;
    }

    /*用户注册*/
    @ResponseBody
    @RequestMapping("/register")
    @CrossOrigin
    public String register(@RequestBody Map map){
        UserEntity user = null;
        logger.info(JSON.toJSONString(map));
        if(map.get("className")==null||map.get("eMail")==null||
                map.get("nickname")==null||map.get("password")==null||
                map.get("stuName")==null||map.get("stuNum")==null||map.get("stuSex")==null){
            return null;
        }
        user = new UserEntity((String) map.get("nickname"),Integer.parseInt((String)map.get("stuNum")), (String) map.get("stuName"), (String) map.get("stuSex"), (String) map.get("className"), (String) map.get("eMail"),(String) map.get("password"));
        if(user!=null&&userService.register(user)){
            return "success";
        }
        return "false";
    }

    /*检测用户账号是否存在*/
    @ResponseBody
    @RequestMapping(value="/checkUserName")
    @CrossOrigin
    public String  checkUserName(String r_user_name){

        Map<String,Boolean> m = new HashMap<String,Boolean>();
        if(userService.checkUserName(r_user_name)){
            m.put("valid",false);
        }else{
            m.put("valid",true);
        }
        return JSON.toJSONString(m);
    }

    /*获取该用户信息*/
    @RequestMapping(value="/userInfo")
    @ResponseBody
    @CrossOrigin
    public UserEntity userInfo(HttpSession session){
        return (UserEntity)session.getAttribute("USER_SESSION");
    }

    /*根据学生名称查询学生*/
    @RequestMapping("/getUsersByName")
    @ResponseBody
    @CrossOrigin
    public Page<UserEntity> getUsersByName(@RequestBody Map map){
        if(map.get("name")== null||map.get("page")==null){
            return null;
        }
        String stuName = (String) map.get("name");
        int page = (int)map.get("page");
        Page<UserEntity> users = userService.getUsersByStuNameAndSort(stuName,page,pageSize);
        /*剔除多对多关联 减少传递的数据量*/
        for(UserEntity user:users.getContent()){
            user.setGroups(null);
        }
        return users;
    }

    /*修改个人信息*/
    @RequestMapping("/alertUserInfo")
    @ResponseBody
    @CrossOrigin
    public UserEntity alertUserInfo(@RequestBody Map map){
        /*  id:this.userId,
        nickname:this.nickname,
        stuNum:this.stu_num,
        stuName:this.stu_name,
        stuSex:this.stu_sex,
        eMail:this.stu_mail,
        className:this.stu_class,*/
        /*int 类型没有空值*/
        int stuNum = 0;
        if(map.get("id")==null){
            return null;
        }else if(map.get("stuNum")!=null){
            stuNum = (int) map.get("stuNum");
        }
        UserEntity user = new UserEntity((int)map.get("id"),(String)map.get("nickname"),stuNum,(String)map.get("stuName"),(String)map.get("stuSex"),(String)map.get("className"),(String)map.get("eMail"),(String)map.get("password"));
        UserEntity result = userService.alertUserInfo(user);
        return result;
    }
}

