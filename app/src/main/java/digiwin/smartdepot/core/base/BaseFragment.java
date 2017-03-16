package digiwin.smartdepot.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import digiwin.library.base.BaseAppFragment;
/**
 * Created by ChangquanSun
 * 2017/1/9
 */

public abstract class BaseFragment extends BaseAppFragment {
    protected String TAG;
    protected View mFragmentView;
    private Unbinder unBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            TAG = this.getClass().getSimpleName();
            mFragmentView = inflater.inflate(bindLayoutId(), null);
        }
        unBind = ButterKnife.bind(this, mFragmentView);
        doBusiness();
        return mFragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
        if (unBind != null) unBind.unbind();
        if (null != mFragmentView) {
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
        }
    }

}