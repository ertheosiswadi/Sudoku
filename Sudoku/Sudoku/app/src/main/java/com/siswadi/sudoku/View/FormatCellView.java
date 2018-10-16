package com.siswadi.sudoku.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by esisw on 12/26/2017.
 */

public class FormatCellView extends BaseCell {

    private Paint paint;

    public FormatCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawNumber(canvas);
        drawLines(canvas);
    }

    private void drawNumber(Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setStyle(Paint.Style.FILL);

        Rect bounds = new Rect();
        paint.getTextBounds(String.valueOf(getValue()), 0, String.valueOf(getValue()).length(), bounds);

        if( getValue() != 0 ){
            canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width())/2, (getHeight() + bounds.height())/2	, paint);
        }
    }

    private void drawLines(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }


}
