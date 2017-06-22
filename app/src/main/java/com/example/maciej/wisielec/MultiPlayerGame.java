package com.example.maciej.wisielec;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by Maciej on 05/05/2017.
 */
public class MultiPlayerGame extends Activity {

    int[] tab_serwer;
    DataInputStream dIn;
    DataOutputStream dout;
    String Password_Serwer, Serwer_Name;
    String Coded_Password_Serwer;
    TextView pole_serwer, p_s;
    int mistake_serwer, score;
    AlertDialog dialog;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] buffer;
    Socket socket;
    ServerSocket serverSocket;
    ProgressDialog progressDialog;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayergame);

        pole_serwer = (TextView) findViewById(R.id.editText_serwer);
        tab_serwer = new int[]{R.id.a, R.id.ą, R.id.b, R.id.c, R.id.ć, R.id.d, R.id.e, R.id.ę, R.id.f, R.id.g,
                R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.ł, R.id.m, R.id.n, R.id.ń, R.id.o, R.id.ó, R.id.p,
                R.id.q, R.id.r, R.id.s, R.id.ś, R.id.t, R.id.u, R.id.w, R.id.z, R.id.ź, R.id.ż, R.id.v, R.id.x, R.id.y};

        byteArrayOutputStream = new ByteArrayOutputStream(1024);
        buffer = new byte[1024];
        Serwer_Name = getIntent().getStringExtra("Server_Nick");
        p_s = (TextView) findViewById(R.id.p_s);
        flag = false;

        //////////////////////////AlertDialog/////////////////////////////////////////////////
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MultiPlayerGame.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        mBuilder.setView(mView);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               try {
                    if (!mPassword.getText().toString().isEmpty()) {
                        Send_S(mPassword.getText().toString());
                        dialog.dismiss();
                        showProgressDialogWithTitle();
                    }
                    else
                    {
                        Toast.makeText(MultiPlayerGame.this, "Nie Podałeś hasła", Toast.LENGTH_SHORT).show();
                    }
               }
                catch (IOException e) {
                    e.printStackTrace();
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, " Nie udało sie wysłac - SERWER", Toast.LENGTH_SHORT);
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
//                } else {
//                    Toast.makeText(MultiPlayerGame.this, "Nie Podałeś hasła", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();

        score = 0;
        setNames(Serwer_Name);
        MessageToSend(dialog);
    }


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

    public void Reset_Serwer() {
        TextView buff;
        for (int x : tab_serwer) {
            buff = (TextView) findViewById(x);
            buff.setTextColor(Color.parseColor("#FFFFFF"));
            buff.setBackgroundResource(R.drawable.border_style);
            buff.setClickable(true);
        }
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica_serwer);
        szubienica.setImageResource(0);
        Password_Serwer = "";
    }

    public void Letter_onClick(View v) throws InterruptedException {
        TextView buff = (TextView) v;
        String name = buff.getText().toString();
        String name2 = getResources().getResourceEntryName(buff.getId());
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica_serwer);

        boolean check = false;
        buff.setClickable(false);
        for (int i = 0; i < Password_Serwer.length(); i++) {

            if (Password_Serwer.charAt(i) == name.charAt(1)) {
                Coded_Password_Serwer = Coded_Password_Serwer.substring(0, i) + name.charAt(1) + Coded_Password_Serwer.substring(i + 1);
                pole_serwer.setText(Coded_Password_Serwer);
                check = true;
            }
            if (name2.equals("i")) {
                if (Password_Serwer.charAt(i) == name.charAt(2)) {
                    Coded_Password_Serwer = Coded_Password_Serwer.substring(0, i) + name.charAt(2) + Coded_Password_Serwer.substring(i + 1);
                    pole_serwer.setText(Coded_Password_Serwer);
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
            mistake_serwer++;


            switch (mistake_serwer) {
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
        if (Coded_Password_Serwer.equals(Password_Serwer)) {
            setScore();
            showMessage_Win(dialog);
        }
    }

    public void showMessage_Win(final AlertDialog dial) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Brawo");
        builder.setMessage("Zgadłeś !");
        builder.setPositiveButton("Nastepne hasło", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MessageToSend(dial);
                Reset_Serwer();
            }
        });
        builder.setNegativeButton("Koniec", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Reset_Serwer();
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
        builder.setMessage("Zawisłeś, Hasło to :" + Password_Serwer);
        builder.setNegativeButton("Koniec", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Reset_Serwer();
                Intent intent = new Intent(getApplicationContext(), Wybor_trybu.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Następne hasło", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MessageToSend(dial);
                Reset_Serwer();
            }
        });
        builder.show();
    }

    public void GAME(String pw) {
        mistake_serwer = 0;
        progressDialog.dismiss();
        //////////Kodowanie hasla
        Coded_Password_Serwer = Single_Player.ChangeToPassword(pw);
        MultiPlayerGame.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pole_serwer.setText(Coded_Password_Serwer);
            }
        });
    }

    public void setNames(String nick) {
        p_s.setText(nick + ":"+score);
    }

    public void setScore() {
        score++;
        MultiPlayerGame.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                p_s.setText(Serwer_Name +":"+ score);
            }
        });
    }

    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 9999;

        @Override
        public void run() {
            try {

                serverSocket = new ServerSocket(SocketServerPORT);
                socket = serverSocket.accept();
                System.out.println("czekammm");
                dout = new DataOutputStream(socket.getOutputStream());
                dout.flush();
                dIn = new DataInputStream(socket.getInputStream());

                while (true) {Listen_S();}
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public void Send_S(String pw) throws IOException {dout.writeUTF(pw);}

    public void  Listen_S() throws IOException {
        Password_Serwer = dIn.readUTF();
        Password_Serwer = Password_Serwer.toUpperCase();
        GAME(Password_Serwer);
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
