package com.theekshana.onlineshop.Notification;

import android.os.AsyncTask;
import android.util.Log;

import com.theekshana.onlineshop.Model.Constants;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FCMClient extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {

        SendMesseage(strings[0],strings[1],strings[2]);
        return null;
    }

//
//    public void sendFCMMessage(String token,String Title,String Mes){
//        okhttp3.OkHttpClient client = new OkHttpClient();
//
//        okhttp3.MediaType mediaType = MediaType.parse("application/json");
//        okhttp3.RequestBody body = RequestBody.create(mediaType, "{\r\n   \"to\":\"" + token + "\",\r\n   \"notification\":{\r\n      \"sound\":\"default\",\r\n      \"body\":\"" + Mes + "\",\r\n      \"title\":\"" + Title + "\",\r\n      \"content_available\":true,\r\n      \"priority\":\"high\"\r\n   }\r\n}");
//        okhttp3.Request request = new Request.Builder()
//                .url("https://fcm.googleapis.com/fcm/send")
//                .post(body)
//                .addHeader("authorization", "key=AAAAJbMiaXM:APA91bG_LitfwyMPaPTlTSk7B2mqA7e_mDxR4HZez7EC3qty1M-H1lwK_WTpIWAEOgFk2pM_0nK-cY5KfYkVzrj30V-1WqzwOoWOJZSS7FS2Ge2cj4nFQ0-kACCFHpx33_cAgEAPrCjf") // our Firebase project  server key,........
//                .addHeader("content-type", "application/json")
//                .addHeader("cache-control", "no-cache")
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    public void SendMesseage(String token, String titel,String mes){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n   \"to\":\""+ token +"\",\r\n   \"notification\":{\r\n      \"sound\":\"default\",\r\n      \"body\":\""+ mes +"\",\r\n      \"title\":\""+ titel +"\",\r\n      \"content_available\":true,\r\n      \"priority\":\"high\"\r\n   },\r\n   \"data\":{\r\n      \"sound\":\"default\",\r\n      \"body\":\"test body\",\r\n      \"title\":\"test title\",\r\n      \"content_available\":true,\r\n      \"priority\":\"high\"\r\n   }\r\n}\r\n");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .method("POST", body)
                .addHeader("Authorization", "key=AAAAJbMiaXM:APA91bG_LitfwyMPaPTlTSk7B2mqA7e_mDxR4HZez7EC3qty1M-H1lwK_WTpIWAEOgFk2pM_0nK-cY5KfYkVzrj30V-1WqzwOoWOJZSS7FS2Ge2cj4nFQ0-kACCFHpx33_cAgEAPrCjf")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}