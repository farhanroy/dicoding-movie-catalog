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

import farchan.app.com.belajardicoding.Model.TvModel;
import farchan.app.com.belajardicoding.R;

public class ListTVAdapter extends RecyclerView.Adapter<ListTVAdapter.TVListHolder> {
    private final List<TvModel.DataTv> tvList;
    private OnItemClickListener listener;

    public ListTVAdapter(List<TvModel.DataTv> tvList) {
        this.tvList = tvList;
    }

    @NonNull
    @Override
    public TVListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv, viewGroup, false);
        return new ListTVAdapter.TVListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVListHolder tvListHolder, int i) {
        String name = tvList.get(i).getTitleTv();
        String release = tvList.get(i).getReleaseDateTv();
        String poster = tvList.get(i).getImgTv();
        tvListHolder.txtTvName.setText(name);
        tvListHolder.txtTvRelease.setText(release);
        Picasso.get().load(poster).into(tvListHolder.posterTv);

    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class TVListHolder extends RecyclerView.ViewHolder {
        final TextView txtTvName;
        final TextView txtTvRelease;
        final ImageView posterTv;
        TVListHolder(@NonNull View itemView) {
            super(itemView);
            txtTvName = itemView.findViewById(R.id.txt_name_tv);
            txtTvRelease = itemView.findViewById(R.id.txt_release_tv);
            posterTv = itemView.findViewById(R.id.img_poster_tv);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(tvList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(TvModel.DataTv tv);
    }

    public void setOnItemClickListener(ListTVAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
