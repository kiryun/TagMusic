package com.example.hyun.tagmusic_sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hyun on 2017-12-10.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private String tableName;

    public DBHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version, String tableName)
    {
        super(context, dbName, factory, version);
        this.context = context;
        this.tableName = tableName;
    }

    /* *
     * Database가 존재하지 않을 때, 딱 한번 실행된다.
     * * DB를 만드는 역할을 한다.
     * * @param db
     * */

    @Override public void onCreate(SQLiteDatabase db)
    {
        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE ");
        sb.append(this.tableName);
        sb.append(" ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        //sb.append(" MUSIC TEXT, ");
        //sb.append(" TAG TEXT ) ");
        if(this.tableName.equals("MUSICTAG"))
        {
            sb.append(" MUSIC TEXT, ");
            sb.append(" TAG TEXT ) ");
        }
        else if(this.tableName.equals("PLAYLIST"))
        {
            sb.append(" TAG TEXT ) ");
        }
        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());
        //Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
        //db.close();
    }
    /**
     *  * Application의 버전이 올라가서
     *  * Table 구조가 변경되었을 때 실행된다.
     *  * @param db
     *  * @param oldVersion
     *  * @param newVersion */
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }
    /**
     *  *
     *  */
    public void testDB()
    {
        SQLiteDatabase db = getReadableDatabase();
    }

    /**
     * 특정 column 존재여부 확인
     * @param str 찾으려는 값
     * @param type 찾으려는 column 속성 1: music 2 : tag
     * @return
     */
    /*public boolean findColumnData(String str, int type)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT EXISTS ( SELECT * FROM ");
        sb.append(this.tableName);
        sb.append(" WHERE ");
        if( type ==1 )
        {
            sb.append("MUSIC");
        }
        else if(type == 2)
        {
            sb.append("TAG");
        }
        sb.append(" );");
    }*/

    public int getRowSize()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) FROM ");
        sb.append(this.tableName);
        sb.append(" ");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        //db.close();
        return cursor.getInt(0);
    }
}
