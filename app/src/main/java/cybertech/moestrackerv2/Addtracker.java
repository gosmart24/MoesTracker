package cybertech.moestrackerv2;

import android.content.DialogInterface;
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
 * School : F U T MINNA
 */

public class Addtracker extends AppCompatActivity {

    EditText ednewTrackerID, ednewCRN, edPhoneNo;
    TinyDB tinyDB;
    ArrayList<String> trackerids = null;
    ArrayList<String> trackercrns = null;
    ArrayList<String> trackerNos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtracker);
        ednewTrackerID = (EditText) findViewById(R.id.et_newtracker);
        ednewCRN = (EditText) findViewById(R.id.et_newCRN);
        edPhoneNo = (EditText) findViewById(R.id.et_newDevicePhoneno);
        tinyDB = new TinyDB(this);

        trackerids = tinyDB.getListString("trackerids");
        trackercrns = tinyDB.getListString("trackercrns");
        trackerNos = tinyDB.getListString("trackernos");

    }

    public void addTracker(View view) {
        String newTrackerid = ednewTrackerID.getText().toString();
        String newCRN = ednewCRN.getText().toString();
        String newDevicePhoneno = edPhoneNo.getText().toString();
        if (!newTrackerid.isEmpty() && !newCRN.isEmpty() && !newDevicePhoneno.isEmpty()) {
            // adding to arraylist
            trackerids.add(newTrackerid);
            trackercrns.add(newCRN);
            trackerNos.add(newDevicePhoneno);
            // adding to sharedpreference
            tinyDB.putListString("trackerids", trackerids);
            tinyDB.putListString("trackercrns", trackercrns);
            tinyDB.putListString("trackernos", trackerNos);

            DialogInterface.OnClickListener onListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Addtracker.this, Login.class));
                }
            };
            methods.showOK(Addtracker.this, "Information Status", "You Tracking device :" + newTrackerid + "\nWith CRN: " + newCRN + "\n  has been added sucessfully", onListener);
        }else {
            Toast.makeText(Addtracker.this,R.string.requiredfifelds, Toast.LENGTH_SHORT).show();
        }

    }
}
