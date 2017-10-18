package com.it.mynewfeature;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：chen on 2017/10/17 17:07
 * 邮箱：keshuixiansheng@126.com
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<DataBean> mDataBeanList;
    private Context mContext;

    public RecyclerViewAdapter(List<DataBean> dataBeanList, Context context) {
        mDataBeanList = dataBeanList;
        mContext = context;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataBean dataBean = mDataBeanList.get(position);
        holder.mImageView.setImageResource(dataBean.imageId);
        Glide.with(mContext).load(dataBean.imageId).into(holder.mImageView);
        holder.mTitle.setText(dataBean.title);
    }


    @Override
    public int getItemCount() {
        return mDataBeanList == null ? 0 : mDataBeanList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final TextView mTitle;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTitle = itemView.findViewById(R.id.title);
        }
    }
}
