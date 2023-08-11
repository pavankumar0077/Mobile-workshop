package com.idrbt.atm_nearme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ListView listView;
    private List<AtmLocation> atmLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        listView = findViewById(R.id.listView);
        listView.setVisibility(View.GONE);

        atmLocations = new ArrayList<>();
        atmLocations.add(new AtmLocation("State Bank Of India ATM", new LatLng(17.390747309709223, 78.44114807143532)));
        atmLocations.add(new AtmLocation("Karur Vysya Bank ATM", new LatLng(17.390778691098642, 78.44119223074773)));
        atmLocations.add(new AtmLocation("Icici Bank Atm", new LatLng(17.39086461619988, 78.44223976006604)));
        atmLocations.add(new AtmLocation("HDFC Bank ATM", new LatLng(17.388992835701426, 78.4425735055428)));
        atmLocations.add(new AtmLocation("ICICI Bank Atm", new LatLng(17.392685314894678, 78.43992304664734)));
        atmLocations.add(new AtmLocation("ICICI Bank ATM", new LatLng(17.392359438315882, 78.43778408775188)));
        atmLocations.add(new AtmLocation("State Bank Of India ATM", new LatLng(17.39315807762303, 78.44216364420214)));
        atmLocations.add(new AtmLocation("State Bank ATM", new LatLng(17.39437099550929, 78.4404153877519)));
        atmLocations.add(new AtmLocation("Axis Bank ATM", new LatLng(17.38903711457186, 78.43771072885643)));
        atmLocations.add(new AtmLocation("Axis Bank ATM", new LatLng(17.392913436141797, 78.44183137670638)));



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getAtmNames());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAtmLocationOnMap(atmLocations.get(position));
            }
        });

        findViewById(R.id.buttonFindAtms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearbyAtms();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
            getLastKnownLocation();
        }
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                getLastKnownLocation();
            } else {
                // Handle permission denial
            }
        }
    }

    private void findNearbyAtms() {
        mMap.clear(); // Clear existing markers
        for (AtmLocation location : atmLocations) {
            mMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLocations.get(0).getLatLng(), 12));

        listView.setVisibility(View.VISIBLE);
    }

    private void showAtmLocationOnMap(AtmLocation atmLocation) {
        mMap.clear(); // Clear existing markers
        mMap.addMarker(new MarkerOptions().position(atmLocation.getLatLng()).title(atmLocation.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLocation.getLatLng(), 15));

        LatLng currentLocation = new LatLng(17.39765403313162, 78.44976611797014); // Replace with user's current location
        String directionsUrl = "http://maps.google.com/maps?saddr=" + currentLocation.latitude + "," + currentLocation.longitude +
                "&daddr=" + atmLocation.getLatLng().latitude + "," + atmLocation.getLatLng().longitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(directionsUrl));
        startActivity(intent);
    }

    private List<String> getAtmNames() {
        List<String> atmNames = new ArrayList<>();
        for (AtmLocation location : atmLocations) {
            atmNames.add(location.getName());
        }
        return atmNames;
    }

    @Override
    public void onBackPressed() {
        if (listView.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.GONE);
            mMap.clear(); // Clear existing markers on the map
        } else {
            super.onBackPressed();
        }
    }
}

class AtmLocation {
    private String name;
    private LatLng latLng;

    AtmLocation(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    String getName() {
        return name;
    }

    LatLng getLatLng() {
        return latLng;
    }
}
