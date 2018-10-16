package com.siswadi.sudoku;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;



public class MainMenuActivity extends AppCompatActivity {

    private String name;
    private boolean toggleEditText1;
    private boolean toggleEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = (Button)findViewById(R.id.playButton);
        final EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
        Button beginnerDifficultyButton = (Button)findViewById(R.id.beginnerDifficultyButton);
        Button intermediateDifficultyButton = (Button) findViewById(R.id.intermediateDifficultyButton);
        Button extremeDifficultyButton = (Button)findViewById(R.id.extremeDifficultyButton);

        toggleEditText1 = true;
        toggleEditText2 = false;
        nameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleEditText1) //clear text field
                nameEditText.setText("");
                if(toggleEditText2) //bring keyboard down because the check button
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
                }
                if(toggleEditText1 == true)
                {
                    toggleEditText1 = false;
                    toggleEditText2 = true;
                }
                else
                {
                    toggleEditText1 = true;
                    toggleEditText2 = false;
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener()
        {
            private Button playButton = (Button)findViewById(R.id.playButton);
            private EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
            private Button beginnerDifficultyButton = (Button)findViewById(R.id.beginnerDifficultyButton);
            private Button intermediateDifficultyButton = (Button) findViewById(R.id.intermediateDifficultyButton);
            private Button extremeDifficultyButton = (Button)findViewById(R.id.extremeDifficultyButton);

            public void onClick(View v)
            {
                nameEditText.setVisibility(View.GONE);
                playButton.setVisibility(View.GONE);
                beginnerDifficultyButton.setVisibility(View.VISIBLE);
                intermediateDifficultyButton.setVisibility(View.VISIBLE);
                extremeDifficultyButton.setVisibility(View.VISIBLE);
                name = nameEditText.getText().toString().toUpperCase();
                if(name.length() > 8) name = name.substring(0,8);
            }
        });

        beginnerDifficultyButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startGame(0);
            }
        });

        intermediateDifficultyButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startGame(1);
            }
        });

        extremeDifficultyButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startGame(2);
            }
        });



    }

    private void startGame(int difficulty)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("userName", name);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}

