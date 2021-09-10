/*
this database is going ot be used for a look up table for the current id's given a country name
this should make the running of the application a lot easier to use

TODOO:
    - I actually don't think I need this database, so delete it if you're not going to be using it
 */
package com.example.assone;

import com.example.assone.FlagSchema.FlagTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlagDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = FlagTable.NAME + ".db";


    public FlagDbHelper(Context cntx)
    {
        super(cntx, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sql)
    {
        //how the table is going to be created on load
        sql.execSQL("CREATE TABLE " +FlagTable.NAME + "(" +
                FlagTable.Cols.COUNTRY_NAME + "TEXT, " +
                FlagTable.Cols.COUNTRY_ID + "TEXT)");

        //hardcoding the flag countries with corresponding flag country codes
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int v1, int v2)
    {
        //I don't think that I will actually need this, I will implement it when I will actually need to use it
    }
}
