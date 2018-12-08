package br.edu.ifsp.controlefinancas.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.data.TransacaoDAO;
import br.edu.ifsp.controlefinancas.activity.model.TransacaoInfo;

public class RelatoriosActivity extends AppCompatActivity {

    PieChart pieChartCategoria;

    private List<TransacaoInfo> transacoesList;
    private List<PieEntry> entries;

    TransacaoDAO transacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        transacaoDAO = new TransacaoDAO(this);
        transacoesList = new ArrayList<>();

        //setGraficoCategoria();
    }

    private void refreshChart(){

        pieChartCategoria.invalidate();
        pieChartCategoria.notifyDataSetChanged();
    }


//    void setGraficoCategoria(){
//
//        transacoesList = transacaoDAO.buscaTodasTransacoes();
//
//        List<PieEntry> entries = new ArrayList<>();
//        HashMap<String, Float> map = groupBySumCategoriaTransacao(transacoesList);
//
//        for (Map.Entry<String,Float> pair : map.entrySet()) {
//            entries.add(new PieEntry(pair.getValue(), pair.getKey()));
//        }
//        showPieChart(entries);
//
//    }

//    private HashMap<String, Float> groupBySumCategoriaTransacao(List<TransacaoInfo> transacoesList) {
//
//        HashMap<String, Float> hashMap = new HashMap<String, Float>();
//
//        for (TransacaoInfo t : transacoesList){
//
//            if (!hashMap.containsKey(t.getCategoria())){
//                hashMap.put(t.getCategoria(), t.getValor());
//            }
//            else {
//                Float val = hashMap.get(t.getCategoria());
//                hashMap.put(t.getCategoria(), val + t.getValor());
//            }
//        }
//
//        return hashMap;
//
//    }

//    private void showPieChart(List<PieEntry> list){
//
//        PieDataSet dataSet = new PieDataSet(list, "");
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        PieData data = new PieData(dataSet);
//
//        if (list.isEmpty()){
//            pieChartCategoria.setCenterText(getString(R.string.txtSemDados));
//        }
//        else {
//            pieChartCategoria.setCenterText(getString(R.string.txtCategoria));
//        }
//
//        //pieChart.setContentDescription(nameWallet);
//        pieChartCategoria.animateY(3000);
//        pieChartCategoria.setData(data);
//        pieChartCategoria.invalidate(); // refresh
//
//    }


}
