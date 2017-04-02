package digiwin.smartdepot.login.activity;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseActivity;
import digiwin.smartdepot.core.jpush.JPushManager;
import digiwin.smartdepot.core.json.JsonReqForJava;
import digiwin.smartdepot.core.printer.WiFiPrintManager;
import digiwin.smartdepot.login.activity.operating_center_pw.OperatingCenterDialog;
import digiwin.smartdepot.login.activity.setting_dialog.SettingDialog;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.bean.AppVersionBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.main.activity.MainActivity;
import digiwin.smartdepot.main.bean.StorageBean;

/**
 * 登录界面
 */

public class LoginActivity extends BaseActivity {
    /**
     * 设置背景动画
     */
    @BindView(R.id.iv_login_bg)
    ImageView iv_login_bg;
    /**
     * 系统设置
     */
    @BindView(R.id.tv_setup_systemSettings)
    TextView tv_setup_systemSettings;
    @BindView(R.id.iv_setup_systemSettings)
    ImageView iv_setup_systemSettings;
    /**
     * 运营中心
     */
    @BindView(R.id.rl_login_eye)
    RelativeLayout rl_login_eye;
    @BindView(R.id.tv_login_eye)
    TextView tv_login_eye;
    /**
     * 背景动画
     */
    private AnimationDrawable animationDrawable;

    /**
     * LoginActivity是否是结束状态,开机启动用到
     */
    private static boolean isFinished = true;
    /**
     * 账号
     */
    @BindView(R.id.et_login_user)
    EditText et_login_user;
    /**
     * 密码
     */
    @BindView(R.id.et_login_lock)
    EditText et_login_lock;
    /**
     * 登入
     */
    @BindView(R.id.tv_login_rightNow)
    View tv_login_rightNow;
    /**
     * 记住密码
     */
    @BindView(R.id.cb_remeber_password)
    CheckBox cb_remeber_password;

