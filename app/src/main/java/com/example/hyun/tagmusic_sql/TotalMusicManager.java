package com.example.hyun.tagmusic_sql;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;
import com.example.hyun.tagmusic_sql.Middle.InfoTagClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hyun on 2017-12-11.
 * db에 있는 음악 정보들(tag, music)을 받아온다.
 * db에 있는 음악 정보들(tag, music)을 set한다.
 * storage에 있는 .mp3 파일들의 handle을 가져온다.
 */

@SuppressWarnings("serial")
public class TotalMusicManager implements Serializable {
    //MusicFragment에서 보여줄 music 과 music에 붙어 있는 tags
    //실제 play 할 때 쓰이는 tag와 tag에 붙어있는 musics
    private ArrayList<InfoMusicClass> m_infoMusicList = new ArrayList<InfoMusicClass>();
    private ArrayList<InfoTagClass> m_infoTagList = new ArrayList<InfoTagClass>();
    private ArrayList<InfoMusicClass> m_infoPlayList = new ArrayList<InfoMusicClass>();
    private ArrayList<String> list_tags;
    //DB
    private MusicTagsDBTool MusicTagsDB;
    private PlaylistDBTool PlaylistDB;

    //MainActivity에서 받아온 Context와 Activity
    private Context context;
    private Activity activity;

    private  static  final  int MY_PERMISSION_REQUSET = 1;


    public TotalMusicManager(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;

        //db 생성
        CreateTable();
        //storage에 있는 mp3 파일 get
        GetMusicInfo();
        CreateInfoTag();

        list_tags = getPlaylistDB().getAllTag();
        m_infoPlayList=GetPlaylist(list_tags);
    }

    public void CreateInfoTag()
    {
        Iterator<InfoMusicClass> it = m_infoMusicList.iterator();

        //infoMusicList 전체를 돈다.
        while(it.hasNext())
        {
            //split을 이용해 tags를 ","기준으로 자른다.
            String[] arr_tags = it.next().getTags().split(", ");

            //만약 m_infoTagList에 아무것도 없다면
            if(m_infoTagList.size() == 0)
            {
                Log.d("m_infoTagList.size()","0");
                //m_infoTagList에 tag값을 넣어준다.
                for(int i = 0; i<arr_tags.length; i++)
                {
                    this.m_infoTagList.add(new InfoTagClass(arr_tags[i]));
                }
            }
            //m_infoTagList에 무언가 있다면
            else {
                for (int i = 0; i < arr_tags.length; i++)
                {
                    //arr_tags와 일치하는 것이 있는지 검사
                    //있다면 넘어가고
                    //없다면
                    boolean overlap_flag = false;
                    Iterator<InfoTagClass> it_tag = m_infoTagList.iterator();
                    //m_infoTagList전체를 돌면서
                    while(it_tag.hasNext())
                    {
                        String str = it_tag.next().getTag();

                        if (!str.equals(arr_tags[i])) {

                            overlap_flag = false;

                        }
                        else
                        {
                            overlap_flag=true;
                            break;
                        }
                    }
                    if(!overlap_flag)
                    {
                        //m_infoTagList에 tag add
                        this.m_infoTagList.add(new InfoTagClass(arr_tags[i]));
                    }
                }

            }
        }

        SetMusics();
        Log();
    }

    public void Log()
    {
        Iterator<InfoTagClass> it_tag = m_infoTagList.iterator();
        while(it_tag.hasNext())
        {
            InfoTagClass infotag = it_tag.next();
            Log.d("asdftag : ", infotag.getTag());

            Log.d("asdfmusics : ", infotag.getStringMusics());
        }
    }

