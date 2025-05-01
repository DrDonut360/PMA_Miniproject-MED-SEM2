package com.example.PMA_Miniproject_MED_SEM2.Items;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    protected int value;
    protected int color;

    public abstract List<int[]> getBaseShape();
    public abstract int getValue();
    public abstract int getColor();

    // Each subclass defines its own shape
    public List<int[]> getShapeOffsets(int rotation){
        List<int[]> base = getBaseShape();
        List<int[]> rotated = new ArrayList<>();

        for (int[] offset : base){
            int x = offset[0];
            int y = offset[1];

            switch (rotation % 4){
                case 0: // 0°
                    rotated.add(new int[]{x, y});
                    break;
                case 1: // 90°
                    rotated.add(new int[]{y, -x});
                    break;
                case 2: // 180°
                    rotated.add(new int[]{-x, -y});
                    break;
                case 3: // 270°
                    rotated.add(new int[]{-y, x});
            }
        }
        return rotated;
    }
}
