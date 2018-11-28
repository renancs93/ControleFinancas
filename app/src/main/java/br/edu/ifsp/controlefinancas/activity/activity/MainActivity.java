package br.edu.ifsp.controlefinancas.activity.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.fragments.ContaFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView txtHello;
    private FloatingActionButton btnAddReceita, btnAddDespesa, btnNovaConta;
    private FloatingActionsMenu groupFloatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //vinculo da classe com o Layout
        txtHello = (TextView) findViewById(R.id.hello_world);
        btnNovaConta = (FloatingActionButton) findViewById(R.id.btnNovaConta_FloatActionButton);
        btnAddReceita = (FloatingActionButton) findViewById(R.id.btnAddReceita_FloatActionButton);
        btnAddDespesa = (FloatingActionButton) findViewById(R.id.btnAddDespesa_FloatActionButton);
        groupFloatButton = findViewById(R.id.multipleActionsFloatingButton);

        //Listeners de interacoes com a view
        btnNovaConta.setOnClickListener(this);
        btnAddReceita.setOnClickListener(this);
        btnAddDespesa.setOnClickListener(this);
        groupFloatButton.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                break;
        }
    }

    private void criarNovaConta() {
        ContaFragment contaFragment = new ContaFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, contaFragment)
                .addToBackStack(null)
                .commit();

    }

    private void criarNovaReceita() {
        txtHello.setText("Receita");

    }

    private void criarNovaDespesa() {
        txtHello.setText("Despesa");
    }
}
