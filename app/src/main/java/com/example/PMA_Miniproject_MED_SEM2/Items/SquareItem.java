package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class SquareItem extends Item {
    public SquareItem() {
        super(15, Color.RED);
    }

    @Override
    public List<int[]> getShapeOffsets() {
        return Arrays.asList(
                new int[]{0, 0},
                new int[]{0, 1},
                new int[]{1, 0},
                new int[]{1, 1}
        );
    }
}
