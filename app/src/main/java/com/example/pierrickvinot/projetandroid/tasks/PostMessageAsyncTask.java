package com.example.pierrickvinot.projetandroid.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class PostMessageAsyncTask extends AsyncTask<String, String, Boolean> {
    public interface PostMessageListener{
        public void onPostMessage(boolean result);
    };

    private PostMessageListener Listener;
    private String login;
    private String pwd;
    private String message;

    protected Boolean doInBackground(String... credential) {
        int count =credential.length;

        login=credential[0];
        pwd=credential[1];
        message=credential[2];

        String loginURL = "https://training.loicortola.com/chat-rest/1.0/messages/"+login+"/"+pwd;

        StringBuffer chaine = new StringBuffer("");
        try{
            URL url = new URL(loginURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            JSONObject json = new JSONObject();

            UUID uuid = UUID.randomUUID();

            json.put("uuid",uuid);
            json.put("login",login);
            json.put("message", message);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(json.toString());
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
        }catch (JSONException e) {

            // Do something to recover ... or kill the app.
        }

        return null;
    }

    protected void onPostExecute(Boolean result) {
        if(result)
            Listener.onPostMessage(true);
        else
            Listener.onPostMessage(false);
    }

    public void setPostMessageListener(PostMessageListener LL){
        Listener = LL;
    }
}