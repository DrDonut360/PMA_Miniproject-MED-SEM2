package com.example.PMA_Miniproject_MED_SEM2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set your main layout here

        // Get the button from the layout
        Button playButton = findViewById(R.id.playButton);

        // Set a click listener on the button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the button is clicked, start a new activity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);  // Replace GameActivity with the actual activity to open
                startActivity(intent);
            }
        });
    }
}
