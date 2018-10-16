package com.siswadi.sudoku;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.siswadi.sudoku.AlertDialog.AlertDialogSubmission;
import com.siswadi.sudoku.View.BoardGridView;
import com.siswadi.sudoku.View.BoardGridViewAdapter;
import com.siswadi.sudoku.View.ButtonGridViewAdapter;
import com.siswadi.sudoku.View.CustomCellTextView;

public class GameActivity extends AppCompatActivity {

    private String userName;
    private boolean toggleBoardClick1;
    private boolean toggleBoardClick2;
    private boolean toggleButtonClick;
    private boolean togglePlayAgain;
    private Stopwatch clock;
    private int[] elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final GridView boardGridView = (GridView)findViewById(R.id.boardGridView);
        GridView buttonGridView = (GridView)findViewById(R.id.buttonGridView);
        final SudokuEngine sudoku = new SudokuEngine();

        //getName
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        //Initialize stopwatch
        clock = new Stopwatch((TextView)findViewById(R.id.stopwatchView));

        //switch to gridView
        //final BoardGridView boardGridView = (BoardGridView) findViewById(R.id.boardGridView);
        boardGridView.setAdapter(new BoardGridViewAdapter(this));
        buttonGridView.setAdapter((new ButtonGridViewAdapter((this))));

        /*Get difficulty information from previous Activity*/
        int difficulty = intent.getIntExtra("difficulty", 0);

        /*Play*/
        toggleBoardClick1 = true;
        toggleBoardClick2 = false;
        toggleButtonClick = true;
        setListener(boardGridView, buttonGridView, sudoku, difficulty);

