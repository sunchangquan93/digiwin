package digiwin.smartdepot.core.base;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.library.zxing.MipcaActivityCapture;
import digiwin.library.zxing.camera.GetBarCodeListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.main.activity.SettingActivity;

/**
 * Created by ChangquanSun
 * 2017/1/5
 * 包含Title标题栏的activity
 */

public abstract class BaseTitleActivity extends BaseActivity {

    @BindView(R.id.iv_title_setting)
    public ImageView iv_title_setting;
    @BindView(R.id.tv_title_name)
    public TextView mName;

    @BindView(R.id.tv_title_operation)
    public TextView tvOperation;
    @BindView(R.id.iv_back)
    public ImageView mBack;
    /**
     * 扫码框
     */
    @BindView(R.id.iv_scan)
    public ImageView ivScan;
    @OnClick(R.id.iv_scan)
    public void cameraScan(){
        MipcaActivityCapture.startCameraActivity(activity, new GetBarCodeListener() {
            @Override
            public void onSuccess(String msg) {
                View focusView = ViewUtils.getFocusView(activity);
                if (focusView instanceof EditText){
                   EditText et= (EditText) focusView;
                    et.setText(msg);
                    et.setSelection(msg.length());
                }
            }
        });
    }



    @OnClick(R.id.iv_back)
    public void goBack() {
       onBackPressed();
    }

    @OnClick(R.id.tv_title_name)
    public void goBack2() {
       onBackPressed();
    }

    @OnClick(R.id.iv_title_setting)
    public void goSetting() {
        ActivityManagerUtils.startActivity(activity, SettingActivity.class);
    }

    @Override
    protected void initNavigationTitle() {
        toolbar().setBackgroundResource(R.color.Base_color);
        setSupportActionBar(toolbar());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ivScan.setVisibility(View.VISIBLE);
    }

    /**
     * 返回toolbar标题
     */
    protected abstract Toolbar toolbar();

}
