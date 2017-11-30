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
                if (idCorrect()){
                    Intent intent1=new Intent(SignInActivity.this,MainActivity.class);
                    // TODO:create user in database

                    intent1.putExtra("userName",userName.getText().toString());
                    intent1.putExtra("password",password.getText().toString());
                    SaveSharedPreference.setUserName(this,userName.getText().toString());
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

    public boolean idCorrect(){

        //TODO: Check if username and password is correct

        //(Params: username, password) (Return: true or error message)
//        Log.e("size:", Integer.toString(MainActivity.accounts.size()));
//        Log.e("pass:", MainActivity.accounts.get(userName.getText().toString()));
        String truePassword = MainActivity.accounts.get(userName.getText().toString());
        if (truePassword == null)
            Toast.makeText(SignInActivity.this,"no such userName",Toast.LENGTH_LONG).show();
        else if  (truePassword.equals(password.getText().toString())) return true;

        return false;

    }

}
