package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;



    public class Gameview extends View {
        private Bitmap bmGrass1,bmGrass2,bmSnake,bmApple;
        public static int sizeofMap=75*Constant.SCREEN_WIDTH/1080;
        public int h=21,w=12,score,bestscore;
        private ArrayList<Grass> arrGrass=new ArrayList<>();
        private Snake snake;
        private boolean move=false;
        private float mx,my;
        private Handler handler;
        private Runnable runnable;
        private Apple apple;
        public boolean GameOver=false;
        public boolean start;
        private int gameoversound;
        private int eatsound;
        private SoundPool soundPool;

        public Gameview(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
            bmGrass1 = Bitmap.createScaledBitmap(bmGrass1, sizeofMap, sizeofMap, true);
            bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
            bmGrass2 = Bitmap.createScaledBitmap(bmGrass2, sizeofMap, sizeofMap, true);
            bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
            bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeofMap, sizeofMap, true);
            bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
            bmApple = Bitmap.createScaledBitmap(bmApple, sizeofMap, sizeofMap, true);

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if ((i + j) % 2 == 0) {
                        arrGrass.add(new Grass(bmGrass1, j * sizeofMap + Constant.SCREEN_WIDTH / 2 - (w / 2) * sizeofMap,
                                i * sizeofMap + 100 * Constant.SCREEN_HEIGHT / 1920, sizeofMap, sizeofMap));
                    } else {
                        arrGrass.add(new Grass(bmGrass2, j * sizeofMap + Constant.SCREEN_WIDTH / 2 - (w / 2) * sizeofMap,
                                i * sizeofMap + 100 * Constant.SCREEN_HEIGHT / 1920, sizeofMap, sizeofMap));
                    }
                }
            }
            snake=new Snake(bmSnake,arrGrass.get(126).getX(),arrGrass.get(126).getY(),4);
            apple=new Apple(bmApple,arrGrass.get(randomApple()[0]).getX(),arrGrass.get(randomApple()[1]).getY());

            handler=new Handler();
            runnable=new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            };
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();


            soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {

            });
            gameoversound= soundPool.load(context, R.raw.gameover_sound, 1);
            eatsound = soundPool.load(context, R.raw.eatsound, 1);
        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a=event.getActionMasked();
        switch (a){
            case MotionEvent.ACTION_MOVE:{
                if (move==false){
                    mx=event.getX();
                    my=event.getY();
                    move=true;
                }else {
                    if (mx-event.getX()>100*Constant.SCREEN_WIDTH/1080&&!snake.isMove_right()){
                        mx=event.getX();
                        my=event.getY();
                        snake.setMove_left(true);
                    } else if (event.getX()-mx>100*Constant.SCREEN_WIDTH/1080&&!snake.isMove_left()) {
                        mx=event.getX();
                        my=event.getY();
                        snake.setMove_right(true);
                    }
                    else if (my-event.getY()>100*Constant.SCREEN_WIDTH/1080&&!snake.isMove_bottom()) {
                        mx=event.getX();
                        my=event.getY();
                        snake.setMove_top(true);
                    }
                    else if (event.getY()-my>100*Constant.SCREEN_WIDTH/1080&&!snake.isMove_top()) {
                        mx=event.getX();
                        my=event.getY();
                        snake.setMove_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx=0;
                my=0;
                move=false;
                break;
            }
        }
        return true;
    }

        private void GameOver() {
            // Phát âm thanh game over
            soundPool.play(gameoversound, 1, 1, 0, 0, 1);

            // Cập nhật giao diện khi game over
            MainActivity.txt_Scoreover.setText(""+score);
            MainActivity.rl_gameover.setVisibility(VISIBLE);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawColor(0xFF1A6100);
            for (int i = 0; i < arrGrass.size(); i++) {
                canvas.drawBitmap(arrGrass.get(i).getBitmap(), arrGrass.get(i).getX(), arrGrass.get(i).getY(), null);
            }
            if (start) {
                if (CheckCollision()) {
                    GameOver();
                    snake.update();
                    snake.onDraw(canvas);
                    apple.onDraw(canvas);
                } else {
                    snake.update();
                    snake.onDraw(canvas);
                    apple.onDraw(canvas);

                    // Kiểm tra xem rắn có ăn táo không
                    if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())) {
                        randomApple();
                        apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
                        snake.addPart();
                        score++;

                        // Phát âm thanh khi ăn táo
                        soundPool.play(eatsound, 1, 1, 0, 0, 1);

                        if (MainActivity.txt_Score != null) {
                            MainActivity.txt_Score.setText("X" + score);
                            MainActivity.txt_Best_Score.setText("X" + Math.max(score, bestscore));
                        }
                    }
                    handler.postDelayed(runnable, 100);
                }
            }
        }


        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }
    public int[] randomApple(){
        int []xy=new int[2];
        Random r=new Random();
        xy[0]=r.nextInt(arrGrass.size()-1);
        xy[1]=r.nextInt(arrGrass.size()-1);
        Rect rect=new Rect(arrGrass.get(xy[0]).getX(),arrGrass.get(xy[1]).getY(),arrGrass.get(xy[0]).getX()+sizeofMap,arrGrass.get(xy[1]).getY()+sizeofMap);
        boolean check=true;
        while (check){
            check=false;
            for (int i = 0; i <snake.getArrPartSnake().size() ; i++) {
            if(rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                check=true;
                xy[0]=r.nextInt(arrGrass.size()-1);
                xy[1]=r.nextInt(arrGrass.size()-1);
                rect=new Rect(arrGrass.get(xy[0]).getX(),arrGrass.get(xy[1]).getY(),arrGrass.get(xy[0]).getX()+sizeofMap,arrGrass.get(xy[1]).getY()+sizeofMap);
            }

            }
        }
        return xy;
    }public void resetGame() {
            score = 0;
            GameOver = false;
            start = false;
            snake = new Snake(bmSnake, arrGrass.get(126).getX(), arrGrass.get(126).getY(), 4);
            apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
            CheckCollision();

            // Kiểm tra null để tránh lỗi
            if (MainActivity.txt_Score != null) {
                MainActivity.txt_Score.setText("X" + score);
            } else {
                Log.e("Gameview", "txt_Score is null");
            }

            if (MainActivity.txt_Best_Score != null) {
                MainActivity.txt_Best_Score.setText("X" + Math.max(score, bestscore));
            } else {
                Log.e("Gameview", "txt_Best_Score is null");
            }

            handler.removeCallbacks(runnable);
            invalidate();
        }


        public boolean CheckCollision() {
        // Kiểm tra va chạm giữa đầu rắn và các phần khác của thân
        Rect headRect = snake.getArrPartSnake().get(0).getrBody();
        for (int i = 1; i < snake.getArrPartSnake().size(); i++) {
            if (headRect.intersect(snake.getArrPartSnake().get(i).getrBody())) {
                GameOver = true;
                handler.removeCallbacks(runnable);
                return true;
            }
        }

        // Kiểm tra va chạm với biên màn hình (tùy chỉnh lại theo logic trò chơi của bạn)
        if (headRect.left < 80 || headRect.right > Constant.SCREEN_WIDTH-60 ||headRect.right<100||
                headRect.top < 100 || headRect.bottom > Constant.SCREEN_HEIGHT-500) {
            GameOver = true;
            handler.removeCallbacks(runnable);
            return true;
        }

        return false;
    }


}
