package farchan.app.com.belajardicoding.Database;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void preExecute();

    void postExecute(Cursor favorite);
}
