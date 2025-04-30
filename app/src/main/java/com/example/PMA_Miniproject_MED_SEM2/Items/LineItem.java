package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class LineItem extends Item {
    public LineItem() {
        super(20, Color.BLUE);
    }

    @Override
    public List<int[]> getShapeOffsets() {
        return Arrays.asList(
                new int[]{0, 0},
                new int[]{1, 0},
                new int[]{2, 0},
                new int[]{3, 0}
        );
    }
}
