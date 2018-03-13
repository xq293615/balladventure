package Common;

/**
 * Created by 琪 on 2017/2/7.
 */
public class Ball {
    private int x,y;
    private int lastX,lastY;
    private int direction;//0  nothing   1  left    2 up     3 right     4  below

    public Ball(int x, int y,int lastX, int lastY, int direction){
        this.x=x;
        this.y=y;
        this.lastX=lastX;
        this.lastY=lastY;
        this.direction=direction;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
