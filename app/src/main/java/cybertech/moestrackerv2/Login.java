package cybertech.moestrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText etPin, etusername;
    // TextView errorStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPin = (EditText) findViewById(R.id.et_pinLogin);
        etusername = (EditText) findViewById(R.id.et_Username);
        // errorStatus = (TextView) findViewById(R.id.tvLoginerrormsg);

    }

    int count = 0;

    public void login(View view) {
        String pin = etPin.getText().toString();
        String username = etusername.getText().toString();
        String Xpin = methods.getPref(Login.this, "pin");
        String Xusername = methods.getPref(Login.this, "username");
        if (!pin.isEmpty()) {
            if (!Xpin.trim().isEmpty()) {

                if (username.equals(Xusername)) {
                    if (pin.equals(Xpin)) {
                        startActivity(new Intent(Login.this, TrackDevice.class));
                        etPin.setText("");
                        etusername.setText("");
                        Toast.makeText(Login.this, "Welcome!  " + username, Toast.LENGTH_SHORT).show();
                    } else {
                        count++;
                        Toast.makeText(Login.this, "Invalide pin! ", Toast.LENGTH_SHORT).show();
                        if (count >= 3) {
                            Toast.makeText(Login.this, "Please reset your pin!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, PinRecovery.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                } else {
                    count++;
                    // errorStatus.setText("Invalide Username!");
                    Toast.makeText(Login.this, "Invalide Username!", Toast.LENGTH_SHORT).show();
                    if (count >= 3) {
                        // errorStatus.setText("Please create an Account!");
                        Toast.makeText(Login.this, "Please create an Account!", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                // errorStatus.setText("Sorry no Registered User Found!");
                Toast.makeText(Login.this, "Sorry no Registered User Found!", Toast.LENGTH_SHORT).show();

            }
        } else {
            // errorStatus.setText("Sorry Required field cannot be empty!");
            Toast.makeText(Login.this, "Sorry Required field cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void pinRecovery(View view) {
        startActivity(new Intent(Login.this, PinRecovery.class));
    }
}
