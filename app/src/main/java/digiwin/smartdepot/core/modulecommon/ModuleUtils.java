package digiwin.smartdepot.core.modulecommon;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import digiwin.smartdepot.R;

/**
 * @des      焦点颜色变化
 * @author  maoheng
 * @date    2017/3/4
 */
public class ModuleUtils {
    /**
     * RelativeLayout Change
     */
    public static void viewChange(View rl, List<View> list){
        if (list.size()==0){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setBackground(null);
        }
        rl.setBackgroundResource(R.drawable.focus_get_bg);
    }

    /**
     * TextView Change
     */
    public static void tvChange(Activity activity, TextView tv, List<TextView> list){
        if (list.size()==0){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTextColor(activity.getResources().getColor(R.color.black_32));
        }
        tv.setTextColor(activity.getResources().getColor(R.color.outside_yellow));
    }

    /**
     * EditText Change
     */
    public static void etChange(Activity activity,EditText et,List<EditText> list){
        if (list.size()==0){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTextColor(activity.getResources().getColor(R.color.black_32));
        }
        et.setTextColor(activity.getResources().getColor(R.color.outside_yellow));
        et.setSelectAllOnFocus(true);
    }

}
