package com.example.maciej.wisielec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

/**
 * Created by Maciej on 20/05/2017.
 */

public class MultiPlayerGame_Client extends Activity {
    int[] tab_client;
    DataInputStream dIn;
    DataOutputStream dout;
    String Password_Client;
    AlertDialog dialog;
    int mistake_Client, dstPort;
    String Coded_Password_Client;
    TextView pole_client, p_c, p_s;
    ByteArrayOutputStream byteArrayOutputStream;
    String Client_Name, dstAdress;
    byte[] buffer;
    int score;
    Socket socket;
    ProgressDialog progressDialog;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.multiplayergame_client);

        pole_client = (TextView) findViewById(R.id.editText_client);
        tab_client = new int[]{R.id.a, R.id.ą, R.id.b, R.id.c, R.id.ć, R.id.d, R.id.e, R.id.ę, R.id.f, R.id.g,
                R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.ł, R.id.m, R.id.n, R.id.ń, R.id.o, R.id.ó, R.id.p,
                R.id.q, R.id.r, R.id.s, R.id.ś, R.id.t, R.id.u, R.id.w, R.id.z, R.id.ź, R.id.ż, R.id.v, R.id.x, R.id.y};

        byteArrayOutputStream = new ByteArrayOutputStream(1024);
        buffer = new byte[1024];
        Client_Name = getIntent().getStringExtra("Client_Nick");
        dstAdress = getIntent().getStringExtra("dstAddress");
        dstPort = getIntent().getIntExtra("dstPort", 0);


        p_c = (TextView) findViewById(R.id.p_c);
        flag = false;


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MultiPlayerGame_Client.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        mBuilder.setView(mView);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (!mPassword.getText().toString().isEmpty()) {
                        Send_C(mPassword.getText().toString());
                        dialog.dismiss();
                        showProgressDialogWithTitle();

                    } else {
                        Toast.makeText(MultiPlayerGame_Client.this, "Nie Podałeś hasła", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, " Nie udało sie wysłac - CLIENT", Toast.LENGTH_SHORT);
                    toast.show();
                }
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        mBuilder.setNegativeButton("Koniec Gry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(getApplicationContext(), Multi_Player.class);
                startActivity(intent);
            }
        });
        dialog = mBuilder.create();

