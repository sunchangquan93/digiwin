package digiwin.smartdepot.module.activity.sale.saleoutlet;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepot.module.fragment.sale.saleoutlet.SaleOutletScanFg;
import digiwin.smartdepot.module.fragment.sale.saleoutlet.SaleOutletSumFg;

/**
 * @author xiemeng
 * @des 销售出库
 * @date 2017/3/13
 */
public class SaleOutletActivity extends BaseFirstModuldeActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * tab
     */
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    /**
     * ViewPager
     */
    @BindView(R.id.module_vp)
    public ViewPager mZXVp;

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
    SaleOutletScanFg scanFg;
    /**
     * 汇总提交
     */
    SaleOutletSumFg sumFg;

    ModuleViewPagerAdapter adapter;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.SALEOUTLET;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.saleoutlet);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_saleoutlet;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }


    /**
     * 初始化Fragment
     */
    private void initFragment() {

        scanFg = new SaleOutletScanFg();
        sumFg = new SaleOutletSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        mZXVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(mZXVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        select();
    }

    /**
     * 滑动
     */
    private void select() {
        mZXVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    sumFg.upDateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == DETAILCODE) {
                sumFg.upDateList();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "onActivityResult-->" + e);
        }
    }


    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }

}
