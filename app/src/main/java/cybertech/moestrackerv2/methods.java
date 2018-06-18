package cybertech.moestrackerv2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by CyberTech on 7/31/2017.
 * SAMUEL ADAKOLE
 * Phone : 07038620440
 * Email: stagent24@gmail.com
 * First Degree : Cyber Security Science
 * School : F U T MINNA
 */
public class methods {

    static ProgressDialog progressDialog;

    public static boolean isOnLine(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            Toast.makeText(context, "Network Connection successfully!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public static boolean saveData(Context context, String username, String email, String phoneNo, String confirmPin, boolean registered) {
        //int deviceCounter = 0;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        // editor.putString("trackerid", trackerID);
        //editor.putString("crn", crn);
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("phoneno", phoneNo);
        editor.putString("pin", confirmPin);
        // editor.putString("devicephoneno", devicephone);
        editor.putBoolean("registered", registered);
        // editor.putInt("deviceCounter", deviceCounter);
        return editor.commit();
    }

    public static String getPref(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String result = preferences.getString(key, "");
        return result;
    }

    public static boolean getswitch(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean result = preferences.getBoolean(key, true);
        return result;
    }

    public static boolean checkSettings(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean result = preferences.getBoolean(key, false);
        return result;
    }

    public static boolean resetPassword(Context context, String newPin) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("pin");
        editor.putString("pin", newPin);
        boolean result = editor.commit();
        return result;
    }

    public static void showmsg(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    public static void showmsgwithCancel(Context context, String title, String msg, DialogInterface.OnCancelListener oncancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setOnCancelListener(oncancel);
        builder.show();
    }

    public static void clearFields(EditText editText1, EditText editText2) {
        if (editText2 == null) {
            editText1.setText("");
        } else {
            editText1.setText("");
            editText2.setText("");
        }
    }

    public static void showOK(Context context, String title, String msg, DialogInterface.OnClickListener onCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        if (onCancel != null) {
            builder.setNegativeButton("OK", onCancel);
        } else {
            DialogInterface.OnClickListener cancel2 = null;
            builder.setNeutralButton("OK", cancel2);
        }
        builder.show();
    }

    public static String generateOTP(Context context) {
        String numbers = "0123456789";
        int len = 6;
        // Using random method
        Random rndm_method = new SecureRandom();
        char[] otp = new char[len];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
            stringBuilder.append(otp[i]);
        }
        // saveData(context, "code", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static void saveData(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveData(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void saveData(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void removeData(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(key).commit();
    }

    public static LatLng[] getLocation(final Context context, ArrayList<String> trackerList) {
        final LatLng[] location = new LatLng[26];
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    int count = jsonObject.getInt("count");

                    if (success) {
                        String alpha = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
                        for (int i = 0; i < count; i++) {
                            double latitude = jsonObject.getDouble("latitude" + alpha.charAt(i));
                            double longitude = jsonObject.getDouble("longitude" + alpha.charAt(i));
                            location[i] = new LatLng(latitude, longitude);
                        }

                    } else {
                        showmsg(context, "Information Dialog", "Location is Unavailable!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        progressDialog = ProgressDialog.show(context, null, "Updating device location...please wait", true, true);
        GetLocation getLocation = new GetLocation(trackerList, listener);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(getLocation);
        return location;
    }

    public static void addTracker(Context context, String trackerID, String crn, String devicePhoneno) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int counter = preferences.getInt("deviceCounter", -1);
        counter = ++counter;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("trackerid" + counter, trackerID);
        editor.putString("devicephoneno" + counter, devicePhoneno);
        editor.putString("crn" + counter, crn);
        editor.apply();
        resetDeviceCouter(context, counter);
    }

    public static void resetDeviceCouter(Context context, int Counter) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("deviceCounter");
        editor.putInt("deviceCounter", Counter);
        editor.apply();
    }

    public static String[] getTrackerIDs(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String[] trackers = new String[]{};
        int counter = sp.getInt("deviceCounter", -1);
        if (counter < 0) {
            return null;
        } else {
            for (int i = 0; i <= counter; i++) {
                trackers[i] = sp.getString("trackerid" + (i), "");
            }
        }
        return trackers;
    }

    public static String[] getCRNs(Context context, ArrayList<String> CRNlist) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String[] CRNs = new String[]{};
        int counter = sp.getInt("deviceCounter", -1);
        if (counter < 0) {
            return null;
        } else {
            for (int i = 0; i < CRNlist.size(); i++) {
                CRNs[i] = CRNlist.get(i);
            }
        }
        return CRNs;
    }

}


