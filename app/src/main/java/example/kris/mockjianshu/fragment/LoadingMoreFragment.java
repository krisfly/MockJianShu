package example.kris.mockjianshu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.adapter.MyItemRecyclerViewAdapter;
import example.kris.mockjianshu.view.LoadMoreRecyclerView;

/**
 * Created by fangyu on 2016/4/19.
 */
public class LoadingMoreFragment extends Fragment {

    private static final int COUNT = 25;
    private static final int TOTALPAGE = 1;

    private static final String TAG = "LoadingMoreFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";

    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 0;
    private int mColumnCount = 1;

    public static LoadingMoreFragment newInstance(int type) {
        LoadingMoreFragment fragment = new LoadingMoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mColumnCount = 2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_more, null);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
//        view.findViewById(R.id.mode_switch_btn).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (1 == mColumnCount) {
//                    Log.d(TAG, "GridLayoutManager");
//                    mColumnCount = 2;
//                    ((TextView) v).setText(R.string.list_mode_stagger);
//                    myItemRecyclerViewAdapter.switchMode(true);
//                    final GridLayoutManager lm = new GridLayoutManager(getActivity(), 2);
//                    lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                        @Override
//                        public int getSpanSize(int position) {
//                            Log.d(TAG, "span size position:" + position);
//                            return myItemRecyclerViewAdapter.getItemViewType(position) == LoadMoreRecyclerView.TYPE_FOOTER ? lm.getSpanCount() : 1;
//                        }
//                    });
//                    recyclerView.switchLayoutManager(lm);
////                    recyclerView.switchLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
//                } else {
//                    Log.d(TAG, "LinearLayoutManager");
//                    mColumnCount = 1;
//                    ((TextView) v).setText(R.string.list_mode_list);
//                    myItemRecyclerViewAdapter.switchMode(false);
//                    recyclerView.switchLayoutManager(new LinearLayoutManager(getActivity()));
//                }
//            }
//        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                page = 0;
                myItemRecyclerViewAdapter.setData(getData(page));
                recyclerView.setAutoLoadMoreEnable(hasMore(page));
                myItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
//        if (1 >= mColumnCount) {
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        } else
        {
            final GridLayoutManager lm = new GridLayoutManager(getActivity(), 2);

            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    Log.d(TAG, "span size position:" + position);
                    if (position == myItemRecyclerViewAdapter.getItemCount()) {
                        return lm.getSpanCount();
                    } else {
                        return 1;
                    }
//                    return myItemRecyclerViewAdapter.getItemViewType(position) == LoadMoreRecyclerView.TYPE_FOOTER ? lm.getSpanCount() : 1;
                }
            });
            recyclerView.setLayoutManager(lm);
        }

//        else {
//            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL);
//            recyclerView.setLayoutManager(manager);
//        }
        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(getData(page));
        myItemRecyclerViewAdapter.switchMode(true);
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
        recyclerView.setAutoLoadMoreEnable(true);
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        myItemRecyclerViewAdapter.addDatas(getData(++page));
                        recyclerView.notifyMoreFinish(hasMore(page));
                    }
                }, 1000);
            }
        });
        myItemRecyclerViewAdapter.notifyDataSetChanged();
        return view;
    }

    public static boolean hasMore(int page) {
        return page < TOTALPAGE;
    }

    public static List<String> getData(int page) {
        int start = page * COUNT;
        int end = TOTALPAGE == page ? start + COUNT : start + COUNT;
        List<String> items = new ArrayList<>();
        for (int i = start; i < end; i++) {
            items.add(String.format(Locale.US, "item position is: %d", i));
        }
        return items;
    }
}
