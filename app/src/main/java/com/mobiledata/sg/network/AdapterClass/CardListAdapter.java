package com.mobiledata.sg.network.AdapterClass;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobiledata.sg.network.CommonUtils.RecyclerViewHolder;
import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private JSONArray lMobileData;
    private Activity context;
    RecyclerView recyclerView;
    public CardListAdapter(RecyclerView recyclerView, Activity context, JSONArray lMobileData) {
        this.context = context;
        this.lMobileData = lMobileData;
        this.recyclerView = recyclerView;

    }
    @Override
    public int getItemCount() {
        return (null != lMobileData ? lMobileData.length() : 0);
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        try {
            final JSONObject singleMobileData = lMobileData.getJSONObject(position);
            final RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder
            mainHolder.textView.setText(singleMobileData.getString(Utility.NETWORKDATA));
            mainHolder.year.setText(singleMobileData.getString(Utility.YEAR));
            mainHolder.image_item_clicks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String message= "The decreases volume of Mobile data "+singleMobileData.getString(Utility.MIN_min_network_data)+" quarter "+singleMobileData.getString(Utility.QUARTER)+" in a year "+singleMobileData.getString(Utility.YEAR) ;
                        showAlert(singleMobileData.getString(Utility.YEAR)+"-"+singleMobileData.getString(Utility.QUARTER),message);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    AlertDialog dialogue=null;
    public void showAlert(String year, String message){

        if(dialogue!=null)
        {
            dialogue.cancel();
            dialogue=null;
        }
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Entry ("+year+")")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialogue.cancel();
                        dialogue=null;
                    }
                })
                .setIcon(R.mipmap.decreases_image);

        dialogue = builder.create();
        dialogue.show();

    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.cardview_row, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }
}