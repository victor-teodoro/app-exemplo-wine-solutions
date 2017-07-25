package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import me.pagar.mposandroid.Mpos;
import me.pagar.mposandroid.MposListener;
import me.pagar.mposandroid.EmvApplication;
import me.pagar.mposandroid.MposPaymentResult;
import me.pagar.mposandroid.PaymentMethod;

import static android.R.attr.value;

public class assinatura extends AppCompatActivity {

    private static final String TAG = checkoutActions.class.getName();
    public String device;
    private RequestQueue nRequestQueue;
    private RequestQueue nRequestQueue2;
    private String urlString = "https://api.pagar.me/1/transactions";
    private String urlString2 = "https://api.pagar.me/1/subscriptions";
    public JSONObject jsonBody;
    public JSONObject jsonBody2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura);
        Intent intent = getIntent();
        device = intent.getStringExtra(pagarPinpad.dev);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            try {
                goMpos(device);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void goMpos(String dev) throws IOException {

        String deviceName = dev;
        final Context cont = getApplicationContext();
        BluetoothDevice result = null;

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        if (devices != null) {
            for (BluetoothDevice device : devices) {
                if (deviceName.equals(device.getName())) {
                    result = device;
                    break;
                }
            }
        }
        final Intent success = new Intent(this, transSuccess.class);
        BluetoothDevice device = result;

        try {
            final Mpos mpos = new Mpos(device, "ek_test_u1J8vXbY3wz2iaRglRlJ4gAJBwgfAK", cont);


            mpos.addListener(new MposListener() {
                public void bluetoothConnected() {
                    Log.d("Pagar.me", "Bluetooth connected.");
                    mpos.initialize();
                }

                public void bluetoothDisconnected() {
                    Log.d("Pagar.me", "Bluetooth disconnected.");
                }

                public void bluetoothErrored(int error) {
                    Log.d("Pagar.me", "Received bluetooth error");
                }

                public void receiveInitialization() {
                    Log.d("Pagar.me", "receive initialization!");
                    try {
                        mpos.downloadEMVTablesToDevice(false);
                    } catch (Exception e) {
                        Log.d("Pagar.me", "Got error in initialization and table update " + e.getMessage());
                    }
                }

                public void receiveNotification(String notification) {
                    Log.d("Pagar.me", "Got Notification " + notification);
                }

                @Override
                public void receiveOperationCompleted() {

                }

                public void receiveTableUpdated(boolean loaded) {
                    Log.d("Pagar.me", "received table updated loaded = " + loaded);
                    //EmvApplication visaCredit = new EmvApplication(PaymentMethod.CreditCard, "visa");
                    ArrayList<EmvApplication> l = new ArrayList<EmvApplication>();
                    //l.add(visaCredit);
                    EmvApplication masterCredit = new EmvApplication(PaymentMethod.CreditCard, "master");
                    l.add(masterCredit);

                    mpos.payAmount(222062, null, PaymentMethod.CreditCard);


                }

                public void receiveFinishTransaction() {
                    Log.d("Pagar.me", "Finished transaction");
                    mpos.close("TRANSACAO APROVADA");
                    startActivity(success);
                }

                public void receiveClose() {
                    Log.d("Pagar.me", "Receive close");
                    mpos.closeConnection();
                }

                public void receiveCardHash(String cardHash, MposPaymentResult result) {
                    Log.d("Pagar.me", "Card Hash is " + cardHash);
                    Log.d("Pagar.me", "Card Brand is " + result.cardBrand);
                    Log.d("Pagar.me", "FD = " + result.cardFirstDigits + " LD = " + result.cardLastDigits);
                    Log.d("Pagar.me", "ONL = " + result.isOnline);

                    try {
                        jsonBody = new JSONObject(" { api_key: 'ak_test_Vw84TXCJEazPJlcJ0gd4XrZvfwFsxh'," +
                                "                card_hash: '"+cardHash+"',"+
                                "                amount: '222062',"+
                                "                installments: '3',"+
                                "                soft_descriptor: 'Eventos'," +
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
                                "                   'vendedor' : 'kleber'," +
                                "                   'cod_vendedor' : '00209',"+
                                "                   'assinatura' : 'sim',"+
                                "                   'num_caixas' : '2',"+
                                "                   'evento' : 'casamento'"+
                                "                           }"+
                                "                }");
                        jsonBody2 = new JSONObject(" { api_key: 'ak_test_Vw84TXCJEazPJlcJ0gd4XrZvfwFsxh'," +
                                "                card_hash: '"+cardHash+"',"+
                                "                plan_id: '168887',"+
                                "                customer: {" +
                                "                   'email' : 'api@test.com'"+
                                "                           },"+
                                "                metadata: {" +
                                "                   'vendedor' : 'kleber'," +
                                "                   'cod_vendedor' : '00209',"+
                                "                   'assinatura' : 'sim',"+
                                "                   'num_caixas' : '2',"+
                                "                   'evento' : 'casamento'"+
                                "                           }"+
                                "                }");
                        postApi(mpos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                public void receiveError(int error) {
                    Log.d("Pagar.me", "Received error " + error);
                    if (error == 11){
                        mpos.close("APROVADO!");
                        startActivity(success);

                    }
                    else
                        mpos.closeConnection();

                }

                public void receiveOperationCancelled() {
                    Log.d("Pagar.me", "Cancel");
                }
            });

            Log.d("Pagar.me", "Telling to initialize");
            mpos.openConnection(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void postApi(final Mpos mpos) {

        nRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject t) {
                        String resp = ("Response: " + t.toString());
                        Log.i(TAG,resp);
//                        Log.d("Pagarrr", jsonBody2.toString());
//                        String card_id = "";
//
//                        try {
//                            JSONObject json1 = t.getJSONObject("card");
//                            card_id = json1.getString("card_id");
//                        }catch (JSONException e){};
//
//                        Log.d("logzeras", card_id)
//                        success.putExtra("card_id", card_id);

//                        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
//                                (Request.Method.POST, urlString2, jsonBody2, new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject t) {
//                                        String resp = ("Response: " + t.toString());
//                                        Log.i(TAG,resp);
//                                        try {
//                                            String r = t.get("acquirer_response_code").toString();
//                                            String emv_r = t.get("card_emv_response").toString();
//                                            Log.d("Pagar.me", "resp = " + r);
//                                            Log.d("CACETE", "ROLOU ASSINATURA?");
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        String resp = ("err: " + error.toString());
//                                        Log.i(TAG,resp);
//                                    }
//                                });

                        try {
                            String r = t.get("acquirer_response_code").toString();
                            String emv_r = t.get("card_emv_response").toString();
                            Log.d("Pagar.me", "resp = " + r);
                            Log.d("Pagar.me", "emv_resp = " + emv_r);
                            mpos.finishTransaction(true, Integer.parseInt((String) t.get("acquirer_response_code".toString())), (String) t.get("card_emv_response").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
