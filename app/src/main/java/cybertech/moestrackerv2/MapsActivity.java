package cybertech.moestrackerv2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    MarkerOptions options = new MarkerOptions();
    private GoogleMap mMap;
    TinyDB tinyDB;
    ArrayList<String> trackerids = null;
    ArrayList<String> trackercrns = null;
    ArrayList<String> trackerNos = null;
    ArrayList<Double> latitudes = new ArrayList<>();
    ArrayList<Double> longitutdes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tinyDB = new TinyDB(this);

        trackerids = tinyDB.getListString("trackerids");
        trackercrns = tinyDB.getListString("trackercrns");
        trackerNos = tinyDB.getListString("trackernos");
        latitudes = tinyDB.getListDouble("trackerlats");
        longitutdes = tinyDB.getListDouble("trackerlongs");

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
        drawMarker(mMap);
    }

    private void drawMarker(GoogleMap mMap) {
        //String[] trackerIDs = methods.getTrackerIDs(MapsActivity.this);
        Intent data = getIntent();
        if (trackerids.size() == 1) {
            //LatLng locationA = locations[0];
            LatLng locationA = new LatLng(latitudes.get(0), longitutdes.get(0));
            options.position(locationA).title("Moes Device").snippet("Device: " + trackerids.get(0));
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationA, 10.0f));
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            for (int i = 0; i < trackerids.size(); i++) {
                String tracker = trackerids.get(i);
                LatLng location = new LatLng(latitudes.get(i), longitutdes.get(i));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f));
                options.position(location).title("Moes Device " + (i+1)).snippet("Device: " + tracker);
                mMap.addMarker(options);
                PolylineOptions line = new PolylineOptions();
                line.add(location);
                line.width(5).color(Color.BLUE);
                mMap.addMarker(options);
                mMap.addPolyline(line);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }
}
