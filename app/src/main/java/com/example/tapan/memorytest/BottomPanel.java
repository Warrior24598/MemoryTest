package com.example.tapan.memorytest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tapan on 11-11-2017.
 */

public class BottomPanel extends AppCompatActivity
{
    Button[] ans=new Button[6];
    Context context;

    BottomPanel(Context context)
    {
        this.context=context;
    }

    public void initialize()
    {
        ans[0]=(Button) ((Activity)context).findViewById(R.id.ans1);
        ans[1]=(Button) ((Activity)context).findViewById(R.id.ans2);
        ans[2]=(Button) ((Activity)context).findViewById(R.id.ans3);
        ans[3]=(Button) ((Activity)context).findViewById(R.id.ans4);
        ans[4]=(Button) ((Activity)context).findViewById(R.id.ans5);
        ans[5]=(Button) ((Activity)context).findViewById(R.id.ans6);

        disableAll();

        ans[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[0].setClickable(false);
                ans[0].setAlpha(0);
                Game.index--;
            }
        });
        ans[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[1].setClickable(false);
                ans[0].setClickable(true);
                ans[1].setAlpha(0);
                Game.index--;
            }
        });
        ans[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[2].setClickable(false);
                ans[1].setClickable(true);
                ans[2].setAlpha(0);
                Game.index--;
            }
        });
        ans[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[3].setClickable(false);
                ans[2].setClickable(true);
                ans[3].setAlpha(0);
                Game.index--;
            }
        });
        ans[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[4].setClickable(false);
                ans[3].setClickable(true);
                ans[4].setAlpha(0);
                Game.index--;
            }
        });
        ans[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[5].setClickable(false);
                ans[4].setClickable(true);
                ans[5].setAlpha(0);
                Game.index--;
            }
        });
    }

    public void disableAll()
    {
        for(int i=0;i<6;i++)
        {
           ans[i].setClickable(false);
           ans[i].setAlpha(0);
        }
    }

    public void setButton(int index, int bg)
    {
        ans[index].setClickable(true);
        ans[index].setAlpha(1);
        ans[index].setBackgroundResource(bg);
        for(int i=0;i<index;i++)
        {
            ans[i].setClickable(false);
        }
    }
}