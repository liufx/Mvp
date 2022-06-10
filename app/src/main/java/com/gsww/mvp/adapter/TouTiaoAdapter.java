package com.gsww.mvp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gsww.mvp.R;
import com.gsww.mvp.model.TouTiao;

import java.util.List;

/**
 * @package: com.gsww.mvp.adapter
 * @author: liufx
 * @date: 2018/12/14 1:07 PM
 * @description: 简要描述
 */
public class TouTiaoAdapter extends RecyclerView.Adapter<TouTiaoAdapter.ViewHolder> {

    private Context context;
    private List<TouTiao> data;

    public interface ItemOnClickListener {
        void onClick(int pos);
    }

    private ItemOnClickListener itemOnClickListener;

    public ItemOnClickListener getItemOnClickListener() {
        return itemOnClickListener;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public TouTiaoAdapter(Context context, List<TouTiao> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topline, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TouTiao touTiao = data.get(position);
        holder.tvTitle.setText(touTiao.getTitle());
        holder.tvAuthor.setText(touTiao.getAuthorName());
        holder.tvTime.setText(touTiao.getDate());
        Glide.with(context)
                .load(touTiao.getThumbnailPicS())
                .into(holder.ivImg)
                .onLoadFailed(context.getDrawable(R.mipmap.ic_launcher));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickListener != null) {
                    itemOnClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImg;
        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
