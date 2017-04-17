package digiwin.smartdepot.login.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseActivity;
import digiwin.smartdepot.login.bean.AppVersionBean;
import digiwin.smartdepot.login.fragment.FirstFragment;
import digiwin.smartdepot.login.fragment.LastFragment;
import digiwin.smartdepot.login.fragment.SecondFragment;
import digiwin.smartdepot.login.loginlogic.AppVersionLogic;

import static digiwin.smartdepot.login.activity.LoginActivity.VERSIONKEY;

/**
 * @des      引导页
 * @author  xiemeng
 * @date    2017/1/3
 */
public class WelcomeGuideActivity extends BaseActivity {

    /**
     * viewpager
     */
    @BindView(R.id.pager)
    ViewPager pager;

    /**
     * 布局
     */
    @BindView(R.id.rootlayout)
    View rootlayout;
    /**
     * 引导页个数
     */
    private  static final int NUM_PAGES = 3;
    /**
     * 适配
     */
    private PagerAdapter pagerAdapter;

    private Bundle bundle;

    private AppVersionLogic logic;

    private final int FINISH=1001;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try{
                switch (msg.what){
                    case FINISH:
                        ActivityManagerUtils.startActivityForBundleDataFinish(activity,LoginActivity.class,bundle);
                        overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                        break;
                }

            }catch (Exception e){
                LogUtils.e(TAG,"mHandler异常");
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_welcomeguide;
    }

    @Override
    protected void initNavigationTitle() {

    }
    @Override
    protected void doBusiness() {
        logic = AppVersionLogic.getInstance(context,module,mTimestamp.toString());
        bundle=new Bundle();
        isFirstUse();
        transparentStatusBar();
        getVersion();
    }

    /**
     * ViewPager的展示
     */
    private  void viewPager(){
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    /**
     * 判断是否为第一次使用
     */
    private void isFirstUse() {
        boolean isFirst = (boolean) SharedPreferencesUtils.get(activity, SharePreKey.ISFIRSTKEY, true);
        if (!isFirst){
            pager.setVisibility(View.GONE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FINISH),2000);
        }else {
            rootlayout.setVisibility(View.GONE);
            viewPager();
        }
   }

    /**
     * 获取版本更新
     */
    private  void getVersion(){
        HashMap<String, String> map = new HashMap<>();
        logic.getNewVersion(map, new AppVersionLogic.GetNewVersionListener() {
            @Override
            public void onSuccess(AppVersionBean bean) {
                bundle.putSerializable(VERSIONKEY,bean);
            }
            @Override
            public void onFailed(String msg) {
                showToast(msg);
            }
        });
    }



    /**
     * 适配器，动态创建fragment
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tp = null;
            switch(position){
                case 0:
                    tp = FirstFragment.newInstance();
                    break;
                case 1:
                    tp = SecondFragment.newInstance();
                    break;
                case 2:
                    tp = LastFragment.newInstance(bundle);
                    break;
            }

            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public String moduleCode() {
        module= ModuleCode.OTHER;
        return module;
    }
}
