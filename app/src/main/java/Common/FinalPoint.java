package Common;

/**
 * Created by 琪 on 2017/2/8.
 */
public class FinalPoint {
    private int x,y;
    private int orientation;//0  nothing   1  left    2 up     3 right     4  below

    public FinalPoint(int x,int y,int orientation){
        this.x=x;
        this.y=y;
        this.orientation=orientation;
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

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
