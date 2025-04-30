package com.example.PMA_Miniproject_MED_SEM2;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    // this handles how the game is displayed and run
    //it also calls for the playmanager class.
    private Thread gameThread;
    private final int FPS = 60;
    private PlayManager playManager; // call playmanager Class
    private boolean isRunning;

    public GamePanel(Context context, RelativeLayout layout) {
        super(context);
        getHolder().addCallback(this);
        playManager = new PlayManager(this, layout); // Pass View if needed
        launchGame();
    }

    private void launchGame() {
        isRunning = true;
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
        //PlayManager.fillGridCompactly();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        launchGame();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false; // Stop the game loop when the surface is destroyed
        while (retry) {
            try {
                gameThread.join(); // Wait for the game thread to finish
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
