package farchan.app.com.belajardicoding.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import farchan.app.com.belajardicoding.Model.MovieModel;
import farchan.app.com.belajardicoding.R;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MovieListHolder> {
    private final List<MovieModel.Data> movieNames;
    private OnItemClickListener listener;

    public ListMovieAdapter(List<MovieModel.Data> movieNames) {
        this.movieNames = movieNames;
    }

    @NonNull
    @Override
    public MovieListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListHolder movieListHolder, int i) {
        MovieModel.Data movie = movieNames.get(i);
        movieListHolder.txtMovieName.setText(movie.getTitle());
        movieListHolder.txtMovieRelease.setText(movie.getReleaseDate());
        Picasso.get().load(movie.getPosterMovie()).into(movieListHolder.imgPoster);
        //movieListHolder.imgPoster.setImageResource(getDataImg().get(i).getPosterMovie());
    }

    @Override
    public int getItemCount() {
        return movieNames.size();
    }


    class MovieListHolder extends RecyclerView.ViewHolder {
        final ImageView imgPoster;
        final TextView txtMovieName;
        final TextView txtMovieRelease;
        MovieListHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_movie);
            txtMovieRelease = itemView.findViewById(R.id.txt_release);
            imgPoster = itemView.findViewById(R.id.img_poster);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(movieNames.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(MovieModel.Data movie);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
