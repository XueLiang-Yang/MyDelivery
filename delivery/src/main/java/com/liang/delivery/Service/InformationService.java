package com.liang.delivery.Service;

import com.liang.delivery.Entity.InformationEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InformationService {
    void saveAllCount(List<InformationEntity> infoList);
    void saveInfo(List<InformationEntity> infoList);
    boolean save(InformationEntity info);
    InformationEntity findOne(int id);
    Page<InformationEntity> getNewInfo(List<Integer> list,Integer page,Integer size);

}
