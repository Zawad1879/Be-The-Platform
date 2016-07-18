package me.argha.tonu.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import me.argha.tonu.R;
import me.argha.tonu.activity.MainActivity;
import me.argha.tonu.activity.SharedLocationActivity;
import me.argha.tonu.app.Config;
import me.argha.tonu.app.MyApplication;
import me.argha.tonu.model.Message;
import me.argha.tonu.model.User;

public class MyGcmPushReceiver extends GcmListenerService {

    private static final String TAG = MyGcmPushReceiver.class.getSimpleName();
    public static LatLng staticLatLng= null;
    private NotificationUtils notificationUtils;

    /**
     * Called when message is received.
     *
     * @param from   SenderID of the sender.
     * @param bundle Data bundle containing message data as key/value pairs.
     *               For Set of keys use data.keySet().
     */

    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        String title = bundle.getString("title");
//        String message = bundle.getString("message");
        String data = bundle.getString("data");
//        String image = bundle.getString("image");
        String flag = bundle.getString("flag");
//        String timestamp = bundle.getString("created_at");
        Boolean isBackground = Boolean.valueOf(bundle.getString("is_background"));
//        Toast.makeText(MyGcmPushReceiver.this, "Push notification is received", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"Push notification is received");
        Log.e(TAG, "From: " + from);
        Log.e(TAG, "Title: " + title);
        Log.e(TAG, "data: " + data);
        Log.e(TAG, "isBackground: " + isBackground);
        Log.e(TAG, "flag: " + flag);
//        Log.e(TAG, "image: " + image);
//        Log.e(TAG, "timestamp: " + timestamp);
//        Log.e(TAG, "message: " + message);
        if (flag == null)
            return;

//        if(MyApplication.getInstance().getPrefManager().getUser() == null){
//            // user is not logged in, skipping push notification
//            Log.e(TAG, "user is not logged in, skipping push notification");
//            return;
//        }

        switch (Integer.parseInt(flag)) {
//            case Config.PUSH_TYPE_CHATROOM:
//                // push notification belongs to a chat room
//                processChatRoomPush(title, isBackground, data);
//                break;
            case Config.PUSH_TYPE_USER:
                // push notification is specific to user
                try {
                    processUserMessage(title, isBackground, data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils();
//            notificationUtils.playNotificationSound();
//        } else {
//
//            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
//            resultIntent.putExtra("message", message);
//
//            if (TextUtils.isEmpty(image)) {
//                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//            } else {
//                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, image);
//            }
//        }
    }

    /**
     * Processing user specific push message
     * It will be displayed with / without image in push notification tray
     * */
    private void processUserMessage(String title, boolean isBackground, String data) throws JSONException {
        if (!isBackground) {
            boolean isMsgALocation=false;

            JSONObject datObj = new JSONObject(data);
            String imageUrl = datObj.getString("image");
            User user = new User();
            Message message = new Message();
            String location = "UNINITIALIZED LOCATION";
            try {

                if ((datObj.getString("type")).equals("location")){
                    Log.e(TAG,"Notification is received: lcoation");
                    isMsgALocation=true;
                    location= datObj.getString("message");
                    String name= datObj.getString("name");
                    Log.e(TAG,"Values: "+name+", "+location);
                    myCustomNotific(name,location);
                    return;
                }else{
                    JSONObject mObj = datObj.getJSONObject("message");
                    message.setMessage(mObj.getString("message"));
                    message.setId(mObj.getString("message_id"));
                    message.setCreatedAt(mObj.getString("created_at"));

                    JSONObject uObj = datObj.getJSONObject("user");

                    user.setId(uObj.getString("user_id"));
                    user.setEmail(uObj.getString("email"));
                    user.setName(uObj.getString("name"));
                    message.setSender(user);
                }


                // verifying whether the app is in background or foreground
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                    // app is in foreground, broadcast the push message
                    Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                    if(isMsgALocation){
                        pushNotification.putExtra("type", Config.PUSH_TYPE_LOCATION);
                        pushNotification.putExtra("location", location);
                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

                        // check for push notification image attachment
                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, user.getName() + " : " + message.getMessage(), message.getCreatedAt(), resultIntent);
                        } else {
                            // push notification contains image
                            // show it with the image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message.getMessage(), message.getCreatedAt(), resultIntent, imageUrl);
                        }
                    }else{
                        pushNotification.putExtra("type", Config.PUSH_TYPE_USER);
                        pushNotification.putExtra("message", message);
                    }
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils();
                    notificationUtils.playNotificationSound();
                } else {

                    // app is in background. show the message in notification try
                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

                    // check for push notification image attachment
                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, user.getName() + " : " + message.getMessage(), message.getCreatedAt(), resultIntent);
                    } else {
                        // push notification contains image
                        // show it with the image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message.getMessage(), message.getCreatedAt(), resultIntent, imageUrl);
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "json parsing error: " + e.getMessage());
//                Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {
            // the push notification is silent, may be other operations needed
            // like inserting it in to SQLite
        }
    }

    private void myCustomNotific(String sender, String location) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Location received from "+sender)
                        .setContentText(sender+" is in danger. Click to view location!");
// Creates an explicit intent for an Activity in your app
        Intent intent = new Intent(this, SharedLocationActivity.class);
        String latLng []= location.split(",");
        double lat= Double.valueOf(latLng[0]);
        double lon= Double.valueOf(latLng[1]);
        staticLatLng= new LatLng(lat,lon);
        intent.putExtra("latitude",lat);
        intent.putExtra("longitude", lon);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SharedLocationActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
        playNotificationSound();
    }

    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + MyApplication.getInstance().getApplicationContext().getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(MyApplication.getInstance().getApplicationContext(), alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
