package com.example.bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao

public interface BucketListDAO {

    @Insert
    void insert(BucketListItem bucketListItem);

    @Delete
    void delete(BucketListItem bucketListItem);

    @Delete
    void delete(List<BucketListItem> bucketListItem);

    @Query("SELECT * from bucketListItem_table")
    List<BucketListItem> getAllBucketListItems();

}




