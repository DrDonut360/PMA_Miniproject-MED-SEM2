package com.example.PMA_Miniproject_MED_SEM2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.DynamicLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;

import java.security.PublicKey;
import java.util.Random;

import android.view.View;

import com.example.PMA_Miniproject_MED_SEM2.Items.Block;
import com.example.PMA_Miniproject_MED_SEM2.Items.Item;
import com.example.PMA_Miniproject_MED_SEM2.Items.Item_L1;

public class PlayManager {
    // Handles all the game logic in terms of :
    // The Drawing of the play area
    // Manages the game pieces / items
    // Handles Gameplay actions (deleting GameObj's, score, etc.)

    // _____ Main Play Area _____ \\ 8x5
    private RelativeLayout layout;
    //private View rootView;
    final int WIDTH = 150; // int WIDTH = COLS * Block.SIZE; //where 360 px
    final int HEIGHT = 240; // int WIDTH = COLS * Block.SIZE; //where 600 px
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    private Paint paint = new Paint();

    //Item stating variables
    Item currItem;
    //final int item_Start_x;
    //final int item_Start_y;
    public ArrayList<Item> Items = new ArrayList<>(); // list of items that change when things get stolen or when the game starts
    public ArrayList<Item> tempItems = new ArrayList<>(); // list of items that is based on the OG list of items.

    private final int COLS = 5;
    private final int ROWS = 8;
    private Item[][] grid = new Item[ROWS][COLS];
    // Singleton instance
    private static PlayManager instance;

    //Contructor
    public PlayManager(View rootView, RelativeLayout layout){
        //Main play area frame
        this.layout = layout;
        //this.rootView = rootView; //Passing in rootView for game activity
        //layout = rootView.findViewById(R.id.gameLayout_id); //finds view by id
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams(); // we get the params of the layout view rootView

        left_x = (params.height/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Gives a starting position to the blocks.
        // this will get rewritten at some point
        // to be a random position from bottom up.
//        item_Start_x = left_x + (WIDTH/2) - Block.SIZE;
//        item_Start_y = top_y + Block.SIZE;
//        currItem = new Item_L1();
//        currItem.setXY(item_Start_x, item_Start_y);
        initialItems();

    }
    public void initialItems() {
        Items.clear(); // Clear the item list before use
        int numItems = 7; // Number of items
        int verticalOffset = Block.SIZE; // Vertical offset between items

        for (int i = 0; i < numItems; i++) {
            currItem = Item.getRandomItem();
            int x = left_x + new Random().nextInt(WIDTH - Block.SIZE); // Randomize X position
            int y = bottom_y - ((i + 1) * verticalOffset); // Stack items from bottom up

            // Set the position for each block of the item
            currItem.setXY(x, y);
            Items.add(currItem);
        }
    }

    public void update(){
        currItem.update(); // item update method
    }


    public void draw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);

        canvas.drawRect(
                left_x - 4,
                top_y - 4,
                (left_x - 4) + (WIDTH - 8),
                (top_y - 4) + (HEIGHT - 8),
                paint
        );

        Log.d("GamePanel", "Drawing game screen...");
        // Draw all items
        if (Items != null) {
            for (Item item : Items) {
                item.draw(canvas); // Draw the item
            }
        }

        // Also ensure currItem is being drawn if it's set
        if (currItem != null) {
            currItem.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("GamePanel", "Touch detected at: " + event.getX() + ", " + event.getY());
            float touchX = event.getX();
            float touchY = event.getY();

            // Check if the touch is within the bounds of the current item
            if (currItem != null && currItem.contains(touchX, touchY)) {
                stealItem(currItem);  // Pass currItem to stealItem()
            }
        }
        return true;
    }

    private void stealItem(Item item) {
        Items.remove(item); // Remove the item from the Items list

        // Mark the item as stolen or remove it from the game in other ways (e.g., set it to null or change its color).
        // In this case, we set currItem to null after stealing it.
        currItem = null;
        Log.d("PlayManager", "Item stolen!");
    }

//    public void draw(Graphics2D g2){
//        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(4f));
//        g2.drawRect(left_x-4,top_y-4,WIDTH-8,HEIGHT-8);
//
//    }





    // GRID METHODS - with help from ai

    //mapping pixel to grid and grid to pixel methods
    public int pixelToCol(int x) {
        return (x - left_x) / Block.SIZE;
    }
    public int pixelToRow(int y) {
        return (bottom_y - y) / Block.SIZE;
    }

    public int colToX(int col) {
        return left_x + col * Block.SIZE;
    }
    public int rowToY(int row) {
        return bottom_y - (row + 1) * Block.SIZE;
    }

    //checks if the block can be placed
    private boolean canPlaceItem(Item item) {
        for (Block b : item.permB) {
            int bCol = pixelToCol(b.x);
            int bRow = pixelToRow(b.y);

            if (bCol < 0 || bCol >= COLS || bRow < 0 || bRow >= ROWS || grid[bRow][bCol] != null) {
                return false;
            }
        }
        return true;
    }

    private void addItemToGrid(Item item) {
        for (Block b : item.permB) {
            int bCol = pixelToCol(b.x);
            int bRow = pixelToRow(b.y);
            grid[bRow][bCol] = item;
        }
    }
    // we only want to remove items while iterating and so on in an temp, the removal of the original list should only happen after it has been stolen.
    public void fillGridCompactly() {
        // Clear old state
        tempItems = Items;
        //initialItems();//items.clear();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = null;
            }
        }

        // Fill bottom-up
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLS; col++) {

                // Try placing a random item in this position
                for (int i = 0; i < tempItems.size(); i++){
                    Item item = tempItems.get(i);
                    item.setXY(colToX(col), rowToY(row));
                    if (canPlaceItem(item)) {
                        //items.add(item);
                        addItemToGrid(item);
                    }
                }
            }
        }
    }

    
}
