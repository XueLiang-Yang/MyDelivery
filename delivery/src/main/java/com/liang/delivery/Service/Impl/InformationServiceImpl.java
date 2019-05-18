package com.liang.delivery.Service.Impl;

import com.liang.delivery.Dao.InformationDao;
import com.liang.delivery.Entity.InformationEntity;
import com.liang.delivery.Service.InformationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {
    @Resource
    private InformationDao informationDao;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Modifying
    @Transactional
    public void saveAllCount(List<InformationEntity> infoList) {
        for(InformationEntity info : infoList){
           informationDao.update(info.getId(),info.getEnclosure());
        }
    }
    @Modifying
    @Transactional
    //信息发布
    public void saveInfo(List<InformationEntity> infoList){
        int i = 0;
        for(InformationEntity info : infoList){
            em.persist(info);
            if(i%30==0){
                em.flush();
                em.clear();
            }
            i++;
        }
    }

    /*保存信息*/
    @Override
    @Modifying
    @Transactional
    public boolean save(InformationEntity info) {
        try{
            informationDao.save(info);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public InformationEntity findOne(int id) {
       return informationDao.findById(id).get();
    }

    /*信息分页*/
    @Override
    public Page<InformationEntity> getNewInfo(List<Integer> list,Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"time");
        return informationDao.findAllByGroupIdIn(list,pageable);
    }
}
