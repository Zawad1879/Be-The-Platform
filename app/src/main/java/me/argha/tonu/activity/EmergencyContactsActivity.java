package me.argha.tonu.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.argha.tonu.R;
import me.argha.tonu.helpers.EmergencyContactsDataSource;
import me.argha.tonu.helpers.MyPreferenceManager;
import me.argha.tonu.model.Contact;
import me.argha.tonu.model.User;

public class EmergencyContactsActivity extends AppCompatActivity {


    /*
    ZAWAD, tor kaaj hocche ei activity'r item gula ke clickable kora, click korle menu ashbe
    call, send location, etc. Add korar part ta ami korsi. But select ba delete kori nai. ogula
    dekh. Send Location ta khubi important.
    NB: to add emergency contact, add their name in a set using the methods
    getEmergencyContactNames and append to the set the new name using setEmergencyContactNames.
    Do the same for numbers. Consult these methods in MyPreferenceManager.
     */
    @Bind(R.id.forumMainList)
    ListView forumListView;
    ContactListAdapter contactListAdapter;
    EmergencyContactsDataSource contactsDataSource;
    MyPreferenceManager preferenceManager;
    List<Contact> contactItemArrayList;
    String HARDCODED_NUMBER="01990123387";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactItemArrayList= new ArrayList<Contact>();
        setContentView(R.layout.forum_layout);
        ButterKnife.bind(this);
        preferenceManager= new MyPreferenceManager(this);
        contactsDataSource= new EmergencyContactsDataSource(this);
//        Set<String> contactNames=preferenceManager.getEmergencyContactNames();
//        Set<String> contactNumbers=preferenceManager.getEmergencyContactNumbers();
//        if(contactNames.size()==0 || contactNumbers.size()==0){
//            preferenceManager.setEmergencyContactNames("Zawad","Shahed","Junayed");
//            preferenceManager.setEmergencyContactNumbers("01621209959","01680774007","69");
//        }
        contactItemArrayList= contactsDataSource.getAllContacts();
        forumListView.setAdapter(contactListAdapter=new ContactListAdapter());
    }

//    discarded method
//    private ArrayList<ContactItem> getEmergencyContacts(Set<String> emergencyNames,
//                                                        Set<String>emergencyNumbers) {
//        ArrayList<ContactItem> contacts= new ArrayList<>();
//        Iterator itName, itNumber;
//        itName= emergencyNames.iterator();
//        itNumber= emergencyNumbers.iterator();
//        while(itName.hasNext()){
//            String name= itName.next().toString();
//            String number= itNumber.next().toString();
//            contacts.add(new ContactItem(name,number));
////            itName.next();
////            itNumber.next();
//        }
//        return contacts;
//    }

    @OnClick(R.id.fab)
    public void showContactAddDialog(){
        final EditText nameInputDetailEt=new EditText(this);
        final EditText numberInputDetailEt=new EditText(this);
        final LinearLayout viewLl= new LinearLayout(this);
//        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams
//                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        viewLl.setLayoutParams(lp);
        viewLl.setOrientation(LinearLayout.VERTICAL);
        viewLl.addView(nameInputDetailEt);
        viewLl.addView(numberInputDetailEt);
        nameInputDetailEt.setHint("Name");
        numberInputDetailEt.setHint("Number");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add New Contact")
                .setView(viewLl)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name= nameInputDetailEt.getText().toString();
                        String number= numberInputDetailEt.getText().toString();

                        if (number.length() == 0 || name.length() == 0) {
                            return;
                        }
                        Contact newContact=new Contact(preferenceManager.generateUserId(contactsDataSource.getContactsCount()),name,number);
                        try{
                            contactsDataSource.addContact(newContact);
                        }catch (Exception e){
                            Log.e("TAG","Add contact failed");
                            Toast.makeText(EmergencyContactsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        contactItemArrayList.add(newContact);
                        contactListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        Dialog dialog=builder.create();
        dialog.show();
    }

    class ContactListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(contactItemArrayList==null)
                contactItemArrayList= new ArrayList<>();
            return contactItemArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=getLayoutInflater().inflate(R.layout.single_emergency_item_row,null,false);
            TextView nameTv= (TextView) row.findViewById(R.id.emerNameTv);
            TextView numberTv= (TextView) row.findViewById(R.id.emerContactNumber);
//            numberTv.setEnabled(false);
            nameTv.setText(contactItemArrayList.get(position).getName());
            numberTv.setText(contactItemArrayList.get(position).getNumber());
            return row;
        }
    }
}
