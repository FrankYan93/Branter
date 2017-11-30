package com.branter.jiadongyan.branter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.Toast;
import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class MapsViewActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback{
//LocationListener,GoogleMap.OnMyLocationButtonClickListener
    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ArrayList<Event> listgrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button = (Button)findViewById(R.id.map_back_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        listgrid = new ArrayList<Event>();



       // init();
        listgrid = new ArrayList<>();
        //if (listgrid.isEmpty()) ititData();


        // get all events
        Thread one = new Thread() {
            public void run() {
                try {
                    CSC client = new CSC();
                    Event[] allEvents = client.getAllEvents();
                    for (int i = 0; i < allEvents.length; i++) {
                        GridTest single = new GridTest();
                        Event singleEvent = allEvents[i];
//                        single.setEventTitle(singleEvent.title);
//                        single.setContent(singleEvent.contents);
//                        single.setTime("From " + singleEvent.from.split("T")[0] + " to " + singleEvent.to.split("T")[0]);
//                        single.setHeadphoto("http://www.ayso1236.us/wp-content/uploads/2017/11/cow-cartoon-drawing-monkey-coloring-page.jpg");
//                        single.setImage(FakeImg.img[new Random().nextInt(FakeImg.img.length)]);
//                        single.setId(singleEvent.id);
                        listgrid.add(singleEvent);
                    }
                } catch(Exception v) {
                }
            }
        };

        one.start();
        try {
            one.join();
        } catch (InterruptedException e) {

        }



//        arr.add(new Event("1","A1",null,null,"from","to",42,-71));
//        arr.add(new Event("2","A2",null,null,"from","to",42.1,-71.5));
//        arr.add(new Event("3","A3",null,null,"fromm","to",42.3650, -71.2587));
        LatLng Brandeis = new LatLng(42.3650, -71.2587);
        for (int i = 0; i < listgrid.size(); i++) {
            LatLng temp = new LatLng(listgrid.get(i).lat,listgrid.get(i).lng);
            mMap.addMarker(new MarkerOptions().position(temp).title(listgrid.get(i).title).
                    snippet("Time: from 2019-1-20 to 2019-1-23 People: "+ Integer.toString(i*13+1)));//Need TO DO!!!!

        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.7128,-74.0060)).title("Football Game").snippet("Time: from 2018-5-1 to 2018-5-1 People 12"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(47.6062,-122.3321)).title("Concert").snippet("Time: from 2018-9-12 to 2018-9-12 People 5"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.3662,-71.0621)).title("Celtics Games").snippet("Time: from 2017-12-26 to 2017-12-26 People 36"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.3650,-71.2587)).title("Gourment Festival").snippet("Time: from 2018-2-1 to 2018-2-1 People 8"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Brandeis, 9));
        UiSettings uisettings = mMap.getUiSettings();
        uisettings.setZoomControlsEnabled(true);
        uisettings.setMyLocationButtonEnabled(true);
        //mMap.setOnMyLocationButtonClickListener(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        //mMap.setMyLocationEnabled(true);

        mMap.setOnInfoWindowClickListener(this);


    }




//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//        if (checkLocationPermission()) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//
//                //Request location updates:
//                locationManager.requestLocationUpdates(provider, 400, 1, this);
//            }
//        }
//
//        // Return false so that we don't consume the event and the default behavior still occurs
//        // (the camera animates to the user's current position).
//        return false;
//    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    //Todo!!!
//        Intent intent=new Intent(MainActivity.this,EventDetail.class);
//        GridTest event = listgrid.get(position);
//        intent.putExtra("title", event.getEventTitle());
//        intent.putExtra("time", event.getTime());
//        intent.putExtra("id",event.getId());
//        startActivity(intent);
//        Toast.makeText(MainActivity.this, "clicked on" + (position + 1) + "item", Toast.LENGTH_LONG).show();
     //   startActivity(new Intent("com.branter.jiadongyan.branter.MyAccountActivity"));//Need to DO
    }



//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                new AlertDialog.Builder(this)
//                        .setTitle("allow title")//R.string.title_location_permission)
//                        .setMessage("allow message")//R.string.text_location_permission)
//                        .setPositiveButton("ok!",//R.string.ok,
//                                new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(MapsViewActivity.this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        //Request location updates:
//                        locationManager.requestLocationUpdates(provider, 400, 1, this);
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//
//                }
//                return;
//            }
//
//        }
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        Double lat = location.getLatitude();
//        Double lng = location.getLongitude();
//        Toast.makeText(this, lat.toString()+lng.toString(), Toast.LENGTH_SHORT).show();
//
//
//    }
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }

}
