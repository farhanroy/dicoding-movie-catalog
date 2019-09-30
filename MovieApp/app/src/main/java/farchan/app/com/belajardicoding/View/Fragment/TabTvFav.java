package farchan.app.com.belajardicoding.View.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;

import farchan.app.com.belajardicoding.Adapter.ListFavoriteTv;
import farchan.app.com.belajardicoding.Database.LoadFavoriteCallback;
import farchan.app.com.belajardicoding.Helper.FavoriteHelper;
import farchan.app.com.belajardicoding.Model.Model;
import farchan.app.com.belajardicoding.R;

import static farchan.app.com.belajardicoding.Database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static farchan.app.com.belajardicoding.Helper.MappingHelper.mapCursorToArrayList;


public class TabTvFav extends Fragment implements LoadFavoriteCallback {

    private ListFavoriteTv listFavoriteTv;
    Model model;
    private FavoriteHelper helper;
    private static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";

    public TabTvFav() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_tv_fav, container, false);
        RecyclerView recyclerFavoriteTv = rootView.findViewById(R.id.recycler_tv_fav);

        helper = FavoriteHelper.getInstance(getActivity());
            helper.open();

        listFavoriteTv = new ListFavoriteTv();

        if (savedInstanceState == null) {
            new LoadFavoriteAsync(getActivity(), this).execute();
        } else {
            ArrayList<Model> list = savedInstanceState.getParcelableArrayList(EXTRA_FAVORITE);
            if (list != null) {
                listFavoriteTv.setTvFavs(list);
            }
        }

        recyclerFavoriteTv.setAdapter(listFavoriteTv);

        recyclerFavoriteTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFavoriteTv.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                helper.deleteFavorite(listFavoriteTv.getTvAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerFavoriteTv);
        return rootView;
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor favorite) {
        ArrayList<Model> listFavorite = mapCursorToArrayList(favorite);
        if (listFavorite.size() > 0) {
            listFavoriteTv.setTvFavs(listFavorite);
        } else {
            listFavoriteTv.setTvFavs(new ArrayList<Model>());
        }
    }

    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakFavoriteHelper;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavoriteAsync(Context context, LoadFavoriteCallback callback) {
            weakFavoriteHelper = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakFavoriteHelper.get();
            return context.getContentResolver().query(CONTENT_URI, null, "tv", null, null, null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            Log.d("PO", favorite.toString());
            weakCallback.get().postExecute(favorite);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
