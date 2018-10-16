package com.siswadi.sudoku.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.siswadi.sudoku.R;

/**
 * Created by esisw on 12/26/2017.
 */

public class CustomCellTextView extends android.support.v7.widget.AppCompatTextView {

    private Context context;

    public CustomCellTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setFontBoard()
    {
        this.setTextColor(Color.parseColor("#707070"));
    }


    public void setFontButton()
    {
        this.setTextColor(Color.WHITE);
        this.setTextSize(20);
    }

    public void setNumber(int n)
    {
        String s = n + "";
        setText(s);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
