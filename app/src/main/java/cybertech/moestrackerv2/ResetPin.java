package cybertech.moestrackerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPin extends AppCompatActivity {

    EditText edPin, edConffirmPin;
    // private TextView errordis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        edPin = (EditText) findViewById(R.id.et_Pin_reset);
        edConffirmPin = (EditText) findViewById(R.id.et_confirmpin_reset);
    }

    public void openlogin(View view) {

        String pin = edPin.getText().toString();
        String conPin = edConffirmPin.getText().toString();
        if (!TextUtils.isEmpty(pin) && !TextUtils.isEmpty(conPin)) {
            if (pin.equals(conPin)) {
                boolean result = methods.resetPassword(ResetPin.this, conPin);
                if (result) {
                    Toast.makeText(ResetPin.this, "Pin has been reset Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPin.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ResetPin.this, "Sorry Unknown error has occurred!", Toast.LENGTH_SHORT).show();
                    methods.clearFields(edPin, edConffirmPin);
                }
            } else {
                Toast.makeText(ResetPin.this, "Sorry entered Pin does not match!", Toast.LENGTH_SHORT).show();
                methods.clearFields(edPin, edConffirmPin);
            }
        } else {
            Toast.makeText(ResetPin.this, "Required field cannot be Empty!", Toast.LENGTH_SHORT).show();
            methods.clearFields(edPin, edConffirmPin);
        }
    }
}

