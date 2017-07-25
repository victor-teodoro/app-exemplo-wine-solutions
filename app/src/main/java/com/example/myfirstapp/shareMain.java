package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class shareMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);

        Button botao1 = (Button)findViewById(R.id.botaoShare);
        Button botao2 = (Button)findViewById(R.id.back);
        Button botao3 = (Button)findViewById(R.id.cartao);

        botao3.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent2 = new Intent (shareMain.this, checkoutActions.class);
                        startActivity(intent2);
                    }
                }
        );

        botao1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String url = "https://api.mundipagg.com/checkout/v1/charges/ch_mZyXWLMfjAhvQp06";
                        shareIt(url);
                    }
                }
        );
        botao2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent (shareMain.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void shareIt(String url){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Pague aqui seu pedido! "+url;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Enviar via"));
        finish();
    }
}
