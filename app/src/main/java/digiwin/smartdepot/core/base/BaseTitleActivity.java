package digiwin.smartdepot.core.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.popupwindow.CustomPopWindow;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
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
    private CustomPopWindow popWindow;



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

    }

    /**
     * 返回toolbar标题
     */
    protected abstract Toolbar toolbar();

}
