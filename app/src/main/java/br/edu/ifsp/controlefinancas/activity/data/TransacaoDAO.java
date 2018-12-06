package br.edu.ifsp.controlefinancas.activity.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;

public class TransacaoDAO extends AsyncQueryHandler {

    public static final String TAG = TransacaoDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private WeakReference mContext;

    public TransacaoDAO(Context ctx, SQLiteDatabase database) {
        super(ctx.getContentResolver());
        mContext = new WeakReference(ctx);
    }


//    public TransacaoDAO(Context context) {
//        this.dbHelper = new SQLiteHelper (context);
//    }

    public void buscarContas(){

        database=dbHelper.getReadableDatabase();
        List<Conta> contas = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_CONTA_ID, SQLiteHelper.KEY_CONTA_DESCRICAO, SQLiteHelper.KEY_CONTA_SALDO};

        cursor = database.query(SQLiteHelper.DB_TABLE_CONTA, cols, null , null,
                null, null, SQLiteHelper.KEY_CONTA_DESCRICAO);

    }


}
