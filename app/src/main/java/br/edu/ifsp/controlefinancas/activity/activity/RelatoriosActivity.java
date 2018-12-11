package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class RelatoriosActivity extends AppCompatActivity {

    private List<DataEntry> data = new ArrayList<>();
    private List<TransacaoInfo> transacoes = new ArrayList<>();
    TransacaoDAO transacaoDAO;

    Pie pie;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        transacaoDAO = new TransacaoDAO(this);
        anyChartView = (AnyChartView) findViewById(R.id.piechart_relatorio_categoria);

        pie = new AnyChart().pie();

        setGraficoCategoria();
    }

    private void setGraficoCategoria() {

        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pie.title(getString(R.string.txtRelatorios)+": "+getString(R.string.txtCategoria));

        transacoes = transacaoDAO.buscaTransacaoPorCategoria(0);
        data.clear();

        for (TransacaoInfo t : transacoes){
            data.add(new ValueDataEntry(t.getDescricao(), t.getValor()));
        }
        pie.data(data);

//        if (!data.isEmpty()){
//            pie.data(data);
//        }

        // enable aqua style
        pie.fill("aquastyle");

        pie.animation().enabled(true);
        pie.animation().duration(1500);
        anyChartView.setChart(pie);

    }

    private void refreshChart(){

        transacoes.clear();
        data.clear();

    }

}
