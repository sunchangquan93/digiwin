package digiwin.smartdepot.module.activity.purchase.purchaseinstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.module.adapter.purchase.PurchaseInStorageAdapter;
import digiwin.smartdepot.module.bean.common.FilterBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.smartdepot.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 采购入库 汇总界面
 *
 * @author 唐孟宇
 */
public class PurchaseInStoreActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_barcode_no, R.id.et_provider, R.id.et_item_name, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_barcode_no, R.id.ll_provider, R.id.ll_item_name, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_barcode_no, R.id.tv_provider, R.id.tv_item_name, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.et_barcode_no)
    EditText et_barcode_no;
    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.ll_barcode_no)
    LinearLayout ll_barcode_no;
    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.tv_barcode_no)
    TextView tv_barcode_no;

    @OnFocusChange(R.id.et_barcode_no)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(ll_barcode_no, views);
        ModuleUtils.etChange(activity, et_barcode_no, editTexts);
        ModuleUtils.tvChange(activity, tv_barcode_no, textViews);
    }

    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.et_provider)
    EditText et_provider;

    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.ll_provider)
    LinearLayout ll_provider;
    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.tv_provider)
    TextView tv_provider;

    @OnFocusChange(R.id.et_provider)
    void departmentFocusChanage() {
        ModuleUtils.viewChange(ll_provider, views);
        ModuleUtils.etChange(activity, et_provider, editTexts);
        ModuleUtils.tvChange(activity, tv_provider, textViews);
    }

    /**
     * 筛选框 品名
     */
    @BindView(R.id.et_item_name)
    EditText et_item_name;

    /**
     * 筛选框 品名
     */
    @BindView(R.id.ll_item_name)
    LinearLayout ll_item_name;
    /**
     * 筛选框 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;

    @OnFocusChange(R.id.et_item_name)
    void materialCodeFocusChanage() {
        ModuleUtils.viewChange(ll_item_name, views);
        ModuleUtils.etChange(activity, et_item_name, editTexts);
        ModuleUtils.tvChange(activity, tv_item_name, textViews);
    }


    /**
     * 筛选框 日期
     */
    @BindView(R.id.et_date)
    EditText et_date;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.ll_date)
    LinearLayout ll_date;
    /**
     * 筛选框 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.iv_date)
    ImageView iv_date;

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(pactivity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_date.setText(showDate);
            }
        });
    }

    @OnFocusChange(R.id.et_date)
    void planDateFocusChanage() {
        ModuleUtils.viewChange(ll_date, views);
        ModuleUtils.etChange(activity, et_date, editTexts);
        ModuleUtils.tvChange(activity, tv_date, textViews);
    }

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    CommonLogic commonLogic;

    PurchaseInStoreActivity pactivity;

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
            if (null != sumShowBeanList && sumShowBeanList.size() > 0) {
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                adapter = new PurchaseInStorageAdapter(pactivity, sumShowBeanList);
                ryList.setAdapter(adapter);
                onItemClick();
            }
        } else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search() {
        //TODO
        upDateList();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASEINSTORE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_in_store_order;
    }

    @Override
    protected void doBusiness() {
        et_date.setKeyListener(null);
        pactivity = (PurchaseInStoreActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        SearchDialog();
    }

    /**
     * 点击item跳转到汇总界面
     */
    private void onItemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                final FilterResultOrderBean orderData = sumShowBeanList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddressContants.ORDERDATA, orderData);
                ActivityManagerUtils.startActivityBundleForResult(pactivity, PurchaseInStoreSecondActivity.class, bundle, SUMCODE);
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.purchase_in_store)+getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    PurchaseInStorageAdapter adapter;

    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 汇总展示
     */
    public void upDateList() {
        FilterBean filterBean = new FilterBean();
        try {
            AccoutBean accoutBean = getUserInfo();
            if (null == accoutBean) {
                return;
            }
            Map<String, String> map = new HashMap<>();
            //仓库
            filterBean.setWarehouse_in_no(accoutBean.getWare());
            //供应商
            if (!StringUtils.isBlank(et_provider.getText().toString())) {
                filterBean.setSupplier_no(et_provider.getText().toString());
            }
            //物料编码
            if (!StringUtils.isBlank(et_barcode_no.getText().toString())) {
                filterBean.setBarcode_no(et_barcode_no.getText().toString());
            }
            //品名
            if (!StringUtils.isBlank(et_item_name.getText().toString())) {
                filterBean.setItem_name(et_item_name.getText().toString());
            }
            //日期
            if (!StringUtils.isBlank(et_date.getText().toString())) {
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            showLoadingDialog();
            commonLogic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    if (null != list && list.size() > 0) {
                        dismissLoadingDialog();
                        //查询成功隐藏筛选界面，展示汇总信息
                        ll_search_dialog.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        iv_title_setting.setVisibility(View.VISIBLE);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        sumShowBeanList = list;
                        adapter = new PurchaseInStorageAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        onItemClick();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        adapter = new PurchaseInStorageAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });

        } catch (Exception e) {
            LogUtils.e(TAG, "updateList--getSum--Exception" + e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SUMCODE) {
                adapter = new PurchaseInStorageAdapter(pactivity, new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
