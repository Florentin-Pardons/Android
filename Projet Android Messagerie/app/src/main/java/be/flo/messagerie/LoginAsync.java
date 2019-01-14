package be.flo.messagerie;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.flo.messagerie.Javabean.Utilisateur;
import be.flo.messagerie.Javabean.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginAsync extends AsyncTask<String, Void, Utilisateur> {
    private Activity activity;
    private Intent intent;

    public LoginAsync(Activity act) {
        this.activity = act;
    }

    @Override
    protected Utilisateur doInBackground(String... strings) {
        Utilisateur u = null;
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Utilisateur/Login");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] tab = ("pseudo=" + strings[0] + "&motDePasse=" + strings[1]).getBytes();
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
                    u = new ObjectMapper().readValue(scanner.next(), new TypeReference<Utilisateur>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    protected void onPostExecute(Utilisateur utilisateur) {
        super.onPostExecute(utilisateur);

        if (utilisateur != null) {
            Session.getInstance().setUser(utilisateur);
            new ConversationAsync(activity).execute();
        }
        else {
            intent = new Intent(activity, MainActivity.class);
            intent.putExtra("erreurlog", true);
            activity.startActivity(intent);
        }
    }
}
