package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.ifsp.controlefinancas.R;

public class TransacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent != null){

            String tipoLayout = intent.getAction();

            if (tipoLayout.equals(MainActivity.TAG_RECEITA)) {
                setContentView(R.layout.activity_receita);
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtReceita));
            }
            if (tipoLayout.equals(MainActivity.TAG_DESPESA)) {
                //setContentView(R.layout.activity_receita);
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtDespesa));
            }

        }

    }
}
