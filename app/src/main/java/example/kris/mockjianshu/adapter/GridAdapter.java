package example.kris.mockjianshu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.kris.mockjianshu.R;

/**
 * Created by fangyu on 2016/4/19.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;
    private List<String> data;
    private View header;
    private View footer;
    private Context context;
    private ItemClickListener mItemClickListener;

    public GridAdapter(Context context, View header, View footer) {
        data = new ArrayList<>();
        this.header = header;
        this.footer = footer;
        this.context = context;
    }

    public void addData() {
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add("" + (char) i);
        }
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void addAll(List<String> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (isFooter(position)) {
            return ITEM_VIEW_TYPE_FOOTER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                return new ViewHolder(header);
            case ITEM_VIEW_TYPE_FOOTER:
                return new ViewHolder(footer);
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //如果时Header和Footer的时候直接返回，Header和Footer的操作，可以在Activity中进行
        if (isHeader(position) || isFooter(position)) {
            return;
        }

        //绑定数据时要注意，因为多出Header在前，所以需要－1
        holder.itemView.setText(data.get(position - 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() + 2;
    }

    public void showFooter() {
        if (footer != null) {
            footer.setVisibility(View.VISIBLE);
        }
    }

    public void hideFooter() {
        if (footer != null) {
            footer.setVisibility(View.GONE);
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    public boolean isFooter(int position) {
        return position == (data.size() + 1);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
