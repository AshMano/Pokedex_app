package org.esiea.manoranjithan_papail.pokdex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Info extends AppCompatActivity {

    protected Button myButton;
    String myString = "Veuillez consulter notre site web pour plus d'informations.";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        myButton = (Button)findViewById(R.id.btnId);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),myString,Toast.LENGTH_LONG).show();
            }
        });
    }
}
