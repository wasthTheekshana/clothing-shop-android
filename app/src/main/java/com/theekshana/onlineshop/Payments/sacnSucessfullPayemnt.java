package com.theekshana.onlineshop.Payments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.theekshana.onlineshop.LocationCustomer.HomeDashboard;
import com.theekshana.onlineshop.R;

public class sacnSucessfullPayemnt extends AppCompatActivity {

    private String message = "";
    private String type = "";
    private ImageView imageView;

    private int size = 660;
    private int size_width = 660;
    private int size_height = 264;

    private TextView success_text;
    private ImageView success_imageview;

    private String time;

    private Bitmap myBitmap;
    Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacn_sucessfull_payemnt);
        scan_btn = findViewById(R.id.scan_btn);
        message = getIntent().getStringExtra("orderID");
        type = "QR Code";

        imageView = (ImageView) findViewById(R.id.image_imageview);

        if (message.equals("") || type.equals(""))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(sacnSucessfullPayemnt.this);
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
                imageView.setImageBitmap(bitmap);
            }
        }

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(sacnSucessfullPayemnt.this, HomeDashboard.class);
                startActivity(intent1);
            }
        });
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