package com.jin123d.urp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ChangeColor extends AppCompatActivity {

    Button btn_bule, btn_red;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);
        btn_bule = (Button) findViewById(R.id.btn_blue);
        btn_red = (Button) findViewById(R.id.btn_red);
    }

}
