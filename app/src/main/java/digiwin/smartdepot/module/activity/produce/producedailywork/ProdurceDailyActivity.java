package digiwin.smartdepot.module.activity.produce.producedailywork;

import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;

/**
 * Created by lenovo on 2017/6/1.
 */

public class ProdurceDailyActivity extends BaseTitleActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRODUCEDAILYWORK;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_produce_daily;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.produce_dailywork);
    }

    @Override
    protected void doBusiness() {

    }

}
