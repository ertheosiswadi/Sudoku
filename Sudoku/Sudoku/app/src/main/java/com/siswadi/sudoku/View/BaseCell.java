package com.siswadi.sudoku.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by esisw on 12/26/2017.
 */

public class BaseCell extends View {

    private int value;
    private boolean isModifiable;

    public BaseCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setNotModifiable()
    {
        isModifiable = false;
    }

    public void setInitialValue(int v)
    {
        this.value = v;
        invalidate();//because need to draw(?)
    }
    public void setValue(int v)
    {
        if(isModifiable) this.value = v;
        invalidate();
    }
    public int getValue()
    {
        return value;
    }
}
