package com.example.pierrickvinot.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pierrickvinot.projetandroid.R;
import com.example.pierrickvinot.projetandroid.tasks.SignupAsyncTask;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button buttonSignin = (Button) findViewById(R.id.signup_btn_signup);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = (EditText)findViewById(R.id.signup_username);
                String loginValue = username.getText().toString();

                EditText pwd = (EditText)findViewById(R.id.signup_password);
                String pwdValue = pwd.getText().toString();

                EditText confirmpwd = (EditText)findViewById(R.id.signup_confirm_password);
                String ConfirmpwdValue = confirmpwd.getText().toString();

                if(!pwdValue.equals(ConfirmpwdValue))
                    Toast.makeText(SignupActivity.this,"Passwords don't match",Toast.LENGTH_LONG).show();
                else
                {
                    SignupAsyncTask SignupThread = new SignupAsyncTask();
                    SignupAsyncTask.SignupListener SignupListener = new SignupAsyncTask.SignupListener(){
                        @Override public void onSignup(boolean result, String login,String pwd){
                            if(result){

                                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                intent.putExtra("LOGIN", login);
                                intent.putExtra("PWD", pwd);
                                startActivity(intent);
                                Toast.makeText(SignupActivity.this,"Sign up Success",Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(SignupActivity.this,"Sign up Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    SignupThread.setSignupListener(SignupListener);
                    SignupThread.execute(loginValue,pwdValue);

                }
            }
        });
    }
}
