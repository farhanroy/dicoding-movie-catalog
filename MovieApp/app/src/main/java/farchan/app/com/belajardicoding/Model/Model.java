package farchan.app.com.belajardicoding.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    private int id;
    private String title;
    private String release;
    private String description;
    private String poster;
    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Model() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.description);
        dest.writeString(this.poster);
        dest.writeString(this.type);
    }

    private Model(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.type = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public Model(int id, String title, String release, String description, String poster, String type) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.description = description;
        this.poster = poster;
        this.type = type;
    }
}
