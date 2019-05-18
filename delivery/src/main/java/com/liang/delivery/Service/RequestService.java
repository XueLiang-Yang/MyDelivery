package com.liang.delivery.Service;

import com.liang.delivery.Entity.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RequestService {
    //根据用户id查询请求列表
    List<RequestEntity> findEntityByUserId(int userId);
    //根据用户组查询请求列表
    List<RequestEntity> findEntityByGroupId(int groupId);
    //分页getPage
    Map getPage(String tag, int id, int page, int pageSize);
    //保存请求
    String addRequest(RequestEntity requestEntity);
    //处理申请
    RequestEntity handleRequest(int id,int result);
    //获取用户等待处理的请求
    List<RequestEntity> getUserWaitingHandleRequest(int id);
}
