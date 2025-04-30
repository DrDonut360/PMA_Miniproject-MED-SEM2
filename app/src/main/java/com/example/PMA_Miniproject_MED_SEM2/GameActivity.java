package com.example.PMA_Miniproject_MED_SEM2;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PMA_Miniproject_MED_SEM2.Items.Item;
import com.example.PMA_Miniproject_MED_SEM2.Items.LShapedItem;
import com.example.PMA_Miniproject_MED_SEM2.Items.LineItem;
import com.example.PMA_Miniproject_MED_SEM2.Items.SquareItem;

import java.util.*;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GridLayout gridLayout;
    private TextView scoreText;
    private View[][] gridCells;
    private final int rows = 16;  // Number of rows
    private final int columns = 8; // Number of columns
    private int score = 0;
    private List<Item> itemPool = new ArrayList<>();
    private boolean[][] occupied = new boolean[rows][columns]; // tracks used cells

    // Accelerometer sensor variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private long lastUpdate;
    private static final int SHAKE_THRESHOLD = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get the references for score text and grid layout
        scoreText = findViewById(R.id.scoreText);
        gridLayout = findViewById(R.id.gridLayout);

        // Get the screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Calculate tile size based on screen size, preserving number of rows and columns
        // Let's scale down the tile size to avoid excessive grid size
        int tileWidth = screenWidth / columns;  // Divide the screen width by the number of columns
        int tileHeight = screenHeight / (rows + 2);  // Give some space for the score and UI elements

        // Optionally, we can limit the maximum tile size to avoid extreme scaling
        int maxTileSize = 150;
        tileWidth = Math.min(tileWidth, maxTileSize);
        tileHeight = Math.min(tileHeight, maxTileSize);

        // Set grid layout row and column count
        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(columns);

        // Initialize grid and item pool
        gridCells = new View[rows][columns];
        createEmptyGrid(tileWidth, tileHeight); // Pass dynamic width and height per tile
        initializeItemPool();
        placeItems();

        // Initialize the sensor manager and accelerometer for shake detection
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
    }

    // SensorEventListener methods to detect shake
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if (curTime - lastUpdate > 100) {  // Update every 100ms to save battery
                long diffTime = curTime - lastUpdate;
                lastUpdate = curTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    // Shake detected, rearrange items
                    rearrangeItems();
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this implementation
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the accelerometer sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the accelerometer sensor listener to save battery
        sensorManager.unregisterListener(this);
    }

    // Modify this method to dynamically adjust grid size based on screen size
    private void createEmptyGrid(int tileWidth, int tileHeight) {
        gridLayout.removeAllViews();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                View cell = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = tileWidth;    // Set dynamic width for grid tile
                params.height = tileHeight;  // Set dynamic height for grid tile
                params.setMargins(2, 2, 2, 2); // Optional: Add some spacing between tiles
                cell.setLayoutParams(params);
                cell.setBackgroundColor(Color.LTGRAY);
                gridLayout.addView(cell);
                gridCells[row][col] = cell;
            }
        }
    }

    private void initializeItemPool() {
        itemPool.add(new LShapedItem());
        itemPool.add(new LineItem());
        itemPool.add(new SquareItem());
        // Add more shapes here
    }

    private void placeItems() {
        Random rand = new Random();
        int totalTiles = rows * columns;
        int fillTargetTiles = (int)(totalTiles * 0.55); // 55% of grid
        int filledTiles = 0;

        // Continue spawning items until we've filled the target amount of tiles
        while (filledTiles < fillTargetTiles) {
            // Pick a random item from the pool
            Item item = itemPool.get(rand.nextInt(itemPool.size()));
            boolean placed = false;

            // Try up to 100 random placements for each item
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int anchorRow = rand.nextInt(rows);
                int anchorCol = rand.nextInt(columns);

                List<int[]> shape = item.getShapeOffsets();
                List<int[]> absPositions = new ArrayList<>();
                boolean fits = true;

                // Check if the item fits in the grid at this random position
                for (int[] offset : shape) {
                    int r = anchorRow + offset[0];
                    int c = anchorCol + offset[1];
                    if (r < 0 || r >= rows || c < 0 || c >= columns || occupied[r][c]) {
                        fits = false;
                        break;
                    }
                    absPositions.add(new int[]{r, c});
                }

                // If the item fits, place it on the grid
                if (fits) {
                    for (int[] pos : absPositions) {
                        int r = pos[0];
                        int c = pos[1];
                        View cell = gridCells[r][c];
                        cell.setBackgroundColor(item.getColor());
                        cell.setOnClickListener(v -> {
                            score += item.getValue();
                            scoreText.setText("Score: " + score);
                            for (int[] p : absPositions) {
                                gridCells[p[0]][p[1]].setBackgroundColor(Color.LTGRAY);
                                gridCells[p[0]][p[1]].setOnClickListener(null);
                                occupied[p[0]][p[1]] = false;
                            }
                        });
                        occupied[r][c] = true;
                    }

                    // Update the number of filled tiles
                    filledTiles += shape.size();
                    placed = true;
                }
            }
        }
    }

    private void rearrangeItems() {
        // Randomize item positions to simulate rearranging the backpack/grid
        Random rand = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                View cell = gridCells[row][col];
                cell.setBackgroundColor(Color.LTGRAY);  // Reset cell color
                occupied[row][col] = false;  // Reset occupied state
            }
        }
        placeItems(); // Refill the grid with items
    }
}
