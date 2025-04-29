package com.example.PMA_Miniproject_MED_SEM2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    // this is the activity, it controls the activation adn deactivation methods.
    // it also calls for the gamepanal class to be activated therfor the gameView.

    //Sensor
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private long lastUpdateTime;
    private static final int SHAKE_THRESHOLD = 800; // Adjust as needed
    private static final int TIME_THRESHOLD = 100; // Time interval for detecting shake (in milliseconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Launch the panel as the content
        GamePanel gamePanel = new GamePanel(this);
        setContentView(gamePanel);

        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    // SENSOR MEtHODS
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdateTime > TIME_THRESHOLD) {
                long diffTime = currentTime - lastUpdateTime;
                lastUpdateTime = currentTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    // Shake detected, trigger item reset
                    resetItems();
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can handle sensor accuracy changes here if needed
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // Unregister sensor when activity is paused
    }

    // Method to reset items when shake is detected
    private void resetItems() {
        // Call your reset logic here to reposition items
        //playManager.resetItems();
    }
}

