package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {
    private boolean move_left,move_right,move_top,move_bottom;
    private Bitmap bm,bm_head_up,bm_head_down,bm_head_left,bm_head_right,bm_body_vertical,bm_body_horizontal, bm_body_top_right,bm_body_top_left,bm_body_bottom_right,bm_body_bottom_left,bm_tail_right,bm_tail_left,bm_tail_up,bm_tail_down;
    private int  x,y,length;
    private ArrayList<PartSnake> arrPartSnake=new ArrayList<>();

    public Snake( Bitmap bm, int x, int y, int length) {
        this.bm=bm;
        this.x = x;
        this.y = y;

        this.length = length;
        bm_body_bottom_left=Bitmap.createBitmap(bm,0,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_body_bottom_right=Bitmap.createBitmap(bm,Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_body_horizontal=Bitmap.createBitmap(bm,2*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_body_top_left=Bitmap.createBitmap(bm,3*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_body_top_right=Bitmap.createBitmap(bm,4*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_body_vertical=Bitmap.createBitmap(bm,5*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_head_down=Bitmap.createBitmap(bm,6*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_head_left=Bitmap.createBitmap(bm,7*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_head_right=Bitmap.createBitmap(bm,8*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_head_up=Bitmap.createBitmap(bm,9*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_tail_up=Bitmap.createBitmap(bm,10*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_tail_right=Bitmap.createBitmap(bm,11*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_tail_left=Bitmap.createBitmap(bm,12*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
        bm_tail_down=Bitmap.createBitmap(bm,13*Gameview.sizeofMap,0,Gameview.sizeofMap,Gameview.sizeofMap);
arrPartSnake.add(new PartSnake(bm_head_right,x,y));
        for (int i = 1; i < length-1; i++) {
            arrPartSnake.add(new PartSnake(bm_body_horizontal,arrPartSnake.get(i-1).getX()-Gameview.sizeofMap,y));
        }
        arrPartSnake.add(new PartSnake(bm_tail_right,arrPartSnake.get(length-2).getX()-Gameview.sizeofMap,y));
        setMove_right(true);
    }
    public void update() {
        // Move the body parts
        for (int i = length - 1; i > 0; i--) {
            arrPartSnake.get(i).setX(arrPartSnake.get(i - 1).getX());
            arrPartSnake.get(i).setY(arrPartSnake.get(i - 1).getY());
        }

        // Move the head
        if (move_right) {
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() + Gameview.sizeofMap);
            arrPartSnake.get(0).setBitmap(bm_head_right);
        } else if (move_left) {
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() - Gameview.sizeofMap);
            arrPartSnake.get(0).setBitmap(bm_head_left);
        } else if (move_top) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() - Gameview.sizeofMap);
            arrPartSnake.get(0).setBitmap(bm_head_up);
        } else if (move_bottom) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() + Gameview.sizeofMap);
            arrPartSnake.get(0).setBitmap(bm_head_down);
        }

        // Update the body segments
        for (int i = 1; i < length - 1; i++) {
            PartSnake currentPart = arrPartSnake.get(i);
            PartSnake prevPart = arrPartSnake.get(i - 1);
            PartSnake nextPart = arrPartSnake.get(i + 1);

            if (prevPart.getX() < currentPart.getX() && nextPart.getY() > currentPart.getY() ||
                    nextPart.getX() < currentPart.getX() && prevPart.getY() > currentPart.getY()) {
                currentPart.setBitmap(bm_body_bottom_left);
            } else if (prevPart.getX() > currentPart.getX() && nextPart.getY() > currentPart.getY() ||
                    nextPart.getX() > currentPart.getX() && prevPart.getY() > currentPart.getY()) {
                currentPart.setBitmap(bm_body_bottom_right);
            } else if (prevPart.getX() < currentPart.getX() && nextPart.getY() < currentPart.getY() ||
                    nextPart.getX() < currentPart.getX() && prevPart.getY() < currentPart.getY()) {
                currentPart.setBitmap(bm_body_top_left);
            } else if (prevPart.getX() > currentPart.getX() && nextPart.getY() < currentPart.getY() ||
                    nextPart.getX() > currentPart.getX() && prevPart.getY() < currentPart.getY()) {
                currentPart.setBitmap(bm_body_top_right);
            } else if (prevPart.getX() == nextPart.getX()) {
                currentPart.setBitmap(bm_body_vertical);
            } else if (prevPart.getY() == nextPart.getY()) {
                currentPart.setBitmap(bm_body_horizontal);
            }
        }

        // Update the tail
        PartSnake tail = arrPartSnake.get(length - 1);
        PartSnake prevTail = arrPartSnake.get(length - 2);

        if (prevTail.getX() < tail.getX()) {  // The tail is moving right
            tail.setBitmap(bm_tail_left);  // Tail image should face left
        } else if (prevTail.getX() > tail.getX()) {  // The tail is moving left
            tail.setBitmap(bm_tail_right);  // Tail image should face right
        } else if (prevTail.getY() < tail.getY()) {  // The tail is moving down
            tail.setBitmap(bm_tail_up);  // Tail image should face up
        } else if (prevTail.getY() > tail.getY()) {  // The tail is moving up
            tail.setBitmap(bm_tail_down);  // Tail image should face down
        }
    }
public void  onDraw(Canvas canvas){
    for (int i = 0; i < length; i++) {
        canvas.drawBitmap(arrPartSnake.get(i).getBitmap(),arrPartSnake.get(i).getX(),arrPartSnake.get(i).getY(),null);
    }
}

    public ArrayList<PartSnake> getArrPartSnake() {
        return arrPartSnake;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arrPartSnake = arrPartSnake;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm_body_bottom_left() {
        return bm_body_bottom_left;
    }

    public void setBm_body_bottom_left(Bitmap bm_body_bottom_left) {
        this.bm_body_bottom_left = bm_body_bottom_left;
    }

    public Bitmap getBm_body_bottom_right() {
        return bm_body_bottom_right;
    }

    public void setBm_body_bottom_right(Bitmap bm_body_bottom_right) {
        this.bm_body_bottom_right = bm_body_bottom_right;
    }

    public Bitmap getBm_body_horizontal() {
        return bm_body_horizontal;
    }

    public void setBm_body_horizontal(Bitmap bm_body_horizontal) {
        this.bm_body_horizontal = bm_body_horizontal;
    }

    public Bitmap getBm_body_top_left() {
        return bm_body_top_left;
    }

    public void setBm_body_top_left(Bitmap bm_body_top_left) {
        this.bm_body_top_left = bm_body_top_left;
    }

    public Bitmap getBm_body_top_right() {
        return bm_body_top_right;
    }

    public void setBm_body_top_right(Bitmap bm_body_top_right) {
        this.bm_body_top_right = bm_body_top_right;
    }

    public Bitmap getBm_body_vertical() {
        return bm_body_vertical;
    }

    public void setBm_body_vertical(Bitmap bm_body_vertical) {
        this.bm_body_vertical = bm_body_vertical;
    }



    public Bitmap getBm_head_left() {
        return bm_head_left;
    }

    public void setBm_head_left(Bitmap bm_head_left) {
        this.bm_head_left = bm_head_left;
    }

    public Bitmap getBm_head_right() {
        return bm_head_right;
    }

    public void setBm_head_right(Bitmap bm_head_right) {
        this.bm_head_right = bm_head_right;
    }

    public Bitmap getBm_head_up() {
        return bm_head_up;
    }

    public void setBm_head_up(Bitmap bm_head_up) {
        this.bm_head_up = bm_head_up;
    }

    public Bitmap getBm_tail_down() {
        return bm_tail_down;
    }

    public void setBm_tail_down(Bitmap bm_tail_down) {
        this.bm_tail_down = bm_tail_down;
    }

    public Bitmap getBm_tail_left() {
        return bm_tail_left;
    }

    public void setBm_tail_left(Bitmap bm_tail_left) {
        this.bm_tail_left = bm_tail_left;
    }

    public Bitmap getBm_tail_right() {
        return bm_tail_right;
    }

    public void setBm_tail_right(Bitmap bm_tail_right) {
        this.bm_tail_right = bm_tail_right;
    }

    public Bitmap getBm_tail_up() {
        return bm_tail_up;
    }

    public void setBm_tail_up(Bitmap bm_tail_up) {
        this.bm_tail_up = bm_tail_up;
    }



    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public Bitmap getBm_head_down() {
        return bm_head_down;
    }

    public void setBm_head_down(Bitmap bm_head_down) {
        this.bm_head_down = bm_head_down;
    }

    public boolean isMove_bottom() {
        return move_bottom;
    }

    public void setMove_bottom(boolean move_bottom) {
        s();
        this.move_bottom = move_bottom;
    }

    public boolean isMove_left() {
        return move_left;
    }

    public void setMove_left(boolean move_left) {
        s();
        this.move_left = move_left;
    }

    public boolean isMove_right() {
        return move_right;
    }

    public void setMove_right(boolean move_right) {
        s();
        this.move_right = move_right;
    }

    public boolean isMove_top() {

        return move_top;
    }

    public void setMove_top(boolean move_top) {
        s();
        this.move_top = move_top;
    }
    public void s(){
        this.move_left=false;
        this.move_right=false;
        this.move_bottom=false;
        this.move_top=false;    }

    public void addPart() {
    PartSnake p=this.arrPartSnake.get(length-1);
    this.length+=1;
    if (p.getBitmap()==bm_tail_right){
        this.arrPartSnake.add(new PartSnake(bm_tail_right,p.getX()-Gameview.sizeofMap, p.getY()));
    } else if (p.getBitmap()==bm_tail_left) {
        this.arrPartSnake.add(new PartSnake(bm_tail_left,p.getX()+Gameview.sizeofMap, p.getY()));

    }
    else if (p.getBitmap()==bm_tail_up) {
        this.arrPartSnake.add(new PartSnake(bm_tail_up,p.getX(), p.getY()+Gameview.sizeofMap));

    }
    else if (p.getBitmap()==bm_tail_down) {
        this.arrPartSnake.add(new PartSnake(bm_tail_right,p.getX(), p.getY()-Gameview.sizeofMap));

    }
    }


}
