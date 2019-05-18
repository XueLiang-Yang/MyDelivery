package com.liang.delivery.Service.Impl;

import com.liang.delivery.Dao.RequestDao;
import com.liang.delivery.Entity.RequestEntity;
import com.liang.delivery.Service.RequestService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {
    private final static int UserToGroup = 0;
    private final static int GroupToUser = 1;
    private final static int waitingHandle = 0;
    @Resource
    private RequestDao requestDao;

    /*根据用户id获取请求列表*/
    @Override
    public List<RequestEntity> findEntityByUserId(int userId) {
        return requestDao.findRequestEntitiesByUserId(userId);
    }

    /*根据用户组id获取请求列表*/
    @Override
    public List<RequestEntity> findEntityByGroupId(int groupId) {
        return requestDao.findRequestEntitiesByGroupId(groupId);
    }

    /*分页*/
    @Override
    public Map getPage(String tag, int id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize, Sort.Direction.DESC,"time");
        Map resultMap = new HashMap();
        if(tag.equals("group")){
            /*获取用户请求加入用户组的请求*/
            resultMap.put("UserToGroup",requestDao.findAllByGroupIdAndOperation(id,UserToGroup,pageable));
            /*获取用户组邀请用户加入用户组的请求*/
            resultMap.put("GroupToUser", requestDao.findAllByGroupIdAndOperation(id,GroupToUser,pageable));
            return resultMap;
        }else if(tag.equals("user")){
            /*获取用户请求加入用户组的请求*/
            resultMap.put("UserToGroup",requestDao.findAllByUserIdAndOperation(id,UserToGroup,pageable));
            /*获取用户组邀请用户加入用户组的请求*/
            resultMap.put("GroupToUser", requestDao.findAllByUserIdAndOperation(id,GroupToUser,pageable));
            return resultMap;
        }
        return null;
    }

    /*保存请求*/
    @Override
    public String addRequest(RequestEntity requestEntity) {
        requestEntity.setTime(new Timestamp(new Date().getTime()));
        /*检查是否存在处于等待状态的请求，避免用户重复申请*/
        RequestEntity request = requestDao.findRequestEntityByGroupIdAndUserIdAndResult(requestEntity.getGroupId(),requestEntity.getUserId(),waitingHandle);
        if(request!=null){
            return "waiting";
        }
        RequestEntity req = requestDao.save(requestEntity);
        if(req == null){
            return "saveError";
        }else{
         return "success";
        }
    }

    /*处理请求*/
    @Override
    @Transactional
    @Modifying
    public RequestEntity handleRequest(int id, int result) {
        try{
            RequestEntity request = requestDao.findById(id).get();
            request.setResult(result);
            request.setTime(new Timestamp(new Date().getTime()));
            return request;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*获取用户等待处理的请求*/
    @Override
    public List<RequestEntity> getUserWaitingHandleRequest(int id) {
        return requestDao.findRequestEntitiesByUserIdAndOperationAndAndResult(id,GroupToUser,waitingHandle);
    }
}
