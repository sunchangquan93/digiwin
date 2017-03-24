package digiwin.smartdepot.module.activity.purchase.purchasecheck;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleHActivity;

/**
 * @author xiemeng
 * @des 采购收货图片展示
 * @date 2017/3/21
 */
public class PurchaseCheckShowImgActivity extends BaseTitleHActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.checkimg_vp)
    ViewPager checkimgVp;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_check_showimg;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.showpic);
    }

    @Override
    protected void doBusiness() {
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASECHECK;
        return module;
    }

}
