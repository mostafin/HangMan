package com.example.maciej.wisielec;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

/**
 * Created by Maciej on 03/05/2017.
 */
public class Client extends Activity {

    TextView textResponse;
    EditText editTextAddress, editTextPort, playerName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_client);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        textResponse = (TextView) findViewById(R.id.response);
        playerName = (EditText) findViewById(R.id.editText3);


        View connect = (View) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultiPlayerGame_Client.class);
                intent.putExtra("Client_Nick", playerName.getText().toString());
                intent.putExtra("dstAddress", editTextAddress.getText().toString());
                intent.putExtra("dstPort", Integer.parseInt(editTextPort.getText().toString()));
                startActivity(intent);
            }
        });
    }
}



