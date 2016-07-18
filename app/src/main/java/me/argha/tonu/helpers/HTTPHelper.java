package me.argha.tonu.helpers;

import com.loopj.android.http.AsyncHttpClient;

import me.argha.tonu.utils.AppConst;


/**
 * Author: ARGHA K ROY
 * Date: 11/23/2015.
 */
public class HTTPHelper {
    public static AsyncHttpClient getAsyncClient(){
        AsyncHttpClient httpClient=new AsyncHttpClient();
        httpClient.setMaxRetriesAndTimeout(AppConst.MAX_CONNECTION_TRY,AppConst.TIME_OUT);
        return httpClient;
    }
}
