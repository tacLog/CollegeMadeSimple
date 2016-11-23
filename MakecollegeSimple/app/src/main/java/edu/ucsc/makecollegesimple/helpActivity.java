package edu.ucsc.makecollegesimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class helpActivity extends AppCompatActivity {


    public Button but2;

    public void init() {
        but2 = (Button)findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(helpActivity.this,scholarship.class);
                startActivity(toy);
            }
        });
    }


    public Button but3;

    public void init2() {
        but3 = (Button)findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(helpActivity.this,grant.class);
                startActivity(toy);
            }
        });
    }


    public Button but4;

    public void init3() {
        but4 = (Button)findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(helpActivity.this,loan.class);
                startActivity(toy);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        init();
        init2();
        init3();
    }
}
