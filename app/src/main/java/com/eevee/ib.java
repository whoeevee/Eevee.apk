package com.eevee;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Gennaro Daniele Acciaro
 * http://gdacciaro.com
 * acciarogennaro@gmail.com
 * on 18/02/18.
 *
 * Modified by Eevee, it's better now
 * https://github.com/whoeevee
 * t.me/whoeevee
 * on 02/12/21.

 */

public class ib {
    private Typeface tf;
    private boolean cancelable;
    private String title, subtitle, okLabel, koLabel;
    private final Context context;
    private il positiveListener;
    private il negativeListener;

    public ib(Context context) {
        this.context = context;
    }

    public ib setTitle(String title) {
        this.title = title;
        return this;
    }

    public ib setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public ib setFont(Typeface font) {
        this.tf=font;
        return this;
    }
    public ib setCancelable(boolean cancelable){
        this.cancelable=cancelable;
        return this;
    }

    public ib setNegativeListener(String koLabel,il listener) {
        this.negativeListener=listener;
        this.koLabel=koLabel;
        return this;
    }


    public ib setPositiveListener(String okLabel,il listener) {
        this.positiveListener = listener;
        this.okLabel=okLabel;
        return this;
    }

    public id build(){
        id dialog = new id(context,title,subtitle, tf,cancelable);
        dialog.setNegative(koLabel,negativeListener);
        dialog.setPositive(okLabel,positiveListener);
        return dialog;
    }

    public id build1(){
        id dialog = new id(context,title,subtitle, tf,cancelable);
        dialog.setNegative1(koLabel,negativeListener);
        dialog.setPositive(okLabel,positiveListener);
        return dialog;
    }

}
