package com.siswadi.sudoku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LeaderboardActivity extends AppCompatActivity {

    private SharedPreferences infoOne;
    private SharedPreferences infoTwo;
    private SharedPreferences infoThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //INITIALIZE fields
        infoOne = getApplicationContext().getSharedPreferences("numberOne", Context.MODE_PRIVATE);
        infoTwo = getApplicationContext().getSharedPreferences("numberTwo", Context.MODE_PRIVATE);
        infoThree = getApplicationContext().getSharedPreferences("numberThree", Context.MODE_PRIVATE);
        final TextView nameViewOne = (TextView) findViewById(R.id.leaderboardOne);
        final TextView timeViewOne = (TextView) findViewById(R.id.leaderboardOneTime);
        final TextView nameViewTwo = (TextView) findViewById(R.id.leaderboardTwo);
        final TextView timeViewTwo = (TextView) findViewById(R.id.leaderboardTwoTime);
        final TextView nameViewThree = (TextView) findViewById(R.id.leaderboardThree);
        final TextView timeViewThree = (TextView) findViewById(R.id.leaderboardThreeTime);

        final SharedPreferences.Editor editorOne = infoOne.edit();
        final SharedPreferences.Editor editorTwo = infoTwo.edit();
        final SharedPreferences.Editor editorThree = infoThree.edit();


        //Initialize current user info
        Intent intent = getIntent();
        String name = intent.getStringExtra("userName");
        int[] elapsedTime = intent.getIntArrayExtra("time");

        int secs, mins, msecs;
        mins = elapsedTime[0];
        secs = elapsedTime[1];
        msecs = elapsedTime[2];

        /************STORING STUFF***************/
        //get the times
        int[] one = new int[3];
        int [] two = new int[3];
        int [] three = new int[3];

        getTimes(one, two, three);

        int oneTotalTime = one[0] * 60000 + one[1] * 1000 + one[2];
        int twoTotalTime = two[0] * 60000 + two[1] * 1000 + two[2];
        int threeTotalTime = three[0] * 60000 + three[1] * 1000 + three[2];
        int cTotalTime = mins * 60000 + secs * 1000 + msecs;//msecs


        if(cTotalTime < oneTotalTime)//if faster then set to first
        {
            //second to third
            editorThree.putString("name", infoTwo.getString("name", "BLANK"));
            editorThree.putInt("mins", two[0]);
            editorThree.putInt("secs", two[1]);
            editorThree.putInt("msecs", two[2]);

            //first to second
            editorTwo.putString("name", infoOne.getString("name", "BLANK"));
            editorTwo.putInt("mins", one[0]);
            editorTwo.putInt("secs", one[1]);
            editorTwo.putInt("msecs", one[2]);

            //current to first
            editorOne.putString("name", name);
            editorOne.putInt("mins", mins);
            editorOne.putInt("secs", secs);
            editorOne.putInt("msecs", msecs);
        }
        else if(cTotalTime == oneTotalTime)//shift the second to third & current to second
        {
            editorThree.putString("name", infoTwo.getString("name", "BLANK"));
            editorThree.putInt("mins", two[0]);
            editorThree.putInt("secs", two[1]);
            editorThree.putInt("msecs", two[2]);

            editorTwo.putString("name", name);
            editorTwo.putInt("mins", mins);
            editorTwo.putInt("secs", secs);
            editorTwo.putInt("msecs", msecs);
        }
        else
        {
            if(cTotalTime < twoTotalTime)//set to second
            {
                editorTwo.putString("name", name);
                editorTwo.putInt("mins", mins);
                editorTwo.putInt("secs", secs);
                editorTwo.putInt("msecs", msecs);
            }
            else if(cTotalTime == twoTotalTime)//set to third
            {
                editorThree.putString("name", name);
                editorThree.putInt("mins", mins);
                editorThree.putInt("secs", secs);
                editorThree.putInt("msecs", msecs);
            }
            else
            {
                if(cTotalTime < threeTotalTime)//set to third
                {
                    editorThree.putString("name", name);
                    editorThree.putInt("mins", mins);
                    editorThree.putInt("secs", secs);
                    editorThree.putInt("msecs", msecs);
                }
            }
        }
        //commit changes, why not apply()?
        editorOne.commit();
        editorTwo.commit();
        editorThree.commit();

        //set the textViews
        nameViewOne.setText(infoOne.getString("name", "BLANK"));
        nameViewTwo.setText(infoTwo.getString("name", "BLANK"));
        nameViewThree.setText(infoThree.getString("name", "BLANK"));
        timeViewOne.setText(timeToString(0));
        timeViewTwo.setText(timeToString(1));
        timeViewThree.setText(timeToString(2));


        //Clear Leaderboard textView (TEMPORARY)
        TextView clearLeaderboard = (TextView) findViewById(R.id.clearLeaderboard);
        clearLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorOne.putString("name", "BLANK");
                editorOne.putInt("mins", 99);
                editorOne.putInt("secs", 99);
                editorOne.putInt("msecs", 999);
                editorTwo.putString("name", "BLANK");
                editorTwo.putInt("mins", 99);
                editorTwo.putInt("secs", 99);
                editorTwo.putInt("msecs", 999);
                editorThree.putString("name", "BLANK");
                editorThree.putInt("mins", 99);
                editorThree.putInt("secs", 99);
                editorThree.putInt("msecs", 999);

                editorOne.commit();
                editorTwo.commit();
                editorThree.commit();

                //set the textViews
                nameViewOne.setText(infoOne.getString("name", "BLANK"));
                nameViewTwo.setText(infoTwo.getString("name", "BLANK"));
                nameViewThree.setText(infoThree.getString("name", "BLANK"));
                timeViewOne.setText(timeToString(0));
                timeViewTwo.setText(timeToString(1));
                timeViewThree.setText(timeToString(2));
            }
        });

        Button playAgain = (Button) findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainMenuActivity();//back to MainMenu
            }
        });

    }

    private void startMainMenuActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    //return printable string
    private String timeToString(int n) {
        int secs, mins, msecs;
        String toReturn;
        if(n == 0)//get Time from editor 1
        {
            mins = infoOne.getInt("mins", 99);
            secs = infoOne.getInt("secs", 99);
            msecs = infoOne.getInt("msecs", 999);

        }
        else if(n == 1)//get Time from editor 2
        {
            mins = infoTwo.getInt("mins", 99);
            secs = infoTwo.getInt("secs", 99);
            msecs = infoTwo.getInt("msecs", 999);
        }
        else
        {
            mins = infoThree.getInt("mins", 99);
            secs = infoThree.getInt("secs", 99);
            msecs = infoThree.getInt("msecs", 999);
        }

        String minute = mins+"", seconds = secs+"", mseconds = msecs+"";
        if(mins < 10)minute = "0" + mins;
        if(secs < 10)seconds = "0" + secs;
        if(msecs < 10)mseconds = "00" + msecs;
        if(msecs < 100 && msecs > 9)mseconds = "0" + msecs;

        toReturn = "" + minute + ":" + seconds + ":" + mseconds;

        return toReturn;
    }

    private void getTimes(int[] one, int[] two, int[] three) {
        one[0] = infoOne.getInt("mins", 99);
        one[1] = infoOne.getInt("secs", 99);
        one[2] = infoOne.getInt("msecs", 999);

        two[0] = infoTwo.getInt("mins", 99);
        two[1] = infoTwo.getInt("secs", 99);
        two[2] = infoTwo.getInt("msecs", 999);

        three[0] = infoThree.getInt("mins", 99);
        three[1] = infoThree.getInt("secs", 99);
        three[2] = infoThree.getInt("msecs", 999);
    }
}
