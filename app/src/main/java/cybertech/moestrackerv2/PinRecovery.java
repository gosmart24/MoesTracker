package cybertech.moestrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class PinRecovery extends AppCompatActivity {

    // TextView errorDis;
    EditText edTrackerID;
    private int count = 0;
    ArrayList<String> trackerList;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_recovery);
        tinyDB = new TinyDB(this);
        edTrackerID = (EditText) findViewById(R.id.et_CRN_Pinrecover);
        trackerList = tinyDB.getListString("trackerids");
    }

    public void onGetPin(View view) {

        String trackerid = edTrackerID.getText().toString();
        if (!TextUtils.isEmpty(trackerid)) {
            for (int i = 0; i < trackerList.size(); i++) {
                if (trackerid.equals(trackerList.get(i))) {
                    Intent intent = new Intent(PinRecovery.this, ResetPin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    count++;
                    Toast.makeText(PinRecovery.this, "Invalid Tracker ID!", Toast.LENGTH_LONG).show();
                    methods.clearFields(edTrackerID, null);
                    if (count >= 3) {
                        count = 0;
                        methods.showmsg(PinRecovery.this, "Helper", "Please call our customer care on +2347038620440 for help. you can visit our website also for quick help thank you.");
                    }
                }
            }
        } else {
            Toast.makeText(PinRecovery.this, R.string.requiredfifelds, Toast.LENGTH_LONG).show();
            methods.clearFields(edTrackerID, null);
        }
    }

    // Done
    public void openTrackerIDRecovery(View view) {
        Intent intent = new Intent(PinRecovery.this, TrackerIDRecovery.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
// Done with this class.