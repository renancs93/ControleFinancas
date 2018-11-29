package br.edu.ifsp.controlefinancas.activity.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Conta;

public class ContaDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

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
            conta.setSaldo(cursor.getFloat(cursor.getColumnIndex(SQLiteHelper.KEY_CONTA_SALDO)));

            contas.add(conta);
        }
        cursor.close();

        database.close();
        return contas;
    }

}
