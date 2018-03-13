package UI;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.balladventure.R;

import java.util.ArrayList;
import java.util.List;

import Common.Ball;
import Common.ChangeBitmapSize;
import Common.FinalPoint;
import Common.Wall;
import Sqlite.Sqlite_DB;

/**
 * Created by 琪 on 2017/2/7.
 */
public class GameView extends View implements View.OnTouchListener {
    private Context context;
    private Paint mPaint, mWordPaint;
    private float mHeight, mWidth, allWidth, allHeight, boxWidth, boxHeight;
    private int level,maxlevel;
    private Bitmap myBallIcon, blueWallIcon, redWallIcon, throughWallIcon,gamebgIcon;
    private Bitmap browseWallIcon,arrow_left,arrow_up,arrow_below,arrow_right;
    private Bitmap final_below, final_left, final_right, final_up;
    private boolean isMoving = true;
    private boolean havingDialog = false;
    private boolean isMinus = true;
    private boolean isRunning=true;
    private List<Wall> walls;
    private List<FinalPoint> finalPoints;
    private Ball ball;
    private float touchX1 = 0, touchX2 = 0, touchY1 = 0, touchY2 = 0;
    private long limitTime;
    private callBackActivity callActivity = null;

    public GameView(Context context, int level,int maxlevel, int width, int height) {
        super(context);
        this.context = context;
        this.level = level;
        this.maxlevel=maxlevel;
        allWidth = width;
        allHeight = height;
        mWidth = allWidth;
        mHeight = allWidth * 3 / 2;
        boxWidth = mWidth / 20;
        boxHeight = mHeight / 30;
        initPaint();
        initBitmap();
        initFinalPoint();
        initBall();
        initWalls();
        calculateTime();
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGame(canvas);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        //mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setStrokeWidth(4);
        //mPaint.setStyle(Paint.Style.STROKE);
        mWordPaint = new Paint();
        mWordPaint.setStrokeWidth(3);
        mWordPaint.setTextSize(55);
        mWordPaint.setColor(Color.WHITE);
    }

