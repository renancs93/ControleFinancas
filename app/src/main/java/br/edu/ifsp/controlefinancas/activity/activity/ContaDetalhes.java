package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaDetalhesAdapter;
import br.edu.ifsp.controlefinancas.activity.data.CategoriaDAO;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;

public class ContaDetalhes extends AppCompatActivity {

    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;

    private RecyclerView recyclerView;
    private ContaDetalhesAdapter adapter;

    private List<Transacao> transacaos;

    int idCategoria = 0;
    long idConta = 1;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_detalhes);

        //vinculo da classe com o Layout
        view = findViewById(R.id.view_conta_detalhes_activity);

        //RecyclerView de exibição das contas
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista_transacoes);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //updateUI(null);
    }

    private void updateUI(String transacao){

        transacaos.clear();

        if (transacao==null) {
            transacaos.addAll(transacaoDAO.buscaTodasTransacoes(idConta));
        }
        else {
            //Busca por transacao especifica
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
