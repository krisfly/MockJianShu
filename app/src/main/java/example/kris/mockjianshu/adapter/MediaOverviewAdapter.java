/*
 * Copyright (c) 2016  funshon. All rights reserved.
 */

package example.kris.mockjianshu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.kris.mockjianshu.R;

/**
 * @Author kris
 * @Time 2016/4/1.
 * @Description
 */
public class MediaOverviewAdapter extends RecyclerView.Adapter<MediaOverviewAdapter.MyViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<String> mDatas;

    public MediaOverviewAdapter(Context context) {
        this.context = context;
        initData();
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    public void addData() {
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_program, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProgram;
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProgram = (ImageView) itemView.findViewById(R.id.iv_program);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
