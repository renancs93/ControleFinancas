package br.edu.ifsp.controlefinancas.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ContaViewHolder> {

    private static List<Conta> contas;
    private Context context;

    private static ItemClickListener clickListener;

    public ContaAdapter(Context context, List<Conta> contas) {
        this.context = context;
        this.contas = contas;
    }

    @NonNull
    @Override
    public ContaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.conta_celula, viewGroup, false);
        return new ContaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContaViewHolder holder, int position) {

        holder.descricao.setText(contas.get(position).getDescricao());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    //Classe interna de estruturacao dos itens e interações na lista de contas.
    public class ContaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView descricao, saldo;

        public ContaViewHolder(@NonNull View view) {
            super(view);

            descricao = (TextView) view.findViewById(R.id.tv_descricao_conta);
            saldo = (TextView) view.findViewById(R.id.tv_saldo_conta);

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