package me.argha.tonu.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.argha.tonu.model.Contact;

public class EmergencyContactsDataSource extends SQLiteOpenHelper {

    private static final String DB_NAME="ContactsManager";
    private static final int DB_VERSION = 1;
    private final String KEY_ID="ID";
    private final String KEY_NAME="NAME";
    private final String KEY_NUMBER="NUMBER";
    private final String TABLE_CONTACTS="Contacts";
    public EmergencyContactsDataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        String CREATE_TABLE_CONTACTS="Create table "+TABLE_CONTACTS+"(" +
                KEY_ID+ " INTEGER PRIMARY KEY, " +
                KEY_NAME+ " TEXT, " +
                KEY_NUMBER+ " TEXT)";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // Adding new Contact
    public void addContact(Contact contact) throws Exception {
        Log.e("TAG",contact.getName());
        Log.e("TAG",contact.getNumber());
        Log.e("TAG",contact.getId()+"");
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_ID,contact.getId());
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_NUMBER,contact.getNumber());
        if(db.insert(TABLE_CONTACTS,null,values)==-1){
            throw new Exception("Exception occured while adding contact to db, please check user id generation mechanism");
        }
        db.close();
    }

    // Getting single Contact
    public Contact getContact(int id) {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.query(TABLE_CONTACTS,
                new String[]{KEY_ID, KEY_NAME, KEY_NUMBER},
                KEY_ID+"=?",
                new String[]{String.valueOf(id)},
                null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Contact contact= new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        return contact;
    }

    // Getting All Contact
    public List<Contact> getAllContacts() {

        SQLiteDatabase db= this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_CONTACTS;
        Cursor cursor= db.rawQuery(query, null);
        ArrayList<Contact> contacts= new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Contact contact= new Contact(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));
                contacts.add(contact);
            }while (cursor.moveToNext());
            return contacts;
        }
        return null;
    }

    // Getting contacts Count
    public int getContactsCount() {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("Select * from "+TABLE_CONTACTS, null);
        cursor.close();
        return cursor.getCount();
    }
    // Updating single Contact
    public int updateContact(Contact Contact) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_NAME,Contact.getName());
        values.put(KEY_NUMBER,Contact.getNumber());
        return db.update(TABLE_CONTACTS,values,KEY_ID+"=?",new String[]{
                String.valueOf(Contact.getId())
        });
    }

    // Deleting single Contact
    public void deleteContact(Contact Contact) {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_ID+"=?",new String[]{String.valueOf(Contact.getId())});
        db.close();
    }
    public void dropTable(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTS);
        onCreate(db);
    }
}
