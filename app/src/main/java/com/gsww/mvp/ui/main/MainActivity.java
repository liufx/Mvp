package com.gsww.mvp.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gsww.base.activity.BaseMvpActivity;
import com.gsww.http.exception.ResponseException;
import com.gsww.mvp.R;
import com.gsww.mvp.adapter.TouTiaoAdapter;
import com.gsww.mvp.model.TouTiao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresenterImpl> implements MainContract.View {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    List<TouTiao> data;
    TouTiaoAdapter touTiaoAdapter;

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
    }

    @Override
    protected void initView() {
        touTiaoAdapter = new TouTiaoAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(touTiaoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MainActivity.this).resumeRequests();//恢复Glide加载图片
                } else {
                    Glide.with(MainActivity.this).pauseRequests();//禁止Glide加载图片
                }
            }
        });
        touTiaoAdapter.setItemOnClickListener(new TouTiaoAdapter.ItemOnClickListener() {
            @Override
            public void onClick(int pos) {

            }
        });

    }

    @Override
    protected void loadData() {

        presenter.getTouTiaoData("0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onTouTiaoSuccess(List<TouTiao> data) {
        this.data.addAll(data);
        touTiaoAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTouTiaoError(ResponseException e) {

    }


}
