package be.flo.messagerie;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import be.flo.messagerie.Javabean.Message;

public class MessageAsync extends AsyncTask<String, String, List<Message>> {
    private Activity activity;
    private Intent intent;
    private String id;

    public MessageAsync(Activity activity){this.activity=activity;}


    @Override
    protected List<Message> doInBackground(String... strings) {
        List<Message> messages = new ArrayList<Message>();
        id=strings[0];
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Message/Get?id="+id);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
            is.close();
            urlConnection.disconnect();
            messages = new ObjectMapper().readValue(result, new TypeReference<List<Message>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
        super.onPostExecute(messages);
        intent = new Intent(activity, MessageActivity.class);

        List list = new ArrayList();
        list.add(messages);

        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        intent.putExtra("messages", json);
        intent.putExtra("idconv", id);
        activity.startActivity(intent);
    }
}