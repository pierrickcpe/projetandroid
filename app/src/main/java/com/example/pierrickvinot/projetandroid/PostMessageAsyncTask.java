package com.example.pierrickvinot.projetandroid;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

    protected Boolean doInBackground(String... credential) {
        int count =credential.length;

        login=credential[0];
        pwd=credential[1];

        String loginURL = "https://training.loicortola.com/chat-rest/2.0/register/";

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

            json.put("login",login);
            json.put("password", pwd);

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