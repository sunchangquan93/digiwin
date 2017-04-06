package digiwin.smartdepot.module.activity.dailywork.dailworkscan;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepot.module.fragment.dailywork.entrance.EntranceDetailFg;
import digiwin.smartdepot.module.fragment.dailywork.entrance.EntranceScanFg;

/**
 * @author maoheng
 * @des 扫入扫描
 * @date 2017/4/1
 */

public class EntranceActivity extends BaseFirstModuldeActivity {

    /**
     * 作业号
     */
    private String mode;

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
    public EntranceScanFg scanFg;
    /**
     * 明细提交
     */
    public EntranceDetailFg detailFg;

    ModuleViewPagerAdapter adapter;

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.scanin_scan);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        mode = ModuleCode.SCANINSCAN;
        return mode;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_entrance;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new EntranceScanFg();
        detailFg = new EntranceDetailFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(detailFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.scandetail));
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
    private void select() {
        mZXVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    detailFg.upDateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }

}