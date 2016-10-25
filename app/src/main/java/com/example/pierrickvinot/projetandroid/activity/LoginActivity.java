package com.example.pierrickvinot.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pierrickvinot.projetandroid.tasks.LoginAsyncTask;
import com.example.pierrickvinot.projetandroid.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonSignin = (Button) findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){

                EditText login = (EditText)findViewById(R.id.ETlogin);
                String loginValue = login.getText().toString();

                EditText pwd = (EditText)findViewById(R.id.ETpwd);
                String pwdValue = pwd.getText().toString();

                LoginAsyncTask LoginThread = new LoginAsyncTask();
                LoginAsyncTask.LoginListener LoginListener = new LoginAsyncTask.LoginListener(){
                    @Override public void onLogin(boolean result, String login,String pwd){
                        if(result){


                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("LOGIN", login);
                            intent.putExtra("PWD", pwd);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                LoginThread.setLoginListener(LoginListener);
                LoginThread.execute(loginValue,pwdValue);

            }
        });

        Button buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){

                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
