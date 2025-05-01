package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item_T extends Item{
    @Override
    public List<int[]> getBaseShape() {
        // ------- SHAPE ------- \\
        //                       \\
        //              [ ]      \\
        //      [0] [ ] [ ]      \\
        //              [ ]      \\
        //                       \\
        // --------------------- \\
        return Arrays.asList(
                new int[]{0, 0},
                new int[]{0, 1},
                new int[]{0, 2},
                new int[]{1, 2},
                new int[]{-1, 2}
        );
    }

    public int getColor(){
        return Color.DKGRAY;
    }

    public int getValue(){
        return 50;
    }
}
