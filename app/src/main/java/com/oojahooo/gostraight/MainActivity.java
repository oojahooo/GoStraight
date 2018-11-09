package com.oojahooo.gostraight;

import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
                intent.putExtra("com.oojahooo.gostraight.MESSAGE", message);
                startActivity(intent);
            }
        });
    }
}
