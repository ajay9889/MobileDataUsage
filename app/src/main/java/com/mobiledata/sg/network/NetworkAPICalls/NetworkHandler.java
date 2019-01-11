package com.mobiledata.sg.network.NetworkAPICalls;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkHandler {

    public void onRequest(final ResponseInterface mResponseInterface, final int requestCode, String url){

        try{
            Request request=null;
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try{
                        mResponseInterface.onResponse(response.message(),requestCode);
                        response.body().close();
                        call.cancel();
                    }catch(IllegalStateException e){e.printStackTrace();}
                }
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    mResponseInterface.onResponse(null, requestCode);
                }
            });
        }catch(Exception e){e.printStackTrace();}
    }
}
