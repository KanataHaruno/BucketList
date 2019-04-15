package com.example.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBucketItem extends AppCompatActivity {

    public EditText title;
    public EditText description;
    public Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket_item);

        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        create = findViewById(R.id.addButton);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BucketListItem bucket = new BucketListItem(title.getText().toString(), description.getText().toString());

                Intent intent = new Intent();

                // Needs to use parcable to put object in putExtra
                intent.putExtra(MainActivity.ITEM, bucket);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
