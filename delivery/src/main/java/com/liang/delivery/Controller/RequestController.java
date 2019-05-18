package com.liang.delivery.Controller;

import com.liang.delivery.Entity.GroupsEntity;
import com.liang.delivery.Entity.RequestEntity;
import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.GroupService;
import com.liang.delivery.Service.RequestService;
import com.liang.delivery.Service.UserGroupService;
import com.liang.delivery.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/request")
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    //定义请求列表分页每页显示8条信息
    final static int pageSize = 8;

    @Resource
    private RequestService requestService;
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserGroupService userGroupService;

    /*获取用户等待处理的请求*/
    @RequestMapping("/getUserWaitingHandleRequest")
    @ResponseBody
    @CrossOrigin
    public List<RequestEntity> getUserWaitingHandleRequest(@RequestBody Map map){
        if(map.get("userId")!=null){
            int userId = (int)map.get("userId");
            return requestService.getUserWaitingHandleRequest(userId);
        }
        return null;
    }

/*    *//*获取用户的请求*//*
    @RequestMapping("/getUserRequest")
    @ResponseBody
    @CrossOrigin
    public Map getUserRequest(@RequestBody Map map){
        if(map.get("userId")!=null&&map.get("page")!=null){
            int userId = (int)map.get("userId");
            int page = (int)map.get("page");
            *//*用于存放用户组邀请用户加入用户组的id列容器*//*
            List<Integer> groupIdList = new ArrayList<Integer>();
            *//*获取用户组邀请用户加入组的page和获取用户申请加入用户组的page*//*
            Map responseMap = requestService.getPage("user",userId,page,pageSize);
            *//*获取用户组邀请用户加入用户列的id*//*
            Page<RequestEntity> GroupToUserPage = null;
            if((GroupToUserPage = (Page<RequestEntity>) responseMap.get("GroupToUser"))!=null){
                for(RequestEntity request : GroupToUserPage){
                    groupIdList.add(request.getGroupId());
                }
            }

        }
        return null;
    }*/

    /*根据key获取请求
    * key为user就获取该用户的请求以及请求对象信息的分页
    * key为group就获取该用户组的请求以及请求对象信息的分页*/
    @RequestMapping("/getRequest")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public Map getGroupRequest(@RequestBody Map map){
        if(map.get("id")!=null&&map.get("page")!=null&&map.get("key")!=null){
            int page = (int)map.get("page");
            String key = (String) map.get("key");
            int id;
            if(key.equals("group")){
                id = Integer.parseInt((String)map.get("id"));
            }else{
                id = (int) map.get("id");
            }
            /*用于存放请求对象信息的id列的容器*/
            List<Integer> id_list = new ArrayList<Integer>();
            /*获取用户组邀请用户加入组的page和获取用户申请加入用户组的page*/
            Map responseMap = requestService.getPage(key,id,page,pageSize);
            /*开始分离用户请求还是用户组请求*/
            if(key.equals("group")){
                /*获取用户请求加入用户列的id*/
                Page<RequestEntity> userToGroupPage = (Page<RequestEntity>) responseMap.get("UserToGroup");
                Page<RequestEntity> groupToUserPage = (Page<RequestEntity>) responseMap.get("GroupToUser");
                if(userToGroupPage!=null||groupToUserPage!=null){
                    /*根据用户id把需要用到的用户信息查询出来*/
                    /*用户加入用户组的用户信息*/
                    for(RequestEntity request : userToGroupPage.getContent()){
                        id_list.add(request.getUserId());
                    }
                    /*用户组邀请用户加入的用户信息*/
                    for(RequestEntity request : groupToUserPage.getContent()){
                        id_list.add(request.getUserId());
                    }
                }
                /*接受返回的用户信息列*/
                List<UserEntity> userList = userService.findUsersByIdIn(id_list);
                Map<Integer,UserEntity> userMap = new HashMap();
                /*剔除多对多关系 并转化成Map方便前端调用*/
                for(UserEntity user : userList){
                    user.setGroups(null);
                    userMap.put(user.getId(),user);
                }
                responseMap.put("userInfo",userMap);
            }else if(key.equals("user")){
                /*获取用户组邀请用户加入用户列的id*/
                Page<RequestEntity> GroupToUserPage = null;
                if((GroupToUserPage = (Page<RequestEntity>) responseMap.get("GroupToUser"))!=null){
                    for(RequestEntity request : GroupToUserPage){
                        id_list.add(request.getGroupId());
                    }
                }
                /*接受返回的用户组信息列*/
                List<GroupsEntity> groupList = groupService.findGroupsByIdIn(id_list);
                Map<Integer,GroupsEntity> groupMap = new HashMap();
                /*剔除多对多关系 并转化成Map方便前端调用*/
                for(GroupsEntity group : groupList){
                    /*此处多对多映射被设置为不被JSON序列化 所以不用剔除*/
                    groupMap.put(group.getId(),group);
                }
                responseMap.put("groupInfo",groupMap);
            }
            return responseMap;
        }
        return null;
    }

    /*保存请求*/
    @RequestMapping("/SaveRequest")
    @ResponseBody
    @CrossOrigin
    public String saveRequest(@RequestBody  RequestEntity requestEntity){
        String result = requestService.addRequest(requestEntity);
        String returnKey = null;
        switch (result){
            case "success":
                returnKey = "申请已发送";
                break;
            case "waiting":
                returnKey = "用户组或您已发送过申请，请先确认";
                break;
            case "sendError":
                returnKey = "申请保存失败,请重试！！";
                break;
            default:
                returnKey = "发生不知名错误，请联系管理员！！！";
                break;
        }
        return returnKey;
    }


    /*  处理请求
    `result`
        '标记该请求的结果，
       0代表该请求还未响应，
       1代表用户同意，
       -1代表用户拒绝',*/
    @RequestMapping("/HandleRequest")
    @ResponseBody
    @Transactional
    @CrossOrigin
    public boolean handlRequest(@RequestBody Map map){
        if(map.get("id")==null||map.get("result")==null){
            return false;
        }
        int id = (int)map.get("id");
        int result = (int)map.get("result");

        RequestEntity request = requestService.handleRequest(id,result);
        /*如果处理请求失败返回false*/
        if(request == null){
            return false;
        }
        /*如果请求被同意 则把用户放到用户组里面*/
        if(result==1){
            /*权重默认为0 (普通用户)*/
            UserGroupEntity userGroupEntity = new UserGroupEntity(request.getUserId(),request.getGroupId(),0);
            /*返回是否把用户放到用户组里面，true为已放入，false为失败*/
            boolean res = userGroupService.addGroup(userGroupEntity);
            return res;
        }
        return true;
    }

}
