package com.example.cms9cc.admin.basis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.cms9cc.admin.bean.StatisticsBean;
import com.example.cms9cc.admin.mapper.StatisticsMapping;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class StatisticsController {
    @Resource
    StatisticsMapping statisticsMapping;

    @GetMapping("/getstatistics")
    public StatisticsBean getStatistics() {
        return statisticsMapping.selectOne(new QueryWrapper<>());
    }

    @PostMapping("/editstatistics")
    public Integer editStatistics(@RequestBody StatisticsBean statisticsBean) {

        if (statisticsBean != null && !StringUtils.isEmpty(statisticsBean.getJsUrl())) {
            Long count = statisticsMapping.selectCount(new QueryWrapper<>());
            if (count == 0) {
                return statisticsMapping.insert(statisticsBean);
            } else {
                return statisticsMapping.update(statisticsBean, new UpdateWrapper<>());
            }
        }
        return null;
    }
}
