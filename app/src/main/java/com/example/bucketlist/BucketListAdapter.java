package com.example.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {

    List<BucketListItem> bucketList;

    public BucketListAdapter(List<BucketListItem> bucketList) {
        this.bucketList = bucketList;
    }

    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.content_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(row);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BucketListAdapter.ViewHolder viewHolder, int i) {
        final BucketListItem bucket = bucketList.get(i);
        (viewHolder).titleView.setText(bucket.getTitle());
        (viewHolder).descriptionView.setText(bucket.getDescription());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.descriptionView.setPaintFlags(viewHolder.descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.titleView.setPaintFlags(viewHolder.titleView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.descriptionView.setPaintFlags(viewHolder.descriptionView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return bucketList.size();
    }

    public void swapList (List<BucketListItem> newList) {
        bucketList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView titleView;
        TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);


        }
    }
}
