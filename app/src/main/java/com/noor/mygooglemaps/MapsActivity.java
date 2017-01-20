package com.noor.mygooglemaps;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button btnMenu,btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        btnMenu=(Button)findViewById(R.id.menu_Button);
        btnOk=(Button)findViewById(R.id.ok_Button);


        btnMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String mapTypes[] = { "None", "Normal", "Satellite",
                        "Terrain", "Hybrid"};
                AlertDialog.Builder builder = new AlertDialog.Builder(com.noor.mygooglemaps.MapsActivity.this);
                builder.setTitle("Set Map Type");
                builder.setCancelable(false);
                builder.setItems(mapTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int possition) {
                        switch (possition) {
                            case 0:
                                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                                break;

                            case 1:
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                break;

                            case 2:
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;

                            case 3:
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;

                            case 4:
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;

                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int possition) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mapTypeDialog = builder.create();
                mapTypeDialog.show();

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

        // Add a marker in Sydney and move the camera
        LatLng ctg = new LatLng(22.35, 91.81);
        mMap.addMarker(new MarkerOptions().position(ctg).title("Marker in Chittagong"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ctg, 0));



        mMap.getUiSettings().setZoomControlsEnabled(true);  // for zooming

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);  //for showing current location

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //for map type selection

    }



    public void onSearch()
    {
        EditText txtSearch=(EditText)findViewById(R.id.txtSearch);
        String location=txtSearch.getText().toString();

        List<Address> addressList=null;

        if(location.isEmpty())
        {
            Toast.makeText(this, "Please enter a location !", Toast.LENGTH_SHORT).show();
        }

        else if(location!=null)
        {
            Geocoder geocoder=new Geocoder(this);
            try {
                addressList= geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            android.location.Address address=addressList.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in "+location));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }
}
