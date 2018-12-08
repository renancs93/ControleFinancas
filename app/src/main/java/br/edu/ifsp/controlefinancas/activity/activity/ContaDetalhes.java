package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaDetalhesAdapter;
import br.edu.ifsp.controlefinancas.activity.data.CategoriaDAO;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class ContaDetalhes extends AppCompatActivity {

    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;

    private RecyclerView recyclerView;
    private ContaDetalhesAdapter adapter;

    //private List<Transacao> transacaos = new ArrayList<>();
    private List<TransacaoInfo> transacaos = new ArrayList<>();

    Conta conta;
    Transacao transacao;

    TextView txtSaldo;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_detalhes);

        transacaoDAO = new TransacaoDAO(this);

        //vinculo da classe com o Layout
        view = findViewById(R.id.view_conta_detalhes_activity);

        //RecyclerView de exibição das contas
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista_transacoes);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        adapter = new ContaDetalhesAdapter(this, transacaos);
        recyclerView.setAdapter(adapter);

        txtSaldo = (TextView) findViewById(R.id.tv_saldo_conta_transacao);

        if (getIntent().hasExtra(MainActivity.TAG_CONTA)){

            //recuperacao da Conta pela Intent
            this.conta = (Conta) getIntent().getSerializableExtra(MainActivity.TAG_CONTA);

            getSupportActionBar().setTitle(getString(R.string.txtConta)+": "+conta.getDescricao());

            txtSaldo.setText(getText(R.string.txtSaldo)+": "+conta.getSaldo());

            updateUI(conta.getId());
        }
    }

    private void updateUI(long idConta){

        transacaos.clear();
        transacaos.addAll(transacaoDAO.buscaTodasTransacoesPorConta(idConta));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
