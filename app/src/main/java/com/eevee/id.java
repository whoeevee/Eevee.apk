package com.eevee;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gennaro Daniele Acciaro
 * http://gdacciaro.com
 * acciarogennaro@gmail.com
 * on 04/09/17.
 *
 * Modified by Eevee, it's better now
 * https://github.com/whoeevee
 * t.me/dxrtxn
 * on 02/12/21.
 *
 **/




public class id {
    private final Dialog dialog;
    private TextView dialogButtonOk ,dialogButtonNo;
    private TextView title_lbl, subtitle_lbl;
    private View separator;
    private il positiveListener;
    private il negativeListener;
    private boolean negativeExist;
    private static final String LOG_ERROR = "iOSDialog_ERROR";

    public id(Context context, String title, String subtitle, Typeface typeFace,boolean cancelable) {
        negativeExist=false;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alerts_two_buttons);
        if(dialog.getWindow()!=null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initViews();

        dialog.setCancelable(cancelable);
        setTitle(title);
        setSubtitle(subtitle);
        setTypefaces(typeFace);

        initEvents();
    }

    public void setPositive(String okLabel, il listener) {
        this.positiveListener = listener;
        this.dismiss();
        setPositiveLabel(okLabel);
    }
    public void setNegative(String koLabel, il listener) {
        if (listener != null){
            this.negativeListener = listener;
            this.dismiss();
            negativeExist = true;
            setNegativeLabel(koLabel);
        }
    }
    public void setNegative1(String koLabel, il listener) {
        if (listener != null){
            this.negativeListener = listener;
            dialogButtonNo.setTextColor(dialogButtonOk.getCurrentTextColor());
            this.dismiss();
            negativeExist = true;
            setNegativeLabel(koLabel);
        }
    }
    public void show(){
        if(!negativeExist){
            dialogButtonNo.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public void setTitle(String title){
        title_lbl.setText(title);
    }
    public void setSubtitle(String subtitle){
        subtitle_lbl.setText(subtitle);
    }
    private void setPositiveLabel(String positive){
        dialogButtonOk.setText(positive);
    }
    private void setNegativeLabel(String negative){
        dialogButtonNo.setText(negative);
    }
    private void setTypefaces(Typeface appleFont){
        if(appleFont!=null) {
            title_lbl.setTypeface(appleFont);
            subtitle_lbl.setTypeface(appleFont);
            dialogButtonOk.setTypeface(appleFont);
            dialogButtonNo.setTypeface(appleFont);
        }
    }


    private void initViews() {
        title_lbl = dialog.findViewById(R.id.title);
        subtitle_lbl =  dialog.findViewById(R.id.subtitle);
        dialogButtonOk =  dialog.findViewById(R.id.dialogButtonOK);
        dialogButtonNo =  dialog.findViewById(R.id.dialogButtonNO);
        separator =  dialog.findViewById(R.id.separator);
    }

    private void initEvents(){
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positiveListener != null) {
                    positiveListener.onClick(id.this);
                }
            }
        });
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (negativeListener != null) {
                    negativeListener.onClick(id.this);
                }
            }
        });
    }


}

/*
 *  Made with love (and coffee and pizza) in Naples, Italy!
 *
 *  __
 * // ""--.._
 * ||  (_)  _ "-._
 * ||    _ (_)    '-.
 * ||   (_)   __..-'
 * \\__..--""
 *
 */