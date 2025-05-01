package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class Item_I extends Item {
    @Override
    public List<int[]> getBaseShape() {
        // Line Shape
        // [ ] [x] [ ] [ ]
        // [ ] [x] [ ] [ ]
        // [ ] [x] [ ] [ ]
        // [ ] [x] [ ] [ ]
        return Arrays.asList(
                new int[]{0, 0},
                new int[]{1, 0},
                new int[]{2, 0},
                new int[]{3, 0}
        );
    }

    public int getColor(){
        return Color.BLUE;
    }

    public int getValue(){
        return 20;
    }
}
