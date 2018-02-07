package com.apsu.joshua.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button button = (Button) findViewById(R.id.btnPlay);
        button.setOnClickListener(this);

        Button button1 = (Button) findViewById(R.id.btnHow);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnPlay){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btnHow){
            Intent intent = new Intent(this, HowActivity.class);
            startActivity(intent);
        }

    }
}
