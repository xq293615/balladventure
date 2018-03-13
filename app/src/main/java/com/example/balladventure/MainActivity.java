package com.example.balladventure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import Sqlite.Sqlite_DB;
import UI.GameView;

public class MainActivity extends AppCompatActivity implements GameView.callBackActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int level=bundle.getInt("mylevel");
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        gameView = new GameView(this, level, Integer.parseInt(Sqlite_DB.find_Data(2)),width,height);
        gameView.setCallBackActivityListener(this);
        setContentView(gameView);
        getSupportActionBar().hide();
    }

    @Override
    public void setFinish() {
        gameView.setRunning(false);
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,SelectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setFinish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