    /**
     * 版本对象传入到bundle的key值
     */
    static final String VERSIONKEY = "Version";
    /**
     * 逻辑管理
     */
    LoginLogic logic;
    /**
     * 营用中心
     */
    private List<String> mPlants;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initNavigationTitle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable = (AnimationDrawable) iv_login_bg.getBackground();
        animationDrawable.start();
    }

    @Override
    protected void doBusiness() {
        final    WiFiPrintManager wiFiPrintManager=   WiFiPrintManager.getManager();
        wiFiPrintManager.openWiFi("", 0, new WiFiPrintManager.OpenWiFiPrintListener() {
            @Override
            public void isOpen(boolean isOpen) {
                wiFiPrintManager.printText("123");
            }
        });
        mPlants = new ArrayList<>();
        et_login_user.setOnFocusChangeListener(focusChangeListener);
        isFinished = false;
        transparentStatusBar();
        logic = LoginLogic.getInstance(activity, module, mTimestamp.toString());
        et_login_user.setText("tiptop");
        et_login_lock.setText("tiptop");
        getUserInfo();
        updatePlant();
        getVersion();
    }

    /**
     * 运营中心
     */
    @OnClick(R.id.rl_login_eye)
    void showOperatingCenter() {
        String plant = tv_login_eye.getText().toString();
        OperatingCenterDialog.showOperatingCenterDialog(activity, plant, mPlants);
    }

    /**
     * 对话框点击更新界面的营用中心
     */
    private void updatePlant() {
        OperatingCenterDialog.setCallBack(new OperatingCenterDialog.OperatingCenterCallBack() {
            @Override
            public void operatingCenterCallBack(String choosePlant) {
                tv_login_eye.setText(choosePlant);
            }
        });
    }

    /**
     * 弹出Dialog
     */
    @OnClick(R.id.tv_setup_systemSettings)
    void showSetting() {
        SettingDialog.showSettingDialog(activity);
    }

    @OnClick(R.id.iv_setup_systemSettings)
    void showSetting1() {
        SettingDialog.showSettingDialog(activity);
    }

    /**
     * 跳转主界面
     */
    @OnClick(R.id.tv_login_rightNow)
    void login() {
        if (StringUtils.isBlank(et_login_user.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context,R.string.username_not_null);
            return;
        }
        if (StringUtils.isBlank(et_login_lock.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context,R.string.password_not_null);
            return;
        }
        if (StringUtils.isBlank(tv_login_eye.getText().toString())) {
            AlertDialogUtils.showFailedDialog(context,R.string.plant_not_null);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", et_login_user.getText().toString());
        map.put("password", et_login_lock.getText().toString());
        map.put("plant", tv_login_eye.getText().toString());
        showLoadingDialog();
        logic.login(map, tv_login_eye.getText().toString(), new LoginLogic.LoginListener() {
            @Override
            public void onSuccess(AccoutBean accoutBean) {
                String vernum = accoutBean.getVernum();
                //TODO:由于开启自启动原因，所以该处会再次判断是否为最新版
//                if (!StringUtils.isBlank(vernum) && StringUtils.string2Float(vernum)>TelephonyUtils.getMAppVersion(context)) {
//                    AppVersionBean versionBean = new AppVersionBean();
//                    versionBean.setVernum(accoutBean.getVernum());
//                    versionBean.setVerurl(accoutBean.getVerurl());
//                    versionBean.setVerwhat(accoutBean.getVerwhat());
//                    matchVersion(versionBean);
//                }
//        else
                {
                    //传入权限
                    Bundle bundle=new Bundle();
                    bundle.putString("access",accoutBean.getAccess());
                    accoutBean.setPassword(et_login_lock.getText().toString());
                    if (cb_remeber_password.isChecked()) {
                        accoutBean.setIsRemeberPassWord("Y");
                    } else {
                        accoutBean.setIsRemeberPassWord("N");}
                    List<String> split = StringUtils.split(accoutBean.getWare());
                    ArrayList<StorageBean> storageList = new ArrayList<>();
                    for (int i=0;i<split.size();i++){
                        StorageBean storageBean = new StorageBean();
                        storageBean.setWare(split.get(i));
                        storageList.add(storageBean);
                    }
                    if (storageList.size()>0){
                        accoutBean.setWare(storageList.get(0).getWare());
                    }
                    Connector.getDatabase();
                    DataSupport.deleteAll(StorageBean.class);
                    DataSupport.deleteAll(AccoutBean.class);
                    DataSupport.saveAll(storageList);
                    accoutBean.save();
                    //上传用户词表
                    ActivityManagerUtils.startActivityForBundleDataFinish(activity,MainActivity.class,bundle);
                    JPushManager.login(TelephonyUtils.getDeviceId(activity),TelephonyUtils.getDeviceId(activity));
//                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailed(String msg) {
                dismissLoadingDialog();
                AlertDialogUtils.showFailedDialog(context,msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        isFinished = true;
    }



    /**
     * 返回当前LoginActivity的状态,避免开机启动重复开启
     *
     * @return boolean
     */
    public static boolean getIsFinished() {
        return isFinished;
    }

    /**
     * 获取服务器版本信息
     */
    private void getVersion() {
        Bundle bundle = getIntent().getExtras();
        // 从bundle数据包中取出数据
        if (null != bundle) {
            AppVersionBean versionBean = (AppVersionBean) bundle.getSerializable(VERSIONKEY);
            // 获取当前版本号进行匹对
            if (null != versionBean) {
                matchVersion(versionBean);
            }
        }
    }


    /**
     * 当用户名焦点发生变化时调用
     */
    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String userName = et_login_user.getText().toString();
            if (!hasFocus && !StringUtils.isBlank(userName)) {
                getPlant(userName);
            }
        }
    };

    /**
     * 从服务器获取营用中心
     */
    private void getPlant(String userName) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        logic.getPlant(map, new LoginLogic.GetPlantListener() {
            @Override
            public void onSuccess(List<String> plants) {
                try {
                    mPlants = plants;
                    if (mPlants.size() > 0) {
                        tv_login_eye.setText(mPlants.get(0));
                    }
                }catch (Exception e){
                    LogUtils.e(TAG,"getPlant异常");
                }
            }

            @Override
            public void onFailed(String msg) {
                mPlants.add("SP2");
                if (mPlants.size() > 0) {
                    tv_login_eye.setText(mPlants.get(0));
                }
                AlertDialogUtils.showFailedDialog(context,msg);
            }
        });
    }

    /**
     * 配对版本号,高于当前版本强制弹出更新框.
     *
     * @param version
     */
    public void matchVersion(AppVersionBean version) {
        float mAppVersion = TelephonyUtils.getMAppVersion(activity);
        float newVersion = StringUtils.string2Float(version.getVernum());
        if (newVersion > mAppVersion) {
//              VersionsSettingDialog.showVersionDialog(activity, version);
        } else {
            return;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size() > 0) {
            AccoutBean accoutBean = accoutBeen.get(0);
            et_login_user.setText(accoutBean.getUsername());
            tv_login_eye.setText(accoutBean.getPlant());
            if ("Y".equals(accoutBean.getIsRemeberPassWord())) {
                et_login_lock.setText(accoutBean.getPassword());
                cb_remeber_password.setChecked(true);
            } else {
                cb_remeber_password.setChecked(false);
            }
            et_login_user.requestFocus();
            getPlant(accoutBean.getUsername());
        }
    }

    @Override
    public String moduleCode() {
        module= ModuleCode.OTHER;
        return module;
    }

}
