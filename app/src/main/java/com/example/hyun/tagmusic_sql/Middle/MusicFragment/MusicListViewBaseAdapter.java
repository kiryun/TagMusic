package com.example.hyun.tagmusic_sql.Middle.MusicFragment;

mimport android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;
import com.example.hyun.tagmusic_sql.MusicTagsDBTool;
import com.example.hyun.tagmusic_sql.R;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

import java.util.ArrayList;

/**
 * Created by Hyun on 2017-12-10.
 */

public class MusicListViewBaseAdapter extends BaseAdapter{
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private Context mContext = null;
    private ArrayList<InfoMusicClass> m_infoMusicList;
    private MusicTagsDBTool MusicTagsDB;

    static private TotalMusicManager tmm;

    public MusicListViewBaseAdapter(Context context, TotalMusicManager tmm)
    {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.tmm = tmm;
        this.m_infoMusicList = tmm.getInfoMusicList();
        this.MusicTagsDB = tmm.getMusicTagsDB();
    }
    // Adapter가 관리할 Data의 개수를 설정 합니다.
    @Override
    public int getCount()
    {
        return m_infoMusicList.size();
    }

    // Adapter가 관리하는 Data의 Item 의 Position을 <객체> 형태로 얻어 옵니다.
    @Override
    public InfoMusicClass getItem(int position)
    {
        return m_infoMusicList.get(position);
    }

    // Adapter가 관리하는 Data의 Item 의 position 값의 ID 를 얻어 옵니다.
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    // ListView의 뿌려질 한줄의 Row를 설정 합니다.
    @Override
    public View getView(final int position, View convertview, ViewGroup parent)
    {

        View v = convertview;

        if(v == null)
        {
            Log.d("MusicListViewBaseAdapte", "View is null");
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.listview_music_item, null);
            // layout->listview_music_item.xml->tv_musicitem
            viewHolder.tv_musicitem = (TextView) v.findViewById(R.id.tv_musicitem);
            // layout->listview_music_item.xmml -> btn_musicitem_tag
            viewHolder.btn_musicitem_tag = (Button)v.findViewById(R.id.btn_musicitem_tag);

            v.setTag(viewHolder);

        } else {
            Log.d("MusicListViewBaseAdapte", "View is not null");
            viewHolder = (ViewHolder)v.getTag();
        }

        //textview에 infoMusicClass에 있는 music_title 값 정의
        viewHolder.tv_musicitem.setText(getItem(position).getMusic()); // Info에 있는 멤버 변수 music을 TextView로 정의
        viewHolder.btn_musicitem_tag.setText(getItem(position).getTags()); //Info에 있는 멤버 변수 tags를 button의 text로 정의
        //viewHolder.info_music = getItem(position);

        viewHolder.btn_musicitem_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("btn_musicitem_tag","clicked");
                final InfoMusicClass info_music = getItem(position);
                LinearLayout layout = new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText etTag = new EditText(mContext);
                layout.addView(etTag);
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

                dialog.setTitle("Tag 수정")
                        .setView(layout)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String tag = etTag.getText().toString();
                                String music = info_music.getMusic();

                                if(info_music.checkTags(tag))
                                {
                                    //viewHolder.btn_musicitem_tag.setText(tag);
                                    info_music.setTags(tag);
                                    MusicTagsDB.SetTags(music, tag);
                                    tmm.CreateInfoTag();
                                }
                                else
                                {
                                    Toast.makeText(mContext, "Tag형식이 잘못 되었습니다.(#abc, #abcd)", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
            }
        });
/*
        //update 버튼을 누를 경우
        //1. tags에 들어 갈수 있는 값이 맞는지 검사 (이 함수는 InfoMusicClass에 정의)
        //2. MUSICTAGS Table Update
        //3. InfoMusicClass Update
        //3. 현재 UI refresh
        final int pos = position;
        //final InfoMusicClass dummyInfoMusic = getItem(position);
        //final String dummyMusic = viewHolder.tv_musicitem.getText().toString();
        //final String dummyTags = viewHolder.et_musicitem_tag.getText().toString()
        viewHolder.btn_confirm_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dummyMusic = viewHolder.tv_musicitem.getText().toString();
                String dummyTags = viewHolder.et_musicitem_tag.getText().toString();
                InfoMusicClass dummyInfoMusic = getItem(pos-1);
                MusicTagsDB.SetTags(dummyMusic, dummyTags);
                Log.d("setTags", "at Fragment Adapter");
                Log.d("why can not call ", "?");
                Log.d("getTags",MusicTagsDB.getTags(viewHolder.tv_musicitem.getText().toString()));
                //dummyInfoMusic.setTags(dummyMusic);
                dummyInfoMusic.setTags(dummyTags);

            }
        });*/
        return v;
    }

    // Adapter가 관리하는 Data List를 교체 한다.
    // 교체 후 Adapter.notifyDataSetChanged() 메서드로 변경 사실을
    // Adapter에 알려 주어 ListView에 적용 되도록 한다.
    public void setArrayList(ArrayList<InfoMusicClass> arrays)
    {
        this.m_infoMusicList = arrays;
    }

    public ArrayList<InfoMusicClass> getArrayList()
    {
        return m_infoMusicList;
    }


    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder{
        public TextView tv_musicitem = null;
        public Button btn_musicitem_tag = null;
        public InfoMusicClass info_music = null;

    }

    @Override
    protected void finalize() throws Throwable {
        free();
        super.finalize();
    }

    private void free(){
        inflater = null;
        m_infoMusicList = null;
        viewHolder = null;
        mContext = null;
    }
}
