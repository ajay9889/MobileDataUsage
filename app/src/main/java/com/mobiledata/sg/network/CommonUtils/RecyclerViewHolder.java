package com.mobiledata.sg.network.CommonUtils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobiledata.sg.network.R;

/**
 * Created by Ajay on 25/5/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView   year,textView;
    public ImageView  image_item_clicks;

    public RecyclerViewHolder(View view) {
        super(view);

        this.year = (TextView)view.findViewById(R.id.year);
        this.textView = (TextView)view.findViewById(R.id.textView);
        this.image_item_clicks = (ImageView)view.findViewById(R.id.image_item_clicks);

    }

}