    private void initBitmap() {
        myBallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        myBallIcon = new ChangeBitmapSize().getBitmap(myBallIcon, (int) boxWidth, (int) boxHeight);
        blueWallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluewall);
        blueWallIcon = new ChangeBitmapSize().getBitmap(blueWallIcon, (int) boxWidth, (int) boxHeight);
        redWallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.redwall);
        redWallIcon = new ChangeBitmapSize().getBitmap(redWallIcon, (int) boxWidth, (int) boxHeight);
        final_below = BitmapFactory.decodeResource(context.getResources(), R.drawable.final_below);
        final_below = new ChangeBitmapSize().getBitmap(final_below, (int) boxWidth, (int) boxHeight);
        final_left = BitmapFactory.decodeResource(context.getResources(), R.drawable.final_left);
        final_left = new ChangeBitmapSize().getBitmap(final_left, (int) boxWidth, (int) boxHeight);
        final_right = BitmapFactory.decodeResource(context.getResources(), R.drawable.final_right);
        final_right = new ChangeBitmapSize().getBitmap(final_right, (int) boxWidth, (int) boxHeight);
        final_up = BitmapFactory.decodeResource(context.getResources(), R.drawable.final_up);
        final_up = new ChangeBitmapSize().getBitmap(final_up, (int) boxWidth, (int) boxHeight);
        throughWallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.thrwall);
        throughWallIcon = new ChangeBitmapSize().getBitmap(throughWallIcon, (int) boxWidth, (int) boxHeight);
        gamebgIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.gamebg);
        gamebgIcon = new ChangeBitmapSize().getBitmap(gamebgIcon, (int) allWidth, (int) allHeight);
        browseWallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.brownwall);
        browseWallIcon = new ChangeBitmapSize().getBitmap(browseWallIcon, (int) boxWidth, (int) boxHeight);
        arrow_below = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_below);
        arrow_below = new ChangeBitmapSize().getBitmap(arrow_below, (int) boxWidth, (int) boxHeight);
        arrow_up = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_up);
        arrow_up = new ChangeBitmapSize().getBitmap(arrow_up, (int) boxWidth, (int) boxHeight);
        arrow_left = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_left);
        arrow_left = new ChangeBitmapSize().getBitmap(arrow_left, (int) boxWidth, (int) boxHeight);
        arrow_right = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        arrow_right = new ChangeBitmapSize().getBitmap(arrow_right, (int) boxWidth, (int) boxHeight);
    }

    private void initWalls() {
        walls = new ArrayList<Wall>();
        if (level == 1) {
            Wall wall1 = new Wall((int) (7 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall2 = new Wall((int) (8 * boxHeight), (int) (15 * boxHeight), 1);
            Wall wall3 = new Wall((int) (9 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall4 = new Wall((int) (7 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall5 = new Wall((int) (7 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall6 = new Wall((int) (13 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall7 = new Wall((int) (14 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall8 = new Wall((int) (15 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall9 = new Wall((int) (15 * boxWidth), (int) (14 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
        } else if (level == 2) {
            Wall wall1 = new Wall((int) (4 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall2 = new Wall((int) (5 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall3 = new Wall((int) (6 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall4 = new Wall((int) (16 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall5 = new Wall((int) (6 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall6 = new Wall((int) (7 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall7 = new Wall((int) (12 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall8 = new Wall((int) (6 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall9 = new Wall((int) (12 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall10 = new Wall((int) (6 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall11 = new Wall((int) (11 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall12 = new Wall((int) (12 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall13 = new Wall((int) (17 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall14 = new Wall((int) (7 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall15 = new Wall((int) (11 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall16 = new Wall((int) (6 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall17 = new Wall((int) (6 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall18 = new Wall((int) (16 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall19 = new Wall((int) (3 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall20 = new Wall((int) (4 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall21 = new Wall((int) (16 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall22 = new Wall((int) (16 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall23 = new Wall((int) (3 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall24 = new Wall((int) (10 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall25 = new Wall((int) (10 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall26 = new Wall((int) (13 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall27 = new Wall((int) (3 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall28 = new Wall((int) (13 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall29 = new Wall((int) (3 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall30 = new Wall((int) (6 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall31 = new Wall((int) (7 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall32 = new Wall((int) (8 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall33 = new Wall((int) (9 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall34 = new Wall((int) (10 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall35 = new Wall((int) (13 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall36 = new Wall((int) (14 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall37 = new Wall((int) (8 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall38 = new Wall((int) (11 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall39 = new Wall((int) (12 * boxWidth), (int) (23 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
        } else if (level == 3) {
            Wall wall1 = new Wall((int) (10 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall2 = new Wall((int) (12 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall3 = new Wall((int) (5 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall4 = new Wall((int) (14 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall5 = new Wall((int) (15 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall6 = new Wall((int) (16 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall7 = new Wall((int) (7 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall8 = new Wall((int) (8 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall9 = new Wall((int) (9 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall10 = new Wall((int) (7 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall11 = new Wall((int) (3 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall12 = new Wall((int) (3 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall13 = new Wall((int) (13 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall14 = new Wall((int) (4 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall15 = new Wall((int) (6 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall16 = new Wall((int) (13 * boxWidth), (int) (11 * boxHeight), 2);
            wall16.setMark(1);
            Wall wall17 = new Wall((int) (14 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall18 = new Wall((int) (3 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall19 = new Wall((int) (4 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall20 = new Wall((int) (10 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall21 = new Wall((int) (11 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall22 = new Wall((int) (10 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall23 = new Wall((int) (7 * boxWidth), (int) (18 * boxHeight), 2);
            wall23.setMark(2);
            Wall wall24 = new Wall((int) (11 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall25 = new Wall((int) (12 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall26 = new Wall((int) (8 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall27 = new Wall((int) (8 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall28 = new Wall((int) (9 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall29 = new Wall((int) (10 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall30 = new Wall((int) (16 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall31 = new Wall((int) (13 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall32 = new Wall((int) (13 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall33 = new Wall((int) (13 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall34 = new Wall((int) (15 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall35 = new Wall((int) (8 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall36 = new Wall((int) (11 * boxWidth), (int) (27 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
        }else if(level==4){
            Wall wall1 = new Wall((int) (1 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall2 = new Wall((int) (3 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall3 = new Wall((int) (4 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall4 = new Wall((int) (6 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall5 = new Wall((int) (7 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall6 = new Wall((int) (8 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall7 = new Wall((int) (10 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall8 = new Wall((int) (11 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall9 = new Wall((int) (13 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall10 = new Wall((int) (15 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall11 = new Wall((int) (16 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall12 = new Wall((int) (17 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall13 = new Wall((int) (18 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall14 = new Wall((int) (1 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall15 = new Wall((int) (3 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall16 = new Wall((int) (4 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall17 = new Wall((int) (6 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall18 = new Wall((int) (7 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall19 = new Wall((int) (8 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall20 = new Wall((int) (10 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall21 = new Wall((int) (11 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall22 = new Wall((int) (13 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall23 = new Wall((int) (15 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall24 = new Wall((int) (16 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall25 = new Wall((int) (17 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall26 = new Wall((int) (18 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall27 = new Wall((int) (10 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall28 = new Wall((int) (11 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall29 = new Wall((int) (1 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall30 = new Wall((int) (3 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall31 = new Wall((int) (4 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall32 = new Wall((int) (13 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall33 = new Wall((int) (15 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall34 = new Wall((int) (18 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall35 = new Wall((int) (1 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall36 = new Wall((int) (1 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall37 = new Wall((int) (3 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall38 = new Wall((int) (4 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall39 = new Wall((int) (7 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall40 = new Wall((int) (8 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall41 = new Wall((int) (11 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall42 = new Wall((int) (15 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall43 = new Wall((int) (18 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall44 = new Wall((int) (18 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall45 = new Wall((int) (15 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall46 = new Wall((int) (18 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall47 = new Wall((int) (1 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall48 = new Wall((int) (2 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall49 = new Wall((int) (3 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall50 = new Wall((int) (4* boxWidth), (int) (10 * boxHeight), 1);
            Wall wall51 = new Wall((int) (7 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall52 = new Wall((int) (11 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall53 = new Wall((int) (15 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall54 = new Wall((int) (18 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall55 = new Wall((int) (7 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall56 = new Wall((int) (11 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall57 = new Wall((int) (15 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall58 = new Wall((int) (18 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall59 = new Wall((int) (1 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall60 = new Wall((int) (4 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall61 = new Wall((int) (3 * boxWidth), (int) (14 * boxHeight), 2);
            wall61.setMark(1);
            Wall wall62 = new Wall((int) (9 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall63 = new Wall((int) (17 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall64 = new Wall((int) (18 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall65 = new Wall((int) (1 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall66 = new Wall((int) (4* boxWidth), (int) (16 * boxHeight), 1);
            Wall wall67 = new Wall((int) (11 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall68 = new Wall((int) (15 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall69 = new Wall((int) (17 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall70 = new Wall((int) (18 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall71 = new Wall((int) (17 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall72 = new Wall((int) (18 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall73 = new Wall((int) (1 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall74 = new Wall((int) (4 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall75 = new Wall((int) (10 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall76 = new Wall((int) (11* boxWidth), (int) (18 * boxHeight), 1);
            Wall wall77 = new Wall((int) (15 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall78 = new Wall((int) (16 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall79 = new Wall((int) (17 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall80 = new Wall((int) (18 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall131 = new Wall((int) (1 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall132 = new Wall((int) (4* boxWidth), (int) (19 * boxHeight), 1);
            Wall wall81 = new Wall((int) (10 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall82 = new Wall((int) (11* boxWidth), (int) (19 * boxHeight), 1);
            Wall wall83 = new Wall((int) (15 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall84 = new Wall((int) (16 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall85 = new Wall((int) (17 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall86 = new Wall((int) (18 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall87 = new Wall((int) (1 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall88 = new Wall((int) (4 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall89 = new Wall((int) (13 * boxWidth), (int) (20 * boxHeight), 2);
            wall89.setMark(2);
            Wall wall90 = new Wall((int) (1 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall91 = new Wall((int) (3 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall92 = new Wall((int) (4 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall93 = new Wall((int) (5 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall94 = new Wall((int) (18 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall95 = new Wall((int) (1 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall96 = new Wall((int) (3* boxWidth), (int) (22 * boxHeight), 1);
            Wall wall97 = new Wall((int) (4 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall98 = new Wall((int) (5 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall99 = new Wall((int) (8 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall100 = new Wall((int) (10 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall101 = new Wall((int) (11 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall102 = new Wall((int) (15* boxWidth), (int) (22 * boxHeight), 1);
            Wall wall103 = new Wall((int) (16 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall104 = new Wall((int) (18 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall105 = new Wall((int) (1 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall106 = new Wall((int) (3 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall107 = new Wall((int) (4 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall108 = new Wall((int) (5 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall109 = new Wall((int) (8 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall110 = new Wall((int) (8 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall111 = new Wall((int) (10 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall112 = new Wall((int) (11 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall113 = new Wall((int) (12 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall114 = new Wall((int) (15 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall115 = new Wall((int) (16 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall116 = new Wall((int) (18* boxWidth), (int) (24 * boxHeight), 1);
            Wall wall117 = new Wall((int) (1 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall118 = new Wall((int) (2 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall119 = new Wall((int) (1 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall120 = new Wall((int) (2 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall121 = new Wall((int) (4 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall122 = new Wall((int) (5* boxWidth), (int) (26 * boxHeight), 1);
            Wall wall123 = new Wall((int) (6 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall124 = new Wall((int) (8 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall125 = new Wall((int) (10 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall126 = new Wall((int) (11 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall127 = new Wall((int) (12 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall128 = new Wall((int) (15 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall129 = new Wall((int) (16 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall130 = new Wall((int) (18 * boxWidth), (int) (26 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
            walls.add(wall56);
            walls.add(wall57);
            walls.add(wall58);
            walls.add(wall59);
            walls.add(wall60);
            walls.add(wall61);
            walls.add(wall62);
            walls.add(wall63);
            walls.add(wall64);
            walls.add(wall65);
            walls.add(wall66);
            walls.add(wall67);
            walls.add(wall68);
            walls.add(wall69);
            walls.add(wall70);
            walls.add(wall71);
            walls.add(wall72);
            walls.add(wall73);
            walls.add(wall74);
            walls.add(wall75);
            walls.add(wall76);
            walls.add(wall77);
            walls.add(wall78);
            walls.add(wall79);
            walls.add(wall80);
            walls.add(wall81);
            walls.add(wall82);
            walls.add(wall83);
            walls.add(wall84);
            walls.add(wall85);
            walls.add(wall86);
            walls.add(wall87);
            walls.add(wall88);
            walls.add(wall89);
            walls.add(wall90);
            walls.add(wall91);
            walls.add(wall92);
            walls.add(wall93);
            walls.add(wall94);
            walls.add(wall95);
            walls.add(wall96);
            walls.add(wall97);
            walls.add(wall98);
            walls.add(wall99);
            walls.add(wall100);
            walls.add(wall101);
            walls.add(wall102);
            walls.add(wall103);
            walls.add(wall104);
            walls.add(wall105);
            walls.add(wall106);
            walls.add(wall107);
            walls.add(wall108);
            walls.add(wall109);
            walls.add(wall110);
            walls.add(wall111);
            walls.add(wall112);
            walls.add(wall113);
            walls.add(wall114);
            walls.add(wall115);
            walls.add(wall116);
            walls.add(wall117);
            walls.add(wall118);
            walls.add(wall119);
            walls.add(wall120);
            walls.add(wall121);
            walls.add(wall122);
            walls.add(wall123);
            walls.add(wall124);
            walls.add(wall125);
            walls.add(wall126);
            walls.add(wall127);
            walls.add(wall128);
            walls.add(wall129);
            walls.add(wall130);
            walls.add(wall131);
            walls.add(wall132);
        }else if(level==5){
            Wall wall1 = new Wall((int) (8 * boxWidth), (int) (13 * boxHeight), 3);
            wall1.setDirection(4);
            Wall wall2 = new Wall((int) (8 * boxWidth), (int) (17 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
        }else if(level==6){
            Wall wall1 = new Wall((int) (3 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall2 = new Wall((int) (15 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall3 = new Wall((int) (6 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall4 = new Wall((int) (7 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall5 = new Wall((int) (8 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall6 = new Wall((int) (9 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall7 = new Wall((int) (10 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall8 = new Wall((int) (11 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall9 = new Wall((int) (2 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall10 = new Wall((int) (7 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall11 = new Wall((int) (2 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall12 = new Wall((int) (7 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall13 = new Wall((int) (7 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall14 = new Wall((int) (7 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall15 = new Wall((int) (14 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall16 = new Wall((int) (4 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall17 = new Wall((int) (5 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall18 = new Wall((int) (14* boxWidth), (int) (9 * boxHeight), 3);
            wall18.setDirection(3);
            Wall wall19 = new Wall((int) (16 * boxWidth), (int) (9 * boxHeight), 3);
            wall19.setDirection(1);
            Wall wall20 = new Wall((int) (15 * boxWidth), (int) (10 * boxHeight), 3);
            wall20.setDirection(4);
            Wall wall21 = new Wall((int) (5 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall22 = new Wall((int) (5 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall23 = new Wall((int) (11 * boxWidth), (int) (12 * boxHeight), 3);
            wall23.setDirection(2);
            Wall wall24 = new Wall((int) (2 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall25 = new Wall((int) (10 * boxWidth), (int) (13 * boxHeight), 3);
            wall25.setDirection(1);
            Wall wall26 = new Wall((int) (12 * boxWidth), (int) (13 * boxHeight), 3);
            wall26.setDirection(3);
            Wall wall27 = new Wall((int) (16 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall28 = new Wall((int) (17 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall29 = new Wall((int) (11 * boxWidth), (int) (14 * boxHeight), 3);
            wall29.setDirection(4);
            Wall wall30 = new Wall((int) (17 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall31 = new Wall((int) (3 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall32 = new Wall((int) (2 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall33 = new Wall((int) (4 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall34 = new Wall((int) (9 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall35 = new Wall((int) (10 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall36 = new Wall((int) (6 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall37 = new Wall((int) (16 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall38 = new Wall((int) (6 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall39 = new Wall((int) (10 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall40 = new Wall((int) (11 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall41 = new Wall((int) (12 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall42 = new Wall((int) (3 * boxWidth), (int) (21 * boxHeight), 3);
            wall42.setDirection(4);
            Wall wall43 = new Wall((int) (10 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall44 = new Wall((int) (2 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall45 = new Wall((int) (17 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall46 = new Wall((int) (18 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall47 = new Wall((int) (2 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall48 = new Wall((int) (5 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall49 = new Wall((int) (3 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall50 = new Wall((int) (4* boxWidth), (int) (26 * boxHeight), 1);
            Wall wall51 = new Wall((int) (15 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall52 = new Wall((int) (7 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall53 = new Wall((int) (8 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall54 = new Wall((int) (9 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall55 = new Wall((int) (10 * boxWidth), (int) (28 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
        }else if(level==7){
            Wall wall1 = new Wall((int) (3 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall2 = new Wall((int) (7 * boxWidth), (int) (2 * boxHeight), 3);
            wall2.setDirection(4);
            Wall wall3 = new Wall((int) (3 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall4 = new Wall((int) (14 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall5 = new Wall((int) (3 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall6 = new Wall((int) (7 * boxWidth), (int) (4 * boxHeight), 3);
            wall6.setDirection(2);
            Wall wall7 = new Wall((int) (6 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall8 = new Wall((int) (2 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall9 = new Wall((int) (8 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall10 = new Wall((int) (6 * boxWidth), (int) (7 * boxHeight), 2);
            wall10.setMark(1);
            Wall wall11 = new Wall((int) (7 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall12 = new Wall((int) (11 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall13 = new Wall((int) (12 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall14 = new Wall((int) (16 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall15 = new Wall((int) (3 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall16 = new Wall((int) (4 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall17 = new Wall((int) (16 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall18 = new Wall((int) (13 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall19 = new Wall((int) (16 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall20 = new Wall((int) (6 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall21 = new Wall((int) (9 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall22 = new Wall((int) (17 * boxWidth), (int) (14 * boxHeight), 2);
            wall22.setMark(2);
            Wall wall23 = new Wall((int) (7 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall24 = new Wall((int) (8 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall25 = new Wall((int) (9 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall26 = new Wall((int) (10 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall27 = new Wall((int) (5 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall28 = new Wall((int) (9* boxWidth), (int) (21 * boxHeight), 1);
            Wall wall29 = new Wall((int) (2 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall30 = new Wall((int) (18 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall31 = new Wall((int) (4 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall32 = new Wall((int) (4 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall33 = new Wall((int) (4 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall34 = new Wall((int) (8 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall35 = new Wall((int) (17 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall36 = new Wall((int) (3 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall37 = new Wall((int) (4 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall38 = new Wall((int) (12 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall39 = new Wall((int) (17 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall40 = new Wall((int) (9 * boxWidth), (int) (28 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
        }else if(level==8){
            Wall wall1 = new Wall((int) (13 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall2 = new Wall((int) (5 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall3 = new Wall((int) (17 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall4 = new Wall((int) (16 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall5 = new Wall((int) (11 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall6 = new Wall((int) (18 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall7 = new Wall((int) (8 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall8 = new Wall((int) (11 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall9 = new Wall((int) (14 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall10 = new Wall((int) (4 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall11 = new Wall((int) (9 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall12 = new Wall((int) (15 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall13 = new Wall((int) (3 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall14 = new Wall((int) (17 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall15 = new Wall((int) (14 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall16 = new Wall((int) (17 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall17 = new Wall((int) (5 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall18 = new Wall((int) (2 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall19 = new Wall((int) (9 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall20 = new Wall((int) (13 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall21 = new Wall((int) (16 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall22 = new Wall((int) (15 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall23 = new Wall((int) (2 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall24 = new Wall((int) (4 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall25 = new Wall((int) (18 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall26 = new Wall((int) (2 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall27 = new Wall((int) (2 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall28 = new Wall((int) (6 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall29 = new Wall((int) (8 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall30 = new Wall((int) (5 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall31 = new Wall((int) (9 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall32 = new Wall((int) (12 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall33 = new Wall((int) (4* boxWidth), (int) (18 * boxHeight), 1);
            Wall wall34 = new Wall((int) (17 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall35 = new Wall((int) (10 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall36 = new Wall((int) (13 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall37 = new Wall((int) (4 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall38 = new Wall((int) (10 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall39 = new Wall((int) (12 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall40 = new Wall((int) (6 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall41 = new Wall((int) (7 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall42 = new Wall((int) (15 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall43 = new Wall((int) (5 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall44 = new Wall((int) (11 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall45 = new Wall((int) (16 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall46 = new Wall((int) (17 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall47 = new Wall((int) (8 * boxWidth), (int) (28 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
        }else if(level==9){
            Wall wall1 = new Wall((int) (15 * boxWidth), (int) (7 * boxHeight), 2);
            wall1.setMark(1);
            Wall wall2 = new Wall((int) (13 * boxWidth), (int) (28 * boxHeight), 2);
            wall2.setMark(2);
            walls.add(wall1);
            walls.add(wall2);
        }else if(level==10){
            Wall wall1 = new Wall((int) (5 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall2 = new Wall((int) (7 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall3 = new Wall((int) (13 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall4 = new Wall((int) (10 * boxWidth), (int) (7 * boxHeight), 2);
            wall4.setMark(1);
            Wall wall5 = new Wall((int) (9 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall6 = new Wall((int) (12 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall7 = new Wall((int) (11 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall8 = new Wall((int) (10 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall9 = new Wall((int) (12 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall10 = new Wall((int) (13 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall11 = new Wall((int) (6 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall12 = new Wall((int) (12 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall13 = new Wall((int) (16 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall14 = new Wall((int) (7 * boxWidth), (int) (14 * boxHeight), 3);
            wall14.setDirection(4);
            Wall wall15 = new Wall((int) (8 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall16 = new Wall((int) (12 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall17 = new Wall((int) (4 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall18 = new Wall((int) (14 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall19 = new Wall((int) (10 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall20 = new Wall((int) (8 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall21 = new Wall((int) (2 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall22 = new Wall((int) (18 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall23 = new Wall((int) (11 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall24 = new Wall((int) (5* boxWidth), (int) (21 * boxHeight), 1);
            Wall wall25 = new Wall((int) (10 * boxWidth), (int) (21 * boxHeight), 2);
            wall25.setMark(2);
            Wall wall26 = new Wall((int) (3 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall27 = new Wall((int) (15 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall28 = new Wall((int) (6 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall29 = new Wall((int) (7 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall30 = new Wall((int) (7 * boxWidth), (int) (27 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
        }else if(level==11){
            Wall wall1 = new Wall((int) (2 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall2 = new Wall((int) (12 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall3 = new Wall((int) (15 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall4 = new Wall((int) (7 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall5 = new Wall((int) (13 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall6 = new Wall((int) (1 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall7 = new Wall((int) (13 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall8 = new Wall((int) (2 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall9 = new Wall((int) (3 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall10 = new Wall((int) (15 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall11 = new Wall((int) (16 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall12 = new Wall((int) (17 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall13 = new Wall((int) (18 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall14 = new Wall((int) (2 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall15 = new Wall((int) (3 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall16 = new Wall((int) (4 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall17 = new Wall((int) (5 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall18 = new Wall((int) (6 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall19 = new Wall((int) (18 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall20 = new Wall((int) (3 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall21 = new Wall((int) (15 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall22 = new Wall((int) (3 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall23 = new Wall((int) (9 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall24 = new Wall((int) (11 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall25 = new Wall((int) (3 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall26 = new Wall((int) (2 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall27 = new Wall((int) (8 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall28 = new Wall((int) (17 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall29 = new Wall((int) (18 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall30 = new Wall((int) (5 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall31 = new Wall((int) (10 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall32 = new Wall((int) (11 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall33 = new Wall((int) (12*boxWidth), (int) (15 * boxHeight), 1);
            Wall wall34 = new Wall((int) (12 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall35 = new Wall((int) (13 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall36 = new Wall((int) (10 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall37 = new Wall((int) (12 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall38 = new Wall((int) (17 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall39 = new Wall((int) (4 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall40 = new Wall((int) (10 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall41 = new Wall((int) (17 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall42 = new Wall((int) (4 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall43 = new Wall((int) (17 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall44 = new Wall((int) (9 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall45 = new Wall((int) (9 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall46 = new Wall((int) (7 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall47 = new Wall((int) (8 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall48 = new Wall((int) (18 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall49 = new Wall((int) (3 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall50 = new Wall((int) (15 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall51 = new Wall((int) (16 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall52 = new Wall((int) (6 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall53 = new Wall((int) (7 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall54 = new Wall((int) (8 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall55 = new Wall((int) (6*boxWidth), (int) (25 * boxHeight), 1);
            Wall wall56 = new Wall((int) (1 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall57 = new Wall((int) (10 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall58 = new Wall((int) (14 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall59 = new Wall((int) (1 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall60 = new Wall((int) (13 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall61 = new Wall((int) (14 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall62 = new Wall((int) (1 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall63 = new Wall((int) (2 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall64 = new Wall((int) (13 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall65 = new Wall((int) (1 * boxWidth), (int) (29 * boxHeight), 1);
            Wall wall66 = new Wall((int) (17 * boxWidth), (int) (29 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
            walls.add(wall56);
            walls.add(wall57);
            walls.add(wall58);
            walls.add(wall59);
            walls.add(wall60);
            walls.add(wall61);
            walls.add(wall62);
            walls.add(wall63);
            walls.add(wall64);
            walls.add(wall65);
            walls.add(wall66);
        }else if(level==12){
            Wall wall1 = new Wall((int) (2 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall2 = new Wall((int) ( 4* boxWidth), (int) (0 * boxHeight), 1);
            Wall wall3 = new Wall((int) (2 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall4 = new Wall((int) (17 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall5 = new Wall((int) (11 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall6 = new Wall((int) (7 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall7 = new Wall((int) (3 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall8 = new Wall((int) (16 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall9 = new Wall((int) (16 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall10 = new Wall((int) (16 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall11 = new Wall((int) (15 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall12 = new Wall((int) (6 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall13 = new Wall((int) (17 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall14 = new Wall((int) (1 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall15 = new Wall((int) (9 * boxWidth), (int) (9 * boxHeight), 3);
            wall15.setDirection(3);
            Wall wall16 = new Wall((int) (13 * boxWidth), (int) (9 * boxHeight), 2);
            wall16.setMark(1);
            Wall wall17 = new Wall((int) (16 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall18 = new Wall((int) (13 * boxWidth), (int) (11 * boxHeight), 2);
            wall18.setMark(2);
            Wall wall19 = new Wall((int) (17 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall20 = new Wall((int) (6* boxWidth), (int) (12 * boxHeight), 3);
            wall20.setDirection(2);
            Wall wall21 = new Wall((int) (17 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall51 = new Wall((int) (1 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall22 = new Wall((int) (7 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall23 = new Wall((int) (1* boxWidth), (int) (15 * boxHeight), 1);
            Wall wall24 = new Wall((int) (2 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall25 = new Wall((int) (13 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall26 = new Wall((int) (5 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall27 = new Wall((int) (1 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall28 = new Wall((int) (2 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall29 = new Wall((int) (12 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall30 = new Wall((int) (7 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall31 = new Wall((int) (17 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall32 = new Wall((int) (17 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall33 = new Wall((int) (4*boxWidth), (int) (20 * boxHeight), 1);
            Wall wall34 = new Wall((int) (14 * boxWidth), (int) (20* boxHeight), 1);
            Wall wall35 = new Wall((int) (4 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall36 = new Wall((int) (9 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall37 = new Wall((int) (4 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall38 = new Wall((int) (15 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall39 = new Wall((int) (4 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall40 = new Wall((int) (5 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall41 = new Wall((int) (6 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall42 = new Wall((int) (7 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall43 = new Wall((int) (8 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall44 = new Wall((int) (15 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall45 = new Wall((int) (7 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall46 = new Wall((int) (7 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall47 = new Wall((int) (10 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall48 = new Wall((int) (11 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall49 = new Wall((int) (13 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall50 = new Wall((int) (13 * boxWidth), (int) (27 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
        }else if(level==13){
            Wall wall1 = new Wall((int) (5 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall2 = new Wall((int) (1 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall3 = new Wall((int) (16 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall4 = new Wall((int) (12 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall5 = new Wall((int) (10 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall6 = new Wall((int) (1 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall7 = new Wall((int) (14 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall8 = new Wall((int) (7 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall9 = new Wall((int) (17 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall10 = new Wall((int) (4 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall11 = new Wall((int) (11 * boxWidth), (int) (6 * boxHeight), 2);
            wall11.setMark(1);
            Wall wall12 = new Wall((int) (6 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall13 = new Wall((int) (13 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall14 = new Wall((int) (5 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall15 = new Wall((int) (7 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall16 = new Wall((int) (15 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall17 = new Wall((int) (4* boxWidth), (int) (15 * boxHeight), 1);
            Wall wall18 = new Wall((int) (14 * boxWidth), (int) (17 * boxHeight), 2);
            wall18.setMark(2);
            Wall wall19 = new Wall((int) (16 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall20 = new Wall((int) (3 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall21 = new Wall((int) (12 * boxWidth), (int) (21* boxHeight), 1);
            Wall wall22 = new Wall((int) (5 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall23 = new Wall((int) (3 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall24 = new Wall((int) (8 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall25 = new Wall((int) (9 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall26 = new Wall((int) (6 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall27 = new Wall((int) (13 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall28 = new Wall((int) (8 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall29 = new Wall((int) (8 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall30 = new Wall((int) (10 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall31 = new Wall((int) (14 * boxWidth), (int) (26 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
        }else if(level==14){
            Wall wall1 = new Wall((int) (11 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall2 = new Wall((int) ( 14* boxWidth), (int) (0 * boxHeight), 1);
            Wall wall3 = new Wall((int) (15 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall4 = new Wall((int) (2 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall5 = new Wall((int) (3 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall6 = new Wall((int) (4 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall7 = new Wall((int) (14 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall8 = new Wall((int) (4 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall9 = new Wall((int) (8 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall10 = new Wall((int) (16 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall11 = new Wall((int) (2 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall12 = new Wall((int) (13 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall13 = new Wall((int) (5 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall14 = new Wall((int) (6 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall15 = new Wall((int) (9 * boxWidth), (int) (5 * boxHeight), 3);
            wall15.setDirection(2);
            Wall wall16 = new Wall((int) (1* boxWidth), (int) (6 * boxHeight), 1);
            Wall wall17 = new Wall((int) (9 * boxWidth), (int) (6 * boxHeight), 3);
            wall17.setDirection(2);
            Wall wall18 = new Wall((int) (12 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall19 = new Wall((int) (1 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall20 = new Wall((int) (7* boxWidth), (int) (7 * boxHeight), 1);
            Wall wall21 = new Wall((int) (9 * boxWidth), (int) (7 * boxHeight), 3);
            wall21.setDirection(2);
            Wall wall22 = new Wall((int) (9 * boxWidth), (int) (8 * boxHeight), 3);
            wall22.setDirection(2);
            Wall wall23 = new Wall((int) (10* boxWidth), (int) (8 * boxHeight), 1);
            Wall wall24 = new Wall((int) (4* boxWidth), (int) (9 * boxHeight), 1);
            Wall wall25 = new Wall((int) (9 * boxWidth), (int) (9 * boxHeight), 3);
            wall25.setDirection(2);
            Wall wall26 = new Wall((int) (14 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall27 = new Wall((int) (17 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall28 = new Wall((int) (14 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall29 = new Wall((int) (8 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall30 = new Wall((int) (11 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall31 = new Wall((int) (1 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall32 = new Wall((int) (13 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall33 = new Wall((int) (5*boxWidth), (int) (15 * boxHeight), 1);
            Wall wall34 = new Wall((int) (17 * boxWidth), (int) (15* boxHeight), 1);
            Wall wall35 = new Wall((int) (2 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall36 = new Wall((int) (10 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall37 = new Wall((int) (12 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall38 = new Wall((int) (1 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall39 = new Wall((int) (12 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall40 = new Wall((int) (6 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall41 = new Wall((int) (17 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall42 = new Wall((int) (11 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall43 = new Wall((int) (7* boxWidth), (int) (20 * boxHeight), 1);
            Wall wall44 = new Wall((int) (13 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall45 = new Wall((int) (1 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall46 = new Wall((int) (10 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall47 = new Wall((int) (12 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall48 = new Wall((int) (17 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall49 = new Wall((int) (8 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall50 = new Wall((int) (15 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall51 = new Wall((int) (16 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall52 = new Wall((int) (1*boxWidth), (int) (23 * boxHeight), 1);
            Wall wall53 = new Wall((int) (3 * boxWidth), (int) (25* boxHeight), 1);
            Wall wall54 = new Wall((int) (4 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall55 = new Wall((int) (13 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall56 = new Wall((int) (14 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall57 = new Wall((int) (9* boxWidth), (int) (26 * boxHeight), 1);
            Wall wall58 = new Wall((int) (2 * boxWidth), (int) (27 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
            walls.add(wall56);
            walls.add(wall57);
            walls.add(wall58);
        }else if(level==15){
            Wall wall1 = new Wall((int) (11 * boxWidth), (int) (3 * boxHeight), 3);
            wall1.setDirection(3);
            Wall wall2 = new Wall((int) (5 * boxWidth), (int) (4 * boxHeight), 3);
            wall2.setDirection(2);
            Wall wall3 = new Wall((int) (7 * boxWidth), (int) (4 * boxHeight), 3);
            wall3.setDirection(1);
            Wall wall4 = new Wall((int) (8 * boxWidth), (int) (4 * boxHeight), 3);
            wall4.setDirection(1);
            Wall wall5 = new Wall((int) (14 * boxWidth), (int) (4 * boxHeight), 3);
            wall5.setDirection(4);
            Wall wall6 = new Wall((int) (11 * boxWidth), (int) (7 * boxHeight), 3);
            wall6.setDirection(2);
            Wall wall7 = new Wall((int) (6 * boxWidth), (int) (11 * boxHeight), 3);
            wall7.setDirection(4);
            Wall wall8 = new Wall((int) (15 * boxWidth), (int) (11 * boxHeight), 3);
            wall8.setDirection(4);
            Wall wall9 = new Wall((int) (14 * boxWidth), (int) (12 * boxHeight), 3);
            wall9.setDirection(3);
            Wall wall10 = new Wall((int) (5* boxWidth), (int) (13 * boxHeight), 3);
            wall10.setDirection(4);
            Wall wall11 = new Wall((int) (10 * boxWidth), (int) (13 * boxHeight), 3);
            wall11.setDirection(1);
            Wall wall12 = new Wall((int) (15 * boxWidth), (int) (13 * boxHeight), 3);
            wall12.setDirection(2);
            Wall wall13 = new Wall((int) (11 * boxWidth), (int) (19 * boxHeight), 3);
            wall13.setDirection(4);
            Wall wall14 = new Wall((int) (5 * boxWidth), (int) (20 * boxHeight), 3);
            wall14.setDirection(3);
            Wall wall15 = new Wall((int) (8 * boxWidth), (int) (20* boxHeight), 3);
            wall15.setDirection(1);
            Wall wall16 = new Wall((int) (6 * boxWidth), (int) (22 * boxHeight), 3);
            wall16.setDirection(4);
            Wall wall17 = new Wall((int) (11* boxWidth), (int) (23 * boxHeight), 3);
            wall17.setDirection(3);
            Wall wall18 = new Wall((int) (15 * boxWidth), (int) (23 * boxHeight), 3);
            wall18.setDirection(2);
            Wall wall19 = new Wall((int) (6 * boxWidth), (int) (25 * boxHeight), 3);
            wall19.setDirection(3);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
        }else if(level==16){
            Wall wall1 = new Wall((int) (9 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall2 = new Wall((int) (15 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall3 = new Wall((int) (16 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall4 = new Wall((int) (3 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall5 = new Wall((int) (16 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall6 = new Wall((int) (14 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall7 = new Wall((int) (17 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall8 = new Wall((int) (0 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall9 = new Wall((int) (5 * boxWidth), (int) (8 * boxHeight), 3);
            wall9.setDirection(4);
            Wall wall10 = new Wall((int) (4 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall11 = new Wall((int) (6 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall12 = new Wall((int) (10 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall13 = new Wall((int) (11 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall14 = new Wall((int) (3 * boxWidth), (int) (10 * boxHeight), 3);
            wall14.setDirection(3);
            Wall wall15 = new Wall((int) (5 * boxWidth), (int) (10 * boxHeight), 2);
            wall15.setMark(1);
            Wall wall16 = new Wall((int) (7 * boxWidth), (int) (10 * boxHeight), 3);
            wall16.setDirection(1);
            Wall wall17 = new Wall((int) (13* boxWidth), (int) (10 * boxHeight), 1);
            Wall wall18 = new Wall((int) (4 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall19 = new Wall((int) (6* boxWidth), (int) (11 * boxHeight), 1);
            Wall wall20 = new Wall((int) (0 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall21 = new Wall((int) (5 * boxWidth), (int) (12* boxHeight), 3);
            wall21.setDirection(2);
            Wall wall22 = new Wall((int) (11 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall23 = new Wall((int) (12 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall24 = new Wall((int) (17 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall25 = new Wall((int) (6 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall26 = new Wall((int) (17 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall27 = new Wall((int) (16 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall28 = new Wall((int) (16 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall29 = new Wall((int) (4 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall30 = new Wall((int) (0 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall31 = new Wall((int) (5 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall32 = new Wall((int) (15 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall33 = new Wall((int) (10 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall34 = new Wall((int) (6 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall35 = new Wall((int) (3 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall36 = new Wall((int) (17 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall37 = new Wall((int) (5 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall38 = new Wall((int) (14 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall39 = new Wall((int) (15 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall40 = new Wall((int) (5 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall41 = new Wall((int) (10 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall42 = new Wall((int) (12 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall43 = new Wall((int) (9* boxWidth), (int) (26 * boxHeight), 1);
            Wall wall44 = new Wall((int) (10 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall45 = new Wall((int) (11 * boxWidth), (int) (26 * boxHeight), 3);
            wall45.setDirection(2);
            Wall wall46 = new Wall((int) (12 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall47 = new Wall((int) (13 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall48 = new Wall((int) (1 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall49 = new Wall((int) (10 * boxWidth), (int) (27 * boxHeight), 3);
            wall49.setDirection(1);
            Wall wall50 = new Wall((int) (11 * boxWidth), (int) (27 * boxHeight), 2);
            wall50.setMark(2);
            Wall wall51 = new Wall((int) (12 * boxWidth), (int) (27 * boxHeight), 3);
            wall51.setDirection(3);
            Wall wall52 = new Wall((int) (7 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall53 = new Wall((int) (9 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall54 = new Wall((int) (10 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall55 = new Wall((int) (11 * boxWidth), (int) (28 * boxHeight), 3);
            wall55.setDirection(4);
            Wall wall56 = new Wall((int) (12 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall57 = new Wall((int) (13 * boxWidth), (int) (28 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
            walls.add(wall56);
            walls.add(wall57);
        }else if(level==17){
            Wall wall1 = new Wall((int) (8 * boxWidth), (int) (0 * boxHeight), 2);
            wall1.setMark(1);
            Wall wall2 = new Wall((int) (9 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall3 = new Wall((int) (11 * boxWidth), (int) (0 * boxHeight), 1);
            Wall wall4 = new Wall((int) (9 * boxWidth), (int) (1 * boxHeight), 1);
            Wall wall5 = new Wall((int) (9 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall6 = new Wall((int) (10 * boxWidth), (int) (2 * boxHeight), 1);
            Wall wall7 = new Wall((int) (9 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall8 = new Wall((int) (16 * boxWidth), (int) (3 * boxHeight), 1);
            Wall wall9 = new Wall((int) (4 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall10 = new Wall((int) (9 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall11 = new Wall((int) (13 * boxWidth), (int) (4 * boxHeight), 1);
            Wall wall12 = new Wall((int) (0 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall13 = new Wall((int) (7 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall14 = new Wall((int) (9 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall15 = new Wall((int) (13 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall16 = new Wall((int) (14 * boxWidth), (int) (5 * boxHeight), 1);
            Wall wall17 = new Wall((int) (9 * boxWidth), (int) (6 * boxHeight), 1);
            Wall wall18 = new Wall((int) (7 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall19 = new Wall((int) (9 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall85 = new Wall((int) (15 * boxWidth), (int) (7 * boxHeight), 1);
            Wall wall20 = new Wall((int) (1 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall21 = new Wall((int) (9 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall22 = new Wall((int) (17 * boxWidth), (int) (8 * boxHeight), 1);
            Wall wall23 = new Wall((int) (5 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall24 = new Wall((int) (9 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall25 = new Wall((int) (14 * boxWidth), (int) (9 * boxHeight), 1);
            Wall wall26 = new Wall((int) (7 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall27 = new Wall((int) (9 * boxWidth), (int) (10 * boxHeight), 1);
            Wall wall28 = new Wall((int) (3 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall29 = new Wall((int) (9 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall30 = new Wall((int) (12 * boxWidth), (int) (11 * boxHeight), 1);
            Wall wall31 = new Wall((int) (9 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall32 = new Wall((int) (10 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall33 = new Wall((int) (11 * boxWidth), (int) (12 * boxHeight), 1);
            Wall wall34 = new Wall((int) (7 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall35 = new Wall((int) (9 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall36 = new Wall((int) (11 * boxWidth), (int) (13 * boxHeight), 1);
            Wall wall37 = new Wall((int) (0 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall38 = new Wall((int) (2 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall39 = new Wall((int) (6 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall40 = new Wall((int) (7 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall41 = new Wall((int) (9 * boxWidth), (int) (14 * boxHeight), 1);
            Wall wall42 = new Wall((int) (2 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall43 = new Wall((int) (4 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall44 = new Wall((int) (7 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall45 = new Wall((int) (9 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall46 = new Wall((int) (12 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall47 = new Wall((int) (13 * boxWidth), (int) (15 * boxHeight), 1);
            Wall wall48 = new Wall((int) (7 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall49 = new Wall((int) (9 * boxWidth), (int) (16 * boxHeight), 1);
            Wall wall50 = new Wall((int) (0* boxWidth), (int) (17 * boxHeight), 1);
            Wall wall51 = new Wall((int) (9 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall52 = new Wall((int) (13 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall53 = new Wall((int) (14 * boxWidth), (int) (17 * boxHeight), 1);
            Wall wall54 = new Wall((int) (3 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall55 = new Wall((int) (9 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall56 = new Wall((int) (10 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall57 = new Wall((int) (13 * boxWidth), (int) (18 * boxHeight), 1);
            Wall wall58 = new Wall((int) (7 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall59 = new Wall((int) (8 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall60 = new Wall((int) (9 * boxWidth), (int) (19 * boxHeight), 1);
            Wall wall61 = new Wall((int) (14 * boxWidth), (int) (19*  boxHeight), 1);
            Wall wall62 = new Wall((int) (4 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall63 = new Wall((int) (9 * boxWidth), (int) (20 * boxHeight), 1);
            Wall wall64 = new Wall((int) (8 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall65 = new Wall((int) (9 * boxWidth), (int) (21 * boxHeight), 1);
            Wall wall66 = new Wall((int) (3* boxWidth), (int) (22 * boxHeight), 1);
            Wall wall67 = new Wall((int) (9 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall68 = new Wall((int) (11 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall69 = new Wall((int) (17 * boxWidth), (int) (22 * boxHeight), 1);
            Wall wall70 = new Wall((int) (9 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall86 = new Wall((int) (13 * boxWidth), (int) (23 * boxHeight), 1);
            Wall wall71 = new Wall((int) (0 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall72 = new Wall((int) (9 * boxWidth), (int) (24 * boxHeight), 1);
            Wall wall73 = new Wall((int) (4 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall74 = new Wall((int) (9 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall75 = new Wall((int) (16 * boxWidth), (int) (25 * boxHeight), 1);
            Wall wall76 = new Wall((int) (7* boxWidth), (int) (26 * boxHeight), 1);
            Wall wall77 = new Wall((int) (9 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall78 = new Wall((int) (12 * boxWidth), (int) (26 * boxHeight), 1);
            Wall wall79 = new Wall((int) (1 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall80 = new Wall((int) (2 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall81 = new Wall((int) (9 * boxWidth), (int) (27 * boxHeight), 1);
            Wall wall82 = new Wall((int) (10 * boxWidth), (int) (27 * boxHeight), 2);
            wall82.setMark(2);
            Wall wall83 = new Wall((int) (9 * boxWidth), (int) (28 * boxHeight), 1);
            Wall wall84 = new Wall((int) (9 * boxWidth), (int) (29 * boxHeight), 1);
            walls.add(wall1);
            walls.add(wall2);
            walls.add(wall3);
            walls.add(wall4);
            walls.add(wall5);
            walls.add(wall6);
            walls.add(wall7);
            walls.add(wall8);
            walls.add(wall9);
            walls.add(wall10);
            walls.add(wall11);
            walls.add(wall12);
            walls.add(wall13);
            walls.add(wall14);
            walls.add(wall15);
            walls.add(wall16);
            walls.add(wall17);
            walls.add(wall18);
            walls.add(wall19);
            walls.add(wall20);
            walls.add(wall21);
            walls.add(wall22);
            walls.add(wall23);
            walls.add(wall24);
            walls.add(wall25);
            walls.add(wall26);
            walls.add(wall27);
            walls.add(wall28);
            walls.add(wall29);
            walls.add(wall30);
            walls.add(wall31);
            walls.add(wall32);
            walls.add(wall33);
            walls.add(wall34);
            walls.add(wall35);
            walls.add(wall36);
            walls.add(wall37);
            walls.add(wall38);
            walls.add(wall39);
            walls.add(wall40);
            walls.add(wall41);
            walls.add(wall42);
            walls.add(wall43);
            walls.add(wall44);
            walls.add(wall45);
            walls.add(wall46);
            walls.add(wall47);
            walls.add(wall48);
            walls.add(wall49);
            walls.add(wall50);
            walls.add(wall51);
            walls.add(wall52);
            walls.add(wall53);
            walls.add(wall54);
            walls.add(wall55);
            walls.add(wall56);
            walls.add(wall57);
            walls.add(wall58);
            walls.add(wall59);
            walls.add(wall60);
            walls.add(wall61);
            walls.add(wall62);
            walls.add(wall63);
            walls.add(wall64);
            walls.add(wall65);
            walls.add(wall66);
            walls.add(wall67);
            walls.add(wall68);
            walls.add(wall69);
            walls.add(wall70);
            walls.add(wall71);
            walls.add(wall72);
            walls.add(wall73);
            walls.add(wall74);
            walls.add(wall75);
            walls.add(wall76);
            walls.add(wall77);
            walls.add(wall78);
            walls.add(wall79);
            walls.add(wall80);
            walls.add(wall81);
            walls.add(wall82);
            walls.add(wall83);
            walls.add(wall84);
            walls.add(wall85);
            walls.add(wall86);
        }
    }

    private void initBall() {
        if (level == 1) {
            limitTime = 10;
            ball = new Ball((int) (14 * boxWidth), (int) (20 * boxHeight), -1, -1, 0);
        } else if (level == 2) {
            limitTime = 20;
            ball = new Ball((int) (12 * boxWidth), (int) (14 * boxHeight), -1, -1, 0);
        } else if (level == 3) {
            limitTime = 30;
            ball = new Ball((int) (12 * boxWidth), (int) (10 * boxHeight), -1, -1, 0);
        }else if(level==4){
            limitTime = 40;
            ball = new Ball((int) (13 * boxWidth), (int) (8 * boxHeight), -1, -1, 0);
        }else if(level==5){
            limitTime = 10;
            ball = new Ball((int) (8 * boxWidth), (int) (5 * boxHeight), -1, -1, 0);
        }else if(level==6){
            limitTime = 40;
            ball = new Ball((int) (11 * boxWidth), (int) (13 * boxHeight), -1, -1, 0);
        }else if(level==7){
            limitTime = 40;
            ball = new Ball((int) (14 * boxWidth), (int) (16 * boxHeight), -1, -1, 0);
        }else if(level==8){
            limitTime = 40;
            ball = new Ball((int) (11 * boxWidth), (int) (14 * boxHeight), -1, -1, 0);
        }else if(level==9){
            limitTime = 40;
            ball = new Ball((int) (10 * boxWidth), (int) (10 * boxHeight), -1, -1, 0);
        }else if(level==10){
            limitTime = 40;
            ball = new Ball((int) (10 * boxWidth), (int) (14 * boxHeight), -1, -1, 0);
        }else if(level==11){
            limitTime = 40;
            ball = new Ball((int) (11 * boxWidth), (int) (16 * boxHeight), -1, -1, 0);
        }else if(level==12){
            limitTime = 40;
            ball = new Ball((int) (9 * boxWidth), (int) (10 * boxHeight), -1, -1, 0);
        }else if(level==13){
            limitTime = 40;
            ball = new Ball((int) (9 * boxWidth), (int) (10 * boxHeight), -1, -1, 0);
        }else if(level==14){
            limitTime = 40;
            ball = new Ball((int) (11 * boxWidth), (int) (10 * boxHeight), -1, -1, 0);
        }else if(level==15){
            limitTime = 40;
            ball = new Ball((int) (11 * boxWidth), (int) (14 * boxHeight), -1, -1, 0);
        }else if(level==16){
            limitTime = 40;
            ball = new Ball((int) (10 * boxWidth), (int) (13 * boxHeight), -1, -1, 0);
        }else if(level==17){
            limitTime = 60;
            ball = new Ball((int) (2 * boxWidth), (int) (21 * boxHeight), -1, -1, 0);
        }
        isMoving = false;
    }

    private void initFinalPoint() {
        finalPoints=new ArrayList<FinalPoint>();
        if (level == 1) {
            FinalPoint finalPoint1 = new FinalPoint((int) (8 * boxWidth), (int) (7 * boxHeight), 2);
            finalPoints.add(finalPoint1);
        } else if (level == 2) {
            FinalPoint finalPoint1 = new FinalPoint((int) (12 * boxWidth), (int) (15 * boxHeight), 2);
            finalPoints.add(finalPoint1);
        } else if (level == 3) {
            FinalPoint finalPoint1 = new FinalPoint((int) (16 * boxWidth), (int) (15 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==4){
            FinalPoint finalPoint1 = new FinalPoint((int) (6 * boxWidth), (int) (13 * boxHeight), 2);
            finalPoints.add(finalPoint1);
        }else if(level==5){
            FinalPoint finalPoint1 = new FinalPoint((int) (13 * boxWidth), (int) (14 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==6){
            FinalPoint finalPoint1 = new FinalPoint((int) (8 * boxWidth), (int) (4 * boxHeight), 2);
            finalPoints.add(finalPoint1);
        }else if(level==7){
            FinalPoint finalPoint1 = new FinalPoint((int) (13 * boxWidth), (int) (7 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==8){
            FinalPoint finalPoint1 = new FinalPoint((int) (12 * boxWidth), (int) (2 * boxHeight), 2);
            FinalPoint finalPoint2 = new FinalPoint((int) (2 * boxWidth), (int) (8 * boxHeight), 1);
            FinalPoint finalPoint3 = new FinalPoint((int) (18 * boxWidth), (int) (11 * boxHeight), 3);
            FinalPoint finalPoint4 = new FinalPoint((int) (9 * boxWidth), (int) (26 * boxHeight), 4);
            finalPoints.add(finalPoint1);
            finalPoints.add(finalPoint2);
            finalPoints.add(finalPoint3);
            finalPoints.add(finalPoint4);
        }else if(level==9){
            FinalPoint finalPoint1 = new FinalPoint((int) (10 * boxWidth), (int) (2 * boxHeight), 3);
            FinalPoint finalPoint2 = new FinalPoint((int) (4 * boxWidth), (int) (3 * boxHeight), 4);
            FinalPoint finalPoint3 = new FinalPoint((int) (6 * boxWidth), (int) (9 * boxHeight), 1);
            FinalPoint finalPoint4 = new FinalPoint((int) (16 * boxWidth), (int) (10 * boxHeight), 4);
            FinalPoint finalPoint5 = new FinalPoint((int) (13 * boxWidth), (int) (14 * boxHeight), 3);
            FinalPoint finalPoint6 = new FinalPoint((int) (17 * boxWidth), (int) (15 * boxHeight), 4);
            FinalPoint finalPoint7 = new FinalPoint((int) (3 * boxWidth), (int) (16 * boxHeight), 4);
            FinalPoint finalPoint8 = new FinalPoint((int) (9 * boxWidth), (int) (16 * boxHeight), 1);
            FinalPoint finalPoint9 = new FinalPoint((int) (11 * boxWidth), (int) (16 * boxHeight), 2);
            FinalPoint finalPoint10 = new FinalPoint((int) (18 * boxWidth), (int) (16 * boxHeight), 2);
            FinalPoint finalPoint11 = new FinalPoint((int) (5 * boxWidth), (int) (17 * boxHeight), 2);
            FinalPoint finalPoint12 = new FinalPoint((int) (15 * boxWidth), (int) (18 * boxHeight), 3);
            FinalPoint finalPoint13 = new FinalPoint((int) (2 * boxWidth), (int) (19 * boxHeight), 2);
            FinalPoint finalPoint14 = new FinalPoint((int) (6 * boxWidth), (int) (19 * boxHeight), 1);
            FinalPoint finalPoint15 = new FinalPoint((int) (5 * boxWidth), (int) (21 * boxHeight), 2);
            FinalPoint finalPoint16 = new FinalPoint((int) (14 * boxWidth), (int) (21 * boxHeight), 4);
            FinalPoint finalPoint17 = new FinalPoint((int) (10 * boxWidth), (int) (22 * boxHeight), 2);
            FinalPoint finalPoint18 = new FinalPoint((int) (8 * boxWidth), (int) (25 * boxHeight), 2);
            FinalPoint finalPoint19 = new FinalPoint((int) (18 * boxWidth), (int) (25 * boxHeight), 4);
            FinalPoint finalPoint20 = new FinalPoint((int) (13 * boxWidth), (int) (26 * boxHeight), 3);
            finalPoints.add(finalPoint1);
            finalPoints.add(finalPoint2);
            finalPoints.add(finalPoint3);
            finalPoints.add(finalPoint4);
            finalPoints.add(finalPoint5);
            finalPoints.add(finalPoint6);
            finalPoints.add(finalPoint7);
            finalPoints.add(finalPoint8);
            finalPoints.add(finalPoint9);
            finalPoints.add(finalPoint10);
            finalPoints.add(finalPoint11);
            finalPoints.add(finalPoint12);
            finalPoints.add(finalPoint13);
            finalPoints.add(finalPoint14);
            finalPoints.add(finalPoint15);
            finalPoints.add(finalPoint16);
            finalPoints.add(finalPoint17);
            finalPoints.add(finalPoint18);
            finalPoints.add(finalPoint19);
            finalPoints.add(finalPoint20);
        }else if(level==10){
            FinalPoint finalPoint1 = new FinalPoint((int) (17 * boxWidth), (int) (16 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==11){
            FinalPoint finalPoint1 = new FinalPoint((int) (11 * boxWidth), (int) (13 * boxHeight), 4);
            FinalPoint finalPoint2 = new FinalPoint((int) (8 * boxWidth), (int) (16 * boxHeight), 3);
            FinalPoint finalPoint3 = new FinalPoint((int) (14 * boxWidth), (int) (16 * boxHeight), 1);
            FinalPoint finalPoint4 = new FinalPoint((int) (11 * boxWidth), (int) (19 * boxHeight), 2);
            finalPoints.add(finalPoint1);
            finalPoints.add(finalPoint2);
            finalPoints.add(finalPoint3);
            finalPoints.add(finalPoint4);
        }else if(level==12){
            FinalPoint finalPoint1 = new FinalPoint((int) (11 * boxWidth), (int) (19 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==13){
            FinalPoint finalPoint1 = new FinalPoint((int) (8 * boxWidth), (int) (20 * boxHeight), 4);
            finalPoints.add(finalPoint1);
        }else if(level==14){
            FinalPoint finalPoint1 = new FinalPoint((int) (9 * boxWidth), (int) (0 * boxHeight), 2);
            finalPoints.add(finalPoint1);
        }else if(level==15){
            FinalPoint finalPoint1 = new FinalPoint((int) (16* boxWidth), (int) (12 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }else if(level==16){
            FinalPoint finalPoint1 = new FinalPoint((int) (1 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint2 = new FinalPoint((int) (3 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint3 = new FinalPoint((int) (5 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint4 = new FinalPoint((int) (7 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint5 = new FinalPoint((int) (9 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint6 = new FinalPoint((int) (11 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint7 = new FinalPoint((int) (13 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint8 = new FinalPoint((int) (15 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint9 = new FinalPoint((int) (17 * boxWidth), (int) (0 * boxHeight), 2);
            FinalPoint finalPoint10 = new FinalPoint((int) (0 * boxWidth), (int) (2 * boxHeight), 1);
            FinalPoint finalPoint11 = new FinalPoint((int) (19 * boxWidth), (int) (2 * boxHeight), 3);
            FinalPoint finalPoint12 = new FinalPoint((int) (0 * boxWidth), (int) (4 * boxHeight), 1);
            FinalPoint finalPoint13 = new FinalPoint((int) (19 * boxWidth), (int) (4 * boxHeight), 3);
            FinalPoint finalPoint14 = new FinalPoint((int) (0 * boxWidth), (int) (6 * boxHeight), 1);
            FinalPoint finalPoint15 = new FinalPoint((int) (19 * boxWidth), (int) (6 * boxHeight), 3);
            FinalPoint finalPoint17 = new FinalPoint((int) (19 * boxWidth), (int) (8 * boxHeight), 3);
            FinalPoint finalPoint18 = new FinalPoint((int) (0 * boxWidth), (int) (10 * boxHeight), 1);
            FinalPoint finalPoint19 = new FinalPoint((int) (19 * boxWidth), (int) (10 * boxHeight), 3);
            FinalPoint finalPoint21 = new FinalPoint((int) (19 * boxWidth), (int) (12 * boxHeight), 3);
            FinalPoint finalPoint22 = new FinalPoint((int) (0 * boxWidth), (int) (14 * boxHeight), 1);
            FinalPoint finalPoint23 = new FinalPoint((int) (19 * boxWidth), (int) (14 * boxHeight), 3);
            FinalPoint finalPoint24 = new FinalPoint((int) (0 * boxWidth), (int) (16 * boxHeight), 1);
            FinalPoint finalPoint25 = new FinalPoint((int) (19 * boxWidth), (int) (16 * boxHeight), 3);
            FinalPoint finalPoint26 = new FinalPoint((int) (0 * boxWidth), (int) (18 * boxHeight), 1);
            FinalPoint finalPoint27 = new FinalPoint((int) (19 * boxWidth), (int) (18 * boxHeight), 3);
            FinalPoint finalPoint29 = new FinalPoint((int) (19 * boxWidth), (int) (20 * boxHeight), 3);
            FinalPoint finalPoint30 = new FinalPoint((int) (0 * boxWidth), (int) (22 * boxHeight), 1);
            FinalPoint finalPoint31 = new FinalPoint((int) (19 * boxWidth), (int) (22 * boxHeight), 3);
            FinalPoint finalPoint32 = new FinalPoint((int) (0 * boxWidth), (int) (24 * boxHeight), 1);
            FinalPoint finalPoint33 = new FinalPoint((int) (19 * boxWidth), (int) (24 * boxHeight), 3);
            FinalPoint finalPoint34 = new FinalPoint((int) (0 * boxWidth), (int) (26 * boxHeight), 1);
            FinalPoint finalPoint35 = new FinalPoint((int) (19 * boxWidth), (int) (26 * boxHeight), 3);
            FinalPoint finalPoint36 = new FinalPoint((int) (1 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint37 = new FinalPoint((int) (3 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint38 = new FinalPoint((int) (5 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint39 = new FinalPoint((int) (7 * boxWidth), (int) (29* boxHeight), 4);
            FinalPoint finalPoint40 = new FinalPoint((int) (9 * boxWidth), (int) (29* boxHeight), 4);
            FinalPoint finalPoint41 = new FinalPoint((int) (11 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint42= new FinalPoint((int) (13 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint43= new FinalPoint((int) (15 * boxWidth), (int) (29 * boxHeight), 4);
            FinalPoint finalPoint44 = new FinalPoint((int) (17 * boxWidth), (int) (29 * boxHeight), 4);
            finalPoints.add(finalPoint1);
            finalPoints.add(finalPoint2);
            finalPoints.add(finalPoint3);
            finalPoints.add(finalPoint4);
            finalPoints.add(finalPoint5);
            finalPoints.add(finalPoint6);
            finalPoints.add(finalPoint7);
            finalPoints.add(finalPoint8);
            finalPoints.add(finalPoint9);
            finalPoints.add(finalPoint10);
            finalPoints.add(finalPoint11);
            finalPoints.add(finalPoint12);
            finalPoints.add(finalPoint13);
            finalPoints.add(finalPoint14);
            finalPoints.add(finalPoint15);
            finalPoints.add(finalPoint17);
            finalPoints.add(finalPoint18);
            finalPoints.add(finalPoint19);
            finalPoints.add(finalPoint21);
            finalPoints.add(finalPoint22);
            finalPoints.add(finalPoint23);
            finalPoints.add(finalPoint24);
            finalPoints.add(finalPoint25);
            finalPoints.add(finalPoint26);
            finalPoints.add(finalPoint27);
            finalPoints.add(finalPoint29);
            finalPoints.add(finalPoint30);
            finalPoints.add(finalPoint31);
            finalPoints.add(finalPoint32);
            finalPoints.add(finalPoint33);
            finalPoints.add(finalPoint34);
            finalPoints.add(finalPoint35);
            finalPoints.add(finalPoint36);
            finalPoints.add(finalPoint37);
            finalPoints.add(finalPoint38);
            finalPoints.add(finalPoint39);
            finalPoints.add(finalPoint40);
            finalPoints.add(finalPoint41);
            finalPoints.add(finalPoint42);
            finalPoints.add(finalPoint43);
            finalPoints.add(finalPoint44);
        }else if(level==17){
            FinalPoint finalPoint1 = new FinalPoint((int) (16* boxWidth), (int) (14 * boxHeight), 3);
            finalPoints.add(finalPoint1);
        }
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        /*for (int i = 0; i < 21; i++) {
            canvas.drawLine(0 + boxWidth * i, 0, 0 + boxWidth * i, mHeight, mPaint);
        }
        for (int i = 0; i < 31; i++) {
            canvas.drawLine(0, 0 + i * boxHeight, mWidth, 0 + i * boxHeight, mPaint);
        }*/
        canvas.drawBitmap(gamebgIcon, 0, 0, null);
        canvas.drawLine(0,mHeight, mWidth, mHeight, mPaint);
        canvas.drawBitmap(myBallIcon, ball.getX(), ball.getY(), null);
        for(int i=0;i<finalPoints.size();i++){
            FinalPoint finalPoint=finalPoints.get(i);
            if (finalPoint.getOrientation() == 1) {
                canvas.drawBitmap(final_left, finalPoint.getX(), finalPoint.getY(), null);
            } else if (finalPoint.getOrientation() == 2) {
                canvas.drawBitmap(final_up, finalPoint.getX(), finalPoint.getY(), null);
            } else if (finalPoint.getOrientation() == 3) {
                canvas.drawBitmap(final_right, finalPoint.getX(), finalPoint.getY(), null);
            } else if (finalPoint.getOrientation() == 4) {
                canvas.drawBitmap(final_below, finalPoint.getX(), finalPoint.getY(), null);
            }
        }
        if (ball.getX() == ball.getLastX() && ball.getY() == ball.getLastY()) {
            Log.i("initBall", ball.getX() + " " + ball.getY());
            if (ball.getX() < 0 || ball.getX() >= mWidth || ball.getY() < 0 || ball.getY() >= mHeight) {
                isMinus = false;
                handler.sendMessage(handler.obtainMessage(112, "fail"));
            } /*else if (ball.getX() == finalPoint.getX() && ball.getY() == finalPoint.getY() && finalPoint.getOrientation() == ball.getDirection()) {
                isMinus = false;
                handler.sendMessage(handler.obtainMessage(111, "win"));
            }*/else{
                for(int j=0;j<finalPoints.size();j++){
                    FinalPoint finalPoint=finalPoints.get(j);
                    if(ball.getX() == finalPoint.getX() && ball.getY() == finalPoint.getY() && finalPoint.getOrientation() == ball.getDirection()){
                        isMinus = false;
                        handler.sendMessage(handler.obtainMessage(111, "win"));
                        break;
                    }
                }
            }
            Log.i("isMovingasdas", isMoving + "");
            isMoving = false;
        }
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.getStatus() == 0) {
                canvas.drawBitmap(final_up, wall.getX(), wall.getY(), null);
            } else if (wall.getStatus() == 1) {
                if(level<=5){
                    canvas.drawBitmap(redWallIcon, wall.getX(), wall.getY(), null);
                }else if(level<=10){
                    canvas.drawBitmap(blueWallIcon, wall.getX(), wall.getY(), null);
                }else{
                    canvas.drawBitmap(browseWallIcon, wall.getX(), wall.getY(), null);
                }
            } else if (wall.getStatus() == 2) {
                canvas.drawBitmap(throughWallIcon, wall.getX(), wall.getY(), null);
            }else if(wall.getStatus() == 3){
                if(wall.getDirection()==1){
                    canvas.drawBitmap(arrow_left, wall.getX(), wall.getY(), null);
                }else if(wall.getDirection()==2){
                    canvas.drawBitmap(arrow_up, wall.getX(), wall.getY(), null);
                }else if(wall.getDirection()==3){
                    canvas.drawBitmap(arrow_right, wall.getX(), wall.getY(), null);
                }else if(wall.getDirection()==4){
                    canvas.drawBitmap(arrow_below, wall.getX(), wall.getY(), null);
                }
            }
        }
        canvas.drawText("第" + level + "关", 4 * boxWidth, mHeight + 80, mWordPaint);
        canvas.drawText("剩余时间:" + limitTime + "秒", 10 * boxWidth, mHeight + 80, mWordPaint);
    }

    private void calculateTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(1000);
                        if (isMinus) {
                            limitTime--;
                            if (limitTime <= -1) {
                                isMinus = false;
                                handler.sendMessage(handler.obtainMessage(113, "fail"));
                            } else {
                                handler.sendMessage(handler.obtainMessage(222, "draw"));
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void ballMoveAnimation(String direction, final int num) {
        if (direction.equals("x")) {
            int time = (int) ((ball.getX() - ball.getLastX()) / boxWidth) * 20;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ball, "x", ball.getX(), ball.getLastX()).setDuration(Math.abs(time));
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float num = (Float) animation.getAnimatedValue();
                    ball.setX((int) num);
                    invalidate();
                }
            });
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(num!=0){
                        for(int i=0;i<walls.size();i++){
                            Wall wall=walls.get(i);
                            if(wall.getMark()!=0&&wall.getMark()!=num){
                                ball.setX(wall.getX());
                                ball.setY(wall.getY());
                                break;
                            }
                        }
                        calculateLastPosition(ball.getDirection());
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objectAnimator.start();
        } else if (direction.equals("y")) {
            int time = (int) ((ball.getY() - ball.getLastY()) / boxHeight) * 20;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ball, "y", ball.getY(), ball.getLastY()).setDuration(Math.abs(time));
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float num = (Float) animation.getAnimatedValue();
                    ball.setY((int) num);
                    invalidate();
                }
            });
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(num!=0){
                        for(int i=0;i<walls.size();i++){
                            Wall wall=walls.get(i);
                            if(wall.getMark()!=0&&wall.getMark()!=num){
                                ball.setX(wall.getX());
                                ball.setY(wall.getY());
                                break;
                            }
                        }
                        calculateLastPosition(ball.getDirection());
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objectAnimator.start();
        }
    }

    private void calculateLastPosition(int direction){
        ball.setDirection(direction);
        if(direction==3){
            int minDistance = 10000, minWhichWall = -1;
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = walls.get(i);
                if (wall.getY() == ball.getY() && wall.getX() > ball.getX()) {
                    if(wall.getStatus()==3&&wall.getDirection()==ball.getDirection()){

                    }else{
                        if (minDistance > wall.getX() - ball.getX()) {
                            minDistance = wall.getX() - ball.getX();
                            minWhichWall = i;
                        }
                    }
                }
            }
            int nearDistance=10000,minWhichFinalPoint=-1;
            for(int i=0;i<finalPoints.size();i++){
                FinalPoint finalPoint=finalPoints.get(i);
                if(finalPoint.getY()==ball.getY()&&finalPoint.getX()>ball.getX()){
                    if (nearDistance > finalPoint.getX() - ball.getX()) {
                        nearDistance = finalPoint.getX() - ball.getX();
                        minWhichFinalPoint = i;
                    }
                }
            }
            if(minWhichFinalPoint!=-1&&minWhichWall!=-1){
                if(nearDistance<minDistance){//离终点近
                    if(finalPoints.get(minWhichFinalPoint).getOrientation()==3){//到达终点
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }else{
                        ball.setLastX((int) (finalPoints.get(minWhichFinalPoint).getX() - boxWidth));
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }
                    ballMoveAnimation("x",0);
                }else{//离墙近
                    if(walls.get(minWhichWall).getMark()!=0) {
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                    }else{
                        ball.setLastX((int) (walls.get(minWhichWall).getX() - boxWidth));
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("x",0);
                    }
                }
            }else if(minWhichFinalPoint==-1&&minWhichWall!=-1){
                if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                }else{
                    ball.setLastX((int) (walls.get(minWhichWall).getX() - boxWidth));
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",0);
                }
            }else if(minWhichFinalPoint!=-1&&minWhichWall==-1){
                if(finalPoints.get(minWhichFinalPoint).getOrientation()==3){//到达终点
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }else{
                    ball.setLastX((int) (finalPoints.get(minWhichFinalPoint).getX() - boxWidth));
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }
                ballMoveAnimation("x",0);
            }else if(minWhichFinalPoint==-1&&minWhichWall==-1){//出界
                ball.setLastX((int) (mWidth));
                ball.setLastY(ball.getY());
                ballMoveAnimation("x",0);
            }
            /*if (minWhichWall == -1) {
                if (finalPoint.getOrientation() == 3 && finalPoint.getY() == ball.getY() && ball.getX() < finalPoint.getX()) {
                    ball.setLastX(finalPoint.getX());//到达终点
                    ball.setLastY(finalPoint.getY());
                } else if (finalPoint.getOrientation() != 3 && finalPoint.getY() == ball.getY() && ball.getX() < finalPoint.getX()) {
                    ball.setLastX((int) (finalPoint.getX() - boxWidth));//撞到终点，但不是正确入口
                    ball.setLastY(finalPoint.getY());
                }else {
                    ball.setLastX((int) (mWidth));//出界
                    ball.setLastY(ball.getY());
                }
                ballMoveAnimation("x",0);
            } else {
                if (finalPoint.getY() == walls.get(minWhichWall).getY() && finalPoint.getOrientation() == 3 && finalPoint.getX() < walls.get(minWhichWall).getX() && ball.getX() < finalPoint.getX()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("x",0);
                } else if (finalPoint.getOrientation() != 3 && finalPoint.getY() == ball.getY() && ball.getX() < finalPoint.getX() && minDistance > finalPoint.getX() - ball.getX()) {
                    ball.setLastX((int) (finalPoint.getX() - boxWidth));
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("x",0);
                } else if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                } else {
                    ball.setLastX((int) (walls.get(minWhichWall).getX() - boxWidth));
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",0);
                }
            }*/
        }else if(direction==1){
            int minDistance = 10000, minWhichWall = -1;
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = walls.get(i);
                if (wall.getY() == ball.getY() && ball.getX() > wall.getX()) {
                    if(wall.getStatus()==3&&wall.getDirection()==ball.getDirection()){

                    }else{
                        if (minDistance > ball.getX() - wall.getX()) {
                            minDistance = ball.getX() - wall.getX();
                            minWhichWall = i;
                        }
                    }
                }
            }
            int nearDistance=10000,minWhichFinalPoint=-1;
            for(int i=0;i<finalPoints.size();i++){
                FinalPoint finalPoint=finalPoints.get(i);
                if(finalPoint.getY()==ball.getY()&&finalPoint.getX()<ball.getX()){
                    if (nearDistance > ball.getX() - finalPoint.getX()) {
                        nearDistance = ball.getX() - finalPoint.getX();
                        minWhichFinalPoint = i;
                    }
                }
            }
            if(minWhichFinalPoint!=-1&&minWhichWall!=-1){
                if(nearDistance<minDistance){//离终点近
                    if(finalPoints.get(minWhichFinalPoint).getOrientation()==1){//到达终点
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }else{
                        ball.setLastX((int) (finalPoints.get(minWhichFinalPoint).getX() + boxWidth));
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }
                    ballMoveAnimation("x",0);
                }else{//离墙近
                    if(walls.get(minWhichWall).getMark()!=0) {
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                    }else{
                        ball.setLastX((int) (walls.get(minWhichWall).getX() + boxWidth));
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("x",0);
                    }
                }
            }else if(minWhichFinalPoint==-1&&minWhichWall!=-1){
                if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                }else{
                    ball.setLastX((int) (walls.get(minWhichWall).getX() + boxWidth));
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",0);
                }
            }else if(minWhichFinalPoint!=-1&&minWhichWall==-1){
                if(finalPoints.get(minWhichFinalPoint).getOrientation()==1){//到达终点
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }else{
                    ball.setLastX((int) (finalPoints.get(minWhichFinalPoint).getX() + boxWidth));
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }
                ballMoveAnimation("x",0);
            }else if(minWhichFinalPoint==-1&&minWhichWall==-1){//出界
                ball.setLastX((int) -boxWidth);
                ball.setLastY(ball.getY());
                ballMoveAnimation("x",0);
            }
            /*if (minWhichWall == -1) {
                if (finalPoint.getOrientation() == 1 && finalPoint.getY() == ball.getY() && ball.getX() > finalPoint.getX()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                } else if (finalPoint.getOrientation() != 1 && finalPoint.getY() == ball.getY() && ball.getX() > finalPoint.getX()) {
                    ball.setLastX((int) (finalPoint.getX() + boxWidth));
                    ball.setLastY(finalPoint.getY());
                } else {
                    ball.setLastX((int) -boxWidth);
                    ball.setLastY(ball.getY());
                }
                ballMoveAnimation("x",0);
            } else {
                if (finalPoint.getY() == walls.get(minWhichWall).getY() && finalPoint.getOrientation() == 1 && finalPoint.getX() > walls.get(minWhichWall).getX() && ball.getX() > finalPoint.getX()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("x",0);
                } else if (finalPoint.getOrientation() != 1 && finalPoint.getY() == ball.getY() && ball.getX() > finalPoint.getX() && minDistance > ball.getX() - finalPoint.getX()) {
                    ball.setLastX((int) (finalPoint.getX() + boxWidth));
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("x",0);
                } else if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",walls.get(minWhichWall).getMark());
                } else {
                    ball.setLastX((int) (walls.get(minWhichWall).getX() + boxWidth));
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("x",0);
                }
            }*/
        }else if(direction==4){
            int minDistance = 10000, minWhichWall = -1;
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = walls.get(i);
                if (wall.getX() == ball.getX() && wall.getY() > ball.getY()) {
                    if(wall.getStatus()==3&&wall.getDirection()==ball.getDirection()){

                    }else{
                        if (minDistance > wall.getY() - ball.getY()) {
                            minDistance = wall.getY() - ball.getY();
                            minWhichWall = i;
                        }
                    }
                }
            }
            int nearDistance=10000,minWhichFinalPoint=-1;
            for(int i=0;i<finalPoints.size();i++){
                FinalPoint finalPoint=finalPoints.get(i);
                if(finalPoint.getX()==ball.getX()&&finalPoint.getY()>ball.getY()){
                    if (nearDistance > finalPoint.getY() - ball.getY()) {
                        nearDistance = finalPoint.getY() - ball.getY();
                        minWhichFinalPoint = i;
                    }
                }
            }
            Log.i("pppppppppppp",minWhichFinalPoint+" "+minWhichWall+" "+nearDistance+" "+minDistance);
            if(minWhichFinalPoint!=-1&&minWhichWall!=-1){
                if(nearDistance<minDistance){//离终点近
                    if(finalPoints.get(minWhichFinalPoint).getOrientation()==4){//到达终点
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }else{
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY((int) (finalPoints.get(minWhichFinalPoint).getY() - boxHeight));
                    }
                    ballMoveAnimation("y",0);
                }else{//离墙近
                    if(walls.get(minWhichWall).getMark()!=0) {
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                    }else{
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY((int) (walls.get(minWhichWall).getY() - boxHeight));
                        ballMoveAnimation("y",0);
                    }
                }
            }else if(minWhichFinalPoint==-1&&minWhichWall!=-1){
                if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                }else{
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY((int) (walls.get(minWhichWall).getY() - boxHeight));
                    ballMoveAnimation("y",0);
                }
            }else if(minWhichFinalPoint!=-1&&minWhichWall==-1){
                if(finalPoints.get(minWhichFinalPoint).getOrientation()==4){//到达终点
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }else{
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY((int) (finalPoints.get(minWhichFinalPoint).getY() - boxHeight));
                }
                ballMoveAnimation("y",0);
            }else if(minWhichFinalPoint==-1&&minWhichWall==-1){//出界
                ball.setLastX(ball.getX());
                ball.setLastY((int) (mHeight));
                ballMoveAnimation("y",0);
            }
            /*if (minWhichWall == -1) {
                if (finalPoint.getOrientation() == 4 && finalPoint.getX() == ball.getX()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                } else if (finalPoint.getOrientation() != 4 && finalPoint.getX() == ball.getX() && ball.getY() < finalPoint.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY((int) (finalPoint.getY() - boxHeight));
                } else {
                    ball.setLastX(ball.getX());
                    ball.setLastY((int) (mHeight));
                }
                ballMoveAnimation("y",0);
            } else {
                if (finalPoint.getX() == walls.get(minWhichWall).getX() && finalPoint.getOrientation() == 4 && finalPoint.getY() < walls.get(minWhichWall).getY() && ball.getY() < finalPoint.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("y",0);
                } else if (finalPoint.getOrientation() != 4 && finalPoint.getX() == ball.getX() && ball.getY() < finalPoint.getY() && minDistance > finalPoint.getY() - ball.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY((int) (finalPoint.getY() - boxHeight));
                    ballMoveAnimation("y",0);
                } else if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                }else {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY((int) (walls.get(minWhichWall).getY() - boxHeight));
                    ballMoveAnimation("y",0);
                }
            }*/
        }else if(direction==2){
            int minDistance = 10000, minWhichWall = -1;
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = walls.get(i);
                if (wall.getX() == ball.getX() && ball.getY() > wall.getY()) {
                    if(wall.getStatus()==3&&wall.getDirection()==ball.getDirection()){

                    }else{
                        if (minDistance > ball.getY() - wall.getY()) {
                            minDistance = ball.getY() - wall.getY();
                            minWhichWall = i;
                        }
                    }
                }
            }
            int nearDistance=10000,minWhichFinalPoint=-1;
            for(int i=0;i<finalPoints.size();i++){
                FinalPoint finalPoint=finalPoints.get(i);
                if(finalPoint.getX()==ball.getX()&&finalPoint.getY()<ball.getY()){
                    if (nearDistance > ball.getY() - finalPoint.getY()) {
                        nearDistance = ball.getY() - finalPoint.getY();
                        minWhichFinalPoint = i;
                    }
                }
            }
            if(minWhichFinalPoint!=-1&&minWhichWall!=-1){
                if(nearDistance<minDistance){//离终点近
                    if(finalPoints.get(minWhichFinalPoint).getOrientation()==2){//到达终点
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                    }else{
                        ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                        ball.setLastY((int) (finalPoints.get(minWhichFinalPoint).getY() + boxHeight));
                    }
                    ballMoveAnimation("y",0);
                }else{//离墙近
                    if(walls.get(minWhichWall).getMark()!=0) {
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY(walls.get(minWhichWall).getY());
                        ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                    }else{
                        ball.setLastX(walls.get(minWhichWall).getX());
                        ball.setLastY((int) (walls.get(minWhichWall).getY() + boxHeight));
                        ballMoveAnimation("y",0);
                    }
                }
            }else if(minWhichFinalPoint==-1&&minWhichWall!=-1){
                if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                }else{
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY((int) (walls.get(minWhichWall).getY() + boxHeight));
                    ballMoveAnimation("y",0);
                }
            }else if(minWhichFinalPoint!=-1&&minWhichWall==-1){
                if(finalPoints.get(minWhichFinalPoint).getOrientation()==2){//到达终点
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY(finalPoints.get(minWhichFinalPoint).getY());
                }else{
                    ball.setLastX(finalPoints.get(minWhichFinalPoint).getX());
                    ball.setLastY((int) (finalPoints.get(minWhichFinalPoint).getY() + boxHeight));
                }
                ballMoveAnimation("y",0);
            }else if(minWhichFinalPoint==-1&&minWhichWall==-1){//出界
                ball.setLastX(ball.getX());
                ball.setLastY((int) (-boxHeight));
                ballMoveAnimation("y",0);
            }
            /*if (minWhichWall == -1) {
                if (finalPoint.getOrientation() == 2 && finalPoint.getX() == ball.getX()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                } else if (finalPoint.getOrientation() != 2 && finalPoint.getX() == ball.getX() && ball.getY() > finalPoint.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY((int) (finalPoint.getY() + boxHeight));
                } else {
                    ball.setLastX(ball.getX());
                    ball.setLastY((int) (-boxHeight));
                }
                ballMoveAnimation("y",0);
            } else {
                if (finalPoint.getX() == walls.get(minWhichWall).getX() && finalPoint.getOrientation() == 2 && finalPoint.getY() > walls.get(minWhichWall).getY() && ball.getY() > finalPoint.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY(finalPoint.getY());
                    ballMoveAnimation("y",0);
                } else if (finalPoint.getOrientation() != 2 && finalPoint.getX() == ball.getX() && ball.getY() > finalPoint.getY() && minDistance > ball.getY() - finalPoint.getY()) {
                    ball.setLastX(finalPoint.getX());
                    ball.setLastY((int) (finalPoint.getY() + boxHeight));
                    ballMoveAnimation("y",0);
                } else if(walls.get(minWhichWall).getMark()!=0) {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY(walls.get(minWhichWall).getY());
                    ballMoveAnimation("y",walls.get(minWhichWall).getMark());
                }else {
                    ball.setLastX(walls.get(minWhichWall).getX());
                    ball.setLastY((int) (walls.get(minWhichWall).getY() + boxHeight));
                    ballMoveAnimation("y",0);
                }
            }*/
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isMoving) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchX1 = event.getX();
                    touchY1 = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    touchX2 = event.getX();
                    touchY2 = event.getY();
                    Log.i("movelength", Math.pow(touchX2 - touchX1, 2) + Math.pow(touchY2 - touchY1, 2) + "");
                    if (Math.pow(touchX2 - touchX1, 2) + Math.pow(touchY2 - touchY1, 2) > 10000) {
                        isMoving = true;
                        if (Math.abs(touchX2 - touchX1) > Math.abs(touchY2 - touchY1)) {
                            if (touchX2 > touchX1) {
                                calculateLastPosition(3);
                            } else {
                                calculateLastPosition(1);
                            }
                        } else {
                            if (touchY2 > touchY1) {
                                calculateLastPosition(4);
                            } else {
                                calculateLastPosition(2);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.obj.equals("win")) {
                if (!havingDialog) {
                    havingDialog = true;
                    myWin();
                }
            } else if (msg.obj.equals("fail")) {
                if (!havingDialog) {
                    havingDialog = true;
                    myfail();
                }
            } else if (msg.obj.equals("draw")) {
                invalidate();
            }
        }
    };

    private void myWin() {
        if(maxlevel<=level){

        }else{
            if(Integer.parseInt(Sqlite_DB.find_Data(1))<level+1){
                Sqlite_DB.update_Data("level", (level+1)+"");
            }
        }
        final CustomDialog dialog = new CustomDialog(getContext(), R.style.Dialog, true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        ImageButton restart = (ImageButton) dialog.findViewById(R.id.restart);
        ImageButton out = (ImageButton) dialog.findViewById(R.id.out);
        ImageButton go_on = (ImageButton) dialog.findViewById(R.id.go_on);
        restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initBall();
                invalidate();
                havingDialog = false;
                isMinus = true;
                dialog.dismiss();
            }
        });
        out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callActivity.setFinish();
            }
        });
        go_on.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(maxlevel<=level){
                    callActivity.setFinish();
                }else{
                    level++;
                    initFinalPoint();
                    initBall();
                    initWalls();
                    invalidate();
                    havingDialog = false;
                    isMinus = true;
                }
            }
        });
    }

    private void myfail() {
        final CustomDialog dialog = new CustomDialog(getContext(), R.style.Dialog, false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        ImageButton restart = (ImageButton) dialog.findViewById(R.id.restart);
        ImageButton out = (ImageButton) dialog.findViewById(R.id.out);
        restart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initBall();
                invalidate();
                havingDialog = false;
                isMinus = true;
                dialog.dismiss();
            }
        });
        out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callActivity.setFinish();
            }
        });
    }

    public interface callBackActivity {
        public void setFinish();
    }

    public void setCallBackActivityListener(callBackActivity callActivity) {
        this.callActivity = callActivity;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
