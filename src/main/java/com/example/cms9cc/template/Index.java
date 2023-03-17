package com.example.cms9cc.template;


import com.example.cms9cc.BaseListBean;
import com.example.cms9cc.Config;
import com.example.cms9cc.LiveBean;
import com.example.cms9cc.admin.AdminService;
import com.example.cms9cc.admin.bean.AllAdBean;
import com.example.cms9cc.template.bean.*;
import com.example.cms9cc.template.service.IndexService;
import com.example.cms9cc.tools.DateUtils;
import com.example.cms9cc.tools.jsonparse.PJson;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class Index {
    public static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    public static final int TYPE_HOT = 0;
    public static final int TYPE_BASKETBALL = 2;
    public static final int TYPE_FOOTBALL = 1;
    public static final int TYPE_PREDICT = 3;
    private final AllAdBean allConfig;
    @Autowired
    PJson pjson;
    AdminService adminService;
    IndexService indexService;
    Config config;

    @Autowired
    public Index(AdminService adminService, Config config, IndexService indexService) {
        this.adminService = adminService;
        this.config = config;
        this.indexService = indexService;
        allConfig = adminService.getAllConfig();
    }

    public String to(HttpServletRequest request, String date, Integer page, Integer listType, Model model) {
        return this.to(request, date, page, listType, model, 0);
    }

    public String to(HttpServletRequest request, String date, Integer page, Integer listType, Model model, int showType) {
        BaseListDateBean<List<LiveBean.LiveItem>> liveItemBaseBean;
        liveItemBaseBean = indexService.getDataByDate(listType, page, date,50);

        if (liveItemBaseBean != null) {
            model.addAttribute("list", liveItemBaseBean.getData());
            model.addAttribute("page_count", liveItemBaseBean.getTotalPages() == 1 ? 1 : liveItemBaseBean.getTotalPages() - 1);
            model.addAttribute("date_list", liveItemBaseBean.getGetAllDate());
        }
        String[] split = request.getRequestURI().split("/");
        if (split.length >= 1) {
            boolean matches = Pattern.compile("[0-9]*").matcher(split[split.length - 1]).matches();
            if (matches) {
                model.addAttribute("data_base_url", request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/")));
            } else {
                model.addAttribute("data_base_url", request.getRequestURI());
            }
        } else {
            model.addAttribute("data_base_url", request.getRequestURI());
        }
        model.addAttribute("listtype", listType);
        model.addAttribute("cur_date",date);
        model.addAttribute("cur_page",page);
        model.addAttribute("list_type_url", listType==TYPE_HOT?"":
                listType==TYPE_FOOTBALL?"/football":listType==TYPE_BASKETBALL?"/basketball":"");
        getBaseModel(request, model, showType);
        return "index";
    }


    public Model getBaseModel(HttpServletRequest request, Model model, int showType) {
        model.addAttribute("config", adminService.getAllConfig());
        model.addAttribute("requestUrl", request.getRequestURI());

        model.addAttribute("showType", showType);
        return model;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return to(request, simpleDateFormat.format(new Date()), 0, TYPE_HOT, model);
    }

    @GetMapping("/{date}")
    public String indexByDateAndPage(HttpServletRequest request, @PathVariable(required = false) String date, Model model) {
        String DATE_REGEX = "^([1-9]\\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$";
        if (!Pattern.matches(DATE_REGEX,date)) {
                throw new RuntimeException("404错误:"+request.getRequestURI());
        }
        return to(request, date, 0, TYPE_HOT, model);
    }

    @GetMapping("/{date}/{page}")
    public String indexByDateAndPage(HttpServletRequest request, @PathVariable(required = false) String date, @PathVariable(required = false) Integer page,
                                     Model model) {
        return to(request, date, page, TYPE_HOT, model);
    }

    @GetMapping("/football/{date}/{page}")
    public String football(HttpServletRequest request, @PathVariable String date, @PathVariable Integer page, Model model) {
        return to(request, date, page, TYPE_FOOTBALL, model);
    }

    @GetMapping("/football/{date}")
    public String football(HttpServletRequest request, @PathVariable String date,Model model) {
        return to(request, date, 0, TYPE_FOOTBALL, model);
    }

    @GetMapping("/football")
    public String football(HttpServletRequest request,Model model) {
        return to(request, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 0, TYPE_FOOTBALL, model);
    }

    @GetMapping("/basketball/{date}/{page}")
    public String basketball(HttpServletRequest request, @PathVariable String date, @PathVariable Integer page, Model model) {
        return to(request, date, page, TYPE_BASKETBALL, model);
    }

    @GetMapping("/basketball/{date}")
    public String basketball(HttpServletRequest request, @PathVariable String date, Model model) {
        return to(request, date, 0, TYPE_BASKETBALL, model);
    }
    @GetMapping("/basketball")
    public String basketball(HttpServletRequest request, Model model) {
        return to(request, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 0, TYPE_BASKETBALL, model);
    }
    @GetMapping("/predict")
    public String predict(HttpServletRequest request, Model model, @RequestParam(value = "date", required = false) String date) {
        return toPredict(request, model, date);
    }

    private String toPredict(HttpServletRequest request, Model model, String date) {
        getBaseModel(request, model, 1);
        String today_date = DateUtils.getToday();

        if (date == null) {
            date = today_date;
        }

        model.addAttribute("yesterday", DateUtils.getYesterday());
        model.addAttribute("tomorrow", DateUtils.getTomorrow());
        model.addAttribute("today_date", DateUtils.getToday());
        BaseListBean<PredictionsIndexBean> predict = indexService.getPredict(date);

        if (predict == null) {
            return "index";
        }

        PredictionsIndexBean data = predict.getData();
        List<PredictionsBean> predictionsBeans = data.getPredictionsBeans();
        List<Date> allStartTime = data.getAllStartTime();
        model.addAttribute("list", predictionsBeans);
        model.addAttribute("current_select_date", (new SimpleDateFormat("yyyy-MM-dd")).format(data.getCurrentShowDate()));
        model.addAttribute("all_start_time", allStartTime);
        return "index";
    }


    @GetMapping("/bofang/{id}")
    public String bofang(Model model, @PathVariable("id") Long id) {

        model.addAttribute("config", adminService.getAllConfig());
        PlayInfoBean liveInfo = indexService.getLiveInfo(id);

        MatchLeague footballLeague = liveInfo.getFootballLeague();

        model.addAttribute("footballLeague", footballLeague);
        PlayInfoBean.Result liveInfoBean = liveInfo.getLiveInfoBean();

        LiveBean.LiveItem liveItem = liveInfo.getData();

        String liveTypeText = switch (liveItem.getLiveType()) {
            case 1 -> "足球";
            case 2 -> "篮球";
            default -> "未知";
        };

        List<RateOddsItem> oddsItem = liveInfo.getOddsItem();
        if (oddsItem != null && !oddsItem.isEmpty()) {
            model.addAttribute("rateodds", oddsItem.get(0).getList().get(0));

            final String[] companyName = {"BET365"};
            final String finalCompanyName = "BET365";
            for (RateOddsItem rateOddsItem : oddsItem) {

                List<RateOddsItem.OddsItem> oddsItems = rateOddsItem.getList();
                RateOddsItem.OddsItem oddsrateItems = rateOddsItem.getList().stream().filter(new Predicate<RateOddsItem.OddsItem>() {
                    @Override
                    public boolean test(RateOddsItem.OddsItem oddsItem) {
                        return oddsItem.getCompanyName().startsWith(companyName[0]);
                    }
                }).findFirst().orElseGet(() -> {
                    RateOddsItem.OddsItem oddsItem1 = oddsItems.get(0);
                    companyName[0] = oddsItem1.getCompanyName();
                    return oddsItem1;
                });

                if (rateOddsItem.getType() == 1) {
                    // 欧指
                    model.addAttribute("ouzhi", oddsrateItems);
                } else if (rateOddsItem.getType() == 2) {
                    // 亚洲
                    model.addAttribute("yazhou", oddsrateItems);
                } else if (rateOddsItem.getType() == 3) {
                    // 大小盘
                    model.addAttribute("daxiaopan", oddsrateItems);
                }
            }
        }

        model.addAttribute("item", liveItem);
        model.addAttribute("liveTypeText", liveTypeText);
        // 如果infobean 没有说明没有播放链接，只返回比赛信息就可以
        if (liveInfoBean == null) {
            return "bofang";
        }
        List<PlayInfoBean.LiveStreams> liveStreams = liveInfoBean.getLive_streams();

        List<DPlayerQualityBean> collect = liveInfoBean.getLive_streams().stream().filter(new Predicate<PlayInfoBean.LiveStreams>() {
            @Override
            public boolean test(PlayInfoBean.LiveStreams liveStreams) {
                return liveStreams.getFormat() == 2;
            }
        }).map(new Function<PlayInfoBean.LiveStreams, DPlayerQualityBean>() {
            @Override
            public DPlayerQualityBean apply(PlayInfoBean.LiveStreams liveStreams) {
                DPlayerQualityBean dPlayerQualityBean = new DPlayerQualityBean();
                switch (liveStreams.getType()) {
                    case 1:
                        dPlayerQualityBean.setName("卫星/标清");
                        break;
                    case 2:
                        dPlayerQualityBean.setName("标清中文");
                        break;
                    case 3:
                        dPlayerQualityBean.setName("高清原声");
                        break;
                    case 4:
                        dPlayerQualityBean.setName("高清中文");
                        break;
                }

                switch (liveStreams.getFormat()) {
                    case 2:
                        dPlayerQualityBean.setType("application/x-mpegURL");
                        break;
                    case 3:
                        dPlayerQualityBean.setType("flv");
                }
                dPlayerQualityBean.setUrl(liveStreams.getUrl());
                return dPlayerQualityBean;
            }

        }).collect(Collectors.toList());

        if (collect != null) {
            model.addAttribute("playurl", collect);
            model.addAttribute("playUrls", liveStreams);
        }


        return "bofang";
    }


}
