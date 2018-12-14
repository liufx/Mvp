package com.gsww.mvp.apis;


import com.gsww.base.BaseResponse;
import com.gsww.mvp.model.TouTiao;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoulingApis {
    String BASE_URL = "http://tt.ylapi.cn/";

    @GET("toutiao/search.u?uid=11021&appkey=f67de0ad23612c5bdf425b6620f303cb")
    Observable<BaseResponse<List<TouTiao>>> touTiao(@Query("type") String type);

}
