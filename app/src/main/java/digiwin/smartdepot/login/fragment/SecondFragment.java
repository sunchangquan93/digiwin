package digiwin.smartdepot.login.fragment;

import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;

/**
 * @des      引导页面使用单图片的fragment
 * @author  xiemeng
 * @date    2017/1/3
 */

public class SecondFragment extends BaseFragment {
 
    static SecondFragment pane=null;
 
    public static SecondFragment newInstance() {
        if (null==pane){
            pane = new SecondFragment();
        }
        return pane;
    }
    @Override
    protected int bindLayoutId() {
        return R.layout.welcome_fragment02;
    }

    @Override
    protected void doBusiness() {

    }

}