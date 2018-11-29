package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class ContaInfo extends AppCompatActivity implements View.OnClickListener {

    private Conta conta;
    private ContaDAO contaDAO;

    private View view;
    private EditText txtDescricao, txtSaldo;
    private MaterialButton btnSalvar;

    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_info);

        getSupportActionBar().setTitle(R.string.txtNovaConta);

        //Elementos do Layout
        view = findViewById(R.id.coordinator_conta_info);
        txtDescricao = findViewById(R.id.textInput_ed_descricao_conta_info);
        txtSaldo = findViewById(R.id.textInput_ed_saldo_conta_info);
        btnSalvar = findViewById(R.id.button_salvar_conta_info);

        //Listeners de ações com elementos
        btnSalvar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_salvar_conta_info:
                if (validaCampos()){
                    salvarConta();
                }
                else {
                    Util.showSnackBarAlert(view, getString(R.string.txtCamposNaoPreenchidos));
                }
                break;

        }
    }

    private void salvarConta() {

    }

    public boolean validaCampos(){

        boolean ok = true;

        if (txtDescricao.getText().toString().trim().isEmpty()){
            ok = false;
        }
        if (txtSaldo.getText().toString().trim().isEmpty()){
            ok = false;
        }

        return ok;
    }

}
