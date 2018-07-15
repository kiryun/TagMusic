
package com.example.hyun.tagmusic_sql.Middle.PlayFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.hyun.tagmusic_sql.Middle.InfoMusicClass;
import com.example.hyun.tagmusic_sql.Middle.InfoTagClass;
import com.example.hyun.tagmusic_sql.R;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

import java.util.ArrayList;


/**
 * Created by Hyun on 2017-12-10.
 */


public class PlayListViewBaseAdapter extends BaseAdapter{
    private LayoutInflater inflater = null;
    private PlayListViewBaseAdapter.ViewHolder viewHolder = null;
    private Context mContext = null;
    private ArrayList<InfoTagClass> m_infoTagList;
    private ArrayList<InfoMusicClass> m_infoPlayList;

    static private TotalMusicManager tmm;

    public PlayListViewBaseAdapter(Context context, TotalMusicManager tmm ,ArrayList<InfoMusicClass> m_infoPlayList)
    {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.tmm = tmm;
        this.m_infoTagList = tmm.getInfoTagList();
        this.m_infoPlayList = m_infoPlayList;
    }
    // Adapter가 관리할 Data의 개수를 설정 합니다.
    @Override
    public int getCount()
    {
        return m_infoPlayList.size();
    }

    // Adapter가 관리하는 Data의 Item 의 Position을 <객체> 형태로 얻어 옵니다.
    @Override
    public InfoMusicClass getItem(int position)
    {
        return m_infoPlayList.get(position);
    }

    // Adapter가 관리하는 Data의 Item 의 position 값의 ID 를 얻어 옵니다.
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    // ListView의 뿌려질 한줄의 Row를 설정 합니다.
    @Override
    public View getView(int position, View convertview, ViewGroup parent)
    {

        View v = convertview;

        if(v == null)
        {
            viewHolder = new PlayListViewBaseAdapter.ViewHolder();
            v = inflater.inflate(R.layout.listview_play_item, null);
            viewHolder.btn_playitem = (Button) v.findViewById(R.id.btn_playitem); //

            v.setTag(viewHolder);

        } else {
            viewHolder = (PlayListViewBaseAdapter.ViewHolder)v.getTag();
        }

        viewHolder.btn_playitem.setText(getItem(position).getMusic());
        //viewHolder.btn_playitem.setText(getItem(position).play_item);
        /*
        // image 나 button 등에 Tag를 사용해서 position 을 부여해 준다.
        // Tag란 View를 식별할 수 있게 바코드 처럼 Tag를 달아 주는 View의 기능
        // 이라고 생각 하시면 됩니다.
        viewHolder.iv_image.setTag(position);
        viewHolder.iv_image.setOnClickListener(buttonClickListener);

        viewHolder.btn_button.setTag(position);
        viewHolder.btn_button.setText(getItem(position).button);
        viewHolder.btn_button.setOnClickListener(buttonClickListener);

        viewHolder.cb_box.setTag(position);
        viewHolder.cb_box.setOnClickListener(buttonClickListener);*/



        return v;
    }

    // Adapter가 관리하는 Data List를 교체 한다.
    // 교체 후 Adapter.notifyDataSetChanged() 메서드로 변경 사실을
    // Adapter에 알려 주어 ListView에 적용 되도록 한다.
    public void setArrayList(ArrayList<InfoMusicClass> arrays)
    {
        this.m_infoPlayList = arrays;
    }

    public ArrayList<InfoMusicClass> getArrayList()
    {
        return m_infoPlayList;
    }


    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */

    class ViewHolder{
        public Button btn_playitem = null;
        public InfoMusicClass info_music = null;
    }

    @Override
    protected void finalize() throws Throwable {
        free();
        super.finalize();
    }

    private void free(){
        inflater = null;
        m_infoPlayList = null;
        viewHolder = null;
        mContext = null;
    }
}

