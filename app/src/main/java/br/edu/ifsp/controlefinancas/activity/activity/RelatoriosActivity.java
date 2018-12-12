package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class RelatoriosActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btnBuscar;
    private RadioGroup radioGroup;
    private RadioButton rbCategoria, rbConta, rbNatureza;

    private List<DataEntry> data = new ArrayList<>();
    private List<TransacaoInfo> transacoes = new ArrayList<>();
    TransacaoDAO transacaoDAO;

    Pie pie;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        getSupportActionBar().setTitle(R.string.txtRelatorios);

        transacaoDAO = new TransacaoDAO(this);

        btnBuscar = (MaterialButton) findViewById(R.id.btnBuscar_relatorios);
        radioGroup = (RadioGroup) findViewById(R.id.rd_group_tiposCategorias);
        rbCategoria = (RadioButton) findViewById(R.id.rb_categoria);
        rbConta = (RadioButton) findViewById(R.id.rb_conta);
        rbNatureza = (RadioButton) findViewById(R.id.rb_natureza);

        //Graphic
        anyChartView = (AnyChartView) findViewById(R.id.piechart_relatorio_categoria);

        //Clicks Listeners
        btnBuscar.setOnClickListener(this);

        //setGraficoCategoria(0);

    }

    public void buscar(){

        if (rbCategoria.isChecked()) {
            setGraficoCategoria(0);
        }

//        switch (radioGroup.getCheckedRadioButtonId()){
//            case R.id.rb_categoria:
//                setGraficoCategoria(0);
//                break;
//            case R.id.rb_conta:
//                //
//                break;
//            case R.id.rb_natureza:
//                //
//                break;
//
//        }

    }

    private void refreshChart(){

        transacoes.clear();
        data.clear();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBuscar_relatorios:
                //buscar();
                setGraficoCategoria(0);
                break;
        }

    }

    private void setGraficoCategoria(long id) {

        refreshChart();
        pie = new AnyChart().pie();

        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pie.title(getString(R.string.txtRelatorios)+": "+getString(R.string.txtCategoria));

        transacoes = transacaoDAO.buscaTransacaoPorCategoria(id);

        for (TransacaoInfo t : transacoes){
            data.add(new ValueDataEntry(t.getDescricao(), t.getValor()));
        }
        pie.data(data);

        // enable aqua style
        pie.fill("aquastyle");

        pie.animation().enabled(true);
        pie.animation().duration(1500);
        anyChartView.setChart(pie);

    }
}
