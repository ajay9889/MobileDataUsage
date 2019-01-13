package com.mobiledata.sg.network.NetworkAPICalls;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class NetworkHandler {
    public void onRequest(final ResponseInterface mResponseInterface, final int requestCode, String url){
        try{
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder.addQueryParameter("resource_id","a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
            String new_url = urlBuilder.build().toString();
            Request request = new Request.Builder().url(urlBuilder.build()).build();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try{
                        if(response.isSuccessful()){
                            String responses_ = response.body().string();
                            mResponseInterface.onResponse(responses_ , requestCode);
                        }else{
                            mResponseInterface.onResponse(response.message(),requestCode);
                        }
                        response.body().close();
                        call.cancel();
                    }catch(IllegalStateException e){e.printStackTrace();}

                }
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    mResponseInterface.onResponse(e.getMessage(), requestCode);
                }
            });
        }catch(Exception e){e.printStackTrace();}
    }
}
