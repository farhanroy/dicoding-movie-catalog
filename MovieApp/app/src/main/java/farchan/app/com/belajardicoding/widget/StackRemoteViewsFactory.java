package farchan.app.com.belajardicoding.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import farchan.app.com.belajardicoding.Helper.DatabaseHelper;
import farchan.app.com.belajardicoding.Helper.MappingHelper;
import farchan.app.com.belajardicoding.Model.Model;
import farchan.app.com.belajardicoding.R;

import static farchan.app.com.belajardicoding.Database.DatabaseContract.TABLE_FAVORITE;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<Model> movieList = new ArrayList<>();
    private final Context context;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        movieList.clear();
        loadFavoriteWidget();
    }

    private void loadFavoriteWidget() {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        String Query = "SELECT  * FROM " + TABLE_FAVORITE + " WHERE type = ? ";
        Cursor cursor = database.rawQuery(Query, new String[]{"movie"});
        if (cursor != null && cursor.moveToFirst()) {
            movieList.addAll(MappingHelper.mapCursorToArrayList(cursor));
            cursor.close();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        try {
            Model favoriteMovie = movieList.get(position);


            Intent intent = new Intent();
            rv.setOnClickFillInIntent(R.id.iv_banner, intent);

            Log.d("S", favoriteMovie.getPoster());
            try {
                Bitmap preview = Glide.with(context)
                        .asBitmap()
                        .load(favoriteMovie.getPoster())
                        .apply(new RequestOptions().fitCenter())
                        .submit()
                        .get();
                rv.setImageViewBitmap(R.id.iv_banner, preview);
                rv.setTextViewText(R.id.tv_movie_title, favoriteMovie.getTitle());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return movieList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
