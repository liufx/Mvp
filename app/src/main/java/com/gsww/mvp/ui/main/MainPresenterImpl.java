package com.gsww.mvp.ui.main;

import com.gsww.base.BasePresenter;
import com.gsww.http.LoadingObserver;
import com.gsww.http.RequestManager;
import com.gsww.http.RetrofitManager;
import com.gsww.http.exception.ResponseException;
import com.gsww.mvp.apis.YoulingApis;
import com.gsww.mvp.model.TouTiao;

import java.util.List;


public class MainPresenterImpl extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenterImpl(MainContract.View view) {
        super(view);
    }

    @Override
    public void getTouTiaoData(String type) {
        RequestManager.getInstance().execute(this, RetrofitManager.getInstance().create(YoulingApis.class).touTiao(type),
                new LoadingObserver<List<TouTiao>>(context, true, true) {
                    @Override
                    protected void onSuccess(List<TouTiao> data) {

                        view.onTouTiaoSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                        view.onTouTiaoError(e);
                    }
                });
    }
}
