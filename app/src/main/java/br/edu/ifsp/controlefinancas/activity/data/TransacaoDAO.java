package br.edu.ifsp.controlefinancas.activity.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;

public class TransacaoDAO {

    public static final String TAG = TransacaoDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

//    public TransacaoDAO(Context ctx, SQLiteDatabase database) {
//        super(ctx.getContentResolver());
//        mContext = new WeakReference(ctx);
//    }

    public TransacaoDAO(Context context) {
        this.dbHelper = new SQLiteHelper (context);
    }

    public void salvaTransacao(Transacao t) {


        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.KEY_TRANSACAO_ID_CONTA, t.getId_conta());
        values.put(SQLiteHelper.KEY_TRANSACAO_CATEGORIA_ID, t.getId_categoria());
        values.put(SQLiteHelper.KEY_TRANSACAO_DESCRICAO, t.getDescricao());
        values.put(SQLiteHelper.KEY_TRANSACAO_DATE, t.getData());
        values.put(SQLiteHelper.KEY_TRANSACAO_NATUREZA, t.getNatureza());
        values.put(SQLiteHelper.KEY_TRANSACAO_VALOR, t.getValor());

        //atualiza dados transação
        if (t.getId()>0){
            Log.d(TAG, "Transação Atualizada");
            //TODO - Fazer UPDATE

        }
        //cria nova transacao
        else {
            database.insert(SQLiteHelper.DB_TABLE_TRANSACOES, null, values);
            Log.d(TAG, "Nova transacao criada");
        }

        database.close();

    }


    public List<Transacao> buscaTodasTransacoes(long idConta) {

        String filtro = String.valueOf(idConta);

        database=dbHelper.getReadableDatabase();
        List<Transacao> transacaos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_TRANSACAO_ID_CONTA, SQLiteHelper.KEY_TRANSACAO_DESCRICAO, SQLiteHelper.KEY_TRANSACAO_VALOR};
        String where=SQLiteHelper.KEY_TRANSACAO_ID_CONTA + " = ?";
        String[] argWhere=new String[]{filtro};


        cursor = database.query(SQLiteHelper.DB_TABLE_TRANSACOES, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_TRANSACAO_DATE+" DESC");

        while (cursor.moveToNext())
        {
            Transacao transacao = new Transacao();

            transacao.setId(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_ID)));
            transacao.setDescricao(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_DESCRICAO)));
            transacao.setValor(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_VALOR)));

            transacaos.add(transacao);
        }
        cursor.close();

        database.close();

        return transacaos;

    }
}
