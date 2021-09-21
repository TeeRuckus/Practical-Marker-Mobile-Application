package com.example.assone;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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
    private static Flag selectedCountry;
    private static final String TAG = "Flag adapter.";

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
    public View getView(int currPos, View view, ViewGroup viewGroup)
    {
        View rootView = LayoutInflater.from(cntx)
                .inflate(R.layout.item_flag, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.flag_name);
        ImageView flagImage = rootView.findViewById(R.id.image_flag);

        txtName.setText(flagList.get(currPos).getName());
        flagImage.setImageResource(flagList.get(currPos).getImage());
        //selectedCountry = flagList.get(currPos);
        //getting what is on th country screen at the moment

        String displayCountry = flagList.get(currPos).getName();

        int flagID = flagImage.getId();


        selectedCountry = new Flag(displayCountry, flagID);


        // the user is most likley not going ot touch the flag to slelect country, they're giong to
        // select the name, and the name is going to offer a grater surface area for the user to click
        // on
        /*flagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Details of the clicked flag");
                Log.e(TAG, "Country Name: " + txtName.getText().toString());
                Log.e(TAG, "Country drawable ID: " + flagImage.getDrawable().toString());
                selectedCountry = flagList.get(currPos);

            }
        });*/

        if(selectedPos == currPos)
        {
            selectedCountry = flagList.get(currPos);
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

    public static Flag getSelectedCountry()
    {
        return selectedCountry;
    }
}
