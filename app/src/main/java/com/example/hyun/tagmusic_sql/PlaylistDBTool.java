package com.example.hyun.tagmusic_sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hyun on 2017-12-16.
 */

public class PlaylistDBTool extends DBHelper {
    private Context mContext;

    public PlaylistDBTool(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version, "PLAYLIST");
        this.mContext = context;
    }

    //insert
    public void addTagList(String tag)
    {
        //boolean flag = true;
        SQLiteDatabase db = getWritableDatabase();


        /*ArrayList<String> str_dbTag = getAllTag();

        Iterator<String> it = str_dbTag.iterator();
        //db에 tag가 있는 지 검사
        while(it.hasNext())
        {
            if(tag.equals(it.next()))
            {
                flag = false;
                break;
            }
        }*/
        //없다면 db에 추가
        //if(flag)
        //{
            StringBuffer sb = new StringBuffer();


            sb.setLength(0);
            sb.append(" INSERT INTO PLAYLIST ( ");
            sb.append(" TAG ) ");
            sb.append("VALUES ( ? ) ");

            db.execSQL(sb.toString(),
                    new Object[]{
                            tag
                    });
        //}


    }
    public void DeleteTable(boolean flag)
    {
        if(flag)
        {
            StringBuffer sb = new StringBuffer();
            SQLiteDatabase db = getWritableDatabase();
            sb.append(" DELETE FROM PLAYLIST ");
            db.execSQL(sb.toString());
        }
    }
    //get all
    public ArrayList<String> getAllTag()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, TAG FROM PLAYLIST ");

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery(sb.toString(), null);
        ArrayList<String> tags = new ArrayList<String>();
        String tag;
        while(cursor.moveToNext())
        {
            tag = cursor.getString(1);
            tags.add(tag);
        }
        return tags;

    }
}
