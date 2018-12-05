package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaAdapter;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String TIPO_TRANSACAO = "TRANSACAO";
    public static String TAG_RECEITA = "RECEITA";
    public static String TAG_DESPESA = "DESPESA";

    View view;
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
        view = findViewById(R.id.coordinator_main_activity);
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

        adapter = new ContaAdapter(this, contas);
        recyclerView.setAdapter(adapter);

        setupRecyclerView();

        updateUI(null);
    }

    private void setupRecyclerView() {

        adapter.setClickListener(new ContaAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(), "Conta Clicada: "+position, Toast.LENGTH_SHORT).show();
            }
        });

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
                groupFloatButton.collapse();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //Code relativo a conta
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateUI(null);
                Util.showSnackBarAlert(view, getString(R.string.txtContaAdicionada));
            }

        }
        //Code relativo a a transacao
        if (requestCode == 2){



        }



    }

    private void criarNovaConta() {

        Intent intent = new Intent(this, ContaInfo.class);
        startActivityForResult(intent, 1);

    }

    private void criarNovaReceita() {

        Intent intent = new Intent(this, TransacaoActivity.class);
        intent.setAction(TAG_RECEITA);
        startActivityForResult(intent, 2);

    }

    private void criarNovaDespesa() {

        Intent intent = new Intent(this, TransacaoActivity.class);
        intent.setAction(TAG_DESPESA);
        startActivityForResult(intent, 2);
    }

    private void updateUI(String conta){

        contas.clear();

        if (conta==null) {
            contas.addAll(contaDAO.buscaTodasContas());
        }
        else {
            //contas.addAll(contaDAO.buscaContato(nomeContato));
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
