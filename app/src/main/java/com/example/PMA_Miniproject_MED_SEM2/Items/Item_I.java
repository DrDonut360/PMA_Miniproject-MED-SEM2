package com.example.PMA_Miniproject_MED_SEM2.Items;

public class Item_I extends Item {

    public Item_I(){ //instantiates empty block elements with a specific color
        create(200,60,0);
    }

    public void setXY(int x, int y){
        //positions the Shape in the correct form
        //relative to its middle block (permB)

        //___ SHAPE ___\\
        //             \\
        //     [1]     \\
        //     [0]     \\
        //     [2][3]  \\
        //_____________\\
        permB[0].x = x;
        permB[0].y = y;
        permB[1].x = permB[0].x - Block.SIZE;
        permB[1].y = permB[0].y;
        permB[2].x = permB[0].x + Block.SIZE;
        permB[2].y = permB [0].y;
        permB[3].x = permB [0].x + Block.SIZE*2;
        permB[3].y = permB [0].y;
    }

    // feature rotation
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
}
