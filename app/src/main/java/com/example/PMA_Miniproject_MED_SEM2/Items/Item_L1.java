package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item_L1 extends Item {
    @Override
    public List<int[]> getBaseShape() {
        // L Shape
        // [ ] [x] [ ] [ ]
        // [ ] [x] [ ] [ ]
        // [ ] [x] [x] [ ]
        // [ ] [ ] [ ] [ ]
        return Arrays.asList(
            new int[]{0, 0},   // anchor
            new int[]{1, 0},
            new int[]{2, 0},
            new int[]{2, 1}
        );


    }

    public int getColor(){
        return Color.GREEN;
    }

    public int getValue(){
        return 30;
    }
}
