package com.theekshana.onlineshop.Notification;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.theekshana.onlineshop.Login.loginuser;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private loginuser loginuser;
    public MyFirebaseInstanceIDService() {
    }

    public MyFirebaseInstanceIDService(com.theekshana.onlineshop.Login.loginuser loginuser) {
        this.loginuser = loginuser;
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("tag", "Refreshed token:................ " + refreshedToken);

        if(loginuser!=null){
            loginuser.setFCMToken(refreshedToken);
        }


    }




}
