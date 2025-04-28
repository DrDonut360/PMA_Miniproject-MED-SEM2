package com.example.PMA_Miniproject_MED_SEM2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.DynamicLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.security.PublicKey;
import android.view.View;

public class PlayManager {
    // _____ Handles : _____ \\
    // The Drawing of the play area
    // Manages the game pieces / items
    // Handles Gameplay actions (deleting GameObj's, score, etc.)

    // _____ Main Play Area _____ \\ 8x5
    private RelativeLayout layout;
    private View rootView;

    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;


    //Contructor
    public PlayManager(View rootView){
        //Main play area frame

        this.rootView = rootView; //Passing in rootView for game activity
        layout = rootView.findViewById(R.id.gameLayout_id); //finds view by id
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams(); // we get the params of the layout view rootView

        left_x = (params.height/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
    }
    public void update(){

    }
    private Paint paint = new Paint();

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
    }
//    public void draw(Graphics2D g2){
//        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(4f));
//        g2.drawRect(left_x-4,top_y-4,WIDTH-8,HEIGHT-8);
//
//    }
}
