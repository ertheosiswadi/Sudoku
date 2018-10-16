package com.siswadi.sudoku.View;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.siswadi.sudoku.R;

/**
 * Created by esisw on 12/27/2017.
 */

public class ButtonGridViewAdapter extends BaseAdapter {

    private Context context;

    public ButtonGridViewAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.button_layout, viewGroup, false);
        ((CustomCellTextView)view).setFontButton();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/gotham_bold.TTF");
        ((CustomCellTextView)view).setTypeface(typeface);
        return view;
    }
}
