package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class transSuccess extends AppCompatActivity {

    private RequestQueue nRequestQueue;
    private String urlString2 = "https://api.pagar.me/1/subscriptions";
    public JSONObject jsonBody2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_success);

//        String card_id = getIntent().getExtras().getString("card_id");

        assinar();

    }

    public void assinar(){
        try {
            jsonBody2 = new JSONObject(" { api_key: 'ak_test_Vw84TXCJEazPJlcJ0gd4XrZvfwFsxh'," +
                    "                card_id: 'card_ciyef1b1x0kouhm6ez45ff87p',"+
                    "                plan_id: '176979',"+
                    "                customer: {" +
                    "                   'email' : 'api@test.com'"+
                    "                           },"+
                    "                split_rules: [{" +
                    "                   'recipient_id' : 're_cj51cy1x502auf46e2dbnvs9n'," +
                    "                   'charge_processing_fee' : 'true',"+
                    "                   'liable' : 'true',"+
                    "                   'percentage' : '95'"+
                    "                           },{"+
                    "                   'recipient_id' : 're_cj51cytp502avf46e5y7rv1nh'," +
                    "                   'charge_processing_fee' : 'false',"+
                    "                   'liable' : 'false',"+
                    "                   'percentage' : '5'"+
                    "                           }],"+
                    "                metadata: {" +
                    "                   'vendedor' : 'kleber'," +
                    "                   'cod_vendedor' : '00209',"+
                    "                   'assinatura' : 'sim',"+
                    "                   'num_caixas' : '2',"+
                    "                   'evento' : 'casamento'"+
                    "                           }"+
                    "                }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
                (Request.Method.POST, urlString2, jsonBody2, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject t) {
                        String resp = ("Response: " + t.toString());
                        Log.i("ok",resp);
                        try {
                            String r = t.get("acquirer_response_code").toString();
                            String emv_r = t.get("card_emv_response").toString();
                            Log.d("Pagar.me", "resp = " + r);
                            Log.d("CACETE", "ROLOU ASSINATURA?");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String resp = ("err: " + error.toString());
                        Log.i("ok",resp);
                    }
                });
        nRequestQueue.add(jsObjRequest2);

    }
}
