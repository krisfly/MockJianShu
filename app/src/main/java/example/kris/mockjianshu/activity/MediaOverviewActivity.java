package example.kris.mockjianshu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import example.kris.mockjianshu.R;
import example.kris.mockjianshu.fragment.GridLayoutFragment;

/**
 * Created by fangyu on 2016/4/18.
 */
public class MediaOverviewActivity extends FragmentActivity {

    private static final String[] CONTENT = new String[] { "Recent", "Artists", "Albums", "Songs", "Playlists" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_overview);
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new MediaOverviewAdapter(getSupportFragmentManager()));

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }

    class MediaOverviewAdapter extends FragmentPagerAdapter {
        public MediaOverviewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return GridLayoutFragment.newInstance(CONTENT[position % CONTENT.length]);
//            return MediaFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

}
