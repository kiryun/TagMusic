package com.example.hyun.tagmusic_sql;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;

/**
 * Created by Hyun on 2017-12-12.
 */

public class MusicTagsDBTool extends DBHelper{
    private Context mContext;
    //static int cnt = 1;
    public MusicTagsDBTool(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version, "MUSICTAG");
        this.mContext = context;
    }

    /**
     * 특정 column 값 존재 여부 확인
     * @param str
     * @return
     */
    public int findColumnData(String str)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT EXISTS ( SELECT * FROM MUSICTAG WHERE MUSIC = '");
        sb.append(str);
        sb.append("');");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public void addMusicTag(InfoMusicClass infoMusic)
    {
        //Log.d("insert cnt : ", Integer.toString(cnt));
        //cnt++;
        // 1. 쓸 수 있는 DB 객체를 가져온다.
        SQLiteDatabase db = getWritableDatabase();
        // 2. MusicTag Data를 Insert한다.
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MUSICTAG ( ");
        sb.append(" MUSIC, TAG ) ");
        sb.append(" VALUES ( ?, ? ) ");
        // sb.append(" VALUES ( #MUSIC#, #TAG# ) ");
        //
        db.execSQL(sb.toString(),
                new Object[]{
                        infoMusic.getMusic(),
                        infoMusic.getTags()
                });;
        //Toast.makeText(mContext, "Insert 완료", Toast.LENGTH_SHORT).show();
        //db.close();
    }



    public void SetTags(String title, String tags)
    {
        StringBuffer sb = new StringBuffer();
        //UPDATE tablename SET filedA='456' WHERE test='123';
        //tablename의 테이블에 test 칼럼의 값이 123인 칼럼의 filedA칼럼을 456으로 변경
        sb.append(" UPDATE MUSICTAG SET TAG='");
        sb.append(tags);
        sb.append("' WHERE MUSIC='");
        sb.append(title);
        sb.append("';");
        //쓰기전용 db 가져온다.
        SQLiteDatabase db = getWritableDatabase();
        //db.rawQuery(sb.toString(), null);
        db.execSQL(sb.toString());
        //db.close();
    }

    public String getTags(String str)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, MUSIC, TAG FROM MUSICTAG WHERE MUSIC LIKE '");
        sb.append(str);
        sb.append("'");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        //db.close();
        //만약 없으면 ""return 있으면 그에 해당하는 tag return
        try
        {
            return cursor.getString(2);
        }
        catch (CursorIndexOutOfBoundsException e)
        {
            return "";
        }
    }
}
