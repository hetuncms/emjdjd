package com.example.cms9cc.admin.repositories;


import com.example.cms9cc.admin.bean.BannerAdBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BannerAdMapping extends JpaRepository<BannerAdBean,Integer> {
    <S extends BannerAdBean> List<S> findAllByStatusTimeAfterOrderBySort(Date status);
}
