package cybertech.moestrackerv2;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CyberTech on 6/4/2017.
 **/
public class ConnectionRequest extends StringRequest {


    private static final String REQUEST_URI = "https://cybersmart.000webhostapp.com/moes/moesreg.php";
    private static final String SEND_OTP_URI = "https://cybersmart.000webhostapp.com/moes/smsapi.php";
    private static final String COMMAND_URI = "https://cybersmart.000webhostapp.com/moes/command.php";
    private Map<String, String> params;

    // Device Activation constructor
    public ConnectionRequest(String TrackerID, String Crn, String PhoneNO, String email, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URI, listener, null);
        params = new HashMap<>();
        params.put("trackerid", TrackerID);
        params.put("crn", Crn);
        params.put("phoneno", PhoneNO);
        params.put("email", email);
    }

    // send otp to API server  constructor.
    public ConnectionRequest(String otp, String phoneNO, Response.Listener<String> listener) {
        super(Method.POST, SEND_OTP_URI, listener, null);
        String username = "dcgnifes";
        String password = "dcgnifes";
        String sender = "MoesTracker";
        params = new HashMap();
        params.put("username", username);
        params.put("password", password);
        params.put("message", otp);
        params.put("sender", sender);
        params.put("recipient", phoneNO);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
