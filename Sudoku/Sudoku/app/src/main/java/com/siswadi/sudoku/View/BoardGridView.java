package com.siswadi.sudoku.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.siswadi.sudoku.R;

/**
 * Created by esisw on 12/26/2017.
 */

public class BoardGridView extends GridView {

    public BoardGridView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setAdapter(new BoardGridViewAdapter(context));
        /*
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int col = position % 9;
                int row = position / 9;
                Toast.makeText(context, "col: " + col + " row: " + row, Toast.LENGTH_SHORT).show();
                CustomCellTextView cell = (CustomCellTextView)view;
                cell.setNumber(9);
            }
        });*/
    }

    private class BoardGridViewAdapter extends BaseAdapter
    {
        private Context context;

        public BoardGridViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 81;
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
        //create the children (a.k.a cells) view(?) to be supplied to the BoardGridView
        public View getView(int i, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cell_layout, null);
            return view;
            //return new EditText(context);
        }
    }


}
