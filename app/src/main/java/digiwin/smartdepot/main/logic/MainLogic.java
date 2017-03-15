package digiwin.smartdepot.main.logic;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.main.activity.MainActivity;
import digiwin.smartdepot.main.bean.ModuleBean;
import digiwin.smartdepot.main.bean.TotalMode;

import static android.util.TypedValue.applyDimension;

/**
 * Created by ChangquanSun
 * 2017/2/22
 */

public class MainLogic {
    private final String TAG = "MainLogic";
    private int screenWidth;
    private int titleValue;
    private int oldIndex;
    private MainActivity activity;
    /**
     * 采购管理
     */
    private List<ModuleBean> purchaseItems;
    /**
     * 生产管理
     */
    private List<ModuleBean> produceItems;
    /**
     * 库存管理
     */
    private List<ModuleBean> storageItems;
    /**
     * 销售管理
     */
    private List<ModuleBean> salesItems;
    /**
     * 报工管理
     */
    private List<ModuleBean> dailyworkItems;
    /**
     * 看板管理
     */
    private List<ModuleBean> boardItems;
    /**
     * 用户权限模块
     */
    private List<String> powerItems;

    public MainLogic(Context context) {
        activity= (MainActivity) context;
    }

    /**
     * 初始化各个模块
     *
     * @param powerItems     权限数据源
     * @param purchaseItems  采购管理数据源
     * @param produceItems   生产管理数据源
     * @param storageItems   库存管理数据源
     * @param salesItems     销售管理数据源
     * @param dailyworkItems 报工管理数据源
     */
    public void initModule(Context context,List<String> powerItems, List<ModuleBean> purchaseItems,
                                  List<ModuleBean> produceItems, List<ModuleBean> storageItems
            , List<ModuleBean> salesItems, List<ModuleBean> dailyworkItems,List<ModuleBean> boardItems) {
        this.powerItems=powerItems;
        this.purchaseItems=purchaseItems;
        this.produceItems=produceItems;
        this.storageItems= storageItems;
        this.salesItems=salesItems;
        this.dailyworkItems=dailyworkItems;
        this.boardItems=boardItems;

        //初始化采购管理
        ModuleBean purchaseBean1 = new ModuleBean(R.string.title_getgoods_check, R.drawable.checkin, "A001", "com.digiwin.erpfc.biz.intent.GetgoodsCheckListActivity");

        ModuleBean purchaseInStore= new ModuleBean(R.string.purchase_in_store, R.mipmap.lingliaoguozahng, ModuleCode.PURCHASEINSTORE,"android.intent.action.digiwin.PurchaseInStoreActivity");

        ModuleBean materialreceipt = new ModuleBean(R.string.title_material_receipt, R.mipmap.material_receipt, ModuleCode.MATERIALRECEIPTCODE, "android.intent.action.digiwin.MaterialReceiptActivity");
        purchaseItems.add(materialreceipt);

        purchaseItems.add(purchaseBean1);

        purchaseItems.add(purchaseInStore);

        //初始化生产管理
        ModuleBean finishedStorageActivity= new ModuleBean(R.string.finishedstorage, R.mipmap.wangongruku, ModuleCode.FINISHEDSTORAGE,"android.intent.action.digiwin.FinishedStorageActivity");

        ModuleBean transfersToReviewActivity= new ModuleBean(R.string.transfers_to_review, R.mipmap.diaobofuhe, ModuleCode.TRANSFERS_TO_REVIEW,"android.intent.action.digiwin.TransfersToReviewActivity");

        ModuleBean accordingMaterialActivity = new ModuleBean(R.string.according_material,R.mipmap.accordingmaterial,ModuleCode.ACCORDINGMATERIALCODE,"android.intent.action.digiwin.AccordingMaterialActivity");

        ModuleBean distribute= new ModuleBean(R.string.distribute, R.mipmap.shengchanpeihuo, ModuleCode.DISTRIBUTE,"android.intent.action.digiwin.DistributeActivity");

        ModuleBean postMaterial= new ModuleBean(R.string.post_material, R.mipmap.lingliaoguozahng, ModuleCode.POSTMATERIAL,"android.intent.action.digiwin.PostMaterialActivity");

        ModuleBean putInStore = new ModuleBean(R.string.put_in_store, R.mipmap.putaway, ModuleCode.PUTINSTORE,"android.intent.action.digiwin.PutInStoreActivity");

       // ModuleBean workorder = new ModuleBean(R.string.title_work_order,R.mipmap.work_order,ModuleCode.WORKORDERCODE,"android.intent.action.digiwin.WorkOrderActivity");

        ModuleBean materialReturning= new ModuleBean(R.string.mataerial_returning, R.mipmap.return_of_material, ModuleCode.MATERIALRETURNING,"android.intent.action.digiwin.MaterialReturningActivity");

        ModuleBean driectStorage= new ModuleBean(R.string.direct_storage, R.mipmap.direct_storage, ModuleCode.DIRECTSTORAGE,"android.intent.action.digiwin.DirectStorageActivity");

        ModuleBean workorder = new ModuleBean(R.string.title_work_order,R.mipmap.work_order,ModuleCode.WORKORDERCODE,"android.intent.action.digiwin.WorkOrderActivity");

        ModuleBean completingstore = new ModuleBean(R.string.title_completing_store,R.mipmap.complete_storage,ModuleCode.COMPLETINGSTORE,"android.intent.action.digiwin.CompletingStoreActivity");

        produceItems.add(finishedStorageActivity);

        produceItems.add(transfersToReviewActivity);

        produceItems.add(accordingMaterialActivity);

        produceItems.add(distribute);

        produceItems.add(postMaterial);

        produceItems.add(putInStore);

      //  produceItems.add(workorder);

        produceItems.add(materialReturning);

        produceItems.add(workorder);

        produceItems.add(driectStorage);

        produceItems.add(completingstore);

        //初始化库存管理
        ModuleBean storeallotactivity = new ModuleBean(R.string.nocome_allot, R.mipmap.nocome_alllot, ModuleCode.NOCOMESTOREALLOT, "android.intent.action.digiwin.StoreAllotActivity");
        storageItems.add(storeallotactivity);
        //初始化销售管理
        ModuleBean saleoutletactivity = new ModuleBean(R.string.saleoutlet, R.mipmap.saleoutlet, ModuleCode.SALEOUTLET, "android.intent.action.digiwin.SaleOutletActivity");
        salesItems.add(saleoutletactivity);
        //初始化报工管理
        ModuleBean dailyworkBean1 = new ModuleBean(R.string.title_receipt_patch, R.drawable.receiptout, "C001", "com.digiwin.erpfc.biz.intent.ReceiptPatchActivity");
        ModuleBean rcttboardactivity = new ModuleBean(R.string.delivery_uncheck_board, R.drawable.receiptout, ModuleCode.RCCTBOARD, "android.intent.action.digiwin.RcttBoardActivity");
        ModuleBean tctsboardactivity = new ModuleBean(R.string.tcts_board, R.drawable.receiptout, ModuleCode.RCCTBOARD, "android.intent.action.digiwin.TctsBoardActivity");
        dailyworkItems.add(dailyworkBean1);
        boardItems.add(rcttboardactivity);
        boardItems.add(tctsboardactivity);
        // TODO: 2017/3/14 暂时屏蔽测试用

//        showDetailModule();

    }

