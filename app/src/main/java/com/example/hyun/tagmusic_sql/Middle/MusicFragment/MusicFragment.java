package com.example.hyun.tagmusic_sql.Middle.MusicFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.hyun.tagmusic_sql.R;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

import java.util.ArrayList;

/**
 * Created by Hyun on 2017-12-10.
 */

public class MusicFragment extends Fragment{
    private ListView lv_music; // listView
    static private TotalMusicManager tmm;

    public MusicFragment()
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
        Bundle bundle =getArguments();
        tmm = (TotalMusicManager)bundle.getSerializable("TotalMusicManager");
        //Log.d("getSerializable name : ",ttm.toString());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_music, container, false);
        lv_music = (ListView)layout.findViewById(R.id.lv_music);
        //adapter 붙이기
        lv_music.setAdapter(new MusicListViewBaseAdapter(getActivity(), tmm));

        return layout;
    }

}
