package com.example.balladventure;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Sqlite.My_Sqlite;
import Sqlite.Sqlite_DB;

public class BeginActivity extends AppCompatActivity {
    private My_Sqlite sql;
    public static SQLiteDatabase db;
    private Button begin_button;
    private int maxlevel=17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        getSupportActionBar().hide();
        sql = new My_Sqlite(BeginActivity.this, "person.db", null, 1);
        db=sql.getWritableDatabase();
        Sqlite_DB.isInsert();
        if(Integer.parseInt(Sqlite_DB.find_Data(2))<maxlevel){
            Sqlite_DB.update_Data("maxlevel",maxlevel+"");
        }
        init();
        begin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(BeginActivity.this,SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        begin_button=(Button)findViewById(R.id.begin_button);
    }
}
