package com.liang.delivery.Controller;

import com.liang.delivery.Entity.UserGroupEntity;
import com.liang.delivery.Service.UserGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userGroup")
public class UserGroupController {
    @Resource
    private UserGroupService userGroupService;

    @RequestMapping("/getManager")
    @ResponseBody
    @CrossOrigin
    public Map<Integer,String> getManager(@RequestBody Map map){
        if(map.get("groupId")==null){
            return null;
        }
        int groupId = Integer.parseInt((String)map.get("groupId"));
        List<UserGroupEntity> managers = userGroupService.findManager(groupId);
        Map<Integer,String> list = new HashMap<Integer,String>();
        for(UserGroupEntity manager:managers){
            list.put(manager.getUserId(),"管理员");
        }
        return list;
    }

    @RequestMapping("/setManager")
    @ResponseBody
    @CrossOrigin
    public boolean setManager(@RequestBody Map map){
        if(map.get("groupId")==null||map.get("userId")==null){
            return false;
        }
        int userId = Integer.parseInt((String) map.get("userId"));
        int groupId = Integer.parseInt((String) map.get("groupId"));
        UserGroupEntity userGroupEntity = userGroupService.setManager(groupId,userId);
        if(userGroupEntity!=null){
            return true;
        }
        return false;
    }

    /*退出用户组*/
    @RequestMapping("/quitGroup")
    @ResponseBody
    @CrossOrigin
    public boolean quitGroup(@RequestBody Map map){
        if(map.get("groupId")==null||map.get("userId")==null){
            return false;
        }
        int userId = (int) map.get("userId");
        int groupId = Integer.parseInt((String) map.get("groupId"));
        boolean result = userGroupService.quitGroup(groupId,userId);
        return result;
    }
}
