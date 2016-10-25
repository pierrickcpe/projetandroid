package com.example.pierrickvinot.projetandroid.tasks;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.pierrickvinot.projetandroid.models.Attachment;
import com.example.pierrickvinot.projetandroid.models.Message;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class SendPicAsyncTask extends AsyncTask<String, String, Boolean> {

    public SendPicAsyncTask(SendPicListener listener, String login, String pwd, String message, String data) {
        Listener = listener;
        this.login = login;
        this.pwd = pwd;
        this.message = message;
        this.data = data;
    }

    public interface SendPicListener{
        public void onSendPic(boolean result);
    };

    private SendPicListener Listener;
    private String login;
    private String pwd;
    private String message;
    private String data;

    protected Boolean doInBackground(String... credential) {

        String loginURL = "https://training.loicortola.com/chat-rest/2.0/messages";

                try{
            URL url = new URL(loginURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            String basicAuth = "Basic "+ Base64.encodeToString((login+":"+pwd).getBytes(),Base64.NO_WRAP);

            con.setRequestProperty("Authorization", basicAuth);

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            Message destMsg = new Message();

            destMsg.setUuid(UUID.randomUUID().toString());
            destMsg.setLogin(login);
            destMsg.setMessage(message);

            Attachment attachments = new Attachment();
            attachments.setMimeType("image/jpg");
            attachments.setData(data);
            List<Attachment> attachmentList=new ArrayList<>();
            attachmentList.add(attachments);
            destMsg.setAttachments(attachmentList);
            Gson gson = new Gson();
            String json = gson.toJson(destMsg);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(json);
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                return false;
            }

            } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Boolean result) {
        Listener.onSendPic(result);
    }

}

