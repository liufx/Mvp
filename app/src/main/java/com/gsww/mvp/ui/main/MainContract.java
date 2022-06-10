package com.gsww.mvp.ui.main;

import com.gsww.base.BaseView;
import com.gsww.http.exception.ResponseException;
import com.gsww.mvp.model.TouTiao;

import java.util.List;

/**
 * @package: com.gsww.mvp.ui.main
 * @author: liufx
 * @date: 2018/12/14 8:54 AM
 * @description: 简要描述
 */
public interface MainContract {

    interface View extends BaseView {

        void onTouTiaoSuccess(List<TouTiao> data);

        void onTouTiaoError(ResponseException e);

    }

    interface Presenter {
        void getTouTiaoData(String type);

    }
}
