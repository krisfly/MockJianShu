package example.kris.mockjianshu.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import example.kris.mockjianshu.R;


/**
 * Created by fangyu on 2016/4/19.
 */
public class RefreshLayout extends SwipeRefreshLayout {

    private static final String TAG = "RefreshLayout";

    private int mTouchSlop;
    private RecyclerView mListView;
    private OnLoadListener mOnLoadListener;
    private View mListViewFooter;
    private int mYDown;
    private int mLastY;
    private boolean isLoading = false;


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mListView = (RecyclerView) childView;
                mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });
                Log.d(TAG, "find list view");
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int)ev.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    private boolean isBottom() {
//        if (mListView != null && mListView.getAdapter() != null) {
//            return mListView.getl() == (mListView.getAdapter().getCount() - 1);
//        }
        return false;
    }

    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
//        if (isLoading) {
//            mListView.addFooterView(mListViewFooter);
//        } else {
//            mListView.removeFooterView(mListViewFooter);
//            mYDown = 0;
//            mLastY = 0;
//        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }


    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public interface OnLoadListener {
        void onLoad();
    }
}