    public void SetMusics()
    {
        Iterator<InfoMusicClass> it = m_infoMusicList.iterator();
        //int cnt  = 0;
        while(it.hasNext())
        {
            //Log.d("cnt : ",Integer.toString(cnt));
            //cnt++;
            Iterator<InfoTagClass> it_tag = m_infoTagList.iterator();
            InfoMusicClass infomusic = it.next();
            String[] tag = infomusic.getTags().split(", ");
            while (it_tag.hasNext())
            {
                InfoTagClass infotag = it_tag.next();
                for(int i = 0; i<tag.length; i++)
                {
                    if(infotag.getTag().equals(tag[i]))
                    {
                        if(!checkOverlapMusic(infomusic.getMusic(), infotag)) // no such element exception
                        {
                            //InfoTagClass dummy = it_tag.next();
                            //String strmusic = it.next().getMusic(); // no such element exception
                            //dummy.AddMusics(strmusic);
                            //it_tag.next().AddMusics(it.next().getMusic());
                            infotag.AddMusics(infomusic.getMusic());
                        }
                    }
                }
            }
        }
    }
    /**
     * m_infoTagList에 Tag와 일치하는 music이 중복이 되는지 검사.
     * @param music infoTagClass
     * @return 있다면 true 없다면 false
     */
    public boolean checkOverlapMusic(String music, InfoTagClass infoTagClass)
    {
        boolean flag = false;
        ArrayList<String> musics = infoTagClass.getMusics();
        Iterator<String> it_tag_music = musics.iterator();

        while(it_tag_music.hasNext())
        {
            if(it_tag_music.next().equals(music))
            {
                return true;
            }
        }
        return false;
    }

    public void CreateTable()
    {
        MusicTagsDB = new MusicTagsDBTool(context, "music_tag", null, 1);
        MusicTagsDB.testDB();
        Log.d("db name: ", MusicTagsDB.getDatabaseName());
        Log.d("row size at create db: ",Integer.toString(MusicTagsDB.getRowSize()));

        PlaylistDB = new PlaylistDBTool(context, "playlist", null, 1);
        PlaylistDB.testDB();

    }

