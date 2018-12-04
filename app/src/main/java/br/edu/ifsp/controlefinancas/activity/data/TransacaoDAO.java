package br.edu.ifsp.controlefinancas.activity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Transacao;

public class TransacaoDAO {

    public static final String TAG = TransacaoDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public TransacaoDAO(Context context) {
        this.dbHelper= new SQLiteHelper (context);
    }

    public List<Transacao> buscaTodasTransacoes(long idConta){

        List<Transacao> transacoes = new ArrayList<>();



        return transacoes;
    }




}
