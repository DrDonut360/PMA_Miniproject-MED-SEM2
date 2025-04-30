package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class LShapedItem extends Item {
    public LShapedItem() {
        super(30, Color.GREEN); // value and color
    }

    @Override
    public List<int[]> getShapeOffsets() {
        List<int[]> offsets = new ArrayList<>();
        offsets.add(new int[]{0, 0});   // anchor
        offsets.add(new int[]{1, 0});
        offsets.add(new int[]{2, 0});
        offsets.add(new int[]{2, 1});
        return offsets;
    }
}
