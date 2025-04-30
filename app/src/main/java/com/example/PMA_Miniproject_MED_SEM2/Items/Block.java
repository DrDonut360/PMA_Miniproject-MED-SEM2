package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

public class Block { // basic item bounds contruct
    public static final int SIZE = 30; // 30x30px block
    public int x, y;
    public int width = SIZE;
    public int height = SIZE;
    private Paint paint;

    public Block(int r, int g, int b) {
         paint = new Paint();
         paint.setColor(Color.rgb(r, g, b));
         paint.setStyle(Paint.Style.FILL);
    }
      public void draw(@NonNull Canvas canvas) {
        Log.d("GamePanel", "Drawing block at: " + x + ", " + y); // Add log here
        canvas.drawRect(x, y, x + width, y + height, paint);



     }
}
