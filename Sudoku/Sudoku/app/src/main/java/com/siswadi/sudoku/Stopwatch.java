package com.siswadi.sudoku;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by esisw on 12/27/2017.
 */

public class Stopwatch {

    private Button bStart, bPause;
    private TextView timerDisplay;
    private Handler customHandler;
    private long startTime, timeInMilliseconds, timeSwapBuff, updateTime;
    private int secs, mins, msecs;
    private Runnable updateTimerThread;


    public Stopwatch(TextView view) {
        customHandler = new Handler();
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        timerDisplay = view;
    }

    public void startStopWatch()
    {
        //get starting time
        startTime = SystemClock.uptimeMillis();

        //this is the infinite recursive runnable
        updateTimerThread = new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);//not sure what this does, in hopes of multithreading so stopwatch doesn't wait for others :(

                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updateTime = timeSwapBuff + timeInMilliseconds;
                secs = (int) (updateTime/1000); //convert from ms -> s
                mins = secs/60;
                secs %= 60; //secs now holds the remainder
                msecs = (int) (updateTime % 1000);

                String minute = mins+"", seconds = secs+"", mseconds = msecs+"";
                if(mins < 10)minute = "0" + mins;
                if(secs < 10)seconds = "0" + secs;
                if(msecs < 10)mseconds = "00" + msecs;
                if(msecs < 100 && msecs > 9)mseconds = "0" + msecs;

                timerDisplay.setText("" + minute + ":" + seconds + ":"
                                                     + mseconds);
                customHandler.postDelayed(this,0);
            }
        };
        customHandler.postDelayed(updateTimerThread, 0);//start the infinite recursive runnable
    }

    public int[] getTimeStopWatch()
    {
        int[] time = new int[3];

        customHandler.removeCallbacks(updateTimerThread);//stop the stopwatch

        //get the elapsed time
        time[0] = mins;
        time[1] = secs;
        time[2] = msecs;

        return time;
    }
}
