package cybertech.moestrackerv2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class CRNRecovery extends AppCompatActivity {

    EditText edphoneno;
    TextView edmsg;
    ConnectionRequest connectionRequest;
    RequestQueue requestQueue;
    String phone;
    ProgressDialog progressDialog;

    String otp;
    private String Xphone;
    ArrayList<String> crnList, phoneList;
    TinyDB tinyDB;
    private String crn;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crnrecovery);
        edphoneno = (EditText) findViewById(R.id.et_CRN_recovery);
        edmsg = (TextView) findViewById(R.id.tvmsg_CRN_recovery);
        Xphone = methods.getPref(CRNRecovery.this, "phoneno");
        edmsg.setText(R.string.Enterregno);
        tinyDB = new TinyDB(this);
        crnList = tinyDB.getListString("trackercrns");
        phoneList = tinyDB.getListString("trackernos");

    }

    public void sendOtp(View view) {
        phone = edphoneno.getText().toString();
        if (!phone.isEmpty()) {
            int i;
            for ( i = 0; i < phoneList.size(); i++) {
                if (phone.equals(phoneList.get(i))) {
                    crn = crnList.get(i);
                    DialogInterface.OnClickListener onCancel = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CRNRecovery.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    };
                    methods.showOK(CRNRecovery.this, "Information Dialog", "Your CRN is : " + crn + "\nThank you", onCancel);
                }else {
                    count ++;
                    if (i == (phoneList.size() - 1)) {
                        Toast.makeText(this, "Invalid phone Number! please try again", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        } else {
            Toast.makeText(CRNRecovery.this, R.string.requiredfifelds, Toast.LENGTH_SHORT).show();
        }
    }


}

