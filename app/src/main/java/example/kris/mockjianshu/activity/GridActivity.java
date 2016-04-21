package example.kris.mockjianshu.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.adapter.GridAdapter;

public class GridActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private View headerView;
    private View footerView;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        headerView = getLayoutInflater().inflate(R.layout.layout_header, null);
        footerView = getLayoutInflater().inflate(R.layout.layout_footer, null);

        adapter = new GridAdapter(this, headerView, footerView);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        adapter.addAll(list);
        //初始化布局管理器
        final GridLayoutManager lm = new GridLayoutManager(this, 2);

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
                int postion = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
                if (adapter.isHeader(postion) || adapter.isFooter(postion)) {
                    outRect.set(0, 0, 0, 20);
                } else {
                    outRect.set(20, 20, 20, 20);
                }
            }
        });

        mRecyclerView.setAdapter(adapter);

    }
}
