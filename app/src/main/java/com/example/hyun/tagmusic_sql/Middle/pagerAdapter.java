package com.example.hyun.tagmusic_sql.Middle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hyun.tagmusic_sql.Middle.MusicFragment.MusicFragment;
import com.example.hyun.tagmusic_sql.Middle.PlayFragment.PlayFragment;
import com.example.hyun.tagmusic_sql.Middle.TagFragment.TagFragment;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

/**
 * Created by Hyun on 2017-12-10.
 */

public class pagerAdapter extends FragmentStatePagerAdapter {
    private TotalMusicManager tmm;
    private MusicFragment fragment_mf;
    private PlayFragment fragment_pf;
    private TagFragment fragment_tf;
    private Bundle throw_args;

    public pagerAdapter(android.support.v4.app.FragmentManager fm, Context context, Activity activity, TotalMusicManager tmm)
    {
        super(fm);

        this.tmm = tmm;//new TotalMusicManager(context, activity);
        fragment_mf = new MusicFragment();
        fragment_pf = new PlayFragment();
        fragment_tf = new TagFragment();
        throw_args = new Bundle();
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                throw_args.putSerializable("TotalMusicManager", tmm);
                fragment_mf.setArguments(throw_args);
                return fragment_mf;
            case 1:
                throw_args.putSerializable("TotalMusicManager", tmm);
                fragment_tf.setArguments(throw_args);
                return fragment_tf;
            case 2:
                throw_args.putSerializable("TotalMusicManager", tmm);
                fragment_pf.setArguments(throw_args);
                return fragment_pf;
            default:
                return null;
        }
    }
    @Override
    public int getCount()
    {
        return 3;
    }
}
