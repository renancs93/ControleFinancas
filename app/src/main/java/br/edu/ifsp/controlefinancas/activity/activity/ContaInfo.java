package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class ContaInfo extends AppCompatActivity implements View.OnClickListener {

    private Conta conta;
    private ContaDAO contaDAO;

    private EditText txtDescricao, txtSaldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_info);

        getSupportActionBar().setTitle(R.string.txtNovaConta);

    }






    @Override
    public void onClick(View v) {

    }
}
