package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsViewActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    //event_id = getEventId



    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        ArrayList<EventData> arr = new ArrayList<EventData>();
        arr.add(new EventData("A1",42,-71,56));
        arr.add(new EventData("A2",42.1,-71.5,32));
        arr.add(new EventData("A3",42.3650, -71.2587,42));
        LatLng sydney = new LatLng(42.3650, -71.2587);
        for (int i = 0; i < arr.size(); i++) {
            LatLng temp = new LatLng(arr.get(i).Lat,arr.get(i).Lng);
            mMap.addMarker(new MarkerOptions().position(temp).title(arr.get(i).name).
                    snippet("Time: "+ arr.get(i).startdate.toString()+"    People: "+arr.get(i).people_number));

        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        startActivity(new Intent("com.branter.jiadongyan.branter.MyAccountActivity"));
    }
}
