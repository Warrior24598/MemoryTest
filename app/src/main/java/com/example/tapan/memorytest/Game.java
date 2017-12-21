package com.example.tapan.memorytest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    Button green, red, blue, yellow;
    TextView tvround,tvscore;

    Random r;

    int[] puzzle = new int[10];
    int[] ans = new int[10];
    int s = 0,round=0,score=0;
    static int bounce=5,anim=R.anim.scale_medium,wait=6,index = 0;

    BottomPanel b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        b=new BottomPanel(this);

        r = new Random();

        final Animation animation=AnimationUtils.loadAnimation(Game.this,R.anim.click);

        green = (Button) findViewById(R.id.green);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        yellow = (Button) findViewById(R.id.yellow);

        tvround=(TextView) findViewById(R.id.round);
        tvscore=(TextView) findViewById(R.id.score);

        b.initialize();
        startGame();

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                green.startAnimation(animation);
                b.setButton(index, R.drawable.green);
                ans[index++] = 1;
                verifyAns();
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                red.startAnimation(animation);
                b.setButton(index, R.drawable.red);
                ans[index++] = 2;
                verifyAns();
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blue.startAnimation(animation);
                b.setButton(index, R.drawable.blue);
                ans[index++] = 3;
                verifyAns();
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yellow.startAnimation(animation);
                b.setButton(index, R.drawable.yellow);
                ans[index++] = 4;
                verifyAns();
            }
        });

    }

    public void startGame()
    {
        AlertDialog.Builder abd = new AlertDialog.Builder(Game.this);

        abd.setTitle("Memory Test");
        abd.setMessage("Remember The Sequence Of Button Bounce and Click The Button in Same Sequence");
        abd.setCancelable(false);

        abd.setPositiveButton("Start", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {playPuzzle();}
        });

        abd.setNegativeButton("Exit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
//                Intent intent=new Intent(Game.this,MainActivity.class);
//                startActivity(intent);
            }
        });
        abd.show();
    }
    public void getRandom()
    {
        for(int i=0;i<bounce;i++)
        {
            puzzle[i]=r.nextInt(3)+1;
        }
    }
    public void playPuzzle()
    {
        round++;

        tvround.setText("Round: "+round);

        b.disableAll();

        index=0;
        s=0;
        getRandom();
        for(int i=0;i<bounce;i++)
        {
            s=s+wait;
            switch(puzzle[i])
            {
                case 1:
                    delay(s,green);
                    break;
                case 2:
                    delay(s,red);
                    break;
                case 3:
                    delay(s,blue);
                    break;
                case 4:
                    delay(s,yellow);
                    break;
            }
        }
    }
    public boolean checkAns()
    {
        for(int i=0;i<bounce;i++)
        {
            if(puzzle[i]!=ans[i])
                return false;
        }
        return true;
    }
    public void delay(final int seconds, final Button b)
    {
        runOnUiThread(new Runnable() {
            final Animation scale= AnimationUtils.loadAnimation(Game.this, anim);
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.startAnimation(scale);
                    }
                }, seconds*100);
            }
        });
    }
    public void verifyAns()
    {
        if (index == bounce) {
            if (checkAns())
            {
                Toast.makeText(Game.this,"Correct",Toast.LENGTH_SHORT).show();
                score++;
                tvscore.setText("Score: "+score);
            }
            else
                Toast.makeText(Game.this,"Wrong",Toast.LENGTH_SHORT).show();
            if(round<=7)
                playPuzzle();
            else
                gameFinish();
        }
    }
    public void gameFinish()
    {
        SharedPreferences pref = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        int hs = pref.getInt("Score",0);

        AlertDialog.Builder abd = new AlertDialog.Builder(Game.this);

        if(score<=hs)
            abd.setMessage("Score: "+score+"\n"+"High Score: "+hs);
        else
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Score",score);
            editor.apply();
            abd.setMessage("You Set A New High Score. Congratulations!!!!\n"+"New High Score: "+score);
        }

        abd.setTitle("Game Finish");
        abd.setCancelable(false);

        abd.setPositiveButton("New Game", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent restartIntent = new Intent(Game.this, Game.class);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restartIntent);
            }
        });

        abd.setNegativeButton("Exit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
                System.exit(0);
            }
        });
        abd.show();
    }
    public void onBackPressed()
    {
        AlertDialog.Builder abd = new AlertDialog.Builder(Game.this);

        abd.setTitle("Paused");
        abd.setMessage("Do You Want To Quit?");
        abd.setCancelable(false);

        abd.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
                System.exit(0);
//                Intent intent=new Intent(Game.this,MainActivity.class);
//                startActivity(intent);
            }
        });

        abd.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        abd.show();

    }
}
