package example.kris.mockjianshu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.fragment.LoadingMoreFragment;

public class LoadingMoreActivity extends AppCompatActivity {

    private LoadingMoreFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_more);
        attachFragment(2);
    }

    /**
     * 加载布局
     */
    private void attachFragment(int type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mListFragment = LoadingMoreFragment.newInstance(type);
        transaction.replace(R.id.container, mListFragment);
        transaction.commit();
    }
}
