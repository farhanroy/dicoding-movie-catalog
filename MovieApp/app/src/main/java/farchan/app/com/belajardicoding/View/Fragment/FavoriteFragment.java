package farchan.app.com.belajardicoding.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import farchan.app.com.belajardicoding.Adapter.FavoriteViewPager;
import farchan.app.com.belajardicoding.R;


public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false); // Inflate the layout for this fragment
        TabLayout tabFavorite = rootView.findViewById(R.id.tab_container);
        ViewPager viewPagerFav = rootView.findViewById(R.id.view_pager_fav);
        FavoriteViewPager favoriteViewPager = new FavoriteViewPager(getChildFragmentManager());

        viewPagerFav.setAdapter(favoriteViewPager);
        tabFavorite.setupWithViewPager(viewPagerFav);


        return rootView;
    }

}