    /**
     * 判断权限小模块
     */
    private void showDetailModule() {
        List<ModuleBean> tempPurchaseItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < purchaseItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (purchaseItems.get(i).getId().equals(powerItems.get(j))) {
                    tempPurchaseItems.add(purchaseItems.get(i));
                }
            }
        }
        purchaseItems = tempPurchaseItems;

        List<ModuleBean> tempProduceItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < produceItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (produceItems.get(i).getId().equals(powerItems.get(j))) {
                    tempProduceItems.add(produceItems.get(i));
                }
            }
        }
        produceItems = tempProduceItems;

        List<ModuleBean> tempStorageItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < storageItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (storageItems.get(i).getId().equals(powerItems.get(j))) {
                    tempStorageItems.add(storageItems.get(i));
                }
            }
        }
        storageItems = tempStorageItems;

        List<ModuleBean> tempSalesItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < salesItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (salesItems.get(i).getId().equals(powerItems.get(j))) {
                    tempSalesItems.add(salesItems.get(i));
                }
            }
        }
        salesItems = tempSalesItems;

        List<ModuleBean> tempDailyworkItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < dailyworkItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (dailyworkItems.get(i).getId().equals(powerItems.get(j))) {
                    tempDailyworkItems.add(dailyworkItems.get(i));
                }
            }
        }
        dailyworkItems = tempDailyworkItems;

        List<ModuleBean> tempBoardItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < boardItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (boardItems.get(i).getId().equals(powerItems.get(j))) {
                    tempBoardItems.add(boardItems.get(i));
                }
            }
        }
        boardItems = tempBoardItems;

    }

    /**
     * 显示标题栏
     */
    public void showTitle(List<TotalMode> totalModes,List<String> titles) {
        try {
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < purchaseItems.size(); j++) {
                    if (powerItems.get(i).equals(purchaseItems.get(j).getId())) {
                        TotalMode totalMode = new TotalMode(activity.getString(R.string.purchasemanger), purchaseItems);
                        totalModes.add(totalMode);
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < produceItems.size(); j++) {
                    if (powerItems.get(i).equals(produceItems.get(j).getId())) {
                        totalModes.add(new TotalMode(activity.getString(R.string.producemanager), produceItems));
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < storageItems.size(); j++) {
                    if (powerItems.get(i).equals(storageItems.get(j).getId())) {
                        totalModes.add(new TotalMode(activity.getString(R.string.storagemanager), storageItems));
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < salesItems.size(); j++) {
                    if (powerItems.get(i).equals(salesItems.get(j).getId())) {
                        totalModes.add(new TotalMode(activity.getString(R.string.salesmanager), salesItems));
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < dailyworkItems.size(); j++) {
                    if (powerItems.get(i).equals(dailyworkItems.get(j).getId())) {
                        totalModes.add(new TotalMode(activity.getString(R.string.dailyworkmanager), dailyworkItems));
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (int i = 0; i < powerItems.size(); i++) {
                boolean flag = false;
                for (int j = 0; j < boardItems.size(); j++) {
                    if (powerItems.get(i).equals(boardItems.get(j).getId())) {
                        totalModes.add(new TotalMode(activity.getString(R.string.boardmanager), boardItems));
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            for (TotalMode mode : totalModes) {
                titles.add(mode.name);
            }
        } catch (Exception e) {
            Log.e(TAG, "MainActivity--showTitle-----error");
        }
    }

    public void setCustomTab(Context context, TabLayout tablayout, List<String> titles) {
        //设置tab布局样式
        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tablayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(setTabStyle(context, i, tablayout, titles));
            }
        }

        if ((titles.size() + 1) * titleValue >= screenWidth) {
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tablayout.setTabMode(TabLayout.MODE_FIXED);
        }
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private View setTabStyle(Context context, int i, TabLayout tablayout, List<String> titles) {
        titleValue = ViewUtils.Dp2Px(context, 75);
        screenWidth = ViewUtils.getScreenWidth(context);
        LogUtils.e("sunchangquan", "titleValue==" + titleValue + ",screenWidth--" + screenWidth);
        View view = LayoutInflater.from(context).inflate(R.layout.tablayout_item, tablayout, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(titleValue, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        TextView tv = (TextView) view.findViewById(R.id.tablayout_tv);
//        ImageView iv = (ImageView) view.findViewById(R.id.tablayout_im);
        View iv = (View) view.findViewById(R.id.tablayout_im);
        tv.setText(titles.get(i));
        if (i == tablayout.getSelectedTabPosition()) {
            tv.setTextColor(context.getResources().getColor(R.color.Assist_color));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            iv.setVisibility(View.VISIBLE);
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.tab_item_text));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            iv.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void setPonit(Context context, List<String> titles, List<View> points, LinearLayout linearLayoutPoints) {
        int value = (int) applyDimension(TypedValue.COMPLEX_UNIT_SP, 7, context.getResources().getDisplayMetrics());
        for (int i = 0; i < titles.size(); i++) {
            //小点
            View view = new View(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(value, value);
            layoutParams.leftMargin = value;
            if (i == 0) {
                view.setBackgroundResource(R.drawable.point_seletor_yes);
            } else {
                view.setBackgroundResource(R.drawable.point_seletor_no);
            }
            view.setLayoutParams(layoutParams);
            linearLayoutPoints.addView(view);
            points.add(view);
        }
    }

    public void initListener(final Context context,final List<View> points,final ViewPager viewPager,TabLayout tablayout){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                points.get(position).setBackgroundResource(R.drawable.point_seletor_yes);
                points.get(oldIndex).setBackgroundResource(R.drawable.point_seletor_no);
                oldIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextColor(context.getResources().getColor(R.color.Assist_color));
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                ((View) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.VISIBLE);
//                ((ImageView) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv))
                        .setTextColor(context.getResources().getColor(R.color.tab_item_text));
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                ((View) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.INVISIBLE);
//                ((ImageView) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
