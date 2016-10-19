package com.example.pierrickvinot.projetandroid;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

class GetListAsyncTask extends AsyncTask<String, String, List<Message>> {

    public interface GetListListener{
        public void onGetList(List<Message> result);
    };

    private GetListListener Listener;

    protected List<Message> doInBackground(String... credential) {
        int count =credential.length;

        String loginURL = "https://training.loicortola.com/chat-rest/1.0/messages/";

        for (int i = 0; i < count; i++) {
            loginURL+=credential[i]+"/";
        }

        try {

            URL url = new URL(loginURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int result = urlConnection.getResponseCode();


            Reader reader = new InputStreamReader(urlConnection.getInputStream(), "utf-8");
            Type listType = new TypeToken<List<Message>>(){}.getType();
            Gson gson = new Gson();
            List<Message> list= (List<Message>)gson.fromJson(reader,listType);



            if(result==200)
                return list;


        }catch(Exception e){
            String error =e.getMessage();
        }

        return null;
    }

    protected void onPostExecute(List<Message> result) {
        Listener.onGetList(result);

    }

    public void setLoginListener(GetListListener LL){
        Listener = LL;
    }
}

