package com.example.cms9cc.admin.repositories;


import com.example.cms9cc.admin.bean.FloatBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FloatAdMapping extends JpaRepository<FloatBean,Integer> {

    <S extends FloatBean> List<S> findAllByStatusTimeAfter(Date status);
}
