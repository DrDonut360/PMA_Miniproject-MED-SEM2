// Main package of the project
package com.example.PMA_Miniproject_MED_SEM2;

// Imports for Android views, sensors, and utilities
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PMA_Miniproject_MED_SEM2.Items.Item;
import com.example.PMA_Miniproject_MED_SEM2.Items.LShapedItem;
import com.example.PMA_Miniproject_MED_SEM2.Items.LineItem;
import com.example.PMA_Miniproject_MED_SEM2.Items.SquareItem;

import java.util.*;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    // UI elements
    private GridLayout gridLayout;
    private TextView scoreText;

    // Grid setup
    private View[][] gridCells;
    private final int rows = 16;
    private final int columns = 8;
    private int score = 0;
    private List<Item> itemPool = new ArrayList<>();
    private boolean[][] occupied = new boolean[rows][columns];

    // Sensor variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private long lastUpdate;
    private static final int SHAKE_THRESHOLD = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreText = findViewById(R.id.scoreText);
        gridLayout = findViewById(R.id.gridLayout);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Calculate tile sizes
        int tileWidth = screenWidth / columns;
        int tileHeight = screenHeight / (rows + 2);

        int maxTileSize = 150;
        tileWidth = Math.min(tileWidth, maxTileSize);
        tileHeight = Math.min(tileHeight, maxTileSize);

        // Set up the grid layout with desired dimensions
        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(columns);

        gridCells = new View[rows][columns];
        createEmptyGrid(tileWidth, tileHeight); // Builds and shows the grid
        initializeItemPool(); // Adds items to choose from
        placeItems(); // Randomly places items in the grid

        // Prepare sensor for shake detection
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
    }

    // Detects movement from the accelerometer
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if (curTime - lastUpdate > 100) { // Run every 100ms
                long diffTime = curTime - lastUpdate;
                lastUpdate = curTime;

                // Get new accelerometer values
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Calculate movement speed using change in x, y, z
                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                // Check if speed crosses the shake threshold
                if (speed > SHAKE_THRESHOLD) {
                    rearrangeItems(); // Shuffle grid on shake
                }

                // Save current values for the next comparison
                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // Creates a blank grid of tiles (Views)
    private void createEmptyGrid(int tileWidth, int tileHeight) {
        gridLayout.removeAllViews(); // Clear any previous content

        // Outer loop creates each row
        for (int row = 0; row < rows; row++) {
            // Inner loop creates each column within the row
            for (int col = 0; col < columns; col++) {
                View cell = new View(this); // A blank tile
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = tileWidth;
                params.height = tileHeight;
                params.setMargins(2, 2, 2, 2);
                cell.setLayoutParams(params);
                cell.setBackgroundColor(Color.LTGRAY); // Default background
                gridLayout.addView(cell); // Add to grid
                gridCells[row][col] = cell; // Store reference
            }
        }
    }

    // Adds available item shapes to the pool
    private void initializeItemPool() {
        itemPool.add(new LShapedItem());
        itemPool.add(new LineItem());
        itemPool.add(new SquareItem());
    }

    // Attempts to place items randomly until grid is ~55% full
    private void placeItems() {
        Random rand = new Random();
        int totalTiles = rows * columns;
        int fillTargetTiles = (int)(totalTiles * 0.55); // Fill target is 55%
        int filledTiles = 0;

        // Keep adding items until fill target is reached
        while (filledTiles < fillTargetTiles) {
            // Pick a random item from the pool
            Item item = itemPool.get(rand.nextInt(itemPool.size()));
            boolean placed = false;

            // Try up to 100 times to place this item somewhere valid
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int anchorRow = rand.nextInt(rows); // Pick a random cell
                int anchorCol = rand.nextInt(columns);

                List<int[]> shape = item.getShapeOffsets(); // Item's shape pattern
                List<int[]> absPositions = new ArrayList<>(); // Holds final positions of shape
                boolean fits = true;

                // Check if the entire shape fits in the grid and doesn't overlap
                for (int[] offset : shape) {
                    int r = anchorRow + offset[0];
                    int c = anchorCol + offset[1];
                    // If out of bounds or already occupied, reject placement
                    if (r < 0 || r >= rows || c < 0 || c >= columns || occupied[r][c]) {
                        fits = false;
                        break;
                    }
                    absPositions.add(new int[]{r, c}); // Store valid cell
                }

                if (fits) {
                    // Place the item: color its tiles and set up interaction
                    for (int[] pos : absPositions) {
                        int r = pos[0];
                        int c = pos[1];
                        View cell = gridCells[r][c];
                        cell.setBackgroundColor(item.getColor());

                        // Set click listener to collect item
                        cell.setOnClickListener(v -> {
                            score += item.getValue(); // Add points
                            scoreText.setText("Score: " + score);
                            // Clear all parts of this item
                            for (int[] p : absPositions) {
                                gridCells[p[0]][p[1]].setBackgroundColor(Color.LTGRAY);
                                gridCells[p[0]][p[1]].setOnClickListener(null);
                                occupied[p[0]][p[1]] = false;
                            }
                        });

                        occupied[r][c] = true; // Mark cell as used
                    }
                    filledTiles += shape.size(); // Count the number of cells this item used
                    placed = true; // Exit the retry loop
                }
            }
        }
    }

    // Resets the grid and repopulates it with new items
    private void rearrangeItems() {
        Random rand = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                View cell = gridCells[row][col];
                cell.setBackgroundColor(Color.LTGRAY); // Reset color
                occupied[row][col] = false; // Mark as empty
            }
        }
        placeItems(); // Place new items again
    }
}