package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PartSnake {
    private Bitmap bitmap;
    private int x,y;
    private Rect rBody,rTop,rBottom,rLeft,rRight;

    public PartSnake(Bitmap bitmap,int x,int y) {
        this.bitmap = bitmap;
        this.x=x;
        this.y=y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getrBody() {
        return new Rect(this.x,this.y,this.x+Gameview.sizeofMap,this.y+Gameview.sizeofMap);
    }

    public void setrBody(Rect rBody) {
        this.rBody = rBody;
    }

    public Rect getrBottom() {
        return new Rect(this.x-10*Constant.SCREEN_WIDTH/1080,this.y+Gameview.sizeofMap,this.x,this.y+Gameview.sizeofMap+10*Constant.SCREEN_HEIGHT/1920);
    }

    public void setrBottom(Rect rBottom) {
        this.rBottom = rBottom;
    }

    public Rect getrLeft() {
        return new Rect(this.x,this.y,this.x+Gameview.sizeofMap,this.y+Gameview.sizeofMap);
    }

    public void setrLeft(Rect rLeft) {
        this.rLeft = rLeft;
    }

    public Rect getrRight() {
        return new Rect(this.x,this.y,this.x+Gameview.sizeofMap+10*Constant.SCREEN_WIDTH*1080,this.y+Gameview.sizeofMap);
    }

    public void setrRight(Rect rRight) {
        this.rRight = rRight;
    }

    public Rect getrTop() {
        return new Rect(this.x-10*Constant.SCREEN_HEIGHT/1920,this.y,this.x+Gameview.sizeofMap,this.y);
    }

    public void setrTop(Rect rTop) {
        this.rTop = rTop;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
