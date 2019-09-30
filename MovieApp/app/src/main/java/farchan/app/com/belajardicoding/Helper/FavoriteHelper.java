package farchan.app.com.belajardicoding.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import farchan.app.com.belajardicoding.Model.Model;

import static android.provider.BaseColumns._ID;
import static farchan.app.com.belajardicoding.Database.DatabaseContract.FavoriteColumns.COLUMN_NAME;
import static farchan.app.com.belajardicoding.Database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
        if (database.isOpen())
            database.close();
    }

    public void deleteFavorite(Model model) {
        database.delete(TABLE_FAVORITE, COLUMN_NAME + " = '" + model.getTitle() + "'", null);
    }

    public Cursor favoriteCheck(String name){
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE name = ? ", new String[]{name});
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider(String type) {
        String Query = "SELECT  * FROM " + DATABASE_TABLE + " WHERE type = ? ";
        return database.rawQuery(Query, new String[]{type});
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String name) {
        return database.delete(DATABASE_TABLE, COLUMN_NAME + " = ?", new String[]{name});
    }
}

