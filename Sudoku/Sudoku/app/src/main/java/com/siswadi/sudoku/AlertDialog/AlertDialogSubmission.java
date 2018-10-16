package com.siswadi.sudoku.AlertDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.GridView;

import com.siswadi.sudoku.GameActivity;
import com.siswadi.sudoku.R;
import com.siswadi.sudoku.SudokuEngine;

/**
 * Created by esisw on 12/29/2017.
 */

@SuppressLint("ValidFragment")
public class AlertDialogSubmission extends DialogFragment {

    private GameActivity a;
    private GridView boardGridView;
    private SudokuEngine sudoku;

    public AlertDialogSubmission(GridView boardGridView, SudokuEngine sudoku, GameActivity a) {
        this.a = a;
        this.boardGridView = boardGridView;
        this.sudoku = sudoku;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = a;
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder
                .setMessage("Submit Now?")
                .setPositiveButton(R.string.submit_ok_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a.submitAnswers(boardGridView, sudoku);
                    }
                })
                .setNegativeButton("Still trying", null);
        return builder.create();
    }
}
