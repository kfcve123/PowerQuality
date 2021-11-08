package com.cem.powerqualityanalyser.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.cem.powerqualityanalyser.R;

public class HintDialog {
    private Context context;

    private AlertDialog alertDialog;
    public HintDialog(Context context) {
        this.context = context;
        if (alertDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            alertDialog = builder.setTitle("")
                    .setMessage("")
                    .setPositiveButton("",null)
                    .setNegativeButton("",null)
                    .setCancelable(false)
                    .create();
        }
    }
    public HintDialog(Context context,int stytle) {
        this.context = context;
        if (alertDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(context,stytle);
            alertDialog = builder.setTitle("")
                    .setMessage("")
                    .setPositiveButton("",null)
                    .setNegativeButton("",null)
                    .setCancelable(false)
                    .create();
        }
    }


    public void showDialog(String title, String message, String left, String right, DialogInterface.OnClickListener leftListener, DialogInterface.OnClickListener rightListener){

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,right,rightListener);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,left,leftListener);
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(right);
//        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setText(left);
        alertDialog.show();

    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
