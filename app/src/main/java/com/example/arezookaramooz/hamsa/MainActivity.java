package com.example.arezookaramooz.hamsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText inputEditText;
    String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamsa_main_activity);

        inputEditText = findViewById(R.id.input_edit_text);
        Button okButton = findViewById(R.id.ok_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = inputEditText.getText().toString();
                Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                myIntent.putExtra("inputString", inputText);
                Log.d("MainActivity", "inputText is: " + inputText);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}