package com.example.tapan.memorytest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    Button play;
    TextView highscore,complexity;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button) findViewById(R.id.playButton);
        highscore=(TextView) findViewById(R.id.highscore);
        complexity=(TextView) findViewById(R.id.complexity);
        seekBar=(SeekBar) findViewById(R.id.seekBar);

        printHighScore();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                switch (i)
                {
                    case 0:
                        Game.bounce=4;
                        Game.anim=R.anim.scale_easy;
                        Game.wait=10;
                        complexity.setText("Easy");
                        break;
                    case 1:
                        Game.bounce=5;
                        Game.anim=R.anim.scale_medium;
                        Game.wait=6;
                        complexity.setText("Medium");
                        break;
                    case 2:
                        Game.bounce=6;
                        Game.anim=R.anim.scale_hard;
                        Game.wait=4;
                        complexity.setText("Hard");
                        break;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Game.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
        System.exit(0);
    }

    public void printHighScore()
    {
        SharedPreferences pref = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        int hs = pref.getInt("Score",0);
        highscore.setText("High Score: "+hs);
    }
}
