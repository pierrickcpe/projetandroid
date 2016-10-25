package com.example.pierrickvinot.projetandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierrickvinot.projetandroid.tasks.GetListAsyncTask;
import com.example.pierrickvinot.projetandroid.adapter.ItemAdapter;
import com.example.pierrickvinot.projetandroid.models.Message;
import com.example.pierrickvinot.projetandroid.tasks.PostMessageAsyncTask;
import com.example.pierrickvinot.projetandroid.R;
import com.example.pierrickvinot.projetandroid.tasks.SendPicAsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {

    String login;
    String pwd;
    EditText message;
    String messageValue;
    int TAKE_PHOTO_CODE = 0;
    String dir;
    File newdir;
    String filename;

    ListView listView;
    ItemAdapter adapter;

    GetListAsyncTask GetListThread;
    PostMessageAsyncTask PostMessageThread;
    Button buttonPostMessage;
    Button buttonRefresh;
    Button buttonAddPic;
    Button buttonTakePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        login = extras.getString("LOGIN");
        pwd = extras.getString("PWD");
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        newdir = new File(dir);

        listView = (ListView) findViewById(R.id.listView);
        buttonPostMessage = (Button) findViewById(R.id.main_btn_post);
        buttonRefresh = (Button) findViewById(R.id.main_btn_refresh);
        buttonAddPic = (Button) findViewById(R.id.main_send_pic);
        buttonTakePic = (Button) findViewById(R.id.main_take_pic);
        message = (EditText) findViewById(R.id.main_message);


        refresh();



        buttonPostMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PostMessageThread = new PostMessageAsyncTask();
                PostMessageAsyncTask.PostMessageListener PostMessageListener = new PostMessageAsyncTask.PostMessageListener() {
                    @Override
                    public void onPostMessage(boolean result) {
                        if (result) {
                            Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_LONG).show();
                            message.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Send Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                PostMessageThread.setPostMessageListener(PostMessageListener);

                messageValue = message.getText().toString();

                PostMessageThread.execute(login, pwd, messageValue);
                refresh();
            }
        });


        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        buttonAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendPicAsyncTask.SendPicListener SendPicListener = new SendPicAsyncTask.SendPicListener() {
                    @Override
                    public void onSendPic(boolean result) {
                        if (result) {

                            Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_LONG).show();
                            message.setText("");

                        } else {
                            Toast.makeText(MainActivity.this, "Send Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                messageValue = message.getText().toString();

                try {
                    Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/FatDog.jpeg");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    SendPicAsyncTask SendPicThread = new SendPicAsyncTask(SendPicListener, login, pwd, messageValue, encodedImage);

                    SendPicThread.execute();
                } catch (OutOfMemoryError e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });

        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newdir.mkdirs();
                Calendar c = Calendar.getInstance();
                int date = c.get(Calendar.DATE);
                filename = dir+"pic"+date+".jpg";
                File newfile = new File(filename);
                try {
                    newfile.createNewFile();
                }
                catch (IOException e)
                {
                }

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            try {
                Bitmap bm = BitmapFactory.decodeFile(filename);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                messageValue = message.getText().toString();
                SendPicAsyncTask.SendPicListener SendPicListener = new SendPicAsyncTask.SendPicListener() {
                    @Override
                    public void onSendPic(boolean result) {
                        if (result) {

                            Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Send Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                SendPicAsyncTask SendPicThread = new SendPicAsyncTask(SendPicListener, login, pwd, messageValue, encodedImage);

                SendPicThread.execute();
            } catch (OutOfMemoryError e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void refresh(){
        GetListThread = new GetListAsyncTask();
        GetListAsyncTask.GetListListener GetListListener = new GetListAsyncTask.GetListListener(){
            @Override public void onGetList(List<Message> result) {
                listView = (ListView) findViewById(R.id.listView);
                adapter = new ItemAdapter(result, MainActivity.this, login, pwd);
                listView.setAdapter(adapter);
            }
        };
        GetListThread.setLoginListener(GetListListener);
        GetListThread.execute(login,pwd);
    }

}
