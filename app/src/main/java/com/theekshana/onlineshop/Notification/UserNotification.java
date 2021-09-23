package com.theekshana.onlineshop.Notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theekshana.onlineshop.Model.Constants;
import com.theekshana.onlineshop.R;

public class UserNotification extends AppCompatActivity {

    SwitchCompat fcmSwitch;
    private static final String enabledMessage = "Notification are enabled";
    private static final String dibledMessage = "Notification are disable";

    private boolean isChecekd = false;

    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);

        fcmSwitch = findViewById(R.id.notifi_switch);
        firebaseAuth = FirebaseAuth.getInstance();

        //int sheredperfrence
        sharedPreferences = getSharedPreferences("SETTINGS_SP",MODE_PRIVATE);
        isChecekd = sharedPreferences.getBoolean("FCM_ENABLED",false);
        fcmSwitch.setChecked(isChecekd);




        fcmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //check enable notification
                    subscribeTopic();
                } else {
                    //check disable notification
                    UnsubscribeTopic();
                }
            }
        });
//AAAAJbMiaXM:APA91bG_LitfwyMPaPTlTSk7B2mqA7e_mDxR4HZez7EC3qty1M-H1lwK_WTpIWAEOgFk2pM_0nK-cY5KfYkVzrj30V-1WqzwOoWOJZSS7FS2Ge2cj4nFQ0-kACCFHpx33_cAgEAPrCjf

    }

    private void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //success subscribe
                        spEditor = sharedPreferences.edit();
                        spEditor.putBoolean("FCM_ENABLED",true);
                        spEditor.apply();

                        Toast.makeText(UserNotification.this, ""+enabledMessage, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserNotification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("e.getMessage",e.getMessage());
            }
        });
    }

    public void UnsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.FCM_TOPIC)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        spEditor = sharedPreferences.edit();
                        spEditor.putBoolean("FCM_ENABLED",false);
                        spEditor.apply();
                        Toast.makeText(UserNotification.this, ""+dibledMessage, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserNotification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}