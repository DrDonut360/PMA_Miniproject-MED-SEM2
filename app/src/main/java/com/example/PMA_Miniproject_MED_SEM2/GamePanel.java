package com.example.PMA_Miniproject_MED_SEM2;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GamePanel extends View implements Runnable {
    // this handles how the game is displayed and run
    //it also calls for the playmanager class.
    private Thread gameThread;
    private final int FPS = 60;
    private PlayManager playManager; // call playmanager Class

    public GamePanel(Context context) {
        super(context);
        playManager = new PlayManager(this); // Pass View if needed
        launchGame();
    }

    private void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                postInvalidate(); // triggers onDraw
                delta--;
            }
        }
    }

    public void update() {
        playManager.update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        playManager.draw(canvas);
    }

    public void resetItems(){
        PlayManager.fillGridCompactly();
    }
}
