package br.edu.ifsp.controlefinancas.activity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.adapter.ContaAdapter;
import br.edu.ifsp.controlefinancas.activity.data.ContaDAO;
import br.edu.ifsp.controlefinancas.activity.model.Conta;
import br.edu.ifsp.controlefinancas.activity.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String TAG_CONTA = "CONTA";
    public static String TIPO_TRANSACAO = "TRANSACAO";
    public static String TAG_RECEITA = "RECEITA";
    public static String TAG_DESPESA = "DESPESA";

    View view;
    private FloatingActionButton btnAddReceita, btnAddDespesa, btnNovaConta;
    private FloatingActionsMenu groupFloatButton;

    private List<Conta> contas = new ArrayList<>();

    private ContaDAO contaDAO ;
    private RecyclerView recyclerView;
    private ContaAdapter adapter;

    long idConta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("HOME");

        contaDAO = new ContaDAO(this);

        //vinculo da classe com o Layout
        view = findViewById(R.id.coordinator_main_activity);
        btnNovaConta = (FloatingActionButton) findViewById(R.id.btnNovaConta_FloatActionButton);
        btnAddReceita = (FloatingActionButton) findViewById(R.id.btnAddReceita_FloatActionButton);
        btnAddDespesa = (FloatingActionButton) findViewById(R.id.btnAddDespesa_FloatActionButton);
        groupFloatButton = findViewById(R.id.multipleActionsFloatingButton);

        //RecyclerView de exibição das contas
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista_contas);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //Listeners de interacoes com a view
        btnNovaConta.setOnClickListener(this);
        btnAddReceita.setOnClickListener(this);
        btnAddDespesa.setOnClickListener(this);
        groupFloatButton.setOnClickListener(this);

        adapter = new ContaAdapter(this, contas);
        recyclerView.setAdapter(adapter);

        setupRecyclerView();

        updateUI(null);
    }

    private void setupRecyclerView() {

        adapter.setClickListener(new ContaAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(getApplicationContext(), "Conta Clicada: "+position, Toast.LENGTH_SHORT).show();
                final Conta conta = contas.get(position);
                Intent intent = new Intent(getApplicationContext(), ContaDetalhes.class);
                intent.putExtra(TAG_CONTA, conta);
                startActivity(intent);
            }
        });

        /**
         * Swipe Right (Remove)
         */
        ItemTouchHelper.SimpleCallback simpleItemTouchCallbackRight = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                if (swipeDir == ItemTouchHelper.RIGHT) {
                    Conta conta = contas.get(viewHolder.getAdapterPosition());
                    contaDAO.apagaContato(conta);
                    contas.remove(viewHolder.getAdapterPosition());
                    recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                    Util.showSnackBarAlert(view, getString(R.string.txtContaRemovida));
                }
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Paint p = new Paint();

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(ContextCompat.getColor(getBaseContext(), R.color.colorDelete));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_remove_circle_outline_black);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallbackRight);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_relatorios:
                abrirRelatorios();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddReceita_FloatActionButton:
                criarNovaReceita();
                groupFloatButton.collapse();
                break;
            case R.id.btnAddDespesa_FloatActionButton:
                criarNovaDespesa();
                groupFloatButton.collapse();
                break;
            case R.id.btnNovaConta_FloatActionButton:
                criarNovaConta();
                groupFloatButton.collapse();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //Code relativo a conta
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateUI(null);
                Util.showSnackBarAlert(view, getString(R.string.txtContaAdicionada));
            }

        }
        //Code relativo a a transacao
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                updateUI(null);
                Util.showSnackBarAlert(view, getString(R.string.txtTransacaoAdicionada));
            }
        }

    }

    private void criarNovaConta() {

        Intent intent = new Intent(this, ContaInfo.class);
        startActivityForResult(intent, 1);

    }

    private void criarNovaReceita() {

        Intent intent = new Intent(this, TransacaoActivity.class);
        intent.setAction(TAG_RECEITA);
        startActivityForResult(intent, 2);

    }

    private void criarNovaDespesa() {

        Intent intent = new Intent(this, TransacaoActivity.class);
        intent.setAction(TAG_DESPESA);
        startActivityForResult(intent, 2);
    }

    private void abrirRelatorios() {
        Intent intent = new Intent(this, RelatoriosActivity.class);
        startActivity(intent, null);
    }


    private void updateUI(String conta){

        contas.clear();

        if (conta==null) {
            contas.addAll(contaDAO.buscaTodasContas(0));
        }
        else {
            //contas.addAll(contaDAO.buscaContato(nomeContato));
        }

        //esconde os botões de adicionar receita ou despesa, caso não exista nenhuma conta criada
        if (contas.isEmpty()){
            btnAddDespesa.setVisibility(View.GONE);
            btnAddReceita.setVisibility(View.GONE);
        }
        else {
            btnAddDespesa.setVisibility(View.VISIBLE);
            btnAddReceita.setVisibility(View.VISIBLE);
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }


}
