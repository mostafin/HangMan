package com.example.maciej.wisielec;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class Wybor_trybu extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_wybor_trybu);

        View button2 =(View)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Single_Player.class);
                intent.putExtra("Category","Jedzenie");
                startActivity(intent);
            }
        });

        View button =(View)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Single_Player.class);
                intent.putExtra("Category","Geografia");
                startActivity(intent);
            }
        });

        View button3 =(View)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Single_Player.class);
                intent.putExtra("Category","Zwierzęta");
                startActivity(intent);
            }
        });

        View button4 =(View)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Single_Player.class);
                intent.putExtra("Category","Sport");
                startActivity(intent);
            }
        });

        View button5 =(View)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Single_Player.class);
                intent.putExtra("Category","Wszystkie");
                startActivity(intent);
            }
        });
    }


}
