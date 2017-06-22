package com.example.maciej.wisielec;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.lang.InterruptedException;

import java.util.StringTokenizer;

public class Single_Player extends Activity {
    DataBaseHelper myDb;
    String Coded_Password;
    String Password_to_gues;
    TextView score_view_pointer,category_reminder;
    TextView pole;
    int mistake;
    int score;
    int []tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_singel_player);
        myDb = new DataBaseHelper(this);
        score_view_pointer = (TextView) findViewById(R.id.scoreView);
        category_reminder=(TextView)findViewById(R.id.textView4);
        tab = new int[]{R.id.a, R.id.ą, R.id.b, R.id.c, R.id.ć, R.id.d, R.id.e, R.id.ę, R.id.f, R.id.g,
                R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.ł, R.id.m, R.id.n, R.id.ń, R.id.o, R.id.ó, R.id.p,
                R.id.q, R.id.r, R.id.s, R.id.ś, R.id.t, R.id.u, R.id.w, R.id.z, R.id.ź, R.id.ż, R.id.v, R.id.x, R.id.y};
        score = 0;

        //////////Dodawanie do bazy danych
        AddData();

        ///Wynik poczatkowy
        score_view_pointer.setText("Punkty:0");
        GAME();




    }

    //////////onClik na litery

    public void Letter_onClick(View v) throws InterruptedException {
        TextView buff = (TextView) v;
        String name = buff.getText().toString();
        String name2 = getResources().getResourceEntryName(buff.getId());
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica);

        boolean check = false;
        buff.setClickable(false);
        for (int i = 0; i < Password_to_gues.length(); i++) {

            if (Password_to_gues.charAt(i) == name.charAt(1)) {
                Coded_Password = Coded_Password.substring(0, i) + name.charAt(1) + Coded_Password.substring(i + 1);
                pole.setText(Coded_Password);
                check = true;
            }
            if (name2.equals("i")) {
                if (Password_to_gues.charAt(i) == name.charAt(2)) {
                    Coded_Password = Coded_Password.substring(0, i) + name.charAt(2) + Coded_Password.substring(i + 1);
                    pole.setText(Coded_Password);
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
            mistake++;


            switch (mistake) {
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
                    showMessage_Lose();
                    break;
                }
            }
        }
        if (Coded_Password.equals(Password_to_gues)) {
            SeeScore();
            showMessage_Win();
        }
    }

    public void AddData() {
        myDb.insertData("Pierogi ruskie", "Jedzenie");
        myDb.insertData("Honduras", "Geografia");
        myDb.insertData("Port Royal", "Geografia");
        myDb.insertData("Zatoka Meksykańska", "Geografia");
        myDb.insertData("Pustynia Gobi", "Geografia");
        myDb.insertData("Koło Podbiegunowe", "Geografia");
        myDb.insertData("Cieśnina Bosfor", "Geografia");
        myDb.insertData("Amazonka", "Geografia");
        myDb.insertData("Mierzeja Wiślana", "Geografia");
        myDb.insertData("Madagaskar", "Geografia");
        myDb.insertData("Mikronezja", "Geografia");
        myDb.insertData("Dżakarta", "Geografia");
        myDb.insertData("Góry Stołowe", "Geografia");
        myDb.insertData("Morze Ochockie", "Geografia");
        myDb.insertData("Jangcy", "Geografia");
        myDb.insertData("Pensylwania", "Geografia");

        myDb.insertData("Pierogi ruskie", "Jedzenie");
        myDb.insertData("Ryba po grecku", "Jedzenie");
        myDb.insertData("Małże", "Jedzenie");
        myDb.insertData("Prosciutto", "Jedzenie");
        myDb.insertData("Lasagne", "Jedzenie");
        myDb.insertData("Risotto", "Jedzenie");
        myDb.insertData("Sashimi", "Jedzenie");
        myDb.insertData("guacamole", "Jedzenie");
        myDb.insertData("CReME BReLeE", "Jedzenie");
        myDb.insertData("KURCZAK TANDOORI", "Jedzenie");
        myDb.insertData("Tiramisu", "Jedzenie");
        myDb.insertData("CANNELLONI", "Jedzenie");
        myDb.insertData("Kluski śląskie", "Jedzenie");
        myDb.insertData("Królewiec", "Jedzenie");
        myDb.insertData("Gołąbki", "Jedzenie");

        myDb.insertData("Konik Morski", "Zwierzęta");
        myDb.insertData("Langusta", "Zwierzęta");
        myDb.insertData("Fenek", "Zwierzęta");
        myDb.insertData("Kameleon", "Zwierzęta");
        myDb.insertData("Uszatka", "Zwierzęta");
        myDb.insertData("Kiwi", "Zwierzęta");
        myDb.insertData("Nosorożec", "Zwierzęta");
        myDb.insertData("Leniwiec", "Zwierzęta");
        myDb.insertData("Mrówkojad", "Zwierzęta");
        myDb.insertData("Kałamarnica", "Zwierzęta");
        myDb.insertData("Szczupak", "Zwierzęta");
        myDb.insertData("Pantera", "Zwierzęta");
        myDb.insertData("łoś", "Zwierzęta");
        myDb.insertData("Myszoskoczek", "Zwierzęta");
        myDb.insertData("Wiewiórka", "Zwierzęta");
        myDb.insertData("Lew Morski", "Zwierzęta");
        myDb.insertData("Waran z Komodo", "Zwierzęta");

        myDb.insertData("Piłka Nożna", "Sport");
        myDb.insertData("Baseball", "Sport");
        myDb.insertData("Adam Małysz", "Sport");
        myDb.insertData("Ewa Swoboda", "Sport");
        myDb.insertData("Rzut Oszczepem", "Sport");
        myDb.insertData("Pchnięcie Kulą", "Sport");
        myDb.insertData("Futbol Amerykański", "Sport");
        myDb.insertData("Robert Lewandowski", "Sport");
        myDb.insertData("Justyna Kowalczyk", "Sport");
        myDb.insertData("Adam Nawałka", "Sport");
        myDb.insertData("Siatkówka", "Sport");
        myDb.insertData("Spływ Kajakiem", "Sport");
        myDb.insertData("Skok wzwyż", "Sport");
        myDb.insertData("łukasz Piszczek", "Sport");
        myDb.insertData("Piłka ręczna", "Sport");
        myDb.insertData("Pływanie synchroniczne", "Sport");
        myDb.insertData("Gimnastyka", "Sport");


    }

    public  void showMessage_Win() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Brawo");
        builder.setMessage("Zgadłeś !");
        builder.setPositiveButton("Nastepne hasło", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Single_Player.this.Reset();
                Single_Player.this.GAME();
            }
        });
        builder.setNegativeButton("Koniec", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),Wybor_trybu.class);
                startActivity(intent);
            }
        });
        builder.show();

    }
    public void showMessage_Lose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Porażka");
        builder.setMessage("Zawisłeś, Hasło to :" + Password_to_gues + "\nKoniec gry !");
        builder.setPositiveButton("Powrot", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),Wybor_trybu.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    public  static  String ChangeToPassword(String password) {
        String password_buffor = "";
        for (int i = 0; i < password.length(); i++) {
            char buf = password.charAt(i);
            if (buf != ' ')
                password_buffor += "-";
            else
                password_buffor += ' ';

        }
       // pole.setText(password_buffor);
        return password_buffor;
    }

    public String getPassword(String category) {
        Cursor res = myDb.getWord(category);
        String choose_word = res.getString(1).toUpperCase();
        return choose_word;
    }

    public void SeeScore() {
        score++;
        String buff = "Punkty:" + score;
        score_view_pointer.setText(buff);
    }

    public void GAME() {
        mistake = 0;
        score_view_pointer.setText("Punkty:" + score);

        //////////Kwerenda
        String CATEGORY = getIntent().getStringExtra("Category");

        ///////ustawianie przypomienia kategori w panely gry
        category_reminder.setText(CATEGORY);

        //////////Haslo do odgadniecia
        Password_to_gues = getPassword(CATEGORY);

        //////////Kodowanie hasla
        pole = (TextView) findViewById(R.id.editText);
        Coded_Password = ChangeToPassword(Password_to_gues);
        pole.setText(Coded_Password);

    }
    public void Reset(){
        TextView buff;
        for(int x : tab) {
            buff = (TextView) findViewById(x);
            buff.setTextColor(Color.parseColor("#FFFFFF"));
            buff.setBackgroundResource(R.drawable.border_style);
            buff.setClickable(true);
        }
        ImageView szubienica = (ImageView) findViewById(R.id.szubienica);
        szubienica.setImageResource(0);
    }

}
/*
  public void ShowSelectedRow(final String category) {
        View button4 = (View)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id" + res.getString(0) + "\n");
                   buffer.append("Word :" + res.getString(1) + "\n");
                    buffer.append("Category :" + res.getString(2) + "\n");
                    buffer.append("Amount :" + res.getString(3) + "\n\n");

                }
                showMessage("Data", buffer.toString());
                    Cursor res = myDb.getWord(category);
                    StringBuffer buffer = new StringBuffer();
                   buffer.append("Id" + String.valueOf(res.getInt(0)) + "\n");
                    buffer.append("Word :" + res.getString(1) + "\n");
                    buffer.append("Amount :" + String.valueOf(res.getInt(2)) + "\n\n");
                    showMessage("Data", buffer.toString());

            }
        });
    }
    */


  /* public void DeleteData() {
        View button3 = (View) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                myDb.deleteAll();
            }
        });
    }*/

    /*  public void ViewAllData() {
          View button6 = (View)findViewById(R.id.button6);
          button6.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                    Cursor res = myDb.getAllData();
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                     buffer.append("Id" + res.getString(0) + "\n");
                     buffer.append("Word :" + res.getString(1) + "\n");
                     buffer.append("Category :" + res.getString(2) + "\n");
                     buffer.append("Amount :" + res.getString(3) + "\n\n");
                     }
                    showMessage("Data", buffer.toString());
              }
          });
      }*/

