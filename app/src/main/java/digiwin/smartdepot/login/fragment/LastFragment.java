package digiwin.smartdepot.login.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.login.activity.LoginActivity;


/**
 * @des      引导页最后一个fragment
 * @author  xiemeng
 * @date    2017/1/4
 */
public class LastFragment extends BaseFragment {

    /**
     * 内圈
     */
    @BindView(R.id.welcome_incircle)
    View welcome_incircle;
    /**
     * 外圈
     */
    @BindView(R.id.welcome_outcircle)
    View welcome_outcircle;

    /**
     * 立即开启
     */
    @BindView(R.id.welcome_start)
    View welcome_start;
    static LastFragment pane;
    private Bundle bundle;

    public static LastFragment newInstance(Bundle bundle) {
        if (null==pane){
            pane = new LastFragment();
            pane.setArguments(bundle);
        }
        return pane;
    }
    @Override
    protected int bindLayoutId() {
        bundle = getArguments();//从activity传过来的Bundle
        return R.layout.welcome_fragment03;
    }

    @Override
    protected void doBusiness() {
        rotate();
    }

    /**
     * 旋转动画
     */
    private void rotate(){
        ObjectAnimator outcircle = ObjectAnimator.ofFloat(welcome_outcircle, "rotation", 0F, 360F).setDuration(10000);
        outcircle.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        outcircle.start();
        ObjectAnimator incircle = ObjectAnimator.ofFloat(welcome_incircle, "rotation", 360F, 0F).setDuration(10000);
        incircle.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        incircle.start();
    }

    /**
     * 跳转至登陆
     */
    @OnClick(R.id.welcome_start)
    void gotoLogin(){

        SharedPreferencesUtils.put(activity, SharePreKey.ISFIRSTKEY,false);
        ActivityManagerUtils.startActivityForBundleDataFinish(activity, LoginActivity.class,bundle);
        activity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }
}