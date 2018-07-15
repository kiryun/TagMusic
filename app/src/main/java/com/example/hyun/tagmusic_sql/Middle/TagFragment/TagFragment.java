package com.example.hyun.tagmusic_sql.Middle.TagFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class TagFragment extends Fragment{
    private ListView lv_tag;
    static private TotalMusicManager tmm;

    public TagFragment()
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


        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_tag, container, false);
        lv_tag = (ListView)layout.findViewById(R.id.lv_tag);

        lv_tag.setAdapter(new TagListViewBaseAdapter(getActivity(), tmm));

        return layout;
    }
}
