package be.flo.messagerie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConversationAjouterActivity extends AppCompatActivity {

    //Variable
    private EditText nom;
    private Button ajouter, retour;
    private Intent conversationIntent;
    private String nomC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_conversation);

        //Bouton
        ajouter = (Button) findViewById(R.id.btnCreerConversation);
        retour = (Button) findViewById(R.id.btn_creer_conversation_retour);

        //Edit text
        nom = (EditText) findViewById(R.id.txtConv);

        //Instance
        conversationIntent = new Intent(ConversationAjouterActivity.this, ConversationActivity.class);

        //Event
        ajouter.setOnClickListener(AjouterMth);
        retour.setOnClickListener(RetourMthd);
    }

    //Creation
    View.OnClickListener AjouterMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            nomC = nom.getText().toString();

            if(!nomC.equals("")) {
                new ConversationAjouterAsync(ConversationAjouterActivity.this).execute(nomC);
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ConversationAjouterActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ConversationAjouterActivity.this);
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

    //Retour vers la liste de conversation
    View.OnClickListener RetourMthd = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            new ConversationAsync(ConversationAjouterActivity.this).execute();
            startActivity(conversationIntent);
            finish();
        }
    };
}
