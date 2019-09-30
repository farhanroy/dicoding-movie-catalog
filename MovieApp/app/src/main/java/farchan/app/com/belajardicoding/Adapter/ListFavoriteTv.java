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

public class ListFavoriteTv extends RecyclerView.Adapter<ListFavoriteTv.FavoriteTvHolder> {

    private List<Model> tvFavList = new ArrayList<>();

    public ListFavoriteTv() {
    }

    public void setTvFavs(List<Model> favs) {
        this.tvFavList = favs;
        notifyDataSetChanged();
    }

    public Model getTvAt(int position){
        return tvFavList.get(position);
    }
    @NonNull
    @Override
    public FavoriteTvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new ListFavoriteTv.FavoriteTvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvHolder favoriteTvHolder, int i) {
        Model favoriteTv = tvFavList.get(i);
        Log.d("S", favoriteTv.getTitle());
        favoriteTvHolder.titleTvFav.setText(favoriteTv.getTitle());
        favoriteTvHolder.releaseTvFav.setText(favoriteTv.getRelease());
        Picasso.get().load(favoriteTv.getPoster()).into(favoriteTvHolder.posterTvFav);
    }

    @Override
    public int getItemCount() {
        return tvFavList.size();
    }

    class FavoriteTvHolder extends RecyclerView.ViewHolder {
        final ImageView posterTvFav;
        final TextView titleTvFav;
        final TextView releaseTvFav;
        FavoriteTvHolder(@NonNull View itemView) {
            super(itemView);
            posterTvFav = itemView.findViewById(R.id.poster_fav);
            titleTvFav = itemView.findViewById(R.id.title_fav);
            releaseTvFav = itemView.findViewById(R.id.release_fav);
        }
    }
}
