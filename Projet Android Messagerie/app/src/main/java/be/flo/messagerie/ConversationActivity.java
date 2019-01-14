package be.flo.messagerie;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.flo.messagerie.Javabean.Conversation;
import be.flo.messagerie.Javabean.Session;

public class ConversationActivity extends AppCompatActivity {

    //Variable
    private Button creer, deconnexion;
    private Intent creerConvIntent, deconnexionIntent;

    private TableLayout table_layout;

    private Conversation conv;
    private List<Conversation> listConv = new ArrayList<Conversation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_conversations);

        //Bouton
        creer = (Button) findViewById(R.id.btn_choix_conversation_creer);
        deconnexion = (Button) findViewById(R.id.btn_choix_conversation_deconnexion);

        //Table
        table_layout = (TableLayout) findViewById(R.id.table_conversation);

        //Instance
        creerConvIntent = new Intent(ConversationActivity.this, ConversationAjouterActivity.class);
        deconnexionIntent = new Intent(ConversationActivity.this, MainActivity.class);

        //Event
        creer.setOnClickListener(CreerConvMth);
        deconnexion.setOnClickListener(DeConnexionMth);

        //Deserealisation du json en list de conversation + ajustement
        try {
            String json = getIntent().getExtras().getString("conversations");
            json = json.replaceFirst("\\[","");
            json = json.replaceAll("\\]]","]");
            listConv = new ObjectMapper().readValue(json, new TypeReference<List<Conversation>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Construction de la table
        buildTable();
    }

    //Creer Conversation
    View.OnClickListener CreerConvMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            startActivity(creerConvIntent);
        }
    };

    //DeConnexion
    View.OnClickListener DeConnexionMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Session.getInstance().setUser(null);
            startActivity(deconnexionIntent);
        }
    };

    //Construction de la table
    private void buildTable()
    {
        table_layout.removeAllViews();

        int index = 0;

        for (Conversation a : listConv)
        {
            final int id = a.getId();
            final String nom = a.getSujet();

            final TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.LEFT);
            tv.setTextSize(20);
            tv.setText(nom);
            row.setId(index);
            row.addView(tv);

            //On clic vers les messages de la conversation
            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new MessageAsync(ConversationActivity.this).execute(Integer.toString(id));
                }
            });

            table_layout.addView(row);

            index += 1;
        }
    }

    //Rafraichissement
    @Override
    protected void onRestart() {
        super.onRestart();
        new ConversationAsync(ConversationActivity.this).execute();
    }
}
