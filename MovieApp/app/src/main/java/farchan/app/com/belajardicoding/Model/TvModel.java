package farchan.app.com.belajardicoding.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvModel {
    public List<TvModel.DataTv> getTvData() {
        return data;
    }

    @SerializedName("results")
    private
    List<TvModel.DataTv> data;
    public class DataTv{

        @SerializedName("original_name")
        @Expose
        private String titleTv;

        @SerializedName("first_air_date")
        @Expose
        private String releaseDateTv;

        @SerializedName("overview")
        @Expose
        private String descriptionTv;

        @SerializedName("poster_path")
        @Expose
        private String imgTv;

        public String getTitleTv() {
            return titleTv;
        }

        public String getReleaseDateTv() {
            return releaseDateTv;
        }

        public String getDescriptionTv() {
            return descriptionTv;
        }

        public String getImgTv() {
            return "https://image.tmdb.org/t/p/w500"+imgTv;
        }

    }
}
