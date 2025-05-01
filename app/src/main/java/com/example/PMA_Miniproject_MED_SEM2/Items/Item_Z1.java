package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item_Z1 extends Item{
    @Override
    public List<int[]> getBaseShape() {
        return Arrays.asList(
                new int[]{0, 0},
                new int[]{0, 1},
                new int[]{1, 1},
                new int[]{1, 2}
        );
    }

    public int getColor(){
        return Color.MAGENTA;
    }

    public int getValue(){
        return 35;
    }
}
