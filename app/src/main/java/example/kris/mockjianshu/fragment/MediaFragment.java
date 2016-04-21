package example.kris.mockjianshu.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.adapter.MediaOverviewAdapter;

/**
 * Created by fangyu on 2016/4/18.
 */
public class MediaFragment extends Fragment {

    private static final String TAG = "MediaFragment";

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MediaOverviewAdapter adapter;
    private boolean isLoadingMore = false;

    public static MediaFragment newInstance(String name) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        fragment.setArguments(args);

        return fragment;
    }

    public MediaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MediaOverviewAdapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TabActivity", "onRefresh");
                refreshContent();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItems = layoutManager.findLastVisibleItemPosition();
                Log.d(TAG,"visibleItems =" + visibleItems);
                Log.d(TAG,"adapter.getItemCount() =" + adapter.getItemCount());
                if (dy > 0 && visibleItems >= adapter.getItemCount() - 1 && !isLoadingMore) {
                    Log.d(TAG,"will loadNewFeeds");
                    loadMore();
                }
            }
        });

        mSwipeRefreshLayout.setRefreshing(true);
        refreshContent();

    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Toast.makeText(MediaFragment.this.getActivity(), "refresh finish", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void loadMore() {
        isLoadingMore = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData();
                adapter.notifyDataSetChanged();
                Toast.makeText(MediaFragment.this.getActivity(), "load more finish", Toast.LENGTH_SHORT).show();
                isLoadingMore = false;
            }
        }, 2000);
    }
}
