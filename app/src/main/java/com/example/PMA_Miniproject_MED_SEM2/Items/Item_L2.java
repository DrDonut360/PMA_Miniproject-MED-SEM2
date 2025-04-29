package com.example.PMA_Miniproject_MED_SEM2.Items;

public class Item_L2 extends Item {

    public Item_L2(){ //instantiates empty block elements with a specific color
        create(200,60,0);
    }

    public void setXY(int x, int y){
        //positions the Shape in the correct form
        //relative to its middle block (permB)

        //___ SHAPE ___\\
        //             \\
        //        [1]  \\
        //        [0]  \\
        //     [3][2]  \\
        //_____________\\
        permB[0].x = x;
        permB[0].y = y;
        permB[1].x = permB[0].x;
        permB[1].y = permB[0].y - Block.SIZE;
        permB[2].x = permB[0].x;
        permB[2].y = permB [0].y + Block. SIZE;
        permB[3].x = permB [0].x - Block.SIZE;
        permB[3].y = permB [0].y + Block.SIZE;
    }

    // feature rotation
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
}
