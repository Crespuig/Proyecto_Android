package com.example.proyecto_android.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.MapaViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, LocationListener {

    private MapaViewModel slideshowViewModel;
    MapaFragment context = this;
    private GoogleMap mMap;

    private Geocoder geocoder;
    private List<Address> addresses;

    private MarkerOptions marker;
    private Marker markerMyLocation;

    private FloatingActionButton fab;

    private LocationManager locationManager;
    private Location currebtLocation;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(this);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapa);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // MI LOCALIZACION
        /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        LatLng palMarquesMalf = new LatLng(39.46975, -0.37739);

        marker = new MarkerOptions();
        marker.position(palMarquesMalf);
        marker.title("Mi marcador");
        marker.draggable(true);
        marker.snippet("Esto es una caja de texto dond modificar los datos");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on));

        mMap.addMarker(marker);

        /*MarkerOptions mo = new MarkerOptions();
        mo.position(palMarquesMalf);
        mo.title("PALACIO DE LOS MARQUESES DE MALFERIT,O, DE LOS CONDES DE BRIZUELA").draggable(true);
        mMap.addMarker(mo);*/

        CameraPosition camera = new CameraPosition.Builder()
                .target(palMarquesMalf)
                .zoom(18)    // limite 21
                .bearing(0)  // 0 - 365º
                .tilt(45)    // efecto 3D limite 90
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(palMarquesMalf));

        geocoder = new Geocoder(getContext(), Locale.getDefault());

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getActivity(), "Click on: \n" +
                       "Lat: " + latLng.latitude + "\n" +
                        "Long: " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(getActivity(), "Long Click on: \n" +
                        "Lat: " + latLng.latitude + "\n" +
                        "Long: " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.hideInfoWindow();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();

                marker.setSnippet(address);
                marker.showInfoWindow();

                /*Toast.makeText(getContext(), "address: " + address + "\n" +
                        "city: " + city + "\n" +
                        "state: " + state + "\n" +
                        "country: " + country + "\n" +
                        "postalCode: " + postalCode + "\n",
                        Toast.LENGTH_LONG).show();*/
            }
        });
    }

    private boolean isGpsEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0){
                // NO tenemos señal de gps
                return false;
            }else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showInfoAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Señal GPS")
                .setMessage("Su señal GPS no está activada. ¿Quiere activarla ahora?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCELAR", null)
                .show();

    }

    @Override
    public void onClick(View view) {
        if(!this.isGpsEnabled()){
            showInfoAlert();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(getContext(), "changed" + location.getProvider(), Toast.LENGTH_LONG).show();
        if (markerMyLocation == null){
            markerMyLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        }else{
            markerMyLocation.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}