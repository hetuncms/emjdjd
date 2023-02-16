package com.example.cms9cc.template.service;

import com.example.cms9cc.BaseBean;
import com.example.cms9cc.BaseListBean;
import com.example.cms9cc.LiveBean;
import com.example.cms9cc.net.NetInterface;
import com.example.cms9cc.net.NetService;
import com.example.cms9cc.template.bean.PlayInfoBean;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class IndexService {

    private final NetInterface netInterface;
    NetService netService;

    public IndexService(NetService netService) {
        this.netService = netService;
        netInterface = netService.getRetrofit().create(NetInterface.class);
    }

    public BaseListBean<List<LiveBean.LiveItem>> index(int liveType, int page) {
        try {
            BaseListBean<List<LiveBean.LiveItem>> body = netInterface.index(liveType, page).execute().body();
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveBean.LiveItem getLiveItem(Long id) {
        try {
            return netInterface.getLiveItem(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlayInfoBean getLiveInfo(Long id) {
        Call<PlayInfoBean> playInfo = netInterface.getPlayInfo(id);
        Response<PlayInfoBean> execute = null;
        try {
            execute = playInfo.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PlayInfoBean body = execute.body();
        return body;
    }
}
