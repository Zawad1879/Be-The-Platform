package me.argha.tonu.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import me.argha.tonu.model.User;

public class MyPreferenceManager {


    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    public SharedPreferences pref;

    // Editor for Shared preferences
    public SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "myPreferences";

    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_EMERGENCY_CONTACT_NAMES = "emergency_contact_names";
    private static final String KEY_EMERGENCY_CONTACT_NUMBERS = "emergency_contact_numbers";
    private static final String KEY_EMERGENCY_CONTACTS = "emergency_contacts";
    private static final String KEY_GENERATE_USER_ID = "generate_user_id";

    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeUser(User user) {
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getName() + ", " + user.getEmail());
    }

    public User getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String id, name, email;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            email = pref.getString(KEY_USER_EMAIL, null);

            User user = new User(id, name, email);
            return user;
        }
        return null;
    }

    public void addNotification(String notification) {

        // get old notifications
        String oldNotifications = getNotifications();

        if (oldNotifications != null) {
            oldNotifications += "|" + notification;
        } else {
            oldNotifications = notification;
        }

        editor.putString(KEY_NOTIFICATIONS, oldNotifications);
        editor.commit();
    }

    public String getNotifications() {
        return pref.getString(KEY_NOTIFICATIONS, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void setEmergencyContactNames(String ... contacts){
        Set<String> emergencyContacts= new HashSet<String>();
        for(String str:contacts){
            emergencyContacts.add(str);
        }
        editor.putStringSet(KEY_EMERGENCY_CONTACT_NAMES,emergencyContacts);
        editor.commit();
    }

    public void setEmergencyContactNames(Set<String> contacts){
        editor.putStringSet(KEY_EMERGENCY_CONTACT_NAMES,contacts);
        editor.commit();
    }

    public Set<String> getEmergencyContactNames(){
        Set<String> contacts;
        contacts=pref.getStringSet(KEY_EMERGENCY_CONTACT_NAMES, new HashSet<String>());
        return contacts;
    }

    public Set<String> getEmergencyContactNumbers() {
        Set<String> contacts;
        contacts=pref.getStringSet(KEY_EMERGENCY_CONTACT_NUMBERS, new HashSet<String>());
        return contacts;
    }
    public void setEmergencyContactNumbers(Set<String> contacts){
        editor.putStringSet(KEY_EMERGENCY_CONTACT_NUMBERS,contacts);
        editor.commit();
    }
    public void setEmergencyContactNumbers(String ... contacts){
        Set<String> emergencyContacts= new HashSet<String>();
        for(String str:contacts){
            emergencyContacts.add(str);
        }
        setEmergencyContactNumbers(emergencyContacts);
    }

    public int generateUserId(int count){
//        int count= pref.getInt(KEY_GENERATE_USER_ID,0);
//        count++;
        editor.putInt(KEY_GENERATE_USER_ID,++count);
        editor.commit();
        return count;
    }

//    public Set<User> getEmergencyContacts() {
//        Set<String> contacts;
//        contacts=pref.getStringSet(KEY_EMERGENCY_CONTACTS, new HashSet<String>());
//        return contacts;
//    }
}
