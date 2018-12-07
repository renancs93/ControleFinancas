package br.edu.ifsp.controlefinancas.activity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class ContaDAO {

    public static final String TAG = ContaDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
long idConta = 1;
    public ContaDAO(Context context) {
        this.dbHelper= new SQLiteHelper (context);
    }

    public List<Conta> buscaTodasContas()
    {
        database=dbHelper.getReadableDatabase();
        List<Conta> contas = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_CONTA_ID, SQLiteHelper.KEY_CONTA_DESCRICAO, SQLiteHelper.KEY_CONTA_SALDO};

        cursor = database.query(SQLiteHelper.DB_TABLE_CONTA, cols, null , null,
                null, null, SQLiteHelper.KEY_CONTA_DESCRICAO);

        while (cursor.moveToNext())
        {
            Conta conta = new Conta();

            conta.setId(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_ID)));
            conta.setDescricao(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_DESCRICAO)));
            conta.setSaldo(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_SALDO)));

            contas.add(conta);
        }
        cursor.close();

        database.close();
        return contas;
    }

    public void salvaConta(Conta c){

        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_CONTA_DESCRICAO, c.getDescricao());
        values.put(SQLiteHelper.KEY_CONTA_SALDO, c.getSaldo());

        //atualiza dados conta
        if (c.getId()>0){
            Log.d(TAG, "Conta Atualizada");

        }
        //cria nova conta
        else {
            Log.d(TAG, "Nova conta criada");
            database.insert(SQLiteHelper.DB_TABLE_CONTA, null, values);
        }

        database.close();
    }

}
