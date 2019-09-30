package farchan.app.com.belajardicoding.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import farchan.app.com.belajardicoding.Model.Model;
import farchan.app.com.belajardicoding.R;

public class ListFavoriteMovie extends RecyclerView.Adapter<ListFavoriteMovie.FavoriteMovieHolder> {

    private List<Model> movieFavList = new ArrayList<>();

    public ListFavoriteMovie() {
    }

    public void setMovieFavs(List<Model> movieFavs) {
        this.movieFavList = movieFavs;
        notifyDataSetChanged();
    }

    public Model getMovieAt(int position){
        return movieFavList.get(position);
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new ListFavoriteMovie.FavoriteMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder favoriteMovieHolder, int i) {
        Model favoriteMovie = movieFavList.get(i);
        Log.d("S", favoriteMovie.getTitle());
        favoriteMovieHolder.titleMovieFav.setText(favoriteMovie.getTitle());
        favoriteMovieHolder.releaseMovieFav.setText(favoriteMovie.getRelease());
        Picasso.get().load(favoriteMovie.getPoster()).into(favoriteMovieHolder.posterMovieFav);
    }

    @Override
    public int getItemCount() {
        return movieFavList.size();
    }


    class FavoriteMovieHolder extends RecyclerView.ViewHolder {
        final ImageView posterMovieFav;
        final TextView titleMovieFav;
        final TextView releaseMovieFav;
        FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);

            posterMovieFav = itemView.findViewById(R.id.poster_fav);
            titleMovieFav = itemView.findViewById(R.id.title_fav);
            releaseMovieFav = itemView.findViewById(R.id.release_fav);
        }
    }
}
