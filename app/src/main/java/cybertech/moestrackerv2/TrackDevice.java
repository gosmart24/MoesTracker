package cybertech.moestrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CyberTech on 7/31/2017.
 * SAMUEL ADAKOLE
 * Phone : 07038620440
 * Email: stagent24@gmail.com
 * First Degree : Cyber Security Science
 * School : FUT MINNA
 */

public class TrackDevice extends AppCompatActivity {

    TinyDB tinyDB;
    public String input, msg = "";
    ArrayList<String> trackerids = null;
    EditText edTrackerID;
    //TextView ederrorTreckDevice;


    // Toast.makeText(TrackDevice.this,"Require field cannot be Empty!",Toast.LENGTH_LONG ).show();
    //methods.clearFields(edTrackerID, null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_device);
        tinyDB = new TinyDB(this);
        edTrackerID = (EditText) findViewById(R.id.et_trakerId_trackDevice);
        trackerids = tinyDB.getListString("trackerids");
    }

    public void onGetTrack(View view) {
        input = edTrackerID.getText().toString();
        if (!input.isEmpty()) {
            int count = 1;
            for (int i = 0; i < trackerids.size(); i++) {
                String trackerID = trackerids.get(i);
                if (trackerID.equals(input)) {
                    // other logic goes here.
                    Intent intent = new Intent(TrackDevice.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    methods.clearFields(edTrackerID, null);
                    // finish();
                    break;
                } else {
                    if (i == (trackerids.size() - 1)) {
                        msg = getString(R.string.invalidID);
                        Toast.makeText(TrackDevice.this, msg, Toast.LENGTH_LONG).show();
                    } else {
                        continue;
                    }

                }
            }

            methods.clearFields(edTrackerID, null);
        } else {
            Toast.makeText(TrackDevice.this, "Require field cannot be Empty!", Toast.LENGTH_LONG).show();
            methods.clearFields(edTrackerID, null);
        }

    }

    // Done with this Method
    public void recoverID(View view) {
        startActivity(new Intent(TrackDevice.this, TrackerIDRecovery.class));
        finish();
    }
}
