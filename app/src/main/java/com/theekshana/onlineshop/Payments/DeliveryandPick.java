package com.theekshana.onlineshop.Payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.Map.MapsActivity;
import com.theekshana.onlineshop.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DeliveryandPick extends AppCompatActivity {

    TextView timer;
    Button scanQr,trackMap;
    private String message = "";
    private int size = 660;
    private String type = "";
    private Bitmap myBitmap;
    ImageButton backbtn;
    private String time;
    private FirebaseAuth firebaseAuth;
    private String OderId;
    TextView inPrograssRound,confrimRound,packRound,finishRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryand_pick);
        timer = findViewById(R.id.timer);
        scanQr = findViewById(R.id.scanQr);
        backbtn = findViewById(R.id.backbtn);
        trackMap = findViewById(R.id.trackMap);
        inPrograssRound = findViewById(R.id.inPrograssRound);
        confrimRound = findViewById(R.id.confrimRound);
        packRound = findViewById(R.id.packRound);
        finishRound = findViewById(R.id.finishRound);
        firebaseAuth = FirebaseAuth.getInstance();
        OderId = getIntent().getStringExtra("OderId");
        loadOderDetails();
        long duration = TimeUnit.MINUTES.toMillis(30);
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                Toast.makeText(DeliveryandPick.this, "CountDown End", Toast.LENGTH_SHORT).show();
            }
        }.start();

        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openBottomPage(OderId);
            }
        });

        trackMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track =  new Intent(DeliveryandPick.this, MapsActivity.class);
                startActivity(track);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track =  new Intent(DeliveryandPick.this, HomeDashboard.class);
                startActivity(track);
                finish();
            }
        });
    }

    private void loadOderDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref.child(firebaseAuth.getUid()).child("Orders").child(OderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String oderBy = ""+snapshot.child("oderBy").getValue();
                String orderId = ""+snapshot.child("orderId").getValue();
                String orderCost = ""+snapshot.child("orderCost").getValue();
                String oderAddress = ""+snapshot.child("oderAddress").getValue();
                String deliveryStatus = ""+snapshot.child("deliveryStatus").getValue();
                String orderStatus = ""+snapshot.child("orderStatus").getValue();
                String orderTime = ""+snapshot.child("orderTime").getValue();

//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(Long.parseLong(orderTime));
//                String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();

                if (orderStatus.equals("In Progress")){
                    inPrograssRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Finished")){
                    finishRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Cancelled")){
                    finishRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Confrim")){
                    confrimRound.setBackgroundResource(R.drawable.round_shape);
                }else if (orderStatus.equals("Packaging")){
                    packRound.setBackgroundResource(R.drawable.round_shape);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void openBottomPage(String OderId) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DeliveryandPick.this);
        bottomSheetDialog.setContentView(R.layout.qr_code_scan);

        ImageButton backBtn = bottomSheetDialog.findViewById(R.id.backBtn);
        ImageView productIconIv = bottomSheetDialog.findViewById(R.id.productIconIv);
        message = OderId;
        type = "QR Code";


        if (message.equals("") || type.equals(""))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(DeliveryandPick.this);
            dialog.setTitle("Error");
            dialog.setMessage("Invalid input!");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //  do nothing
                }
            });
            dialog.create();
            dialog.show();
        }
        else
        {
            Bitmap bitmap = null;
            try
            {
                bitmap = CreateImage(message, type);
                myBitmap = bitmap;
            }
            catch (WriterException we)
            {
                we.printStackTrace();
            }
            if (bitmap != null)
            {
                productIconIv.setImageBitmap(bitmap);
            }
        }
        bottomSheetDialog.show();

    }
    public Bitmap CreateImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            case "QR Code": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}