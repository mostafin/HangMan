package com.example.maciej.wisielec;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Ekran_Glowny extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ekran__glowny);

        ImageView image = (ImageView) findViewById(R.id.im);
        image.setImageResource(R.drawable.tool);
        image.setVisibility(image.VISIBLE);




        View button1 =(View)findViewById(R.id.textView);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Wybor_trybu.class);
                startActivity(intent);
            }
        });

        View button2 =(View)findViewById(R.id.textView2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Multi_Player.class);
                startActivity(intent);
            }
        });

    }
}
