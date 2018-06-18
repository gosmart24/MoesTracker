package cybertech.moestrackerv2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class activation extends AppCompatActivity {

    // basic declaration of variables.
    EditText edTrackerID, edCRN, edEmail, edPhoneNo, edNewPin, edConfirmPin, edusername, edDevicePhoneNo;
    String trackerID, email, phoneNo, newPin, confirmPin, crn, username, DevicePhoneNo;

    ProgressDialog progressDialog;
    private String msg = "";
    private ConnectionRequest request;
    private RequestQueue queue;
    TinyDB tinyDB;
    ArrayList<String> trackerids = null;
    ArrayList<String> trackercrns = null;
    ArrayList<String> trackerNos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startup();
        // initialization of variables.

    }

    private void startup() {
        boolean registered = methods.checkSettings(activation.this, "registered");
        if (registered) {
            startActivity(new Intent(activation.this, Login.class));
            finish();
        } else {
            setContentView(R.layout.activity_activation);

            tinyDB = new TinyDB(getApplicationContext());

            trackerids = new ArrayList<>();
            trackercrns = new ArrayList<>();
            trackerNos = new ArrayList<>();

            edTrackerID = (EditText) findViewById(R.id.et_TrackerIDReg);
            edEmail = (EditText) findViewById(R.id.et_emailReg);
            edPhoneNo = (EditText) findViewById(R.id.et_phoneReg);
            edNewPin = (EditText) findViewById(R.id.et_NewPinReg);
            edConfirmPin = (EditText) findViewById(R.id.et_CofirmPinReg);
            edCRN = (EditText) findViewById(R.id.et_CRNReg);
            edDevicePhoneNo = (EditText) findViewById(R.id.et_devicephoneno);
            edusername = (EditText) findViewById(R.id.et_username_Act);
        }
    }

    // this method is called when the Activate Button is clicked.
    public void setupTracker(View view) {

        if (doValidation()) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                // this is the respond method returned by the online database server.
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject status = new JSONObject(response);
                        boolean success = status.getBoolean("success");
                        msg = status.getString("message");
                        if (success) {
                            // data successfully inserted to the online database, saving to internal prefernce settings.
                            boolean registered = true;
                            boolean inserted = methods.saveData(activation.this, username, email, phoneNo, confirmPin, registered);
                            // adding to arraylist
                            trackerids.add(trackerID);
                            trackercrns.add(crn);
                            trackerNos.add(DevicePhoneNo);
                            // adding to sharedpreference
                            tinyDB.putListString("trackerids", trackerids);
                            tinyDB.putListString("trackercrns", trackercrns);
                            tinyDB.putListString("trackernos", trackerNos);

                            if (inserted) {
                                DialogInterface.OnClickListener onOkBTN = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(activation.this, Login.class));
                                        finish();
                                    }
                                };
                                methods.showOK(activation.this, "Status", msg, onOkBTN);
                            } else {
                                msg = "Data insertion error please try again!";
                                methods.showmsg(activation.this, "Status", msg);
                            }
                        } else {

                            msg = "Registration failed!";
                            Toast.makeText(activation.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            progressDialog = ProgressDialog.show(activation.this, "Status", "Activation in Progress...", true, true);
            request = new ConnectionRequest(trackerID, crn, phoneNo, email, listener);
            queue = Volley.newRequestQueue(activation.this);
            queue.add(request);

        } else {
            Toast.makeText(activation.this, msg, Toast.LENGTH_LONG).show();
        }

    }

    // this method is use to validates all inputs.to the app.
    private boolean doValidation() {
        trackerID = edTrackerID.getText().toString();
        email = edEmail.getText().toString();
        phoneNo = edPhoneNo.getText().toString();
        newPin = edNewPin.getText().toString();
        confirmPin = edConfirmPin.getText().toString();
        crn = edCRN.getText().toString();
        DevicePhoneNo = edDevicePhoneNo.getText().toString();
        username = edusername.getText().toString();
        if (!trackerID.isEmpty() && !DevicePhoneNo.isEmpty() && !email.isEmpty() && !phoneNo.isEmpty() && !newPin.isEmpty() && !confirmPin.isEmpty() && !crn.isEmpty() && !username.isEmpty()) {
            if (newPin.equals(confirmPin)) {
                return true;
            } else {
                msg = "Login pin does not Match!";
                return false;
            }
        } else {
            msg = "Required field cannot be Empty!";
            return false;
        }
    }


}

