package com.example.testing.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.Camp;
import com.example.testing.classes.Coordinates;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Project;
import com.example.testing.databinding.ActivityNgocampLocationMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NGOCampLocationMap extends FragmentActivity implements OnMapReadyCallback {
    private static final int PERMISSION_ID = 44;
    private GoogleMap mMap;
    private ActivityNgocampLocationMapBinding binding;
    private int ngoid;
    private CCDatabase db;
    private ArrayList<Coordinates> coords;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLoc;
    private Button btnGetCoordinates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ngoid = intent.getIntExtra("NGOID", -1);
        db = CCDatabase.getInstance(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ngoid != -1) {
            coords = (ArrayList<Coordinates>) db.campDao().getCoordinatesByNGOID(ngoid);
        }else{
            coords = (ArrayList<Coordinates>) db.campDao().getAllCoordinates();
        }

        binding = ActivityNgocampLocationMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLastLocation();
        btnGetCoordinates = findViewById(R.id.btnGetCampLocationCoOrdinates);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15f));
               marker.showInfoWindow();
               return true;
            }
        });
        if (CurrentAccount.getAccType() == CurrentAccount.NGO_TYPE) {
            btnGetCoordinates.setVisibility(View.VISIBLE);
            btnGetCoordinates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.dialog_get_coordinates, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(NGOCampLocationMap.this);
            builder.setView(dialoglayout);
            builder.setTitle("Project Details");
                    EditText edLatitude,edLongitude,edLocName;
                    edLatitude=dialoglayout.findViewById(R.id.dialog_latitude);
                    edLongitude=dialoglayout.findViewById(R.id.dialog_longitude);
                    edLocName=dialoglayout.findViewById(R.id.dialog_location_name);
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(edLatitude.getText().toString().equals("")||edLongitude.getText().toString().equals("")||edLocName.getText().toString().equals("")){
                        Toast.makeText(NGOCampLocationMap.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                            db.campDao().insertCamp(new Camp (CurrentAccount.getCurrentAccID(),new Coordinates(Double.parseDouble(edLatitude.getText().toString()),Double.parseDouble(edLongitude.getText().toString()),edLocName.getText().toString())));
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    });

        }
        else {
            btnGetCoordinates.setVisibility(View.GONE);
        }

        Log.d("currentLocationFunction", "Current Location found2 : " + currentLoc);

       }



    public Coordinates findNearestLocation(ArrayList<Coordinates> allcoords,Coordinates userCoords){
        if(allcoords.size()==0){
            return null;
        }
        Coordinates shortest=new Coordinates();
        double distance=0,newDistance=0;
        for(int i=0;i<allcoords.size();i++){
            if(i==0){
                distance=Math.sqrt((Math.pow(userCoords.getLatitude()-allcoords.get(i).getLatitude(),2.0))+(Math.pow(userCoords.getLongitude()-allcoords.get(i).getLongitude(),2.0)));
                shortest=allcoords.get(i);
            }
            else{
                newDistance=Math.sqrt((Math.pow(userCoords.getLatitude()-allcoords.get(i).getLatitude(),2.0))+(Math.pow(userCoords.getLongitude()-allcoords.get(i).getLongitude(),2.0)));
                if(distance>newDistance){
                    distance=newDistance;
                    shortest=allcoords.get(i);
                }
            }

        }
        return shortest;

    }


    private boolean getLastLocation() {


        if (checkPermissions()) {

            if (isLocationEnabled()) {

                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            currentLoc = new LatLng(location.getLatitude(),location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(currentLoc).title("Your Location")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

//
                            if (ngoid != -1) {
                                if (coords.size() != 0) {
                                    for (int i = 0; i < coords.size(); i++) {
                                        mMap.addMarker(new MarkerOptions().position(coords.get(i).getLocationLatLng()).title(coords.get(i).getLocationName()));
                                    }
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords.get(0).getLocationLatLng(), 10f));

                                } else {
                                    Toast.makeText(NGOCampLocationMap.this, "Can not find NGO Camp Location", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Coordinates nearest = findNearestLocation(coords,new Coordinates(currentLoc.latitude,currentLoc.longitude,"Your Here"));
                                mMap.addMarker(new MarkerOptions().position(new LatLng(nearest.getLatitude(),nearest.getLongitude())).title(nearest.getLocationName()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10f));
                            }
                        }
                    }
                });
            } else {

                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {


            requestPermissions();

        }
        return true;
    }


    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


}





