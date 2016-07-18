package me.argha.tonu.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import me.argha.tonu.BuildConfig;


public class Util {

    /**
     * Checking for all possible internet providers
     * **/
    public static boolean isConnectedToInternet(Context con){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static void showNoInternetDialog(final Context con) {

        AlertDialog.Builder build=new AlertDialog.Builder(con);
        build.setTitle("No Internet");
        build.setMessage("Internet is not available. Please check your connection");
        build.setCancelable(true);
        build.setPositiveButton("Settings", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                con.startActivity(intent);
            }
        });

        build.setNegativeButton("Cancel", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=build.create();
        alert.show();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static void showToast(Context con,String message){
        Toast.makeText(con, message, Toast.LENGTH_SHORT).show();
    }

    public static void printDebug(String...msg){
        if(BuildConfig.DEBUG){
            String output="";
            for (String m:msg) {
                output+=m;
            }
            Log.d(AppConst.DEBUG_KEY, output);
        }
    }

    public static boolean isGPSOn(Context context) {
        return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps");
    }

    public static ProgressDialog getProgressDialog(Context context,String message){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static String parseDateTime(int dayOfMonth, int month, int year,int hour,int minute) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date date=new Date(calendar.getTimeInMillis());
        SimpleDateFormat timeDate=new SimpleDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss");
        return timeDate.format(date);
    }

    public static String normalDateTime(int dayOfMonth, int month, int year,int hour,int minute) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date date=new Date(calendar.getTimeInMillis());
        SimpleDateFormat timeDate=new SimpleDateFormat("dd'/'MM'/'yy hh':'mm a");
        return timeDate.format(date);
    }

    public static String eventDate(String dateTime) {
        SimpleDateFormat from=new SimpleDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss");
        SimpleDateFormat to=new SimpleDateFormat("EEE, MMM dd, HH':'mm a");
        Date date= null;
        try {
            date = from.parse(dateTime);
            return to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Util.printDebug("Date excep",e.getMessage());
        }
        return dateTime;
    }

    public static String editEventDate(String dateTime) {
        SimpleDateFormat from=new SimpleDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss");
        SimpleDateFormat to=new SimpleDateFormat("dd'/'MM'/'yy hh':'mm a");
        Date date= null;
        try {
            date = from.parse(dateTime);
            return to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Util.printDebug("Date excep",e.getMessage());
        }
        return dateTime;
    }

    public static String parseDateTime(int dayOfMonth, int month, int year) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        Date date=new Date(calendar.getTimeInMillis());
        SimpleDateFormat dateOnly=new SimpleDateFormat("yyyy'-'MM'-'dd");
        return dateOnly.format(date);
    }

    public static String visualDateTime(int dayOfMonth, int month, int year) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        Date date=new Date(calendar.getTimeInMillis());
        SimpleDateFormat dateOnly=new SimpleDateFormat("dd'/'MM'/'yyyy");
        return dateOnly.format(date);
    }

    public static String getLatLonString(LatLng position) {
        double lat;
        double lon;
        lat= position.latitude;
        lon= position.longitude;
        String loc=lat+","+lon;
        Log.e("UTIL.getLatLonString",loc);
        return loc;
    }

    public static LatLng getLatLon(String loc) {
        loc=loc.trim();
        String [] latLon= loc.split(",");
        LatLng latLng= new LatLng(Double.parseDouble(latLon[0]),Double.parseDouble
                (latLon[1]));
        Log.e("getLatLon_UTIL",latLng.latitude+","+latLng.longitude);
        return latLng;
    }
}
