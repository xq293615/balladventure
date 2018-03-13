package Common;

/**
 * Created by 琪 on 2017/2/7.
 */
public class Wall {
    private int x,y;
    private int status;//  1 无特殊功能的墙   2 可以穿越的墙   3箭头墙
    private int mark;//可以穿越的墙之中的唯一标识   1和2
    private int direction; //-1  nothing   1  left    2 up     3 right     4  below

    public Wall(int x, int y, int status){
        this.x=x;
        this.y=y;
        this.status=status;
        mark=0;
        direction=-1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
