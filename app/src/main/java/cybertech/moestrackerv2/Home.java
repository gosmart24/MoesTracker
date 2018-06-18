package cybertech.moestrackerv2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static cybertech.moestrackerv2.methods.progressDialog;

/**
 * Created by CyberTech on 7/31/2017.
 * SAMUEL ADAKOLE
 * Phone : 07038620440
 * Email: stagent24@gmail.com
 * First Degree : Cyber Security Science
 * School : F U T MINNA
 */

public class Home extends ActionBarActivity {
    ToggleButton switchDevice;
    String trackerID;
    double latitudeA;
    double longitudeA;
    double latitudeB;
    double longitudeB;
    ProgressDialog progress;
    private ConnectionRequest request;
    private RequestQueue queue;
    private String trackerid2;
    private LatLng[] location = null;
    TinyDB tinyDB;
    ArrayList<String> trackerids = null;
    ArrayList<String> trackercrns = null;
    ArrayList<String> trackerNos = null;
    ArrayList<Double> trackerLatitudes = null;
    ArrayList<Double> trackerLongitudes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tinyDB = new TinyDB(this);
        //getting String Arraylist from S.P
        trackerids = tinyDB.getListString("trackerids");
        trackercrns = tinyDB.getListString("trackercrns");
        trackerNos = tinyDB.getListString("trackernos");

        location = new LatLng[10];

        getLocation(Home.this, trackerids);

        // trackerLatitudes = tinyDB.getListDouble("trackerlats");
        // trackerLongitudes = tinyDB.getListDouble("trackerlongs");
        trackerLatitudes = new ArrayList<>();
        trackerLongitudes = new ArrayList<>();

        //converting to array.
        for (int i = 0; i < trackerids.size(); i++) {
            Log.d("TAG", trackerids.get(i));
            // location[i] = new LatLng(trackerLatitudes.get(i), trackerLongitudes.get(i));
        }

        switchDevice = (ToggleButton) findViewById(R.id.toggleButton);
        switchDevice.setChecked(methods.getswitch(Home.this, "devicestatus"));
        switchDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    methods.saveData(Home.this, "devicestatus", true);
                    switchDevice.setBackgroundColor(Color.GREEN);
                    Toast.makeText(Home.this, "Device has been Turn ON.", Toast.LENGTH_SHORT).show();
                } else {
                    methods.saveData(Home.this, "devicestatus", false);
                    Toast.makeText(Home.this, "Device is being Turn OFF.", Toast.LENGTH_SHORT).show();
                    switchDevice.setBackgroundColor(Color.RED);
                    Response.Listener<String> switchListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progress.dismiss();
                            //  request.cancel();
                            // queue.cancelAll("switch");
                        }
                    };
                    String devicePhoneNo = methods.getPref(Home.this, "devicephoneno");
                    String command = "TRUE";
                    progress = ProgressDialog.show(Home.this, null, "Deactivating device please wait...",true,true);
                    request = new ConnectionRequest(command, devicePhoneNo, switchListener);
                    request.setTag("switch");
                    queue = Volley.newRequestQueue(Home.this);
                    queue.add(request);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_trackerdevice) {
            startActivity(new Intent(Home.this, Addtracker.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getLocation(final Context context, final ArrayList<String> trackerList) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Response: " + response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    int count = Integer.valueOf(jsonObject.getString("count"));
                    double latitude;
                    double longitude;

                    if (success) {
                        String alpha = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
                        for (int i = 0; i < count; i++) {
                            latitude = Double.valueOf(jsonObject.getString("latitude" + alpha.charAt(i)));
                            longitude = Double.valueOf(jsonObject.getString("longitude" + alpha.charAt(i)));
                            // longitude = Double.valueOf(jsonObject.getString("longitudeA"));
                            // latitude = Double.valueOf(jsonObject.getString("latitudeA"));
                            Log.d("TAG", "Latitudes: " + latitude + " Longitudes:  " + longitude);
                            // LatLng latLng = new LatLng(latitude, longitude);
                            location[i] = new LatLng(latitude, longitude);
                            // location[i] = latLng;

                            trackerLatitudes.add(latitude);
                            trackerLongitudes.add(longitude);
                        }
                        tinyDB.putListDouble("trackerlats", trackerLatitudes);
                        tinyDB.putListDouble("trackerlongs", trackerLongitudes);
                    } else {
                        methods.showmsg(context, "Information Dialog", "Location is Unavailable!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        progressDialog = ProgressDialog.show(context, null, "Updating device location...please wait", true, true);
        GetLocation getLocation = new GetLocation(trackerList, listener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(getLocation);
    }


    // getting location from the online server.
    // opening map Activity.
    public void openMap(View view) {
        if (location != null) {
            Intent mapintent = new Intent(Home.this, MapsActivity.class);
            // mapintent.putExtra("counter", location.length);
            mapintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mapintent);

        } else {
            showmsg("Information Dialog", "Unable to fetch location!");
        }
    }

    public void getLocation(View view) {
        setViewLocations();
    }

    private void setViewLocations() {
        if (location != null) {
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < trackerids.size(); i++) {
                msg.append("Device ID: ");
                msg.append(trackerids.get(i));
                msg.append("\nLatitude is : ");
                // more work to be done....
                msg.append(location[i].latitude);
                msg.append("\nLongitude is : ");
                msg.append(location[i].longitude);
                msg.append("\n\n");
            }
            showmsg("Device Locations ", msg.toString());
        } else {
            showmsg("Tracker Locations", "Unable to fetch location!");
        }
    }


    public void showmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(msg);
        DialogInterface.OnClickListener onclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        builder.setNeutralButton("OK", onclick);
        builder.show();
    }

}
