package com.mobiledata.sg.network.NetworkAPICalls;

import org.json.JSONObject;

import java.util.Map;

public interface ResponseInterface {
    void onResponse(String serverResponse, int requestCode);
}
