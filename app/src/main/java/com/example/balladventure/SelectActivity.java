package com.example.balladventure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Sqlite.Sqlite_DB;

public class SelectActivity extends AppCompatActivity {

    private GridView myselect_gridview;
    private List<myselectgridview_Item> myselectList;
    private myselectgridview_Adapter myselectgridview_adapter;
    private ImageView myactionbar_back;
    private int level,maxlevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getSupportActionBar().hide();
        myselect_gridview=(GridView)findViewById(R.id.myselect_gridview);
        myactionbar_back=(ImageView)findViewById(R.id.myactionbar_back);
        level= Integer.parseInt(Sqlite_DB.find_Data(1));
        maxlevel= Integer.parseInt(Sqlite_DB.find_Data(2));
        Log.i("value",level+" "+maxlevel);
        myselectList=new ArrayList<myselectgridview_Item>();
        for(int i=1;i<=level;i++){
            myselectgridview_Item myselectgridview_Item1=new myselectgridview_Item();
            myselectgridview_Item1.setIcon(R.mipmap.icon_finish);
            myselectgridview_Item1.setLevel("第"+i+"关");
            myselectList.add(myselectgridview_Item1);
        }
        for(int i=1;i<=maxlevel-level;i++){
            myselectgridview_Item myselectgridview_Item2=new myselectgridview_Item();
            myselectgridview_Item2.setIcon(R.mipmap.icon_locked);
            myselectgridview_Item2.setLevel("未解锁");
            myselectList.add(myselectgridview_Item2);
        }
        myselectgridview_adapter=new myselectgridview_Adapter(SelectActivity.this);
        myselectgridview_adapter.Fill_Data(myselectList);
        myselect_gridview.setAdapter(myselectgridview_adapter);
        myselect_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(level>=position+1){
                    Intent intent=new Intent();
                    intent.setClass(SelectActivity.this,MainActivity.class);
                    intent.putExtra("mylevel", position+1);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SelectActivity.this,"尚未解锁",Toast.LENGTH_SHORT).show();
                }
            }
        });
        myactionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SelectActivity.this,BeginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent();
            intent.setClass(SelectActivity.this,BeginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class  myselectgridview_Item{
        private String level;
        private int Icon;

        public int getIcon() {
            return Icon;
        }

        public void setIcon(int icon) {
            Icon = icon;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    private class myselectgridview_Adapter extends BaseAdapter{
        private List<myselectgridview_Item> list;
        private LayoutInflater inflater;
        private Context context;

        public myselectgridview_Adapter(Context context){
            this.context=context;
        }

        public void Fill_Data(List<myselectgridview_Item> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(context);
            View view = null;
            ViewHolder holder = null;
            if (convertView == null || convertView.getTag() == null) {
                view = inflater.inflate(R.layout.gridview_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) convertView.getTag();
            }
            myselectgridview_Item gridview_Item = (myselectgridview_Item)getItem(position);
            holder.text1.setText(gridview_Item.getLevel());
            holder.imageView.setImageResource(gridview_Item.getIcon());
            return view;
        }
    }

    class ViewHolder {
        private TextView text1;
        private ImageView imageView;

        public ViewHolder(View view) {
            this.imageView = (ImageView) view.findViewById(R.id.myselectgridview_image);
            this.text1 = (TextView) view.findViewById(R.id.myselectgridview_title);
        }
    }

}
