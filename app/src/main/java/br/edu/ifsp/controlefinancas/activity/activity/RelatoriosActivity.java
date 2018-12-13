package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class RelatoriosActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btnBuscar;
    private RadioGroup radioGroup;
    private RadioButton rbCategoria, rbConta, rbNatureza;

    private List<DataEntry> data = new ArrayList<>();
    private List<TransacaoInfo> transacoes = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();
    TransacaoDAO transacaoDAO;
    ContaDAO contaDAO;

    Pie pie;
    AnyChartView anyChartView;
    Cartesian cartesian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        getSupportActionBar().setTitle(R.string.txtRelatorios);

        transacaoDAO = new TransacaoDAO(this);
        contaDAO = new ContaDAO(this);

        btnBuscar = (MaterialButton) findViewById(R.id.btnBuscar_relatorios);
        radioGroup = (RadioGroup) findViewById(R.id.rd_group_tiposCategorias);
        rbCategoria = (RadioButton) findViewById(R.id.rb_categoria);
        rbConta = (RadioButton) findViewById(R.id.rb_conta);
        //rbNatureza = (RadioButton) findViewById(R.id.rb_natureza);

        //Graphic
        anyChartView = (AnyChartView) findViewById(R.id.piechart_relatorios);

        //Clicks Listeners
        btnBuscar.setOnClickListener(this);

        //tipo do gráfico
        pie = new AnyChart().pie();
        cartesian = AnyChart.column();

        // enable aqua style
        pie.fill("aquastyle");

        //config charts
        anyChartView.setChart(pie);
        //anyChartView.setChart(cartesian);

        pie.animation().enabled(true);
        pie.animation().duration(1500);
        //anyChartView.animate();

    }

    public void buscar(){


        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.rb_categoria:
                setGraficoCategoria(0);
                break;
            case R.id.rb_conta:
                setGraficoConta(0);
                break;
//            case R.id.rb_natureza:
//                //
//                break;

        }

    }

    private void refreshChart(){

        anyChartView.invalidate();
        transacoes.clear();
        contas.clear();

        data.clear();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBuscar_relatorios:
                buscar();
                break;
        }

    }

    private void setGraficoCategoria(long idCategoria) {

        refreshChart();

        //APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pie.title(getString(R.string.txtRelatorios) + ": " + getString(R.string.txtCategoria));

        transacoes = transacaoDAO.buscaTransacaoPorCategoria(idCategoria);

        for (TransacaoInfo t : transacoes) {
            data.add(new ValueDataEntry(t.getDescricao(), t.getValor()));
        }
        pie.data(data);

        //refresh
        pie.draw(true);

    }

    private void setGraficoConta(long idConta){

        refreshChart();

        //APIlib.getInstance().setActiveAnyChartView(anyChartView);
        pie.title(getString(R.string.txtRelatorios) + ": " + getString(R.string.txtConta));

        contas = contaDAO.buscaTodasContas(0);

        for (Conta c : contas) {
            data.add(new ValueDataEntry(c.getDescricao(), c.getSaldo()));
        }
        pie.data(data);

        //refresh
        pie.draw(true);


    }


}
