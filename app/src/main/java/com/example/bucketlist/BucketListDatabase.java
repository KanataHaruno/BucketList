package com.example.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// All entities/classes that need to be added to the database
@Database(entities = {BucketListItem.class}, version = 3)

public abstract class BucketListDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "bucketlist_database";

    public abstract BucketListDAO bucketListDAO();

    private static BucketListDatabase INSTANCE;

    public static BucketListDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (BucketListDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BucketListDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
