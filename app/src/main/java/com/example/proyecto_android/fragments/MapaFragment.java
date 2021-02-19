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
import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.model.MapaViewModel;
import com.example.proyecto_android.model.Monumento;
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

    private List<Monumento> monumentoList;

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

        monumentoList = this.getAllMonumentos();

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

        anyadirMarcadores(googleMap);

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

        //for (Monumento m: monumentoList) {
            //marker.title("Mi marcador");
            //marker.draggable(true);
            //marker.snippet("Esto es una caja de texto dond modificar los datos");
            //marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on));
        //}

        CameraPosition camera = new CameraPosition.Builder()
                .target(new LatLng(39.46975, -0.37739))
                .zoom(13)    // limite 21
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

    public void anyadirMarcadores(GoogleMap googleMap){
        mMap = googleMap;

        final LatLng archivoReino = new LatLng(39.4723377,-0.3645571);
        final LatLng asiloIglesiaSantaMonica = new LatLng(39.4818788,-0.3748564);
        final LatLng antHospGen = new LatLng(39.470492,-0.381345);
        final LatLng bancajaFachada = new LatLng(39.4736277,-0.3708903);
        final LatLng almudin = new LatLng(39.4765038,-0.3741034);
        final LatLng asiloMarquesCampo = new LatLng(39.4759357,-0.3739955);
        final LatLng asiloAncianosDesamp = new LatLng(39.4813114,-0.376185);
        final LatLng nataliciaSantVFerr = new LatLng(39.4733214,-0.3709665);
        final LatLng portalValldigna = new LatLng(39.4775334,-0.378749);
        final LatLng csaVestuario = new LatLng(39.4759587,-0.3757966);

        mMap.addMarker(new MarkerOptions().position(archivoReino).title("Archivo del Reino de Valencia").snippet("Passeig de l'Albereda, 22, 46010 Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(asiloIglesiaSantaMonica).title("Asilo iglesia Santa Mónica").snippet("Plaça de Santa Mònica, 1, 46009 Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(antHospGen).title("Antiguo hospital general").snippet("Carrer de l'Hospital, 11, 46001 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(bancajaFachada).title("Bancaja fachada").snippet("Plaça de Tetuan, 23, 46003 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(almudin).title("Almudin").snippet("Plaça de Sant Lluís Bertran, 2, 46003 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(asiloMarquesCampo).title("Asilo Marques de campo").snippet("Plaça de l´Arquebisbe, 3, 46003 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(asiloAncianosDesamp).title("Asilo ancianos desamparados").snippet("Carrer de la Mare Teresa Jornet, 1, 46009 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(nataliciaSantVFerr).title("Casa natalicia Sant Vicent Ferrer").snippet("Carrer del Pouet de Sant Vicent, 1, 46003 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(portalValldigna).title("Portal Valldigna").snippet("C/ del Portal de Valldigna, 46003 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));

        mMap.addMarker(new MarkerOptions().position(csaVestuario).title("Casa Vestuario").snippet("Plaça de la Verge, 1, 46001 València, Valencia")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on)));
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

    private List<Monumento> getAllMonumentos(){
        return MiBD.getInstance(getContext()).recuperarMonumentos();
    }
}