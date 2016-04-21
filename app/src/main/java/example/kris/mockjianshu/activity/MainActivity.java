package example.kris.mockjianshu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.fragment.HomeFragment;

public class MainActivity extends FragmentActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private RadioGroup mMainTabGroup;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initViews();
        switchFragment();
    }

    private void initViews() {
        mMainTabGroup = (RadioGroup) findViewById(R.id.rg_tab);
        mMainTabGroup.check(R.id.rb_find);
        mMainTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_find:
                        switchFragment();
                        break;
                    case R.id.rb_attention:
                        startActivity(new Intent(MainActivity.this, MediaOverviewActivity.class));
                        break;
                    case R.id.rb_write:
                        startActivity(new Intent(MainActivity.this, LoadingMoreActivity.class));
                        break;
                    case R.id.rb_message:
                        break;
                    case R.id.rb_me:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void switchFragment() {
        Log.d(TAG, "switchFragment");
        HomeFragment homeFragment = HomeFragment.newInstance();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.container, homeFragment);
        transaction.commit();
    }


}
