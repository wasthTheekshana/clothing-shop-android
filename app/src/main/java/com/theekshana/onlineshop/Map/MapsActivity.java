package com.theekshana.onlineshop.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.theekshana.onlineshop.POJO.mapDistanceObj;
import com.theekshana.onlineshop.POJO.mapTimeObj;
import com.theekshana.onlineshop.R;
import com.theekshana.onlineshop.directionLib.FetchURL;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "GetURL";
    private String town1;
    private Geocoder geocoder;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng curenLoc, dropLoc,newtown;
    public Marker currentmaker;
    public  Marker lastMarker;
    TextView mapdistance,mapTime;
    private Polyline currentPolline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapdistance = findViewById(R.id.distance);
        mapTime = findViewById(R.id.mapTime);
        geocoder = new Geocoder(this);
        town1 = "102/1 Pamunuwa Rd,Maharagama";
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mMap = googleMap;

        UpdateCustomerLocation();
    }

    private int LOCATION_PERMITION=100;
    private void UpdateCustomerLocation() {

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMITION);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location !=null){
                    curenLoc = new LatLng(location.getLatitude(),location.getLongitude());
                    dropLoc = new LatLng(location.getLatitude(),location.getLongitude());

                    BitmapDescriptor icon_tracker = bitmapDescriptor(MapsActivity.this,R.drawable.ic_tracking);
                    BitmapDescriptor icon_curr = bitmapDescriptor(MapsActivity.this,R.drawable.ic_walkto);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(town1, 1);
                        Address address = addresses.get(0);
                        newtown = new LatLng(address.getLatitude(), address.getLongitude());
                        MarkerOptions destinationLocation = new MarkerOptions().icon(icon_tracker).draggable(false).position(newtown).title("Marker in " + town1);
                        lastMarker =  mMap.addMarker(destinationLocation);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newtown,18));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    MarkerOptions currentLocation = new MarkerOptions().icon(icon_curr).draggable(false).position(curenLoc).title("i am Here !");

                    LatLng customerLocation =new LatLng(location.getLatitude(),location.getLongitude());
                    currentmaker =  mMap.addMarker(currentLocation);
                    //  mMap.addMarker(destinationLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(customerLocation));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(18));

                    new FetchURL(){
                        @Override
                        public void onTaskDone(Object... values) {
                            if (currentPolline!=null){
                                currentPolline.remove();
                            }
                            currentPolline = mMap.addPolyline((PolylineOptions) values[0]);
                        }

                        @Override
                        public void onDistanceTaskDone(mapDistanceObj distance) {

                            double startPrice = 40;
                            double per1KM = 40;
                            double extraDistanceM = distance.getDistanceValM()-1000;
                            double extarPrice = ((int)extraDistanceM/1000);

                            double newPrice = startPrice + extarPrice;
                            mapdistance.setText(extarPrice+"Km");
                            Toast.makeText(getApplicationContext(), "Your Ditance: "+ extarPrice, Toast.LENGTH_LONG).show();
//                                    ((CustomerHome)(getActivity())).setEstimateValue(newPrice);
                        }

                        @Override
                        public void onTimeTaskDone(mapTimeObj time) {
                            mapTime.setText(time.getTimeInText());
                            //  Toast.makeText(getActivity(), time.getTimeInText(),Toast.LENGTH_SHORT).show();
//                                    ((CustomerHome)(getActivity())).setduration(time.getTimeInText());
                        }


                    }.execute(getUrl(curenLoc,newtown,"driving"),"driving");

                    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            newtown = marker.getPosition();

                            new FetchURL(){
                                @Override
                                public void onTaskDone(Object... values) {
                                    if (currentPolline!=null){
                                        currentPolline.remove();
                                    }
                                    currentPolline = mMap.addPolyline((PolylineOptions) values[0]);
                                }

                                @Override
                                public void onDistanceTaskDone(mapDistanceObj distance) {

//                                    double startPrice = 40;
//                                    double per1KM = 40;
//                                    double extraDistanceM = distance.getDistanceValM()-1000;
//                                    double extarPrice = ((int)extraDistanceM/1000) * per1KM;
//
//                                    double newPrice = startPrice + extarPrice;
//                                    Toast.makeText(getActivity(), "Your Ditance Price : "+ newPrice, Toast.LENGTH_LONG).show();
//                                    ((CustomerHome)(getActivity())).setEstimateValue(newPrice);
                                }

                                @Override
                                public void onTimeTaskDone(mapTimeObj time) {
                                    //  Toast.makeText(getActivity(), time.getTimeInText(),Toast.LENGTH_SHORT).show();
//                                    ((CustomerHome)(getActivity())).setduration(time.getTimeInText());
                                }


                            }.execute(getUrl(curenLoc,newtown,"driving"),"driving");
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private BitmapDescriptor bitmapDescriptor(FragmentActivity activity, int ic_walkto) {
        Drawable LAYER_1 = ContextCompat.getDrawable(activity,ic_walkto);
        LAYER_1.setBounds(0, 0, LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LAYER_1.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyCDV7cz1phOj9Mli1aglYC5J4r8yrkcIEc";
        Log.d(TAG,"URL:"+url);
        return url;
    }
}