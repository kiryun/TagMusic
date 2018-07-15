
package com.example.hyun.tagmusic_sql.Middle.TagFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hyun.tagmusic_sql.Middle.InfoTagClass;
import com.example.hyun.tagmusic_sql.R;
import com.example.hyun.tagmusic_sql.TotalMusicManager;

import java.util.ArrayList;


/**
 * Created by Hyun on 2017-12-10.
 */


public class TagListViewBaseAdapter extends BaseAdapter{
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private Context mContext = null;
    private ArrayList<InfoTagClass> m_infoTagList;

    static private TotalMusicManager tmm;

    public TagListViewBaseAdapter(Context c, TotalMusicManager tmm)
    {
        this.mContext = c;
        this.inflater = LayoutInflater.from(c);
        this.tmm = tmm;
        this.m_infoTagList = tmm.getInfoTagList();
    }
    // Adapter가 관리할 Data의 개수를 설정 합니다.
    @Override
    public int getCount()
    {
        return m_infoTagList.size();
    }

    // Adapter가 관리하는 Data의 Item 의 Position을 <객체> 형태로 얻어 옵니다.
    @Override
    public InfoTagClass getItem(int position)
    {
        return m_infoTagList.get(position);
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
            viewHolder = new TagListViewBaseAdapter.ViewHolder();
            v = inflater.inflate(R.layout.listview_tag_item, null);
            // layout->listview_tag_item.xml -> tv_tagitem
            viewHolder.btn_tagitem = (TextView)v.findViewById(R.id.btn_tagitem);

            v.setTag(viewHolder);

        } else {
            viewHolder = (TagListViewBaseAdapter.ViewHolder)v.getTag();
        }
        viewHolder.btn_tagitem.setText(getItem(position).getTag());

        viewHolder.btn_tagitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoTagClass info_tag = getItem(position);
                LinearLayout layout = new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView tvMusics = new TextView(mContext);
                tvMusics.setText(info_tag.getStringMusics());
                //tvMusics.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                //tvMusics.setGravity(View.TEXT_ALIGNMENT_CENTER);
                layout.addView(tvMusics);

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

                dialog.setTitle("Music 목록")
                        .setView(layout)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
            }
        });

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
        viewHolder.cb_box.setOnClickListener(buttonClickListener);*//*
        */

        return v;
    }

    // Adapter가 관리하는 Data List를 교체 한다.
    // 교체 후 Adapter.notifyDataSetChanged() 메서드로 변경 사실을
    // Adapter에 알려 주어 ListView에 적용 되도록 한다.
    public void setArrayList(ArrayList<InfoTagClass> arrays)
    {
        this.m_infoTagList = arrays;
    }

    public ArrayList<InfoTagClass> getArrayList()
    {
        return m_infoTagList;
    }

/*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */

    class ViewHolder{
        public TextView btn_tagitem = null;

    }

    @Override
    protected void finalize() throws Throwable {
        free();
        super.finalize();
    }

    private void free(){
        inflater = null;
        m_infoTagList = null;
        viewHolder = null;
        mContext = null;
    }
}
