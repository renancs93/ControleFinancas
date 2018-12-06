package br.edu.ifsp.controlefinancas.activity.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.Transacao;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class TransacaoActivity extends AppCompatActivity {

    Context mContext = TransacaoActivity.this;
    private String acao = "";

    private TextInputEditText edDescricao, edValor, edData;
    private MaterialBetterSpinner spinnerCategoria, spinnerConta;
    private ImageButton btnCalendario;

    private ContaDAO contaDAO;
    private TransacaoDAO transacaoDAO;

    private List<Conta> contas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Intent intent = getIntent();

        if (intent != null){

            acao = intent.getAction();

            //Alteracao do título da Activity com base na ação que é esperado deste layout
            if (acao.equals(MainActivity.TAG_RECEITA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtReceita));
            }
            if (acao.equals(MainActivity.TAG_DESPESA)) {
                getSupportActionBar().setTitle(getString(R.string.txtNova)+" "+getString(R.string.txtDespesa));
            }

        }

        //Configurações do Spinner Categoria e Conta
        spinnerCategoria = (MaterialBetterSpinner) findViewById(R.id.spinner_categoria_transacao);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoria_transacao, android.R.layout.simple_spinner_item);
        spinnerCategoria.setAdapter(arrayAdapter);

        spinnerConta = (MaterialBetterSpinner) findViewById(R.id.spinner_conta_transacao);
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

    public void setCampoContas(){

        contaDAO = new ContaDAO(this);
        contas = contaDAO.buscaTodasContas();

        if (!contas.isEmpty() && contas != null){

            ArrayAdapter<Conta> arrayAdapter = new ArrayAdapter<Conta>(this, android.R.layout.simple_spinner_item, contas);
            spinnerConta.setAdapter(arrayAdapter);

            spinnerConta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String descConta = ((Conta)parent.getItemAtPosition(position)).getDescricao();
                    long idConta = ((Conta)parent.getItemAtPosition(position)).getId();

                    Toast.makeText(TransacaoActivity.this , "Conta: "+descConta+"\nID: "+idConta , Toast.LENGTH_SHORT).show();
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
    private int getDataFormatada(){

        String txt = edData.getText().toString();
        return Integer.valueOf(txt.replace("-", ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_transacao, menu);

//        if (!getIntent().hasExtra("contas"))
//        {
//            MenuItem item = menu.findItem(R.id.delConta);
//            item.setVisible(false);
//        }
        return true;
    }

    private boolean validaCampos(){

        boolean ok = true;

        if (edDescricao.getText().toString().isEmpty()){
            ok = false;
        }

        return ok;
    }

    private Transacao getDados(){

        Transacao transacao = new Transacao();

        transacao.setDescricao(edDescricao.getText().toString());
        transacao.setValor(Double.valueOf(edValor.getText().toString()));
        transacao.setData(getDataFormatada());

        return transacao;
    }

}
