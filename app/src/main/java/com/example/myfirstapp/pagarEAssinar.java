package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.myfirstapp.CustomAdapter.checkedView;


public class pagarEAssinar extends AppCompatActivity {

    private static final String TAG = pagarEAssinar.class.getName();
    public static String dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_eassinar);
        // initiate a ListView
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.bringToFront();

        //getting paired devices data
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        List<String> s = new ArrayList<String>();
        for(BluetoothDevice bt : pairedDevices)
            s.add(bt.getName());
        String[] devices = s.toArray(new String[0]);

        // set the adapter to fill the data in ListView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), devices);
        listView.setAdapter(customAdapter);
    }

    public void callPinpad(View view){

        Log.i(TAG,checkedView.getText().toString());

        Intent intent = new Intent(this, assinatura.class);
        intent.putExtra(dev, checkedView.getText().toString());
        startActivity(intent);
    }


}
