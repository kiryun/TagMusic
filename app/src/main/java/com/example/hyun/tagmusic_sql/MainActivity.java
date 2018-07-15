package com.example.hyun.tagmusic_sql;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;
import com.example.hyun.tagmusic_sql.Middle.pagerAdapter;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp_middle; // view pager
    private MediaPlayer m_mediaPlayer;
    private TotalMusicManager tmm;
    private ArrayList<InfoMusicClass> m_infoPlayList = new ArrayList<InfoMusicClass>();

    private SeekBar seekBar;
    private boolean isPlaying = false;
    private int position;
    private ProgressUpdate progressUpdate;
    private ImageButton btn_musicPlay;
    private ImageButton btn_musicNext;
    private ImageButton btn_musicPre;
    private ImageButton btn_musicPause;
    //private static int songCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Initialize
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmm = new TotalMusicManager(MainActivity.this, MainActivity.this);
        m_infoPlayList = tmm.getInfoPlayList();
        m_mediaPlayer = new MediaPlayer();
        //

        ///view pager intit
        vp_middle = (ViewPager)findViewById(R.id.vp_middle);
        ///


        ///button init
        Button btn_music = (Button)findViewById(R.id.btn_music);
        Button btn_tag = (Button)findViewById(R.id.btn_tag);
        final Button btn_play = (Button)findViewById(R.id.btn_play);
        ///

        ///view pager 호출 각 플래그먼트 생성
        vp_middle.setAdapter(new pagerAdapter(getSupportFragmentManager(),MainActivity.this,MainActivity.this, tmm));
        vp_middle.setCurrentItem(0);
        ///

        ///Button 처리
        btn_music.setOnClickListener(movePageListener);
        btn_music.setTag(0);
        btn_tag.setOnClickListener(movePageListener);
        btn_tag.setTag(1);
        btn_play.setOnClickListener(movePageListener);
        btn_play.setTag(2);

        //음악 관리
        btn_musicPlay = (ImageButton)findViewById(R.id.play);
        btn_musicPause = (ImageButton)findViewById(R.id.pause);
        btn_musicPre = (ImageButton)findViewById(R.id.pre);
        btn_musicNext = (ImageButton)findViewById(R.id.next);
        //seekBar = (SeekBar)findViewById(R.id.seekbar);

        //OnTriggerPlayMusic(list.get(position);
        progressUpdate = new ProgressUpdate();
        progressUpdate.start();

        /*seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                m_mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                m_mediaPlayer.seekTo(seekBar.getProgress());
                if(seekBar.getProgress() > 0 && btn_play.getVisibility() == View.GONE)
                {
                    m_mediaPlayer.start();
                }
            }
        });*/

        m_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                m_infoPlayList = tmm.getInfoPlayList();
                if(position+1 < m_infoPlayList.size())
                {
                    position++;
                    OnTriggerPlayMusic(m_infoPlayList.get(position));
                }
            }
        });

        btn_musicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_infoPlayList = tmm.getInfoPlayList();
                OnTriggerPlayMusic(m_infoPlayList.get(position));
                btn_musicPause.setVisibility(View.VISIBLE);
                btn_musicPlay.setVisibility(View.GONE);
                m_mediaPlayer.seekTo(m_mediaPlayer.getCurrentPosition());
                m_mediaPlayer.start();
            }
        });
        btn_musicPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_infoPlayList = tmm.getInfoPlayList();
                if(position-1>=0)
                {
                    position--;
                    OnTriggerPlayMusic(m_infoPlayList.get(position));
                    //seekBar.setProgress(0);
                }
            }
        });
        btn_musicPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_musicPause.setVisibility(View.GONE);
                btn_musicPlay.setVisibility(View.VISIBLE);
                m_mediaPlayer.pause();
            }
        });
        btn_musicNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_infoPlayList = tmm.getInfoPlayList();
                //Log.d("kihyunPlaylistsize",Integer.toString(m_infoPlayList.size()));

                if(position+1<m_infoPlayList.size())
                {
                    position++;
                    OnTriggerPlayMusic(m_infoPlayList.get(position));
                    //seekBar.setProgress(0);
                }
            }
        });


    }

    public void OnTriggerPlayMusic(InfoMusicClass musicDto)
    {
        try
        {
            //seekBar.setProgress(0);
            /*Uri musicURI = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,""+musicDto.getId());*/
            Uri musicURI = Uri.parse(musicDto.getLocation());
            m_mediaPlayer.reset();
            m_mediaPlayer.setDataSource(this, musicURI);
            m_mediaPlayer.prepare();
            m_mediaPlayer.start();
            //seekBar.setMax(m_mediaPlayer.getDuration());
            if(m_mediaPlayer.isPlaying())
            {
                btn_musicPlay.setVisibility(View.GONE);
                btn_musicPause.setVisibility(View.VISIBLE);
            }
            else
            {
                btn_musicPlay.setVisibility(View.VISIBLE);
                btn_musicPause.setVisibility(View.GONE);
            }
        }
        catch (Exception e)
        {
            Log.e("SimplePlayer", e.getMessage());
        }

    }

    class ProgressUpdate extends Thread
    {
        @Override
        public void run()
        {
            /*while(isPlaying)
            {
                try
                {
                    Thread.sleep(500);
                    if(m_mediaPlayer!=null) {
                        seekBar.setProgress(m_mediaPlayer.getCurrentPosition());
                    }
                }
                catch (Exception e)
                {
                    Log.e("ProgressUpdate",e.getMessage());
                }
            }*/
        }

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        isPlaying = false;
        if(m_mediaPlayer!=null)
        {
            m_mediaPlayer.release();
            m_mediaPlayer = null;
        }
    }
    //버튼 누르면 페이지 이동
    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            vp_middle.setCurrentItem(tag);
        }
    };
}
