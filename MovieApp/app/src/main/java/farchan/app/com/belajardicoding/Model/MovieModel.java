package farchan.app.com.belajardicoding.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieModel {
    public List<Data> getData() {
        return data;
    }

    @SerializedName("results")
    private
    List<Data> data;
    public static class Data{
        @SerializedName("id")
        @Expose
        private int idMovie;

        @SerializedName("title")
        @Expose
        private final String title;

        @SerializedName("release_date")
        @Expose
        private String releaseDate;

        @SerializedName("overview")
        @Expose
        private String description;

        @SerializedName("poster_path")
        @Expose
        private String posterMovie;


        public Data(String title, String releaseDate, String description, String posterMovie) {
            this.title = title;
            this.releaseDate = releaseDate;
            this.description = description;
            this.posterMovie = posterMovie;
        }

        public int getIdMovie() {
            return idMovie;
        }

        public void setIdMovie(int idMovie) {
            this.idMovie = idMovie;
        }

        public String getTitle() {
            return title;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPosterMovie() {
            return "https://image.tmdb.org/t/p/w500"+posterMovie;
        }

        public void setPosterMovie(String posterMovie) {
            this.posterMovie = posterMovie;
        }
    }
}
