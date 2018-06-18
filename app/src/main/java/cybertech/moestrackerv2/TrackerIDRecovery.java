package cybertech.moestrackerv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class TrackerIDRecovery extends AppCompatActivity {

    EditText edCrN;
    // TextView error_recovTrackID;
    int count = 0;
    TinyDB tinyDB;
    ArrayList<String> trackerids = null;
    ArrayList<String> trackercrns = null;
    ArrayList<String> trackerNos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_idrecovery);

        tinyDB = new TinyDB(this);
        trackerids = tinyDB.getListString("trackerids");
        trackercrns = tinyDB.getListString("trackercrns");
        trackerNos = tinyDB.getListString("trackernos");

        edCrN = (EditText) findViewById(R.id.et_CRN_TrackID);
    }

    public void openCrnRec(View view) {
        startActivity(new Intent(TrackerIDRecovery.this, CRNRecovery.class));
        finish();
    }

    public void getTrackerID(View view) {
        String input = edCrN.getText().toString();

        if (!input.isEmpty()) {
            for (int i = 0; i < trackercrns.size(); i++) {
                if (trackercrns.get(i).equals(input)) {
                    DialogInterface.OnClickListener onCancel = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(TrackerIDRecovery.this, Login.class));
                            finish();
                        }
                    };
                    String msg = "Your Tracker ID is : " + trackerids.get(i);
                    methods.showOK(TrackerIDRecovery.this, "Information Dialog", msg, onCancel);
                    break;

                } else {
                    count++;
                    if (i == (trackercrns.size() - 1)) {
                        Toast.makeText(TrackerIDRecovery.this, "Invalid CRN!", Toast.LENGTH_LONG).show();
                        edCrN.setText("");

                    }
                }
            }

        } else {
            Toast.makeText(TrackerIDRecovery.this, R.string.requiredfifelds, Toast.LENGTH_LONG).show();
            edCrN.setText("");
        }

    }

    public void showmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(TrackerIDRecovery.this, TrackDevice.class));
                finish();
            }
        });
    }
}
