package com.example.myfirstapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import static com.example.fplib.FingerPrintLibrary.configFingerprint;
import static com.example.fplib.FingerPrintLibrary.getFingerprint;
import static com.example.fplib.FingerPrintLibrary.initFingerprint;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    public static String fg;
    public static String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Context cont = getApplicationContext();
        //initFingerprint(cont, "banana","123456789","id12345");
        //configFingerprint(true,true,true,true,true,true);
        //res = getFingerprint();

        Button botao1 = (Button)findViewById(R.id.cartao);
        Button openDialog = (Button)findViewById(R.id.mpos);

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartao(v);
            }
        });

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // okok

                showDialog();
            }
        });

//        Button.bringToFront();
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, shareMain.class);
        startActivity(intent);

    }
    public void go2pinpad(View view) {
        Intent intent = new Intent(this, pagarPinpad.class);
        startActivity(intent);

    }
    public void mposAssinar(View view) {
        Intent intent = new Intent(this, pagarEAssinar.class);
        startActivity(intent);

    }
    public void cartao(View view) {
        Intent intent2 = new Intent(this, checkoutActions.class);
        startActivity(intent2);

    }

    private void showDialog(){

        final Dialog mydiag = new Dialog(this);

        mydiag.setTitle("Assine!");

        mydiag.setContentView(R.layout.dialog_layout);

        Button assinar = (Button)mydiag.findViewById(R.id.but_assinar);
        Button naoassinar = (Button)mydiag.findViewById(R.id.but_nao_assinar);

        assinar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                mposAssinar(v);
            }
        });

        naoassinar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                go2pinpad(v);
            }
        });

        mydiag.show();

    }
}