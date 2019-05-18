package com.liang.delivery.Dao;

import com.liang.delivery.Entity.InformationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InformationDao extends JpaRepository<InformationEntity,Integer>{
    @Query(value="update InformationEntity set enclosure=?2 where id=?1")
    @Modifying
    @Transactional
    void update(int id,String enclosure);

    Page<InformationEntity> findAllByGroupIdIn(List<Integer> list, Pageable pageable);
}
