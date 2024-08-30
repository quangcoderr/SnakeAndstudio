package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Apple {
    Bitmap bitmap;
    private int x,y;
    private Rect r;

    public Apple(Bitmap bitmap, int x, int y ) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;


    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getR() {
        return new Rect(this.x,this.y,this.x+Gameview.sizeofMap,this.y+Gameview.sizeofMap);
    }

    public void setR(Rect r) {
        this.r = r;
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

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public void reset(int nx,int ny) {
        this.x=nx;
        this.y=ny;
    }
}
