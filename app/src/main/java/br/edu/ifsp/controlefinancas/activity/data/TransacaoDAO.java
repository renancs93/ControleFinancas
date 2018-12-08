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
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

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

    public boolean salvaTransacao(Transacao t) {

        boolean ok = false;

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
        }
        //cria nova transacao
        else {
            database.insert(SQLiteHelper.DB_TABLE_TRANSACOES, null, values);
            Log.d(TAG, "Nova transacao criada");
        }
        ok=true;
        database.close();
        return ok;
    }

//    public List<TransacaoInfo> buscaTodasTransacoes() {
//
//        database=dbHelper.getReadableDatabase();
//        List<TransacaoInfo> transacaos = new ArrayList<>();
//
//        Cursor cursor;
//
//        String sql = "SELECT DISTINCT * FROM "+SQLiteHelper.DB_TABLE_TRANSACOES+" t"+
//                " JOIN "+SQLiteHelper.DB_TABLE_CONTA+" c ON c."+SQLiteHelper.KEY_CONTA_ID+ " = t."+SQLiteHelper.KEY_TRANSACAO_ID_CONTA+
//                " JOIN "+SQLiteHelper.DB_TABLE_CATEGORIAS+" ct ON ct."+SQLiteHelper.KEY_CATEGORIA_ID+" = t."+SQLiteHelper.KEY_TRANSACAO_CATEGORIA_ID+";"
//               ;
//
//        cursor = database.rawQuery(sql, null);
//        cursor.moveToFirst();
//
//        while (cursor.moveToNext())
//        {
//            //Transacao transacao = new Transacao();
//            TransacaoInfo transacao = new TransacaoInfo();
//
//            transacao.setId_conta(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_ID_CONTA)));
//            transacao.setId_transacao(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_ID)));
//            transacao.setValor(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_VALOR)));
//            transacao.setDescricao(cursor.getString(3)/*cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_DESCRICAO))*/);
//            transacao.setCategoria(cursor.getString(11)/*cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CATEGORIA_DESCRICAO))*/);
//            transacao.setData(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_DATE)));
//            transacao.setNatureza(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_NATUREZA)));
//
//            transacaos.add(transacao);
//        }
//        cursor.close();
//
//        database.close();
//
//        return transacaos;
//
//    }

    public List<TransacaoInfo> buscaTodasTransacoesPorConta(long idConta) {

        String filtros[] = {String.valueOf(idConta)};

        database=dbHelper.getReadableDatabase();
        List<TransacaoInfo> transacaos = new ArrayList<>();

        Cursor cursor;

        String sql = "SELECT DISTINCT * FROM "+SQLiteHelper.DB_TABLE_TRANSACOES+" t"+
                " JOIN "+SQLiteHelper.DB_TABLE_CONTA+" c ON c."+SQLiteHelper.KEY_CONTA_ID+ " = t."+SQLiteHelper.KEY_TRANSACAO_ID_CONTA+
                " JOIN "+SQLiteHelper.DB_TABLE_CATEGORIAS+" ct ON ct."+SQLiteHelper.KEY_CATEGORIA_ID+" = t."+SQLiteHelper.KEY_TRANSACAO_CATEGORIA_ID+
                " where "+SQLiteHelper.KEY_TRANSACAO_ID_CONTA+" = ? order by t."+SQLiteHelper.KEY_TRANSACAO_DATE+" DESC";
                ;

        cursor = database.rawQuery(sql, filtros);
        cursor.moveToFirst();

        while (cursor.moveToNext())
        {
            //Transacao transacao = new Transacao();
            TransacaoInfo transacao = new TransacaoInfo();

            transacao.setId_conta(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_ID_CONTA)));
            transacao.setId_transacao(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_ID)));
            transacao.setValor(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_VALOR)));
            transacao.setDescricao(cursor.getString(3)/*cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_DESCRICAO))*/);
            transacao.setCategoria(cursor.getString(11)/*cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CATEGORIA_DESCRICAO))*/);
            transacao.setData(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_DATE)));
            transacao.setNatureza(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.KEY_TRANSACAO_NATUREZA)));

            transacaos.add(transacao);
        }
        cursor.close();

        database.close();

        return transacaos;

    }

    public void atualizaSaldo(long idConta, double valorTransacao, int natureza){

        database=dbHelper.getReadableDatabase();
        Conta conta = new Conta();

        String filtro[] = {String.valueOf(idConta)};
        Cursor cursor;

        //Select conta e saldo
        String sqlSelect = "SELECT * FROM "+SQLiteHelper.DB_TABLE_CONTA+" WHERE "+SQLiteHelper.KEY_CONTA_ID+" = ?;";

        cursor = database.rawQuery(sqlSelect, filtro);
        cursor.moveToFirst();

        conta.setId(idConta);
        conta.setDescricao(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_DESCRICAO)));
        conta.setSaldo(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_SALDO)));

        if (natureza == SQLiteHelper.NATUREZA_RECEITA){
            conta.setSaldo(conta.getSaldo() + valorTransacao);
        }
        else {
            conta.setSaldo(conta.getSaldo() - valorTransacao);
        }

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_CONTA_SALDO, conta.getSaldo());
        values.put(SQLiteHelper.KEY_CONTA_DESCRICAO, conta.getDescricao());

        database=dbHelper.getReadableDatabase();
        database.update(SQLiteHelper.DB_TABLE_CONTA, values, SQLiteHelper.KEY_CONTA_ID+"="+conta.getId(), null);

        database.close();

    }

}
