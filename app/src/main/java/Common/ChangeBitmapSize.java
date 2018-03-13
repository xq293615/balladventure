package Common;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by 琪 on 2017/2/7.
 */
public class ChangeBitmapSize {
    public Bitmap getBitmap(Bitmap bitmap,int newWidth,int newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
