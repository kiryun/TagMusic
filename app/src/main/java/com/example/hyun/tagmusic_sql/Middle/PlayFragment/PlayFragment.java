package com.example.hyun.tagmusic_sql.Middle.PlayFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;
import com.example.hyun.tagmusic_sql.Middle.InfoTagClass;
import com.example.hyun.tagmusic_sql.R;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hyun on 2017-12-12.
 */

public class PlayFragment extends Fragment {
    private ListView lv_play;
    static private TotalMusicManager tmm;
    private ArrayList<InfoTagClass> m_infoTagList;
    private ArrayList<InfoMusicClass> m_infoMusicList;
    private ArrayList<InfoMusicClass> playlist = new ArrayList<InfoMusicClass>();

    public PlayFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        tmm = (TotalMusicManager)bundle.getSerializable("TotalMusicManager");
        this.m_infoTagList = tmm.getInfoTagList();
        this.m_infoMusicList = tmm.getInfoMusicList();
        this.playlist = tmm.getInfoPlayList();

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_play, container, false);
        lv_play = (ListView)layout.findViewById(R.id.lv_play);

        final EditText et_nowPlay = (EditText)layout.findViewById(R.id.et_nowPlay);
        Button btn_update = (Button)layout.findViewById(R.id.btn_update);

        //EditText 업데이트 db의 tag값을 가져와서 update한다.
        ArrayList<String> list_tags = tmm.getPlaylistDB().getAllTag();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<list_tags.size(); i++)
        {
            sb.append(list_tags.get(i));
            if(i+1 < list_tags.size())
            {
                sb.append(", ");
            }
        }
        //db에서 가져온 tag값을 editText를 업데이트한다.
        et_nowPlay.setText(sb.toString());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_nowPlay = et_nowPlay.getText().toString();
                //Log.d("asdfstr_nowPlay",str_nowPlay);
                //playlist update flag
                boolean playlist_update_flag = true;
                if(checkUpdate(str_nowPlay))
                {
                    String[] at_str_nowPlay = str_nowPlay.split(", ");
                    //db delete flag
                    boolean delete_flag = true;
                    for(int i = 0; i<at_str_nowPlay.length; i++)
                    {
                        if(checkTag(at_str_nowPlay[i]))
                        {
                            tmm.getPlaylistDB().DeleteTable(delete_flag);
                            delete_flag=false;
                            tmm.getPlaylistDB().addTagList(at_str_nowPlay[i]);
                            Toast.makeText(getContext(), "update 완료", Toast.LENGTH_SHORT).show();
                            playlist_update_flag = true;
                        }
                        else
                        {
                            Toast.makeText(getContext(), "없는 Tag 입니다.", Toast.LENGTH_SHORT).show();
                            playlist_update_flag = false;
                        }
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Tag형식이 잘못 되었습니다.(#abc, #abcd)", Toast.LENGTH_SHORT).show();
                    playlist_update_flag = false;
                }
                //만약 playlist를 update 했다면
                if(playlist_update_flag)
                {
                    ArrayList<String> dummy_list_tags = new ArrayList<String>();
                    String[] arrStr = str_nowPlay.split(", ");
                    for(int i = 0; i<arrStr.length; i++)
                    {
                        dummy_list_tags.add(arrStr[i]);
                    }
                    playlist = tmm.GetPlaylist(dummy_list_tags);

                    //adapter update
                    PlayListViewBaseAdapter adapter = new PlayListViewBaseAdapter(getContext(), tmm, playlist);
                    adapter.notifyDataSetChanged();
                    lv_play.setAdapter(adapter);
                }
            }
        });



        playlist = tmm.GetPlaylist(list_tags);
        //playlist = GetPlaylist(list_tags);
        Log.d("kihyunplaylistsize",Integer.toString(playlist.size()));
        /*if(playlist.get(0).getMusic().equals(playlist.get(1).getMusic()))
        {
            Log.d(playlist.get(0).getMusic(),playlist.get(1).getMusic());
        }*/
        for(int i = 0; i<playlist.size(); i++)
        {
            Log.d("kihyunplaylist ",playlist.get(i).getMusic());
        }
        lv_play.setAdapter(new PlayListViewBaseAdapter(getActivity(), tmm, playlist));

        return layout;
    }




    //m_infoTagList에 playlist에 적은 tag가 있는지 검사
    public boolean checkTag(String tag)
    {
        boolean flag = false;
        Iterator<InfoTagClass> it_tag = m_infoTagList.iterator();

        while(it_tag.hasNext())
        {
            if(tag.equals(it_tag.next().getTag()))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //playlist tag 문자 형식 검사
    public boolean checkUpdate(String str)
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
}
