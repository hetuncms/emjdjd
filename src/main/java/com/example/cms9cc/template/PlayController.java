package com.example.cms9cc.template;

import com.example.cms9cc.admin.AdminService;
import com.example.cms9cc.template.bean.PlayInfoBean;
import com.example.cms9cc.template.service.PlayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/live")
public class PlayController {

    AdminService adminService;
    PlayService playService;
    @Autowired
    public PlayController(AdminService adminService,PlayService playService) {
        this.adminService = adminService;
        this.playService = playService;
    }

    @GetMapping("/{id}")
    public String playBasketBall(HttpServletRequest request, Model model, @PathVariable("id") Long id){
        model.addAttribute("requestUrl", request.getRequestURI());
        getBaseModel(model);
        PlayInfoBean liveInfo = playService.getLiveInfo(id);
        model.addAttribute("live_info",liveInfo);
        return "bofang";
    }
    public Model getBaseModel(Model model) {
        model.addAttribute("config", adminService.getAllConfig());
        return model;
    }
}
