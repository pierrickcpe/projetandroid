package com.example.pierrickvinot.projetandroid.tasks;

import android.os.AsyncTask;
import android.util.Base64;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pierrick.vinot on 12/10/16.
 */

public class LoginAsyncTask extends AsyncTask<String, String, Boolean> {
    public interface LoginListener{
        public void onLogin(boolean result,String login , String pwd);
    };

    private LoginListener Listener;
    private String login;
    private String pwd;

    protected Boolean doInBackground(String... credential) {
        int count =credential.length;

        login=credential[0];
        pwd=credential[1];

        String loginURL = "https://training.loicortola.com/chat-rest/2.0/connect/";

        try {

            URL url = new URL(loginURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic "+ Base64.encodeToString((login+":"+pwd).getBytes(),Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", basicAuth );
            int result = urlConnection.getResponseCode();
            if(result==200)
                return true;
            return false;

        }catch(Exception e){
            String error =e.getMessage();
        }

        return null;
    }

    protected void onPostExecute(Boolean result) {
        if(result)
            Listener.onLogin(true,login,pwd);
        else
            Listener.onLogin(false,login,pwd);
    }

    public void setLoginListener(LoginListener LL){
        Listener = LL;
    }
}
