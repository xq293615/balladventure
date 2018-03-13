package UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.balladventure.R;

/**
 * Created by 琪 on 2016/11/23.
 */
public class CustomDialog extends Dialog{
    private boolean which;
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomDialog(Context context, int themeResId,boolean which) {
        super(context, themeResId);
        this.which=which;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(which){
            this.setContentView(R.layout.dialogwin_layout);
        }else{
            this.setContentView(R.layout.dialogfail_layout);
        }
    }
}
