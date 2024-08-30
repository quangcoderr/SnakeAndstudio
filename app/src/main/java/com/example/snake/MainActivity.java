package com.example.snake;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static TextView txt_Score, txt_Best_Score,txt_Scoreover;
    public static Button btn_start;
    public static RelativeLayout rl_gameover;
    public static Gameview gameview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize display metrics for screen width and height
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.SCREEN_HEIGHT = dm.heightPixels;
        Constant.SCREEN_WIDTH = dm.widthPixels;

        // Set the content view
        setContentView(R.layout.activity_main);

        txt_Scoreover=findViewById(R.id.txt_ScoreOver);
        gameview = findViewById(R.id.gv);
        btn_start = findViewById(R.id.btn_start);
        rl_gameover = findViewById(R.id.rl_Gameover);
        txt_Score = findViewById(R.id.txt_score);
        txt_Best_Score = findViewById(R.id.txt_best_score);

        // Set the button click listener
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameview.resetGame();
                gameview.setStart(true);
                btn_start.setVisibility(View.GONE);
                rl_gameover.setVisibility(View.GONE);
                gameview.invalidate();
            }
        });

        // Set the game over layout click listener
        rl_gameover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setVisibility(View.VISIBLE);

                // Hide the game over layout and the score text view
                rl_gameover.setVisibility(View.GONE);

            }
        });
    }
}
