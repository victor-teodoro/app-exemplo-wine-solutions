package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class checkoutActions extends AppCompatActivity {

    private static final String TAG = checkoutActions.class.getName();
    private RequestQueue nRequestQueue;
    private String urlString = "https://api.pagar.me/1/transactions";
    public JSONObject jsonBody;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        message = intent.getStringExtra(MainActivity.fg);
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(message);

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        //layout.addView(textView);
        Button botao1 = (Button)findViewById(R.id.sharees);
        Button botao2 = (Button)findViewById(R.id.maq);

        botao1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent3 = new Intent (checkoutActions.this, shareMain.class);
                        startActivity(intent3);
                    }
                }
        );
        botao2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent4 = new Intent (checkoutActions.this, MainActivity.class);
                        startActivity(intent4);
                    }
                }
        );
    }



    public void sendHttpRequest(View view){

        EditText editText1 = (EditText) findViewById(R.id.inputCard);
        String card = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.name);
        String name = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.date);
        String date = editText3.getText().toString();
        EditText editText4 = (EditText) findViewById(R.id.cvv);
        String cvv = editText4.getText().toString();



        try {
            jsonBody = new JSONObject(" { api_key: 'ak_test_Vw84TXCJEazPJlcJ0gd4XrZvfwFsxh'," +
                    "                card_number: '"+card+"'," +
                    "                card_holder_name: '"+name+"'," +
                    "                card_expiration_date: '"+date+"'," +
                    "                amount: '222062'," +
                    "                card_cvv: '"+cvv+"'," +
                    "                split_rules: [{" +
                    "                   'recipient_id' : 're_cj51cy1x502auf46e2dbnvs9n'," +
                    "                   'charge_processing_fee' : 'true',"+
                    "                   'liable' : 'true',"+
                    "                   'percentage' : '90'"+
                    "                           },{"+
                    "                   'recipient_id' : 're_cj51cytp502avf46e5y7rv1nh'," +
                    "                   'charge_processing_fee' : 'false',"+
                    "                   'liable' : 'false',"+
                    "                   'percentage' : '10'"+
                    "                           }],"+
                    "                metadata: {" +
                    "                   'vendedor' : 'carla'," +
                    "                   'cod_vendedor' : '00201',"+
                    "                   'assinatura' : 'nao',"+
                    "                   'num_caixas' : '2',"+
                    "                   'evento' : 'bodas_ouro'"+
                    "                           },"+
                    "                capture: 'true' }");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String print = ("dados: "+jsonBody);
        Log.i(TAG,print);
        postApi2();

    }

    private void postApi2() {

        nRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String resp = ("Response: " + response.toString());
                        Log.i(TAG,resp);
                        Intent fim = new Intent(checkoutActions.this, successNormal.class);
                        startActivity(fim);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String resp = ("err: " + error.toString());
                        Log.i(TAG,resp);

                    }
                });

        nRequestQueue.add(jsObjRequest);
    }
}
