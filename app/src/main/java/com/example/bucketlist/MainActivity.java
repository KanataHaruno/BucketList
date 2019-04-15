package com.example.bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener{

    private RecyclerView recyclerView;
    private GestureDetector mGestureDetector;
    private BucketListDatabase db;
    private Executor executor;

    private List<BucketListItem> bucketList;
    private BucketListAdapter bucketAdapter;

    public static final int REQUESTCODE = 1;
    public static final String ITEM = "Bucket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        db = BucketListDatabase.getDatabase(this);
        executor = Executors.newSingleThreadExecutor();

        // Initialize recyclerview
        recyclerView = findViewById(R.id.recyclerview_bucket_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new BucketListAdapter(bucketList));

        // Fab for adding bucket list item
        FloatingActionButton fab = findViewById(R.id.confirmAddListItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBucketItem.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        // Delete bucket list item when pressed for long
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    deleteBucket(bucketList.get(adapterPosition));
                }
            }
        });

        recyclerView.addOnItemTouchListener(this);
        getAllBuckets();

    }


    // Methods for going through bucket list items
    private void getAllBuckets() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketList = db.bucketListDAO().getAllBucketListItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    private void insertBucket(final BucketListItem bucket) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListDAO().insert(bucket);
                getAllBuckets();
            }
        });
    }

    private void deleteBucket(final BucketListItem bucket) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListDAO().delete(bucket);
                getAllBuckets();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                BucketListItem addBucket = data.getParcelableExtra(MainActivity.ITEM);
                insertBucket(addBucket);
            }
        }
    }


    private void updateUI() {

        if (bucketAdapter == null) {
            bucketAdapter = new BucketListAdapter(bucketList);
            recyclerView.setAdapter(bucketAdapter);
        } else {
            bucketAdapter.swapList(bucketList);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
