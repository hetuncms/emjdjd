package com.example.cms9cc.admin.basis;

import com.example.cms9cc.admin.bean.BaseAdminBean;
import com.example.cms9cc.admin.bean.BasisBean;
import com.example.cms9cc.admin.repositories.BasisMapping;
import com.example.cms9cc.tools.RestartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class BasisController {

    private final RestartService restartService;

    private final BasisMapping basisMapping;

    @Autowired
    public BasisController(RestartService restartService, BasisMapping basisMapping) {
        this.restartService = restartService;
        this.basisMapping = basisMapping;
    }

    @GetMapping("/getbasis")
    public BaseAdminBean<BasisBean> getBasis() {
        BasisBean basisBean = basisMapping.findAll().get(0);
        return new BaseAdminBean.Builder<BasisBean>().buildSucces(basisBean);
    }

    @PostMapping("/editbasis")
    public BaseAdminBean<BasisBean> editBasis(@RequestBody BasisBean basisBean) {
        BasisBean save = basisMapping.save(basisBean);
        restartService.restartApp();

        return new BaseAdminBean.Builder<BasisBean>().buildSucces(save);
    }
}
