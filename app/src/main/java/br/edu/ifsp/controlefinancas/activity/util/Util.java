package br.edu.ifsp.controlefinancas.activity.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static void showSnackBarAlert(View view, String msg) {

        esconderTeclado(view);

        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        //View sbview = snackbar.getView();
        snackbar.show();

    }

    public static void esconderTeclado(View view){

        try {
            Context context = view.getContext().getApplicationContext();

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
