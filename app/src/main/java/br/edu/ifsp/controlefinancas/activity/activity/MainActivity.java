package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtHello;
    private FloatingActionButton btnAddReceita, btnAddDespesa, btnNovaConta;
    private FloatingActionsMenu groupFloatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("HOME");

        ContaDAO contaDAO = new ContaDAO(this);
        contaDAO.buscaTodasContas();

        //vinculo da classe com o Layout
        txtHello = (TextView) findViewById(R.id.hello_world);
        btnNovaConta = (FloatingActionButton) findViewById(R.id.btnNovaConta_FloatActionButton);
        btnAddReceita = (FloatingActionButton) findViewById(R.id.btnAddReceita_FloatActionButton);
        btnAddDespesa = (FloatingActionButton) findViewById(R.id.btnAddDespesa_FloatActionButton);
        groupFloatButton = findViewById(R.id.multipleActionsFloatingButton);

        //Listeners de interacoes com a view
        btnNovaConta.setOnClickListener(this);
        btnAddReceita.setOnClickListener(this);
        btnAddDespesa.setOnClickListener(this);
        groupFloatButton.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddReceita_FloatActionButton:
                criarNovaReceita();
                groupFloatButton.collapse();
                break;
            case R.id.btnAddDespesa_FloatActionButton:
                criarNovaDespesa();
                groupFloatButton.collapse();
                break;
            case R.id.btnNovaConta_FloatActionButton:
                criarNovaConta();
                break;
        }
    }

    private void criarNovaConta() {


    }

    private void criarNovaReceita() {
        txtHello.setText("Receita");

    }

    private void criarNovaDespesa() {
        txtHello.setText("Despesa");
    }
}
