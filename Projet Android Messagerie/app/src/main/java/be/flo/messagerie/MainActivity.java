package be.flo.messagerie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Variable
    private EditText login, mp;
    private Button connexion, inscription;
    private Intent inscriptionIntent, conversationIntent;

    private String loginS, mpS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bouton
        connexion = (Button) findViewById(R.id.btnConnecter);
        inscription = (Button) findViewById(R.id.btnInscription);

        //Edit text
        login = (EditText) findViewById(R.id.txtLogin);
        mp = (EditText) findViewById(R.id.txtMp);

        //Instance
        inscriptionIntent = new Intent(MainActivity.this, InscriptionActivity.class);
        conversationIntent = new Intent(MainActivity.this, ConversationAsync.class);

        //Event
        connexion.setOnClickListener(ConnexionMth);
        inscription.setOnClickListener(InscriptionMthd);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isNew = extras.getBoolean("erreurlog", false);
            if (isNew) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle(R.string.erreur)
                        .setMessage(R.string.erreur_loginConf_msg)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    //Connexion
    View.OnClickListener ConnexionMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            loginS = login.getText().toString();
            mpS = mp.getText().toString();

            if(!loginS.equals("") && !mpS.equals("")) {
                new LoginAsync(MainActivity.this).execute(loginS, mpS);
                //finish();
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle(R.string.erreur)
                        .setMessage(R.string.erreur_login_msg)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    };

    //Inscription
    View.OnClickListener InscriptionMthd = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            startActivity(inscriptionIntent);
            finish();
        }
    };
}
