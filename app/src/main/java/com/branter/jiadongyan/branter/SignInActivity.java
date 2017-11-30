package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName;
    private EditText password;
    private Button loadButton;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);
        // Get variables
        userName=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        //Find two buttons , set tags and set listeners
        loadButton = (Button) findViewById(R.id.signin);
        registerButton = (Button) findViewById(R.id.register);
        loadButton.setTag(1);
        loadButton.setOnClickListener(SignInActivity.this);
        registerButton.setTag(2);
        registerButton.setOnClickListener(SignInActivity.this);

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 1:
                idCorrect();
                if (!SaveSharedPreference.getUserID(SignInActivity.this).equals("id")){
                    Toast.makeText(SignInActivity.this,"Signin Success",Toast.LENGTH_LONG).show();
                    Intent intent1=new Intent(SignInActivity.this,MainActivity.class);
                    SaveSharedPreference.setUserName(this,userName.getText().toString());
                    Log.e("sign in user id", SaveSharedPreference.getUserID(this));
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(SignInActivity.this,"Signin failure",Toast.LENGTH_LONG).show();
                    Intent intent2=new Intent(SignInActivity.this,SignInActivity.class);
                    startActivity(intent2);
                }
                break;

            case 2:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void idCorrect(){

        //TODO: Check if username and password is correct

        Thread one = new Thread() {
            public void run() {
                try {
                    CSC client = new CSC();
                    String id = client.signIn(userName.getText().toString(),password.getText().toString());
                    SaveSharedPreference.setUserID(SignInActivity.this,id);
                    Log.e("return:", id);
                } catch(Exception v) {
                }
            }
        };
        one.start();
        try {
            one.join();
        } catch (InterruptedException v) {

        }
        //(Params: username, password) (Return: true or error message)
//        Log.e("size:", Integer.toString(MainActivity.accounts.size()));
//        Log.e("pass:", MainActivity.accounts.get(userName.getText().toString()));
//        String truePassword = MainActivity.accounts.get(userName.getText().toString());
//        if (truePassword == null)
//            Toast.makeText(SignInActivity.this,"no such userName",Toast.LENGTH_LONG).show();
//        else if  (truePassword.equals(password.getText().toString())) return true;

    }

}
