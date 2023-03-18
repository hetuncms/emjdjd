package com.example.cms9cc.admin.repositories;


import com.example.cms9cc.admin.bean.CoupletsBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CoupletsAdMapping extends JpaRepository<CoupletsBean,Integer> {

    <S extends CoupletsBean> List<S> findAllByStatusTimeAfter(Date status);
}
