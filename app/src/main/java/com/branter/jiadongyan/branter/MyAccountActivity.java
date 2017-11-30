package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAccountActivity extends AppCompatActivity {
    private String usernamestr;
    private String genderstr;
    private String birthstr;
    private int  hoststr;
    private int joinnum;
    private int postnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button but1 = (Button)findViewById(R.id.profile_update_button);
        Button but2 = (Button)findViewById(R.id.profile_back_button);
        final String current_id = SaveSharedPreference.getUserID(this);

        Thread one = new Thread() {
            public void run() {
                try {
                    CSC client = new CSC();
                    User current_user = client.getUserInformation(current_id);
                    usernamestr = current_user.username;

                    if (current_user.gender)
                        genderstr = "Male";
                    else
                        genderstr = "Female";
                    birthstr = current_user.birthday;
                    hoststr = current_user.num_events_host;
                    joinnum = current_user.num_event_joined;
                    postnum = current_user.num_post;
                    //Log.e("return:", id);
                } catch(Exception v) {
                }
            }
        };
        one.start();
        try {
            one.join();
        } catch (InterruptedException v) {

        }

        TextView username = (TextView)findViewById(R.id.username_tag);
        TextView gender = (TextView)findViewById(R.id.gender_tag);
        TextView birthday = (TextView)findViewById(R.id.birthday_tag);
        TextView hostview = (TextView)findViewById(R.id.hostnumber_tag);
        TextView joinview = (TextView)findViewById(R.id.joinnumber_tag);
        TextView postview = (TextView)findViewById(R.id.postnumber_tag);

        username.setText(usernamestr);
        gender.setText(genderstr);
        birthday.setText(birthstr);

        hostview.setText("Host Events: "+Integer.toString(hoststr));
        joinview.setText("Join Events: "+Integer.toString(joinnum));
        postview.setText("Have PostsInteger.toString(postnum));



        but1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent("com.branter.jiadongyan.branter.ProfileUpdateActivity"));
            }
        });

        but2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


    }

}
