package com.example.pierrickvinot.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String login;

    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        login= extras.getString("LOGIN");
        pwd= extras.getString("PWD");

        List<Message> messages = new ArrayList<>();
        GetListAsyncTask GetListThread = new GetListAsyncTask();
        GetListAsyncTask.GetListListener GetListListener = new GetListAsyncTask.GetListListener(){
            @Override public void onGetList(List<Message> result) {
                ListView listView = (ListView) findViewById(R.id.listView);
                ItemAdapter adapter = new ItemAdapter(result, MainActivity.this);
                listView.setAdapter(adapter);
            }
        };
        GetListThread.setLoginListener(GetListListener);
        GetListThread.execute(login,pwd);


        Button buttonPostMessage = (Button) findViewById(R.id.main_btn_post);
        buttonPostMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PostMessageAsyncTask PostMessageThread = new PostMessageAsyncTask();
                PostMessageAsyncTask.PostMessageListener PostMessageListener = new PostMessageAsyncTask.PostMessageListener(){
                    @Override public void onPostMessage(boolean result){
                        if(result){

                            Toast.makeText(MainActivity.this,"Message sent",Toast.LENGTH_LONG).show();

                            EditText message = (EditText)findViewById(R.id.main_message);
                            message.setText("");
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Send Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                PostMessageThread.setPostMessageListener(PostMessageListener);

                EditText message = (EditText)findViewById(R.id.main_message);
                String messageValue = message.getText().toString();

                PostMessageThread.execute(login,pwd,messageValue);
                refresh();

            }
        });

        Button buttonRefresh = (Button) findViewById(R.id.main_btn_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();

            }
        });
    }
    private void refresh(){
        List<Message> messages = new ArrayList<>();
        GetListAsyncTask GetListThread = new GetListAsyncTask();
        GetListAsyncTask.GetListListener GetListListener = new GetListAsyncTask.GetListListener(){
            @Override public void onGetList(List<Message> result) {
                ListView listView = (ListView) findViewById(R.id.listView);
                ItemAdapter adapter = new ItemAdapter(result, MainActivity.this);
                listView.setAdapter(adapter);
            }
        };
        GetListThread.setLoginListener(GetListListener);
        GetListThread.execute(login,pwd);
    }

}
