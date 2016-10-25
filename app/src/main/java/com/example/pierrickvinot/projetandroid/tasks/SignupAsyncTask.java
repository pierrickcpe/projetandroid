package com.example.pierrickvinot.projetandroid.tasks;

import android.os.AsyncTask;
import android.util.Pair;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrick.vinot on 12/10/16.
 */


public class SignupAsyncTask extends AsyncTask<String, String, Boolean> {
    public interface SignupListener{
        public void onSignup(boolean result,String login , String pwd);
    };

    private SignupListener Listener;
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
            Listener.onSignup(true,login,pwd);
        else
            Listener.onSignup(false,login,pwd);
    }

    public void setSignupListener(SignupListener LL){
        Listener = LL;
    }
}