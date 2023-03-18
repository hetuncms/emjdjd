package com.example.cms9cc.admin;

import com.example.cms9cc.admin.ad.service.TopAdService;
import com.example.cms9cc.admin.bean.*;
import com.example.cms9cc.admin.repositories.*;
import com.example.cms9cc.tools.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final StatisticsRepo statisticsMapping;
    private final BannerAdMapping bannerAdMapping;
    private final BasisMapping basisMapping;
    private final FloatAdMapping floatAdMapping;
    private final CoupletsAdMapping coupletsAdMapping;
    private final JsAdRepo jsAdRepo;
    private final TopAdService topAdService;

    @Autowired
    public AdminService(StatisticsRepo statisticsMapping,
                        BannerAdMapping bannerAdMapping,
                        BasisMapping basisMapping,
                        FloatAdMapping floatAdMapping,
                        TopAdService topAdService, CoupletsAdMapping coupletsAdMapping, JsAdRepo jsAdRepo) {
        this.statisticsMapping = statisticsMapping;
        this.bannerAdMapping = bannerAdMapping;
        this.basisMapping = basisMapping;
        this.floatAdMapping = floatAdMapping;
        this.topAdService = topAdService;
        this.coupletsAdMapping = coupletsAdMapping;
        this.jsAdRepo = jsAdRepo;
    }

    public BasisBean getBasis() {
        return basisMapping.findAll().get(0);
    }

    public JsAdBean getJsAd() {
        return jsAdRepo.findAll().get(0);
    }

    public StatisticsBean getStatistics() {
        return statisticsMapping.findAll().get(0);
    }

    public List<BannerAdBean> getBannerAd() {
        return bannerAdMapping.findAllByStatusTimeAfterOrderBySort(DateUtils.now());
    }

    public CoupletsMapBean getCoupletsAd() {
        List<CoupletsBean> topAdBeans = coupletsAdMapping.findAllByStatusTimeAfter(DateUtils.now());
        CoupletsMapBean coupletsMapBean = new CoupletsMapBean();
        topAdBeans.forEach(coupletsBean -> {
            switch (coupletsBean.getLocation()) {
                case "对联左" -> coupletsMapBean.setLeftAd(coupletsBean);
                case "对联右" -> coupletsMapBean.setRightAd(coupletsBean);
            }
        });
        return coupletsMapBean;
    }

    public FloatBean getFloatAd() {
        List<FloatBean> allByStatusTimeAfter = floatAdMapping.findAllByStatusTimeAfter(DateUtils.now());
        if (allByStatusTimeAfter.isEmpty()) {
            return null;
        }
        return allByStatusTimeAfter.get(0);
    }

    public AllAdBean getAllConfig() {
        return new AllAdBean(getBannerAd(), getBasis(), getCoupletsAd(),
                getFloatAd(), getJsAd(), getStatistics(),topAdService.getTopAdList());
    }
}
