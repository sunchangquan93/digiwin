package digiwin.smartdepot.module.activity.produce.processtransfer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepot.R;

/**
 * @des     选择接收转出
 * @author  毛衡
 * @date    2017/6/1
 */
public class ChooseMoveDailog {
    private static CustomDialog dialog;
    private static final String TAG = "ChooseMoveDailog";
    /**
     * 弹出确定取消对话框
     */
    public static void showChooseMoveDailog(Context context, final OnDialogTwoListener listener) {
        try {
            if (context != null) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                CustomDialog.Builder builder = new CustomDialog.Builder(context)
                        .view(R.layout.dialog_process_transfer)
                        .style(R.style.CustomDialog)
                        .addViewOnclick(R.id.tv_move_in, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                listener.onCallback1();
                            }
                        }).addViewOnclick(R.id.tv_move_out, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (listener != null)
                                    dialog.dismiss();
                                listener.onCallback2();
                            }
                        }).cancelTouchout(false).backCancelTouchout(true)
                        .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                        .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog=builder.build();
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "showUnBindStatuDailog-----Error"+e);
        }
    }

    /**
     * 隐藏等待dialog
     */
    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
