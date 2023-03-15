package com.example.cms9cc.template.service;

import com.example.cms9cc.net.NetInterface;
import com.example.cms9cc.net.NetService;
import com.example.cms9cc.template.bean.PlayInfoBean;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class PlayService {

    private final NetInterface netInterface;

    public PlayService(NetService netService) {
        netInterface = netService.getRetrofit().create(NetInterface.class);
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
