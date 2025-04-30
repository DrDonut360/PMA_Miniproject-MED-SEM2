package com.example.PMA_Miniproject_MED_SEM2.Items;

import java.util.List;

public abstract class Item {
    protected int value;
    protected int color;

    public Item(int value, int color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() { return value; }
    public int getColor() { return color; }

    // Each subclass defines its own shape
    public abstract List<int[]> getShapeOffsets();
}
