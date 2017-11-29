package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private Button back;
    private String userName;
    private String password;
    private EditText editUser;
    private EditText pwd;
    private EditText pwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUser=(EditText)findViewById(R.id.usernameR);
        pwd=(EditText)findViewById(R.id.passwordR);
        pwdAgain=(EditText)findViewById(R.id.passwordRCheck);
        register= (Button) findViewById(R.id.registerR);
        register.setTag(1);
        register.setOnClickListener(RegisterActivity.this);
        back=(Button)findViewById(R.id.bSigninR);
        back.setTag(2);
        back.setOnClickListener(RegisterActivity.this);

    }

    @Override
    public void onClick(View v) {
        int Tag=(int)v.getTag();
        switch (Tag){
            case 1:
                userName = editUser.getText().toString();
                password = pwd.getText().toString();
                if (insertData()) {
                    Toast.makeText(RegisterActivity.this,"Register Sccuessfully",Toast.LENGTH_LONG).show();
                    Intent intent1=new Intent(RegisterActivity.this,SignInActivity.class);
                    startActivity(intent1);
                } else {
                    Intent intent2=new Intent(RegisterActivity.this,RegisterActivity.class);
                    startActivity(intent2);
                }
                break;
            case 2:
                Intent intent=new Intent(this,SignInActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(RegisterActivity.this,"Register failure, try again",Toast.LENGTH_LONG).show();
                break;

        }

    }

    public boolean insertData(){
        //TODO:Insert username and password into database
        // Params: username, passwor, Return true or error message // check username is duplicated or not
        if (pwdCorrect() && checkValid()) {
            MainActivity.accounts.put(userName, password);
            Log.e("pass：",MainActivity.accounts.get(userName));
            return true;
        }


        return false;

    }

    public boolean pwdCorrect(){
        if(pwd.getText().toString().equals(pwdAgain.getText().toString())){
            return true;
        }
        Toast.makeText(RegisterActivity.this,"Password is not same, type again",Toast.LENGTH_LONG).show();
        return false;
    }

    public boolean checkValid() {
        if (userName.matches("") || password.matches("")) {
            Toast.makeText(RegisterActivity.this,"can't be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
