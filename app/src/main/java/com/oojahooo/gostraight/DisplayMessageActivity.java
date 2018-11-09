package com.oojahooo.gostraight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra("com.oojahooo.gostraight.MESSAGE");
        TextView textView = (TextView)findViewById(R.id.myMessage);
        textView.setText(message);
    }
}
