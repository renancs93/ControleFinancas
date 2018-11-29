package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaAdapter;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton btnAddReceita, btnAddDespesa, btnNovaConta;
    private FloatingActionsMenu groupFloatButton;

    private List<Conta> contas = new ArrayList<>();

    private ContaDAO contaDAO ;
    private RecyclerView recyclerView;

    private ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("HOME");

        contaDAO = new ContaDAO(this);

        //vinculo da classe com o Layout
        btnNovaConta = (FloatingActionButton) findViewById(R.id.btnNovaConta_FloatActionButton);
        btnAddReceita = (FloatingActionButton) findViewById(R.id.btnAddReceita_FloatActionButton);
        btnAddDespesa = (FloatingActionButton) findViewById(R.id.btnAddDespesa_FloatActionButton);
        groupFloatButton = findViewById(R.id.multipleActionsFloatingButton);

        //RecyclerView de exibição das contas
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista_contas);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //Listeners de interacoes com a view
        btnNovaConta.setOnClickListener(this);
        btnAddReceita.setOnClickListener(this);
        btnAddDespesa.setOnClickListener(this);
        groupFloatButton.setOnClickListener(this);

        adapter = new ContaAdapter(this, contaDAO.buscaTodasContas());
        recyclerView.setAdapter(adapter);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void criarNovaConta() {

        Intent intent = new Intent(this, ContaInfo.class);
        startActivityForResult(intent, 1);

    }

    private void criarNovaReceita() {


    }

    private void criarNovaDespesa() {

    }
}
