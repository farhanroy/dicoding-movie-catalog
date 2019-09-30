package farchan.app.com.belajardicoding.Helper;

import android.database.Cursor;

import java.util.ArrayList;

import farchan.app.com.belajardicoding.Database.DatabaseContract;
import farchan.app.com.belajardicoding.Model.Model;

import static android.provider.BaseColumns._ID;

public class MappingHelper {
    public static ArrayList<Model> mapCursorToArrayList(Cursor favoriteCursor) {
        ArrayList<Model> favoriteList = new ArrayList<>();
        while (favoriteCursor.moveToNext()) {
            int id = favoriteCursor.getInt(favoriteCursor.getColumnIndexOrThrow(_ID));
            String title = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COLUMN_NAME));
            String release = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COLUMN_RELEASE));
            String description = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION));
            String poster = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COLUMN_POSTER));
            String type = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COLUMN_TYPE));
            favoriteList.add(new Model(id, title, release, description, poster, type));
        }
        return favoriteList;
    }
}
