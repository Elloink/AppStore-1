package com.idreamsky.appstore.data;

import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.BaseBean;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.data.http.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Callback;

/**
 * Created by zhaojiuzhou on 2017/7/26.
 */

public class RecommendModel {

    private ApiService apiService;

    public RecommendModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseBean<PageBean<AppInfo>>> getApps(){

        JSONObject object = new JSONObject();
        try {
            object.put("page",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = object.toString();
        return apiService.getAppInfo(params);
    }
}
