package com.example.pierrickvinot.projetandroid.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pierrickvinot.projetandroid.tasks.GetListAsyncTask;
import com.example.pierrickvinot.projetandroid.adapter.ItemAdapter;
import com.example.pierrickvinot.projetandroid.models.Message;
import com.example.pierrickvinot.projetandroid.tasks.PostMessageAsyncTask;
import com.example.pierrickvinot.projetandroid.R;
import com.example.pierrickvinot.projetandroid.tasks.SendPicAsyncTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {

    String login;

    String pwd;

    Base64 data;

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

        Button buttonAddPic = (Button) findViewById(R.id.main_send_pic);
        buttonAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendPicAsyncTask.SendPicListener SendPicListener= new SendPicAsyncTask.SendPicListener(){
                    @Override public void onSendPic(boolean result){
                        if(result){

                            Toast.makeText(MainActivity.this,"Message sent",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,"Send Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                EditText message = (EditText)findViewById(R.id.main_message);
                String messageValue = message.getText().toString();

                try {
                    Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)+"/FatDog.jpeg");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    SendPicAsyncTask SendPicThread = new SendPicAsyncTask(SendPicListener, login,pwd,messageValue,encodedImage);

                    SendPicThread.execute();
                }
                catch (OutOfMemoryError e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


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
