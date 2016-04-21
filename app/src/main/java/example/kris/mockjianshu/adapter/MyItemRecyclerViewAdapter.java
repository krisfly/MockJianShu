package example.kris.mockjianshu.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.view.LoadMoreRecyclerView;

/**
 * Created by fangyu on 2016/4/20.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mValues;
    private boolean mIsStagger;

    public MyItemRecyclerViewAdapter(List<String> items) {
        mValues = items;
    }

    public void switchMode(boolean mIsStagger) {
        this.mIsStagger = mIsStagger;
    }

    public void setData(List<String> datas) {
        mValues = datas;
    }

    public void addDatas(List<String> datas) {
        mValues.addAll(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LoadMoreRecyclerView.TYPE_STAGGER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
            return new StaggerViewHolder(view);
        } else if (viewType == LoadMoreRecyclerView.TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
            return new StaggerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mIsStagger) {
            Log.d("onBindViewHolder", "position is :" + position);
            StaggerViewHolder staggerViewHolder = (StaggerViewHolder) holder;
            staggerViewHolder.iconView.setVisibility(View.VISIBLE);
            staggerViewHolder.mContentView.setText(mValues.get(position));
        } else {
            ViewHolder mHolder = (ViewHolder) holder;
            mHolder.mContentView.setText(mValues.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class StaggerViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ImageView iconView;
        public TextView mContentView;

        public StaggerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            iconView = (ImageView) itemView.findViewById(R.id.iv_program);
            mContentView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView iconView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iconView = (ImageView) view.findViewById(R.id.iv_program);
            mContentView = (TextView) view.findViewById(R.id.tv_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}