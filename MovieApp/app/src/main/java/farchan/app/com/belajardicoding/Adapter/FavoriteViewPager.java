package farchan.app.com.belajardicoding.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import farchan.app.com.belajardicoding.View.Fragment.TabMovieFav;
import farchan.app.com.belajardicoding.View.Fragment.TabTvFav;

public class FavoriteViewPager extends FragmentPagerAdapter {
    public FavoriteViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return  new TabMovieFav();
            case 1:
                return new TabTvFav();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Movie";
            case 1:
                return "Tv";
        }
        return super.getPageTitle(position);
    }
}
