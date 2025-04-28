package com.example.PMA_Miniproject_MED_SEM2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Launch the panel as the content
        GamePanel gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }
}
