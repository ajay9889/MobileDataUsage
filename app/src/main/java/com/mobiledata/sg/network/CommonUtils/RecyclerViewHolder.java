package com.mobiledata.sg.network.CommonUtils;
import android.view.View;
import android.widget.*;

import com.mobiledata.sg.network.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ajay on 25/5/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView   year,textView;
    public ImageView  imageView;

    public RecyclerViewHolder(View view) {
        super(view);

        this.year = (TextView)view.findViewById(R.id.year);
        this.textView = (TextView)view.findViewById(R.id.textView);
        this.imageView = (ImageView)view.findViewById(R.id.imageView);

    }

}