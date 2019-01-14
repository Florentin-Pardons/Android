package be.flo.messagerie;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.flo.messagerie.Javabean.Message;
import be.flo.messagerie.Javabean.Session;
import be.flo.messagerie.Javabean.Utilisateur;

public class MessageActivity extends AppCompatActivity {

    //Variable
    private Button ajouter, retour;
    private EditText text;

    private TableLayout table_layout;

    private List<Message> listMessage = new ArrayList<Message>();

    private String textM;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        //Bouton
        ajouter = (Button) findViewById(R.id.btn_message_ajouter);
        retour = (Button) findViewById(R.id.btn_message_retour);

        //EditText
        text = (EditText) findViewById(R.id.txtMessage);

        //Table
        table_layout = (TableLayout) findViewById(R.id.table_message);

        //Event
        ajouter.setOnClickListener(CreerMessageMth);
        retour.setOnClickListener(RetourConvMth);

        try {
            String json = getIntent().getExtras().getString("messages");
            json = json.replaceFirst("\\[","");
            json = json.replaceAll("\\]]","]");
            listMessage = new ObjectMapper().readValue(json, new TypeReference<List<Message>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildTable();
    }

    //Creer Message
    View.OnClickListener CreerMessageMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            textM = text.getText().toString();

            if(!textM.equals("")) {
                addline(textM, Session.getInstance().getUser());
                new MessageAjouterAsync(MessageActivity.this).execute(textM, getIntent().getExtras().getString("idconv"));
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MessageActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MessageActivity.this);
                }
                builder.setTitle(R.string.erreur)
                        .setMessage(R.string.erreur_Conv_Ajout_msg)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    };

    //Retour
    View.OnClickListener RetourConvMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            new ConversationAsync(MessageActivity.this).execute();
        }
    };

    //Construction de la table
    private void buildTable() {
        //table_layout.removeAllViews();

        for (Message m : listMessage) {
            final String text = m.getText();
            final Utilisateur nom = m.getUtilisateur();

            final TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if(m.getUtilisateur().getSexe() == true)
                tv.setTextColor(Color.BLUE);
            else
                tv.setTextColor(Color.RED);

            if(m.getUtilisateur().getId() == Session.getInstance().getUser().getId())
                tv.setGravity(Gravity.RIGHT);
            else
                tv.setGravity(Gravity.LEFT);

            tv.setTextSize(20);
            tv.setText(nom.getPseudo() + " : " + text);
            row.setId(index);
            row.addView(tv);

            table_layout.addView(row);

            index += 1;
        }
    }

    private void addline(String text, Utilisateur user)
    {
        final TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        if(user.getSexe() == true)
            tv.setTextColor(Color.BLUE);
        else
            tv.setTextColor(Color.RED);

        tv.setGravity(Gravity.RIGHT);


        tv.setTextSize(20);
        tv.setText(user.getPseudo() + " : " + text);
        row.setId(index);
        row.addView(tv);

        table_layout.addView(row);

        index += 1;
    }

    //Refraichissement de la liste de message
    @Override
    protected void onRestart() {
        super.onRestart();
        new MessageAsync(MessageActivity.this).execute(getIntent().getExtras().getString("idconv"));
    }
}