package com.mobiledata.sg.network;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mobiledata.sg.network.AdapterClass.CardListAdapter;
import com.mobiledata.sg.network.CommonUtils.GridSpacingItemDecoration;
import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.ModelClass.MobileData;
import com.mobiledata.sg.network.NetworkAPICalls.EndpointUrls;
import com.mobiledata.sg.network.NetworkAPICalls.NetworkHandler;
import com.mobiledata.sg.network.NetworkAPICalls.ResponseInterface;
import com.mobiledata.sg.network.RoomPersistDatabase.DaoInterface;
import com.mobiledata.sg.network.RoomPersistDatabase.Databasehelper;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.LineChartView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mobiledata.sg.network.MyMainApplication.INSTANCE;

public class MainActivity extends AppCompatActivity implements ResponseInterface {
    MobileData[] lMobileData =null;
    LineChartView lineChartView;
    List yAxisValues = new ArrayList();
    List axisValues = new ArrayList();
    RecyclerView recycletView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Create Database
        INSTANCE=  Databasehelper.getDatabase(MainActivity.this, Utility.TABLEDATABSE_NAME);
        recycletView   = (RecyclerView)findViewById(R.id.recycletView);
        lineChartView = (LineChartView)findViewById(R.id.chart);
        // RequestData
      requestToNetwork();
    }

    public void requestToNetwork(){
        if(Utility.isNetworkAvailable(MainActivity.this)){
            new NetworkHandler().onRequest(this, Utility.REQUEST_CODE , EndpointUrls.FetchNetworkMobileData);
        }else{
            fetchAllLocalCachedData(MainActivity.this);
        }
    }

    MenuItem graph_togle;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graphmenu, menu);
        graph_togle = menu.findItem(R.id.graph_menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.graph_menu:
                if(lineChartView.getVisibility() == View.VISIBLE){
                    graph_togle.setIcon(R.mipmap.app_icon);
                    recycletView.setVisibility(View.VISIBLE);
                    lineChartView.setVisibility(View.GONE);
                }else{
                    graph_togle.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
                    recycletView.setVisibility(View.GONE);
                    lineChartView.setVisibility(View.VISIBLE);
                }
                setGraph();
                break;
        }
        return true;

    }

    /***
     * Network response thread
     */
    float[] yAxisData=null;
    @Override
    public void onResponse(String serverResponse, int requestCode) {
            Log.d("TAG",serverResponse);
        switch(requestCode){
            case Utility.REQUEST_CODE:
                try{
                    if(!TextUtils.isEmpty(serverResponse)){
                        JSONObject mMobileData=new JSONObject(serverResponse);
                        JSONArray mArray =mMobileData.getJSONObject("result").getJSONArray("records");
                        lMobileData =new MobileData[mArray.length()];
                        for(int i =0 ; i <mArray.length() ; i++){
                            JSONObject singData=mArray.getJSONObject(i);
                            String year =singData.getString("quarter").split("-")[0].trim();
                            int year_int=Math.round(Float.valueOf(year));
                                MobileData mMarkToDownload = new MobileData();
                                mMarkToDownload.set_id(singData.getString("_id"));
                                mMarkToDownload.setQuarter(singData.getString("quarter").split("-")[1].trim());
                                mMarkToDownload.setVolume_of_mobile_data(Float.valueOf(singData.getString("volume_of_mobile_data")));
                                mMarkToDownload.setYear(year_int);
                                lMobileData[i] = mMarkToDownload;

                        }

                    insertAll(MainActivity.this, lMobileData);
                    }else{
                        showAlert();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    showAlert();
                }
                break;
        }
    }

    public void showAlert(){
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.net_connection, Snackbar.LENGTH_LONG);
//        snackbar.show();
    }
    public   void insertAll(Context ctx, MobileData[] lMobileData)  {
        new insertAsync(INSTANCE.mobileDataDao() , lMobileData).execute();
    }
    public   class insertAsync extends AsyncTask<Void, Void, Void> {
        private DaoInterface mAsyncTaskDao;
        MobileData[] mData;
        insertAsync(DaoInterface dao,  MobileData[] mData) {
            mAsyncTaskDao = dao;
            this.mData=mData;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if(mData!=null && mData.length>0) {
                // Delete if already exit
                mAsyncTaskDao.deleteTable();
                // Reinsert All data
                mAsyncTaskDao.insertAll(mData);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // display the items here
            fetchAllLocalCachedData(MainActivity.this);
        }
    }


    /*
    * Render the graph
    * **/


    public void setGraph(){
        try {
            if (yAxisData == null) return;
            for (int i = 0; i < yAxisData.length; i++) {
                yAxisValues.add(new PointValue(i, yAxisData[i]));
            }

            Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
            List lines = new ArrayList();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);


            lineChartView.setLineChartData(data);
            Axis axis = new Axis();
            axis.setValues(axisValues);
            data.setAxisXBottom(axis);
            axis.setTextSize(15);
            axis.setTextColor(Color.parseColor("#03A9F4"));
            axis.setName("YEAR");

            Axis yAxis = new Axis();
            yAxis.setLineColor(Color.parseColor("#9C27B0"));
            data.setAxisYLeft(yAxis);

            yAxis.setTextColor(Color.parseColor("#03A9F4"));
            yAxis.setTextSize(15);
            Viewport viewport = new Viewport(lineChartView.getMaximumViewport());

            lineChartView.setMaximumViewport(viewport);
            lineChartView.setCurrentViewport(viewport);
        }catch (Exception e){e.printStackTrace();}
    }
    /**
     * In it data on card view
     * **/
    public void inItView( JSONArray mData){
        /**
         * set Adapter
         * **/
        recycletView.setHasFixedSize(true);
        int spanCount = 5; // 3 columns
        int spacing = 5; // 50px
        boolean includeEdge = false;
        float widths = getResources().getDisplayMetrics().widthPixels;
        float heightPixels = getResources().getDisplayMetrics().heightPixels;
        int width = (int)widths;
        GridLayoutManager gridLayoutManager= null;

        if(widths>heightPixels) {
            gridLayoutManager= new GridLayoutManager(MainActivity.this, 5);
        }else{
            if(width<700)
                gridLayoutManager= new GridLayoutManager(MainActivity.this, 2);
            else
                gridLayoutManager= new GridLayoutManager(MainActivity.this, 3);
        }
        recycletView.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(spanCount, spacing, includeEdge);
        recycletView.addItemDecoration(itemDecoration);
        recycletView.setOnFlingListener(null);
        recycletView.setHasFixedSize(true);
//        recycletView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        recycletView.setAdapter(new CardListAdapter(recycletView , MainActivity.this ,mData));
    }
    // fetch from local database from year 2008 and above
    public  void fetchAllLocalCachedData(Context ctx)  {
        new getMobileDataAsync(INSTANCE.mobileDataDao()).execute();
    }
    public class getMobileDataAsync extends AsyncTask<Void, Void, Void> {
        private DaoInterface mAsyncTaskDao;
        JSONArray mData;
        Cursor mCursor=null;
        getMobileDataAsync(DaoInterface dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mCursor = mAsyncTaskDao.getAllMobileDatas();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                mData = new JSONArray();
                yAxisData = new float[mCursor.getCount()];
                if (mCursor != null && mCursor.getCount() != 0) {
                    if (mCursor.moveToFirst()) {
                        do {
                            JSONObject mJson = new JSONObject();
                            mJson.put(Utility.NETWORKDATA, mCursor.getString(0));
                            yAxisData[mData.length()]= Float.valueOf(mCursor.getString(0));
                            mJson.put(Utility.MIN_min_network_data, mCursor.getString(1));
                            mJson.put(Utility.YEAR, mCursor.getString(2));
                            mJson.put(Utility.QUARTER, mCursor.getString(3));
                            axisValues.add(mData.length(), new AxisValue(mData.length()).setLabel(mCursor.getString(2)));
                            mData.put(mData.length(), mJson);
                        } while (mCursor.moveToNext());
                    }
                    mCursor.close();
                }
                if (mData != null && mData.length() > 0) {
                    inItView(mData);
                } else {
                    showAlert();
                }
            }catch(Exception e){e.printStackTrace();}

        }
    }
}
