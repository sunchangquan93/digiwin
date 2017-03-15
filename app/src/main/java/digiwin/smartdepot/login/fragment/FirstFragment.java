package digiwin.smartdepot.login.fragment;

import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;


/**
 * @des      引导页面使用单图片的fragment
 * @author  xiemeng
 * @date    2017/1/3
 */

public class FirstFragment extends BaseFragment {
    static FirstFragment pane=null;

    public static FirstFragment newInstance() {
        if (null==pane){
            pane = new FirstFragment();
        }
        return pane;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.welcome_fragment01;
    }

    @Override
    protected void doBusiness() {

    }




}