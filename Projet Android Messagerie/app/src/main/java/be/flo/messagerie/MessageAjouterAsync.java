package be.flo.messagerie;

import android.app.Activity;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import be.flo.messagerie.Javabean.Message;
import be.flo.messagerie.Javabean.Session;

public class MessageAjouterAsync extends AsyncTask<String, Void, Message> {
    private Activity activity;
    private String idconv;

    public MessageAjouterAsync(Activity activity){this.activity=activity;}
    @Override
    protected Message doInBackground(String... strings) {
        Message m = null;
        idconv = strings[1];
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Message/Creer");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String param="text=" + strings[0] + "&utilisateur=" + String.valueOf(Session.getInstance().getUser().getId()) + "&conversation=" + strings[1];
            byte[] tab = param.getBytes();
            urlConnection.setRequestProperty("Content-Length", Integer.toString(tab.length));
            urlConnection.setUseCaches(false);
            DataOutputStream wri = new DataOutputStream(urlConnection.getOutputStream());
            wri.write(tab);
            urlConnection.setConnectTimeout(100000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream stream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
                Scanner scanner = new Scanner(inputStreamReader);
                try {
                    m = new ObjectMapper().readValue(scanner.nextLine(), new TypeReference<Message>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    protected void onPostExecute(Message message) {
        super.onPostExecute(message);
        if (message != null) {
            new MessageAsync(activity).execute(idconv);
        }
    }
}