package br.edu.ifsp.controlefinancas.activity.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.CategoriaDAO;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Categoria;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;
import br.edu.ifsp.controlefinancas.activity.util.Util;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

public class TransacaoActivity extends AppCompatActivity {

    Context mContext = TransacaoActivity.this;
    private String acao = "";
    View view;

    private TextInputEditText edDescricao, edValor, edData;
    private MaterialBetterSpinner spinnerCategoria, spinnerConta;
    //private Spinner spinnerCategoria2, spinnerConta2;
    private ImageButton btnCalendario;

    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;

    private List<Conta> contas;
    private List<Categoria> categorias;

    //Campos para recuperacao dos Spinners desta biblioteca externa
    long idConta = 0;
    int idCategoria = 0;
    int idNatureza = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Intent intent = getIntent();

        view = findViewById(R.id.view_transacao_activity);

        categoriaDAO = new CategoriaDAO(this);
        contaDAO = new ContaDAO(this);
        transacaoDAO = new TransacaoDAO(this);

        if (intent != null){

            acao = intent.getAction();

            //Alteracao do título da Activity com base na ação que é esperado deste layout
            if (acao.equals(MainActivity.TAG_RECEITA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtReceita));
                idNatureza = 1;
            }
            if (acao.equals(MainActivity.TAG_DESPESA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtDespesa));
                idNatureza = 2;
            }

        }

        //Configurações do Spinner Categoria e Conta
        spinnerCategoria = findViewById(R.id.spinner_categoria_transacao);
        setCampoCategorias();
        spinnerConta = findViewById(R.id.spinner_conta_transacao);
        setCampoContas();

        //Vinculo da classe com os componentes do Layout
        edDescricao = (TextInputEditText) findViewById(R.id.et_descricao_transacao);
        edValor = (TextInputEditText) findViewById(R.id.et_valor_transacao);
        edData = (TextInputEditText) findViewById(R.id.ed_date_transacao);
        btnCalendario = (ImageButton) findViewById(R.id.ib_calendario_transacao);

        //OnClicks
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarDialog();
            }
        });

        //configuração do campo monetário
        Util.setCampoMonetario(edValor);

    }

    private void setCampoCategorias() {

        categorias = categoriaDAO.buscaTodasCategorias();

        ArrayAdapter<Categoria> arrayAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(arrayAdapter);


        if (categorias.size()>0 && categorias != null){

            spinnerCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String descCat = ((Categoria)parent.getItemAtPosition(position)).getDescricao();
                    idCategoria = ((Categoria)parent.getItemAtPosition(position)).getId();

                    //Toast.makeText(TransacaoActivity.this , "Conta: "+descCat+"\nID: "+idCategoria , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void setCampoContas(){

        contas = contaDAO.buscaTodasContas(0);

        ArrayAdapter<Conta> arrayAdapter = new ArrayAdapter<Conta>(this, android.R.layout.simple_spinner_dropdown_item, contas);
        spinnerConta.setAdapter(arrayAdapter);

        if (contas.size()>0 && contas != null){

            spinnerConta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String descConta = ((Conta)parent.getItemAtPosition(position)).getDescricao();
                    idConta = ((Conta)parent.getItemAtPosition(position)).getId();

                    //Toast.makeText(TransacaoActivity.this , "Conta: "+descConta+"\nID: "+idConta , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void openCalendarDialog() {

        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        DecimalFormat df = new DecimalFormat("00");

                        edData.setText(df.format(year)+"-"+(df.format(month + 1))+"-"+df.format(day));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    //Recupera a informação do campo data sem o hifen
    private int getDataFormatada(EditText campoData){

        String txt = campoData.getText().toString();
        return Integer.valueOf(txt.replace("-", ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_transacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_transacao_salvar:
                salvarTransacao();
                return true;
            case R.id.menu_transacao_cancelar:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvarTransacao() {

        if (validaCampos()){
            Transacao transacao = getDados();

            boolean res = transacaoDAO.salvaTransacao(transacao);

            if (res) {
                transacaoDAO.atualizaSaldo(transacao.getId_conta(), transacao.getValor(), transacao.getNatureza());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                Util.showSnackBarAlert(view, getString(R.string.txtOcorreuErro));
            }
        }
        else {
            Util.showSnackBarAlert(view, getString(R.string.txtCamposNaoPreenchidos));
        }

    }

    private boolean validaCampos(){

        //conta / descricao / valor / categoria / data

        boolean ok = true;

        if (edDescricao.getText().toString().isEmpty()){
            ok = false;
        }
        if(idCategoria == 0){
            ok = false;
        }
        if (idConta == 0){
            ok = false;
        }
        if (edData.getText().toString().isEmpty()){
            ok = false;
        }
        if (edValor.getText().toString().isEmpty() || edValor.getText().toString() == "0.00"){
            ok = false;
        }
        if (idNatureza == 0){
            ok = false;
        }

        return ok;
    }

    private Transacao getDados(){

        Transacao transacao = new Transacao();

        transacao.setDescricao(edDescricao.getText().toString());
        transacao.setValor(Util.getCampoValorMonetario(edValor));
        transacao.setData(getDataFormatada(edData));
        transacao.setId_categoria(idCategoria);
        transacao.setId_conta(idConta);
        transacao.setNatureza(idNatureza);

        return transacao;
    }




}
