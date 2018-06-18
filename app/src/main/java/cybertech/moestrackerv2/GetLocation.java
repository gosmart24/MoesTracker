package cybertech.moestrackerv2;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CyberTech on 7/31/2017.
 * SAMUEL ADAKOLE
 * Phone : 07038620440
 * Email: stagent24@gmail.com
 * First Degree : Cyber Security Science
 * School : F U T MINNA
 *
 * "https://cybersmart.000webhostapp.com/kingsbank/"
 */
public class GetLocation extends StringRequest {
    private static final String LOCATION_URI = "https://cybersmart.000webhostapp.com/moes/getlocation.php";
    Map<String, String> params;

    // constructor for getting Locations from the online database server.
    public GetLocation(ArrayList<String> trackerList, Response.Listener<String> listener) {
        super(Method.POST, LOCATION_URI, listener, null);
        params = new HashMap<>();
        Log.d("TAG", "Number of Trackers: " + trackerList.size());
        if (trackerList.size() == 1) {
            params.put("trackerA", trackerList.get(0));

        } else if ((trackerList.size() == 2)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));

        } else if ((trackerList.size() == 3)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));

        } else if ((trackerList.size() == 4)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));

        } else if ((trackerList.size() == 5)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));

        } else if ((trackerList.size() == 6)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));
            params.put("trackerF", trackerList.get(5));

        } else if ((trackerList.size() == 7)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));
            params.put("trackerF", trackerList.get(5));
            params.put("trackerG", trackerList.get(6));

        } else if ((trackerList.size() == 8)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));
            params.put("trackerF", trackerList.get(5));
            params.put("trackerG", trackerList.get(6));
            params.put("trackerH", trackerList.get(7));

        } else if ((trackerList.size() == 9)) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));
            params.put("trackerF", trackerList.get(5));
            params.put("trackerG", trackerList.get(6));
            params.put("trackerH", trackerList.get(7));
            params.put("trackerI", trackerList.get(8));

        } else if (trackerList.size() == 10) {
            params.put("trackerA", trackerList.get(0));
            params.put("trackerB", trackerList.get(1));
            params.put("trackerC", trackerList.get(2));
            params.put("trackerD", trackerList.get(3));
            params.put("trackerE", trackerList.get(4));
            params.put("trackerF", trackerList.get(5));
            params.put("trackerG", trackerList.get(6));
            params.put("trackerH", trackerList.get(7));
            params.put("trackerI", trackerList.get(8));
            params.put("trackerJ", trackerList.get(9));
        }

        params.put("count", String.valueOf(trackerList.size()));


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
