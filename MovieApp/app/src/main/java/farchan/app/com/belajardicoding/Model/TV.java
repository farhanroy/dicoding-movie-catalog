package farchan.app.com.belajardicoding.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class
TV implements Parcelable {
    private String tvName;
    private String tvRelease;
    private String tvDescription;
    private String posterTV;

    public TV() {

    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvRelease() {
        return tvRelease;
    }

    public void setTvRelease(String tvRelease) {
        this.tvRelease = tvRelease;
    }

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }

    public String getPosterTV() {
        return posterTV;
    }

    public void setPosterTV(String posterTV) {
        this.posterTV = posterTV;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tvName);
        dest.writeString(this.tvRelease);
        dest.writeString(this.tvDescription);
        dest.writeString(this.posterTV);
    }

    private TV(Parcel in) {
        this.tvName = in.readString();
        this.tvRelease = in.readString();
        this.tvDescription = in.readString();
        this.posterTV = in.readString();
    }

    public static final Parcelable.Creator<TV> CREATOR = new Parcelable.Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel source) {
            return new TV(source);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };
}
