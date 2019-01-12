package com.mobiledata.sg.network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import com.mobiledata.sg.network.CommonUtils.GraphView;
import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.ModelClass.MobileDataArtifact;
import com.mobiledata.sg.network.NetworkAPICalls.EndpointUrls;
import com.mobiledata.sg.network.NetworkAPICalls.NetworkHandler;
import com.mobiledata.sg.network.NetworkAPICalls.ResponseInterface;
import com.mobiledata.sg.network.RoomPersistDatabase.DaoInterface;
import com.mobiledata.sg.network.RoomPersistDatabase.Databasehelper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends Activity implements ResponseInterface {
//    @BindView(R.id.graph)
//    GraphView graph;
    private static Databasehelper INSTANCE;
    MobileDataArtifact[] lMobileDataArtifact=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        // Example of a call to a native method
//        Create Database
        INSTANCE=  Databasehelper.getDatabase(MainActivity.this, Utility.TABLEDATABSE_NAME);
        // RequestData
        if(Utility.isNetworkAvailable(MainActivity.this)){
            new NetworkHandler().onRequest(this, Utility.REQUEST_CODE , EndpointUrls.FetchNetworkMobileData);
        }else{
            fetchAllLocalCachedData(MainActivity.this);
        }
//        float[] values = new float[] { 2.0f,1.5f, 2.5f, 1.0f , 3.0f };
//        String[] verlabels = new String[] { "great", "ok", "bad" };
//        String[] horlabels = new String[] { "today", "tomorrow", "next week", "next month" };
//        GraphView graphView = new GraphView(this, values, "GraphViewDemo",horlabels, verlabels, GraphView.BAR);
//        setContentView(graphView);
    }
    /***
     * Network response thread
     */

    @Override
    public void onResponse(String serverResponse, int requestCode) {

        switch(requestCode){
            case Utility.REQUEST_CODE:
                try{
                    if(!TextUtils.isEmpty(serverResponse)){
                        JSONObject mMobileData=new JSONObject(serverResponse);

                        JSONArray mArray =mMobileData.getJSONArray("records");
                        lMobileDataArtifact=new MobileDataArtifact[mArray.length()];
                        for(int i =0 ; i <mArray.length() ; i++){
                            JSONObject singData=new JSONObject(serverResponse);
                            String year =singData.getString("quarter").split("-")[0].trim();
                            MobileDataArtifact mMarkToDownload= new MobileDataArtifact();
                            mMarkToDownload.set_id(singData.getString("_id"));
                            mMarkToDownload.setQuarter(singData.getString("quarter"));
                            mMarkToDownload.setVolume_of_mobile_data(singData.getString("volume_of_mobile_data"));
                            lMobileDataArtifact[i]=mMarkToDownload;

/*//                            if(singData.has("quarter") && Integer.parseInt(year) > 2007)
//                            {
                                MobileDataArtifact mMarkToDownload= new MobileDataArtifact();
                                mMarkToDownload.set_id(singData.getString("_id"));
                                mMarkToDownload.setQuarter(singData.getString("quarter"));
                                mMarkToDownload.setVolume_of_mobile_data(singData.getString("volume_of_mobile_data"));
                                lMobileDataArtifact[i]=mMarkToDownload;
//                            }*/
                        }
                    insertAll(MainActivity.this,lMobileDataArtifact);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    public   void insertAll(Context ctx, MobileDataArtifact[] lMobileDataArtifact)  {
        new insertAsync(INSTANCE.mobileDataDao() , lMobileDataArtifact).execute();
    }
    public   class insertAsync extends AsyncTask<Void, Void, Void> {
        private DaoInterface mAsyncTaskDao;
        MobileDataArtifact[] mData;
        insertAsync(DaoInterface dao,  MobileDataArtifact[] mData) {
            mAsyncTaskDao = dao;
            this.mData=mData;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao. insertAll(mData);
            // display the list here


            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // display the items here
            inItView(mData);
        }
    }

    /**
     * In it data on card view
     * **/

    public void inItView( MobileDataArtifact[] mData){
        if(mData!=null && mData.length>0){

        }
    }


    public  void fetchAllLocalCachedData(Context ctx)  {
        new getMobileDataAsync(INSTANCE.mobileDataDao()).execute();
    }
    public   class getMobileDataAsync extends AsyncTask<Void, Void, Void> {
        private DaoInterface mAsyncTaskDao;
        MobileDataArtifact[] mData;
        List<MobileDataArtifact>  mArrayListData= new ArrayList<MobileDataArtifact>();
        getMobileDataAsync(DaoInterface dao) {
            mAsyncTaskDao = dao;
            this.mData=mData;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mArrayListData = mAsyncTaskDao. getAllMobileData("2008");
            // display the list here
            mData  = (MobileDataArtifact[])mArrayListData.toArray();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            inItView(mData);
        }
    }
}
