package com.example.hyun.tagmusic_sql.Middle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hyun on 2017-12-15.
 */

public class InfoTagClass {
    private String tag;
    private ArrayList<String> musics = new ArrayList<String>();

    public InfoTagClass(String tag)
    {
        this.tag = tag;
    }

    public void AddMusics(String str)
    {
        this.musics.add(str);
    }

    public String getTag()
    {
        return this.tag;
    }

    public ArrayList<String> getMusics()
    {
        return this.musics;
    }
    public String getStringMusics()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String > it = this.musics.iterator();
        while(it.hasNext())
        {
            sb.append(it.next());
            sb.append(" \n");
        }
        return sb.toString();
    }
}
