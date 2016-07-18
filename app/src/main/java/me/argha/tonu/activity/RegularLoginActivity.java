package me.argha.tonu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import me.argha.tonu.R;
import me.argha.tonu.app.EndPoints;
import me.argha.tonu.helpers.MyPreferenceManager;

/**
 * Created by user pc on 5/5/2016.
 */
public class RegularLoginActivity extends AppCompatActivity {
    MyPreferenceManager preferenceManager;
    EditText id;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferenceManager= new MyPreferenceManager(this);

        id =(EditText) findViewById(R.id.content_login_id);
        login =(Button) findViewById(R.id.loginButton);

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegularLoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                String user_id= id.getText().toString();
                AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
                RequestParams params=new RequestParams();
                params.put("user_id", user_id);
                String url= EndPoints.LOGIN;
                asyncHttpClient.post(EndPoints.LOGIN, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Log.e("RegularLoginAct", response.toString(4));
                            if (response.getBoolean("error") == false) {
                                preferenceManager.editor.putBoolean(getResources().getString(R
                                        .string.is_user_logged_in),true);
                                Toast.makeText(RegularLoginActivity.this, "Login complete", Toast.LENGTH_SHORT).show();
                                JSONObject jsonobj=response.getJSONObject("user");
                                String id=jsonobj.getString("user_id");
                                String name=jsonobj.getString("name");
                                String email=jsonobj.getString("email");
                                String phone=jsonobj.getString("phone");
                                preferenceManager.editor.putString("user_id",id);
                                preferenceManager.editor.putString("name",name);
                                preferenceManager.editor.putString("phone",phone);
                                preferenceManager.editor.putString("email",email);
                                preferenceManager.editor.commit();
                                Log.d("JSONid ",id);
                                Log.d("JSONname ",name);
                                Log.d("JSONmail ",email);
                                Log.d("JSONphone ",phone);
                                Log.e("tag","nothing");

                                Intent i = new Intent(RegularLoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(RegularLoginActivity.this, "ID does not match", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                /*Intent i = new Intent(RegularLoginActivity.this, MainActivity.class);
                startActivity(i);*/


            }
        });
    }




}
