package com.example.assone;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FlagAdapter extends BaseAdapter
{
    private Context cntx;
    private List<Flag> flagList;
    private int selectedPos = -1;

    public FlagAdapter(Context inCntx, List<Flag> inFlagList)
    {
        this.cntx = inCntx;
        this.flagList = inFlagList;
    }

    public void setSelectedPos(int selectedFlag)
    {
        this.selectedPos = selectedFlag;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        int size = 0;
        if (flagList != null)
        {
            size = flagList.size();
        }

        return  size;
    }

    @Override
    public Object getItem(int item) {
        return item;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int currPos, View view, ViewGroup viewGroup) {
        //TODO: you will need to actually make this layout before this starts making any sense
        //TODO: come back and finish this when you're done with the layouts
        View rootView = LayoutInflater.from(cntx)
                .inflate(R.layout.item_flag, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.flag_name);
        ImageView flagImage = rootView.findViewById(R.id.image_flag);

        txtName.setText(flagList.get(currPos).getName());
        flagImage.setImageResource(flagList.get(currPos).getImage());

        if(selectedPos == currPos)
        {
            //making whatever the selected item is standard out and pop out more
            txtName.setTextSize(18f);
            txtName.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            //the item which is not selected will not pop out as much to the user
            txtName.setTextSize(14f);
            txtName.setTypeface(null, Typeface.NORMAL);
        }

        return rootView;
    }
}
