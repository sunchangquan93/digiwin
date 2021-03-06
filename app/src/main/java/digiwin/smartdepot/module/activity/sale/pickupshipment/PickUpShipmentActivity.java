package digiwin.smartdepot.module.activity.sale.pickupshipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepot.module.activity.common.HaveSourceUnComActivity;
import digiwin.smartdepot.module.activity.common.NoComeUnComActivity;
import digiwin.smartdepot.module.fragment.sale.pickupshipment.PickUpShipmentScanFg;
import digiwin.smartdepot.module.fragment.sale.pickupshipment.PickUpShipmentSumFg;

/**
 * @author 赵浩然
 * @module 出货过账
 * @date 2017/3/24
 */

public class PickUpShipmentActivity extends BaseFirstModuldeActivity{

    public PickUpShipmentActivity pactivity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

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

    ModuleViewPagerAdapter adapter;

    PickUpShipmentScanFg scanFg;

    PickUpShipmentSumFg sumFg;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    String code = "1";

    /**
     * 未完事项
     */
    @BindView(R.id.un_com)
    ImageView unCom;

    @OnClick(R.id.un_com)
    void toUmcom(){
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        bundle.putString(NoComeUnComActivity.MODULECODE, module);
        ActivityManagerUtils.startActivityForBundleData(activity, HaveSourceUnComActivity.class, bundle);
    }


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pickupshipment;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new PickUpShipmentScanFg();
        sumFg = new PickUpShipmentSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.barcode_scan));
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

                if(position == 0){
                    scanFg.upDateList();
                }

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
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PICKUPSHIPMENT;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        pactivity = (PickUpShipmentActivity) activity;
        mName.setText(getResources().getString(R.string.title_pickupshipment));
        unCom.setVisibility(View.VISIBLE);
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }
}