//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!mPassword.getText().toString().isEmpty()) {
//                    dialog.dismiss();
//
//                } else {
//                    Toast.makeText(MultiPlayerGame_Client.this, "Nie Podałeś hasła", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        score = 0;
        System.out.println("Port:"+ dstAdress);
        showProgressDialogWithTitle();
        MyClientTask clientTask = new MyClientTask(dstAdress,dstPort);
        clientTask.execute();
        setNames(Client_Name);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void MessageToSend(AlertDialog dialog) {
        dialog.show();
    }

    public void Reset_Client() {
        TextView buff;
        for (int x : tab_client) {
            buff = (TextView) findViewById(x);
            buff.setTextColor(Color.parseColor("#FFFFFF"));
            buff.setBackgroundResource(R.drawable.border_style);
            buff.setClickable(true);
        }
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica_client);
        szubienica.setImageResource(0);
        Password_Client = "";
    }

    public void Letter_onClick(View v) throws InterruptedException {
        TextView buff = (TextView) v;
        String name = buff.getText().toString();
        String name2 = getResources().getResourceEntryName(buff.getId());
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica_client);

        boolean check = false;
        buff.setClickable(false);
        for (int i = 0; i < Password_Client.length(); i++) {

            if (Password_Client.charAt(i) == name.charAt(1)) {
                Coded_Password_Client = Coded_Password_Client.substring(0, i) + name.charAt(1) + Coded_Password_Client.substring(i + 1);
                MultiPlayerGame_Client.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        pole_client.setText(Coded_Password_Client);
                    }
                });
               // pole_client.setText(Coded_Password_Client);
                check = true;
            }
            if (name2.equals("i")) {
                if (Password_Client.charAt(i) == name.charAt(2)) {
                    Coded_Password_Client = Coded_Password_Client.substring(0, i) + name.charAt(2) + Coded_Password_Client.substring(i + 1);
                    MultiPlayerGame_Client.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pole_client.setText(Coded_Password_Client);
                        }
                    });
                    //pole_client.setText(Coded_Password_Client);
                    check = true;
                    /* Context context = getApplicationContext();
                      CharSequence text = name2;
                     int duration = Toast.LENGTH_SHORT;

                     Toast toast = Toast.makeText(context,name2, duration);
                     toast.show();*/
                }
            }
        }
        if (check == true) {
            buff.setTextColor(Color.parseColor("#67F967"));
            buff.setBackgroundResource(R.drawable.border_style_green);
        } else {
            buff.setTextColor(Color.parseColor("#E74747"));
            buff.setBackgroundResource(R.drawable.border_style_red);
            mistake_Client++;


            switch (mistake_Client) {
                case 1: {
                    szubienica.setVisibility(szubienica.VISIBLE);
                    szubienica.setImageResource(R.drawable.s1);
                    break;
                }
                case 2: {
                    szubienica.setImageResource(R.drawable.s2);
                    break;
                }
                case 3: {
                    szubienica.setImageResource(R.drawable.s3);
                    break;
                }
                case 4: {
                    szubienica.setImageResource(R.drawable.s4);
                    break;
                }
                case 5: {
                    szubienica.setImageResource(R.drawable.s5);
                    break;
                }
                case 6: {
                    szubienica.setImageResource(R.drawable.s6);
                    break;
                }
                case 7: {
                    szubienica.setImageResource(R.drawable.s7);
                    break;
                }
                case 8: {
                    szubienica.setImageResource(R.drawable.s8);
                    break;
                }
                case 9: {
                    szubienica.setImageResource(R.drawable.s9);
                    showMessage_Lose(dialog);
                    break;
                }
            }
        }
        if (Coded_Password_Client.equals(Password_Client)) {
            setScore();
            showMessage_Win(dialog);
        }
    }


    public void showMessage_Win(final AlertDialog dial) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Brawo");
        builder.setMessage("Zgadłeś !");
        builder.setPositiveButton("Nastepne hasło", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MessageToSend(dial);
                Reset_Client();
            }
        });
        builder.setNegativeButton("Koniec", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Reset_Client();
                Intent intent = new Intent(getApplicationContext(), Wybor_trybu.class);
                startActivity(intent);
            }
        });
        builder.show();

    }

    public void showMessage_Lose(final AlertDialog dial) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Porażka");
        builder.setMessage("Zawisłeś, Hasło to :" + Password_Client);
        builder.setNegativeButton("Koniec", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Reset_Client();
                Intent intent = new Intent(getApplicationContext(), Wybor_trybu.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Następne hasło", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MessageToSend(dial);
                Reset_Client();
            }
        });
        builder.show();
    }

    public void GAME(String pw) {
        mistake_Client = 0;
        progressDialog.dismiss();
        //////////Kodowanie hasla
        Coded_Password_Client = Single_Player.ChangeToPassword(pw);
        MultiPlayerGame_Client.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pole_client.setText(Coded_Password_Client);
            }
        });
    }

    public void setNames(String nick) {
        p_c.setText(nick + ":"+score);
    }

    public void setScore() {
        score++;
        MultiPlayerGame_Client.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                p_c.setText(Client_Name +":"+ score);
            }
        });
    }

    public class MyClientTask extends AsyncTask<Void,Void,Void>{

        String dstAddress;
        int dstPort;

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;

        }

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                socket = null;
//                try {
//                    System.out.println("czekammm");
//                    socket = new Socket(dstAddress, dstPort);
//
//                    dout = new DataOutputStream(socket.getOutputStream());
//                    dout.flush();
//                    dIn = new DataInputStream(socket.getInputStream());
//
//                    Context context = getApplicationContext();
//                    Toast toast = Toast.makeText(context, "Polaczono", Toast.LENGTH_SHORT);
//                    toast.show();
//
//                    while(true){
//                        Listener_C();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        @Override
        protected Void doInBackground(Void... params) {
            socket = null;
            try {
                System.out.println("czekammm");
                socket = new Socket(dstAddress, dstPort);

                dout = new DataOutputStream(socket.getOutputStream());
                dout.flush();
                dIn = new DataInputStream(socket.getInputStream());


                while(true){
                    Listener_C();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void Send_C(String pw) throws IOException {dout.writeUTF(pw);}
    public void Listener_C() throws IOException {

        Password_Client = dIn.readUTF();
        Password_Client = Password_Client.toUpperCase();
        System.out.print("Odebrałem");
        GAME(Password_Client);
    }

    private void showProgressDialogWithTitle() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Czekaj..");
        progressDialog.setMessage("Trwa tura przeciwnika ...");
        progressDialog.show();
    }
}