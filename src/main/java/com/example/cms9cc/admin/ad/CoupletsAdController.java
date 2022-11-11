package com.example.cms9cc.admin.ad.topad;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.cms9cc.admin.bean.CoupletsBean;
import com.example.cms9cc.admin.mapper.CoupletsAdMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/admin")
public class CoupletsAdController {

    private final CoupletsAdMapping coupletsAdMapping;

    @Autowired
    public CoupletsAdController(CoupletsAdMapping coupletsAdMapping) {
        this.coupletsAdMapping = coupletsAdMapping;
    }

    @GetMapping("/getcoupletsad")
    public List<CoupletsBean> getCoupletsAd() {
        List<CoupletsBean> topAdBeans = coupletsAdMapping.selectList(new QueryWrapper<>());
        return topAdBeans;
    }

    @PostMapping("/editcoupletsad")
    public Integer editCoupletsad(@RequestBody CoupletsBean coupletsBean) {

        UpdateWrapper<CoupletsBean> location = new UpdateWrapper<CoupletsBean>()
                .eq("location", coupletsBean.getLocation());
        int update = coupletsAdMapping.update(coupletsBean, location);
        return update;
    }
}
