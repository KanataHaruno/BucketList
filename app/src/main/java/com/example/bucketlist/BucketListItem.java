package com.example.bucketlist;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "bucketListItem_table")
public class BucketListItem implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "bucket")
    private String title;
    private String description;


    public BucketListItem(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }


    protected BucketListItem(Parcel in) {
        this.id = (int)in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Creator<BucketListItem> CREATOR = new Creator<BucketListItem>() {
        @Override
        public BucketListItem createFromParcel(Parcel source) {
            return new BucketListItem(source);
        }

        @Override
        public BucketListItem[] newArray(int size) {
            return new BucketListItem[size];
        }
    };

}
