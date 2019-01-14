package be.flo.messagerie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class VilleActivity extends AppCompatActivity {

    //Variable
    private Button retour;
    private TableLayout table_layout;

    private ArrayList<String> cities = new ArrayList<String>() {{
        add("Mons");
        add("Namur");
        add("Anvers");
        add("Charleroi");
        add("Bruges");
        add("Gand");
        add("Liege");
        add("Ostende");
        add("Tournai");
        add("Courtrai");
        add("Louvain");
        add("Chatelet");
        add("Alost");
        add("Malines");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_ville);

        //Bouton
        retour = (Button) findViewById(R.id.btn_choix_ville_retour);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //Table
        table_layout = (TableLayout) findViewById(R.id.table_villes);

        //Appel de la methode de construction de table
        buildTable();
    }

    //Construction de la table
    private void buildTable() {
        for (final String city : cities) {

            final TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView txtView = new TextView(this);
            txtView.setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            txtView.setGravity(Gravity.CENTER);
            txtView.setTextSize(20);
            txtView.setText(city);

            row.addView(txtView);

            //On clic sur la ville
            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(VilleActivity.this, InscriptionActivity.class);
                    intent.putExtra("nomV", city);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            table_layout.addView(row);
        }
    }
}
