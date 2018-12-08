package br.edu.ifsp.controlefinancas.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alorma.timeline.RoundTimelineView;

import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class ContaDetalhesAdapter extends RecyclerView.Adapter<ContaDetalhesAdapter.ContaDetalhesViewHolder> {

    //private static List<Transacao> transacoes;
    private static List<TransacaoInfo> transacoes;
    private Context context;

    private static ItemClickListener clickListener;

//    public ContaDetalhesAdapter(Context context, List<Transacao> transacaos) {
//        this.context = context;
//        this.transacoes = transacaos;
//    }

    public ContaDetalhesAdapter(Context context, List<TransacaoInfo> transacaos) {
        this.context = context;
        this.transacoes = transacaos;
    }

    @Override
    public ContaDetalhesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.transacao_celula, viewGroup, false);
        return new ContaDetalhesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContaDetalhesViewHolder holder, int position) {

        holder.descricao.setText(transacoes.get(position).getDescricao());
        holder.valor.setText(String.valueOf(transacoes.get(position).getValor()));
        holder.categoria.setText(transacoes.get(position).getCategoria());
        holder.data.setText(Util.intDateForStringDate(transacoes.get(position).getData()));

        int nat = transacoes.get(position).getNatureza();
        if (nat == 1){
            holder.timelineView.setIndicatorColor(Color.GREEN);
        }
        if (nat == 2){
            holder.timelineView.setIndicatorColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return transacoes.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    //Classe interna de estruturacao dos itens e interações na lista de transações.
    public class ContaDetalhesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView descricao, categoria, valor, data;
        RoundTimelineView timelineView;

        public ContaDetalhesViewHolder(View view) {
            super(view);

            descricao = (TextView) view.findViewById(R.id.tv_descricao_transacao);
            valor = (TextView) view.findViewById(R.id.tv_valor_transacao);
            categoria = (TextView) view.findViewById(R.id.tv_categoria_transacao);
            data = (TextView) view.findViewById(R.id.tv_data_transacao);

            timelineView = (RoundTimelineView) view.findViewById(R.id.timeline_view);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}