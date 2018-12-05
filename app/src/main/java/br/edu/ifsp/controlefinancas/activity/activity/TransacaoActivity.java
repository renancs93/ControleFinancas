package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import br.edu.ifsp.controlefinancas.R;

public class TransacaoActivity extends AppCompatActivity {

    private String acao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Intent intent = getIntent();

        if (intent != null){

            acao = intent.getAction();

            if (acao.equals(MainActivity.TAG_RECEITA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtReceita));
            }
            if (acao.equals(MainActivity.TAG_DESPESA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtDespesa));
            }

        }

        //String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoria_transacao ,android.R.layout.simple_spinner_item);
        MaterialBetterSpinner spinnerCategoria = (MaterialBetterSpinner) findViewById(R.id.spinner_categoria_transacao);
        spinnerCategoria.setAdapter(arrayAdapter);


    }
}
