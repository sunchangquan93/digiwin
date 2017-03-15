package digiwin.smartdepot.main.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.login.activity.LoginActivity;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;

/**
 * 账号管理
 *
 * @author 毛衡
 */
public class UserInfoActivity extends BaseTitleActivity {

    /**
     * 用户对象
     */
    private AccoutBean accoutBean;

    /**
     * toolbar
     */
    @BindView(R.id.toolbar_title)
    public Toolbar toolbar;

    /**
     * 用户名
     */
    @BindView(R.id.tv_userInfo_name)
    TextView tv_userInfo_name;

    /**
     * 员工编号
     */
    @BindView(R.id.tv_userInfo_no)
    TextView tv_userInfo_no;

    /**
     * 员工职位
     */
    @BindView(R.id.tv_userInfo_position)
    TextView tv_userInfo_position;

    /**
     * 退出按钮
     */
    @BindView(R.id.tv_userInfo_exit)
    TextView tv_userInfo_exit;
    @OnClick(R.id.tv_userInfo_exit)
    public void exit(){
        AlertDialogUtils.showSettingExitDialog(context, new OnDialogClickListener() {
            @Override
            public void onCallback() {
                ActivityManagerUtils.startActivity(activity, LoginActivity.class);
                activity.finish();
                Activity mainActivity = ActivityManagerUtils.getActivity("MainActivity");
                if (null != mainActivity){
                    mainActivity.finish();
                }
            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initNavigationTitle() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBack.setImageResource(R.drawable.left);
        mBack.setBackgroundResource(R.drawable.image_click_bg);
        mName.setTextColor(getResources().getColor(R.color.black_32));
        Toolbar.LayoutParams tl = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tl.gravity = Gravity.CENTER;
        mName.setLayoutParams(tl);
        mName.setEnabled(false);
        mName.setText(R.string.user_info);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void doBusiness() {
        try {
            accoutBean = LoginLogic.getUserInfo();
            tv_userInfo_name.setText(accoutBean.getRealname());
            tv_userInfo_no.setText(accoutBean.getUsername());
            tv_userInfo_position.setText(accoutBean.getDepartname());
            // JPushManager.sendMessage(TelephonyUtils.getDeviceId(activity),"99999999999999999");
        }catch (Exception e){
            LogUtils.e(TAG,"doBusiness------Exception");
        }

    }

    @Override
    public String moduleCode() {
        module= ModuleCode.OTHER;
        return module;
    }
}
