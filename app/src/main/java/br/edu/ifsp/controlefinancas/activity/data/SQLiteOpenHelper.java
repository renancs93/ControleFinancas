package br.edu.ifsp.controlefinancas.activity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controle_financas.db";

    //Atributos (colunas) da tabela de contas
    static final String DATABASE_TABLE_CONTAS = "contas";
    static final String KEY_CONTA_ID = "id";
    static final String KEY_DESCRICAO = "descricao";
    static final String KEY_SALDO = "saldo";

    //Atributos (colunas) da tabela de transação
    static final String DATABASE_TABLE_TRANSACOES = "transacoes";
    static final String KEY_TRANSACAO_ID = "id";
    static final String KEY_TRANSACAO_PAGAMENTO = "pagamento"; //credito ou debito
    static final String KEY_TRANSACAO_PARCELAS = "parcelas"; //numero de parcelas (se crédito)
    static final String KEY_TRANSACAO_VALOR = "valor"; //valor da transacao
    static final String KEY_TRANSACAO_CATEGORIA = "categoria"; //alimentacao, saude, etc

    //Atributos (colunas) da tabela de categorias
    static final String DATABASE_TABLE_CATEGORIAS = "categorias";
    static final String KEY_CATEGORIA_ID = "id";
    static final String KEY_CATEGORIA_DESCRICAO = "descricao";

    //Atributos (colunas) da tabela de formas de pagamentos
    static final String DATABASE_TABLE_FORMA_PAGAMENTO = "categorias";
    static final String KEY_FPAGAMENTO_ID = "id";
    static final String KEY_FPAGAMENTO_DESCRICAO = "descricao";

    //Registro da última versão do Banco de Dados (utilizado para possíveis atualizações no banco, onde deve ser incrementado)
    private static final int DATABASE_VERSION = 1;

    //SQL de criação da Tabela Transações
    private static final String DATABASE_CREATE_TRANSACOES = "CREATE TABLE "+ DATABASE_TABLE_TRANSACOES +" (" +
            KEY_TRANSACAO_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TRANSACAO_PAGAMENTO + " TEXT NOT NULL, " +
            KEY_TRANSACAO_VALOR + " REAL NOT NULL, "  +
            KEY_TRANSACAO_PARCELAS + " INTEGER, "  +
            KEY_TRANSACAO_CATEGORIA + " TEXT);";

    //SQL de criação da Tabela Contas


    public SQLiteOpenHelper(Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TRANSACOES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
