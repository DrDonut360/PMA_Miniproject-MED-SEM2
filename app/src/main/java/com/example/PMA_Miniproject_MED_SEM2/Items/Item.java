package com.example.PMA_Miniproject_MED_SEM2.Items;

import android.graphics.Canvas;

import java.util.Random;

public class Item { // all items extent from this super class
    public Block[] permB = new Block[4];
    public Block[] tempB = new Block[4];

    public void create(int r,int g,int b){
        //could create a loop to assign the colors instead of hardcoding :
        //for (int i = 0; i < 4; i++) {
        //    permB[i] = new Block(r, g, b);
        //    tempB[i] = new Block(r, g, b);
        //}
        //OR LIKE :
        //Permanent Blocks
        //for (int i = 0; i < 4; i++) {
        //    permB[i] = new Block(r, g, b);
        //}
        //Temporary Blocks
        //for (int i = 0; i < 4; i++) {
        //    tempB[i] = new Block(r, g, b);
        //}

        //Permanent Blocks
        permB[0] = new Block(r,g,b);
        permB[1] = new Block(r,g,b);
        permB[2] = new Block(r,g,b);
        permB[3] = new Block(r,g,b);
        //Temporary Blocks
        tempB[0] = new Block(r,g,b);
        tempB[1] = new Block(r,g,b);
        tempB[2] = new Block(r,g,b);
        tempB[3] = new Block(r,g,b);
    }
    public static Item getRandomItem() {
        Random rand = new Random();
        int index = rand.nextInt(7); // random number of item types

        switch(index) {
            case 0: return new Item_I();
            case 1: return new Item_L1();
            case 2: return new Item_L2();
            case 3: return new Item_O();
            case 4: return new Item_T();
            case 5: return new Item_Z1();
            case 6: return new Item_Z2();
            default: return new Item_L1(); // fallback if anyhting unforseen happens
        }
    }
    public void setXY(int x, int y) {}
    public void updateXY(int direction){}
    public void update () {

    }
    public void draw(Canvas canvas){
        for (int i = 0; i < permB.length; i++) {
            permB[i].draw(canvas);
        }
    }
}
