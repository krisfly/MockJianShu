package example.kris.mockjianshu.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.adapter.GridAdapter;

/**
 * Created by fangyu on 2016/4/19.
 */
public class GridLayoutFragment extends Fragment {

    private static final String TAG = "GridLayoutFragment";
    private RecyclerView mRecyclerView;
    private View headerView;
    private View footerView;
    private GridAdapter adapter;
    private int lastVisibleItem;
    private boolean isLoadingMore = false;


    public static GridLayoutFragment newInstance(String name) {
        return new GridLayoutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grid, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        headerView = getActivity().getLayoutInflater().inflate(R.layout.layout_header, null);
        footerView = getActivity().getLayoutInflater().inflate(R.layout.layout_footer, null);

        adapter = new GridAdapter(getActivity(), headerView, footerView);
        List<String> data = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            data.add("" + (char) i);
        }
        adapter.addAll(data);
        //初始化布局管理器
        final GridLayoutManager lm = new GridLayoutManager(this.getActivity(), 2);

        /*
        *设置SpanSizeLookup，它将决定view会横跨多少列。这个方法是为RecyclerView添加Header和Footer的关键。
        *当判断position指向的View为Header或者Footer时候，返回总列数（ lm.getSpanCount()）,即可让其独占一行。
        */
        lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? lm.getSpanCount() : (adapter.isFooter(position) ? lm.getSpanCount() : 1);
            }
        });
        //设置布局管理器
        mRecyclerView.setLayoutManager(lm);
        //绘制item间的分割线，
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
                if (adapter.isHeader(position) || adapter.isFooter(position)) {
                    outRect.set(0, 0, 0, 20);
                } else {
                    outRect.set(20, 20, 20, 20);
                }
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoadingMore) {
                    adapter.showFooter();
                    loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = lm.findLastVisibleItemPosition();
                Log.d(TAG, "lastVisibleItem :" + lastVisibleItem);
            }
        });
        adapter.setOnItemClickListener(new GridAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Toast.makeText(GridLayoutFragment.this.getActivity(), "on item click position :" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMore() {
        isLoadingMore = true;
        Log.d(TAG, "loadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 'A'; i < 'Z'; i++) {
                    data.add("" + (char) i);
                }
                adapter.addAll(data);
                Toast.makeText(GridLayoutFragment.this.getActivity(), "load more finish", Toast.LENGTH_SHORT).show();
                isLoadingMore = false;
            }
        }, 3000);
    }

}
