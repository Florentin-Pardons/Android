package be.flo.messagerie;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.flo.messagerie.Javabean.Conversation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConversationAsync extends AsyncTask<String, Void, List<Conversation>> {

    //Variable
    private Activity activity;
    private Intent intent;

    public ConversationAsync(Activity act) {
        this.activity = act;
    }

    @Override
    protected List<Conversation> doInBackground(String... strings) {
        List<Conversation> conversations = null;
        try{
            URL url=new URL("http://androidweb.azurewebsites.net/api/Conversation/GetAll");
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type","text/plain");
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String result = "";
            String line = "";
            while((line=br.readLine())!= null){
                result+=line;
            }
            br.close();
            is.close();
            urlConnection.disconnect();
            conversations=new ObjectMapper().readValue(result, new TypeReference<List<Conversation>>() {});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return conversations;
    }

    @Override
    protected void onPostExecute(List<Conversation> conv) {
        super.onPostExecute(conv);

        //Declaration
        intent = new Intent(activity, ConversationActivity.class);

        List list = new ArrayList();
        list.add(conv);

        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        intent.putExtra("conversations", json);
        activity.startActivity(intent);
    }
}