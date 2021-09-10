package com.example.assone;

import com.example.assone.PasswordSchema.PassWordTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = PassWordTable.NAME + ".db";

    public PasswordDbHelper (Context cntx)
    {
        super(cntx, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sql)
    {
        //this is what is going to happen when you're creating the database in memory

        //creating all the needed columns in the database which we need
        sql.execSQL("CREATE TABLE " + PassWordTable.NAME + "(" +
                PassWordTable.Cols.USER_NAME + "TEXT, " +
                PassWordTable.Cols.PASSWORD + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int v1, int v2)
    {
        // I am probably never going to need this, so I am not going to implement it until I actuall need it
    }


}
