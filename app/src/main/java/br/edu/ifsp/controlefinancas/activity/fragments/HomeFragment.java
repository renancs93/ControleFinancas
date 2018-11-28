package br.edu.ifsp.controlefinancas.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import br.edu.ifsp.controlefinancas.R;
import br.edu.ifsp.controlefinancas.activity.interfaces.OnChangeFragmentListener;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView txtHello;
    private FloatingActionButton btnAddReceita, btnAddDespesa, btnNovaConta;
    private FloatingActionsMenu groupFloatButton;

    private OnChangeFragmentListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentListener = (OnChangeFragmentListener) getActivity();

        //vinculo da classe com o Layout
        txtHello = (TextView) view.findViewById(R.id.hello_world);
        btnNovaConta = (FloatingActionButton) view.findViewById(R.id.btnNovaConta_FloatActionButton);
        btnAddReceita = (FloatingActionButton) view.findViewById(R.id.btnAddReceita_FloatActionButton);
        btnAddDespesa = (FloatingActionButton) view.findViewById(R.id.btnAddDespesa_FloatActionButton);
        groupFloatButton = view.findViewById(R.id.multipleActionsFloatingButton);

        //Listeners de interacoes com a view
        btnNovaConta.setOnClickListener(this);
        btnAddReceita.setOnClickListener(this);
        btnAddDespesa.setOnClickListener(this);
        groupFloatButton.setOnClickListener(this);



        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
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

        if (fragmentListener != null){
            fragmentListener.changeFragment(new NovaContaFragment());
        }

    }

    private void criarNovaReceita() {
        txtHello.setText("Receita");

    }

    private void criarNovaDespesa() {
        txtHello.setText("Despesa");
    }
}