    public void GetMusicInfo()
    {
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUSET);

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUSET );
            }
        } else {
            doStuff();//휴대폰내 mp3 파일 업데이트 trigger
            Log.d("start", "doStuff");
        }
    }

    public void doStuff()
    {
        // 휴대폰내 mp3 파일 업데이트
        getMusicFile();
        //////////////////////////////////////////매우 중요 Play Fragment에서 다시 MusicFragment로 오게 되면 다시 update 됨./////////////////////////////////////////////////////////
        Log.d("start doSuff()", "get music list");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void getMusicFile()
    {
        ContentResolver contentResolver= context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);
        //int cnt = 0;
        if(songCursor !=null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist= songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation= songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do{
                String currentTitle = songCursor.getString(songTitle);
                //String currentArist = songCursor.getString(songArtist);
                String currentLocation = songCursor.getString(songLocation);
                //title값이 db에 존재하는지 확인.
                //있다면 m_infoMusicList update할 때 db 기반으로 update
                //없다면 m_infoMusicList update할 대 title 넣고 tags = ""로 정의

                //title을 통해서 tag값을 가져 온다.
                //현재 InfoMusicClass에는 업데이트가 되어 있지 않으니 해당 table에서 가져온다.
                //table에서 tag를 가져 올 때 title로 table을 검색하고 title과 일치하는 raw의 tag항목을 가져온다.
                // et_tag는 db에서 가져와야함

                //만약 이미 존재 한다면
                if(MusicTagsDB.findColumnData(currentTitle) == 1)
                {
                    Log.d("complete such ", currentTitle);
                    //
                    setInfoMusicList(currentTitle, MusicTagsDB.getTags(currentTitle), currentLocation);
                }
                //없다면
                else
                {
                    Log.d("no such", currentTitle);
                    if(MusicTagsDB == null)
                    {
                        MusicTagsDB = new MusicTagsDBTool(context, "db", null, 1);
                    }

                    MusicTagsDB.addMusicTag(new InfoMusicClass(currentTitle, "", currentLocation));
                    Log.d("getRowSize() : ", Integer.toString(MusicTagsDB.getRowSize()));
                    setInfoMusicList(currentTitle, "", currentLocation);
                }
                //this.m_infoMusicList.add(new InfoMusicClass(currentTitle+"\n", MusicTagDB.getTags(currentTitle)));
                //Log.d("title : ", this.m_infoMusicList.get(cnt).getMusic());
                //cnt++;
            }while(songCursor.moveToNext());

        }
    }

    public void setInfoMusicList(String music, String tags, String location)
    {
            m_infoMusicList.add(new InfoMusicClass(music, tags, location));
    }

    public ArrayList<InfoMusicClass> GetPlaylist(ArrayList<String> list_tags)
    {
        //return 해줄 playlist 값
        ArrayList<InfoMusicClass> playlist = new ArrayList<InfoMusicClass>();
        //editText에 있던 tag 값
        Iterator<String> it_strTags = list_tags.iterator();

        //playlist의 music값만 저장되어있는 list
        ArrayList<String> str_music = new ArrayList<String>();

        //InfoTagList의 Tag값과 arr_tags와 같은지 검사
        while(it_strTags.hasNext())
        {
            String tag = it_strTags.next();
            //m_infoTagList
            Iterator<InfoTagClass> it_tagClass = m_infoTagList.iterator();
            while(it_tagClass.hasNext())
            {
                InfoTagClass instance_infotag = it_tagClass.next();
                //만약 같다면
                if(tag.equals(instance_infotag.getTag()))
                {
                    ArrayList<String> infotag_music = instance_infotag.getMusics();
                    //만약 str_music에 아무것도 없다면 전부다 집어넣는다.
                    if(str_music.size() == 0)
                    {
                        Iterator<String> it_infoTagMusic = infotag_music.iterator();
                        while(it_infoTagMusic.hasNext())
                        {
                            str_music.add(it_infoTagMusic.next());
                        }
                    }
                    //InfoTagList의 music값을 돌면서 저장할 ArrayList str_music 과 같은지 검사.
                    else
                    {
                        Iterator<String> it_infoTagMusic = infotag_music.iterator();
                        //InfoTag의 Musics을 돌면서
                        while(it_infoTagMusic.hasNext())
                        {
                            String str = it_infoTagMusic.next();
                            //집어넣을 str_music 값에 중복이 없다면
                            if(!CheckOverlapMusic(str_music, str))
                            {
                                //InfoTag의 Musics의 그 값을 add
                                str_music.add(str);
                            }
                        }
                    }
                }
            }
        }

        //str_music에서 중복되는 것 삭제
        Log.d("kihyunstr_musicsize",Integer.toString(str_music.size()));
        ArrayList<String> str_music_update = new ArrayList<String>();
        for(int i = 0; i< str_music.size(); i++)
        {
            if(!str_music_update.contains(str_music.get(i)))
            {
                str_music_update.add(str_music.get(i));
            }
        }
        Log.d("kihyunmusic_updatesize",Integer.toString(str_music_update.size()));
        //str_music이 완성된 시점에서
        Iterator<String> it_music = str_music_update.iterator();
        //str_music과 m_infoMusicLsit를 돌면서
        while(it_music.hasNext())
        {
            String str = it_music.next();
            Iterator<InfoMusicClass> it_infoMusic = m_infoMusicList.iterator();
            while(it_infoMusic.hasNext())
            {
                InfoMusicClass infomusic = it_infoMusic.next();
                //music 값이 같은 객체는 infomusic에 저장
                if(str.equals(infomusic.getMusic()))
                {
                    playlist.add(new InfoMusicClass(infomusic.getMusic(), infomusic.getTags() ,infomusic.getLocation()));
                    // 지금 InfoMusicList에 노래가 2개 저장됨; 왜그러냐 에뮬에서는 안그러는데 휴대폰에서는 그럼 이유 : 휴대폰에 sd카드에 *.mp3를 복사해서 local에 *.mp3를 넣어놨었음
                    break;
                }
            }
        }

        this.m_infoPlayList = playlist; // m_infoPlayList를 업데이트

        return playlist;
    }

    public boolean CheckOverlapMusic(ArrayList<String> str_music, String infoTagMusic)
    {
        boolean flag = false;
        Iterator<String> it_music = str_music.iterator();
        while(it_music.hasNext())
        {
            if(it_music.next().equals(infoTagMusic))
            {
                flag = true;
                break;
            }
        }
        return false;
    }

    public ArrayList<InfoMusicClass> getInfoMusicList()
    {
        return this.m_infoMusicList;
    }

    public ArrayList<InfoTagClass> getInfoTagList()
    {
        return this.m_infoTagList;
    }

    public ArrayList<InfoMusicClass> getInfoPlayList()
    {
        return this.m_infoPlayList;
    }

    /*public void SetInfoPlayList(ArrayList<InfoMusicClass> playlist)
    {
        this.m_infoPlayList = playlist;
    }*/

    public MusicTagsDBTool getMusicTagsDB()
    {
        return MusicTagsDB;
    }

    public PlaylistDBTool getPlaylistDB()
    {
        return PlaylistDB;
    }

    public void onRequestPermissionResult(int requestCode,  String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_REQUSET: {
                if(grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(context,
                            Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                        //Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                        doStuff();
                    }
                } else{
                    //Toast.makeText(this, "No permisssion granted!", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                return;
            }
        }
    }

}
