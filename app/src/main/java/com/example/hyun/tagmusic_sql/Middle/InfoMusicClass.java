package com.example.hyun.tagmusic_sql.Middle;

import java.util.ArrayList;

/**
 * Created by Hyun on 2017-12-12.
 */

public class InfoMusicClass {
    private String music;
    private String tags;
    private String location;
    public InfoMusicClass(String music, String tags, String location)
    {
        this.music = music;
        this.tags = tags;
        this.location = location;
    }

    public boolean checkTags(String str)
    {
        boolean flag = true;
        char[] char_tags;
        char_tags = str.toCharArray();

        if(char_tags[0] != '#' || char_tags[char_tags.length-1] == '#')
        {
            flag = false;
            return flag;
        }
        else
        {
            for(int i = 0; i < char_tags.length; i++)
            {
                if(char_tags[i] == ',')
                {
                    try
                    {
                        if(char_tags[i+1] == ' ')
                        {
                            if(char_tags[i+2] == '#')
                            {
                                flag = true;
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }
    public String getMusic()
    {
        return music;
    }
    public String getLocation()
    {
        return location;
    }
    public String getTags()
    {
        return tags;
    }

    public void setTags(String str)
    {
        this.tags = str;
    }
}