        final GameActivity act = this;
        /* Submit and Verify - Extract info from board to verify*/
        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call AlertDialog
                AlertDialogSubmission submitDialog = new AlertDialogSubmission(boardGridView, sudoku, act);
                submitDialog.onCreateDialog(null).show();
                //The commented section below uses Toast to display correctness, change to move to new LeaderboardActivity
                /*
                String win = "CONGRATULATIONS! YOU'RE AWESOME\nTime: "
                        + mins + ":"
                        + secs + ":"
                        + String.format("%3d", elapsedTime[2]);
                String lose = "AWW.. THAT'S TOO BAD\nTime: "
                        + mins + ":"
                        + secs + ":"
                        + String.format("%3d", elapsedTime[2]);

                if(sudoku.verify(boardGridView)) Toast.makeText(getApplicationContext(), win, Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(), lose, Toast.LENGTH_LONG).show();
                */
            }
        });

        togglePlayAgain = false;
        final Button surrenderButton = (Button) findViewById(R.id.surrenderButton);
        surrenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!togglePlayAgain)
                {
                    elapsedTime = clock.getTimeStopWatch();
                    sudoku.printAnswerBoard(boardGridView);

                    for (int i = 0; i < 81; i++) {
                        if (sudoku.deletedPointSetContains(i)) {
                            ((CustomCellTextView) boardGridView.getChildAt(i)).setTextColor(Color.parseColor(getString(R.string.pastelRedColor)));
                            ((CustomCellTextView) boardGridView.getChildAt(i)).setBackgroundDrawable(getResources().getDrawable(R.drawable.box));
                        }
                        else
                        {
                            ((CustomCellTextView) boardGridView.getChildAt(i)).setTextColor(Color.parseColor("#707070"));
                        }
                    }
                    surrenderButton.setText(R.string.playAgainText);
                }
                if(togglePlayAgain)
                {
                    finish();
                }
                toggleBoardClick2 = false;
                toggleButtonClick = false;
                togglePlayAgain = true;
            }
        });


    }

    public void submitAnswers(GridView boardGridView, SudokuEngine sudoku) {
        if(sudoku.verify(boardGridView))//if submission is correct
        {
            elapsedTime = clock.getTimeStopWatch();

            //pad time string with zeroes accordingly
            String mins = elapsedTime[0]+"", secs = elapsedTime[1]+"";
            if(elapsedTime[0] < 10)mins = "0" + elapsedTime[0];
            if(elapsedTime[1] < 10) secs = "0" + elapsedTime[1];


            //Start LeaderboardActivity
            //send the new activity: time, name.
            Intent intent = new Intent(this, LeaderboardActivity.class);
            intent.putExtra("time", elapsedTime);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
        else //if submission is false
        {
            Toast t = Toast.makeText(getApplicationContext(), "Sorry, your answer is incorrect.. TICKTOCK", Toast.LENGTH_LONG);

            /* set fontsize and face of TOAST */
            ViewGroup toastView = (ViewGroup) t.getView();
            TextView toastTextView = (TextView) toastView.getChildAt(0);
            toastTextView.setTextSize(20);
            Typeface f = Typeface.createFromAsset(getAssets(), "font/gotham_bold.TTF");
            toastTextView.setTypeface(f);
            /******************************/
            t.show();
        }
    }

    private void setListener(final GridView boardGridView, final GridView buttonGridView, final SudokuEngine sudoku, final int difficulty)
    {
        boardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if(toggleBoardClick1)
                {
                    /*Start stopwatch*/
                    clock.startStopWatch();
                    /*Change the button text to "SUBMIT" */
                    Button submitButton = (Button)findViewById(R.id.submitButton);
                    submitButton.setText(R.string.submitText);

                    /*set the numbers of the button grid */
                    for(int i = 1; i < 10; i++)

                    {
                        CustomCellTextView button = (CustomCellTextView)buttonGridView.getChildAt(i-1);
                        button.setText(i + "");
                    }
                    prepareGameBoard(sudoku,difficulty);
                    sudoku.printGameBoard(boardGridView);
                    toggleBoardClick1 = false;
                    toggleBoardClick2 = true;
                }
                if(toggleBoardClick2)
                {
                    //Toast.makeText(getApplicationContext(), sudoku.deletedPointSetContains(position) + " position: " + position, Toast.LENGTH_SHORT).show();
                    if(sudoku.deletedPointSetContains(position))
                    {
                        //Reset Drawable
                        CustomCellTextView customCell;
                        for(int i = 0; i < 81; i++)
                        {
                            customCell = ((CustomCellTextView)boardGridView.getChildAt(i));

                            customCell.setBackgroundDrawable(getResources().getDrawable(R.drawable.box));
                            if(sudoku.deletedPointSetContains(i)) customCell.setTextColor(Color.parseColor(getString(R.string.pastelRedColor)));
                            else customCell.setTextColor(Color.parseColor("#707070"));
                        }


                        //set the textColor of all the cells in the same row or col of the selected cell
                        //not yet color the 3x3 sections
                        int col = position % 9;
                        int row = position / 9;
                        row *= 9;
                        for(int i = 0; i < 9; i++)
                        {
                            customCell = ((CustomCellTextView)boardGridView.getChildAt(col));
                            if(sudoku.deletedPointSetContains(col)) customCell.setTextColor(Color.parseColor(getString(R.string.pastelRedColor)));
                            else customCell.setTextColor(Color.parseColor(getString(R.string.pastelOrangeColor)));

                            customCell = ((CustomCellTextView)boardGridView.getChildAt(row));
                            if(sudoku.deletedPointSetContains(row)) customCell.setTextColor(Color.parseColor(getString(R.string.pastelRedColor)));
                            else customCell.setTextColor(Color.parseColor(getString(R.string.pastelOrangeColor)));

                            col += 9;
                            row ++;
                        }

                        //set background and textColor of selected cell
                        final CustomCellTextView cell = (CustomCellTextView)view;
                        cell.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell));
                        cell.setTextColor(Color.WHITE);

                        buttonGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(toggleButtonClick)
                                cell.setNumber(position + 1);
                            }
                        });
                    }
                    /*
                    int col = position % 9;
                    int row = position / 9;
                    Toast.makeText(getApplicationContext(), "col: " + col + " row: " + row + " position: " + position, Toast.LENGTH_SHORT).show();
                    CustomCellTextView cell = (CustomCellTextView)view;
                    cell.setNumber(9);
                    ((CustomCellTextView)boardGridView.getChildAt(0)).setNumber(0);
                    */
                }
            }
        });
    }

    private void prepareGameBoard(SudokuEngine s, int difficulty)
    {
        s.generateBoard();
        s.prepareGameBoard(difficulty);
    }

}
