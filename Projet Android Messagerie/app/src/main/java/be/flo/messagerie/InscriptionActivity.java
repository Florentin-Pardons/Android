package be.flo.messagerie;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InscriptionActivity extends AppCompatActivity {

    //Variable
    private EditText pseudo, mp;
    private RadioGroup sexRadioGrp;
    private TextView villechoisie;
    private Button choixville, ajouter, retour;
    private Intent mainIntent;
    private String pseudoS, mpS, sexeS, villeS;
    public final static int NUM_REQUETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Bouton
        choixville = (Button) findViewById(R.id.btn_ville);
        ajouter = (Button) findViewById(R.id.btn_inscription_confirmer);
        retour = (Button) findViewById(R.id.btn_inscription_retour);

        //Edit text
        pseudo = (EditText) findViewById(R.id.txtPseudo);
        mp = (EditText) findViewById(R.id.txtMp);

        //Boutton radio
        sexRadioGrp = (RadioGroup) findViewById(R.id.sexRadioGrp);

        //Text View
        villechoisie = (TextView)  findViewById(R.id.textViewVille);

        //Instance
        mainIntent = new Intent(InscriptionActivity.this, MainActivity.class);

        //Event
        choixville.setOnClickListener(ChoixVilleMth);
        ajouter.setOnClickListener(InscriptionMth);
        retour.setOnClickListener(RetourMthd);
    }

    protected void onActivityResult(int num_requete, int code_retour, Intent data)
    {
        if (num_requete == 1)
        {
            if (code_retour == RESULT_OK)
            {
                villechoisie.setText(data.getStringExtra("nomV"));
            }
        }
    }

    //Choix de la Ville (Wizzard)
    View.OnClickListener ChoixVilleMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent(InscriptionActivity.this, VilleActivity.class);
            startActivityForResult(intent, NUM_REQUETE);
        }
    };

    //Inscription
    View.OnClickListener InscriptionMth = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            pseudoS = pseudo.getText().toString();
            mpS = mp.getText().toString();
            sexeS = (sexRadioGrp.getChildAt(0).isSelected()) ? "True" : "False";
            villeS = villechoisie.getText().toString();

            if(!pseudoS.equals("") && !mpS.equals("") && !sexeS.equals("") && !villeS.equals("")) {
                new InscriptionAsync(InscriptionActivity.this).execute(pseudoS, mpS, sexeS, villeS);
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(InscriptionActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(InscriptionActivity.this);
                }
                builder.setTitle(R.string.erreur)
                        .setMessage(R.string.erreur_inscription_msg)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    };

    //Accueil
    View.OnClickListener RetourMthd = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            startActivity(mainIntent);
            finish();
        }
    };
}
