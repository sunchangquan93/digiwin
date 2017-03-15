package digiwin.smartdepot.module.activity.sale.saleoutlet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.module.activity.purchase.purchaseinstore.PurchaseInStorageSumActivity;
import digiwin.smartdepot.module.adapter.sale.SaleOutletAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.FilterBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.smartdepot.login.loginlogic.LoginLogic.getUserInfo;

/**
 * @author xiemeng
 * @des 销售出库
 * @date 2017/3/13
 */
public class SaleOutletActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_general_number, R.id.et_barcode_no, R.id.et_department, R.id.et_person,R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_general_number, R.id.tv_barcode_no, R.id.ll_department, R.id.ll_person, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_general_number, R.id.tv_barcode_no,
            R.id.tv_department, R.id.tv_person, R.id.tv_date})
    List<TextView> textViews;


    String startDate = "";
    String endDate = "";

    @BindView(R.id.tv_general_number)
    TextView tvGeneralNumber;
    @BindView(R.id.et_general_number)
    EditText etGeneralNumber;
    @BindView(R.id.ll_general_number)
    LinearLayout llGeneralNumber;
    @BindView(R.id.tv_barcode_no)
    TextView tvBarcodeNo;
    @BindView(R.id.et_barcode_no)
    EditText etBarcodeNo;
    @BindView(R.id.ll_barcode_no)
    LinearLayout llBarcodeNo;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.et_person)
    EditText etPerson;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.iv_date)
    ImageView date;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnFocusChange(R.id.et_general_number)
    void generalnumFocusChange() {
        ModuleUtils.etChange(activity, etGeneralNumber, editTexts);
        ModuleUtils.tvChange(activity, tvGeneralNumber, textViews);
        ModuleUtils.viewChange(llGeneralNumber, views);
    }

    @OnFocusChange(R.id.et_barcode_no)
    void barcodeFocusChange() {
        ModuleUtils.etChange(activity, etBarcodeNo, editTexts);
        ModuleUtils.tvChange(activity, tvBarcodeNo, textViews);
        ModuleUtils.viewChange(llBarcodeNo, views);
    }

    @OnFocusChange(R.id.et_department)
    void departFocusChange() {
        ModuleUtils.etChange(activity, etDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
        ModuleUtils.viewChange(llDepartment, views);
    }

    @OnFocusChange(R.id.et_person)
    void personFocusChange() {
        ModuleUtils.etChange(activity, etPerson, editTexts);
        ModuleUtils.tvChange(activity, tvPerson, textViews);
        ModuleUtils.viewChange(llPerson, views);
    }

    @OnFocusChange(R.id.et_date)
    void FocusChange() {
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
        ModuleUtils.viewChange(llDate, views);
    }

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                startDate = mStartDate;
                endDate = mEndDate;
                etDate.setText(showDate);
            }
        });
    }

    /**
     * 搜索是否显示
     */
    boolean isSearching;
    /**
     * 筛选按钮
     */
    @BindView(R.id.iv_title_setting)
    ImageView search;

    @OnClick(R.id.iv_title_setting)
    void isShowSearch() {
        if (isSearching) {
            isSearching = false;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            return;
        } else {
            isSearching = true;
            ryList.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    CommonLogic commonLogic;

    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search() {
        upDateList();
    }

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
    protected int bindLayoutId() {
        return R.layout.activity_saleoutlet;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        isSearching = true;
        commonLogic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    /**
     * 点击item跳转到汇总界面
     */
    private void onItemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                final FilterResultOrderBean orderData = sumShowBeanList.get(position);
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setReceipt_no(orderData.getDoc_no());
                showLoadingDialog();
                commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SaleOutletSumActivity.LIST, (Serializable) list);
                        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity, SaleOutletSumActivity.class, bundle, SUMCODE);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error);
                    }
                });
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.saleoutlet);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    SaleOutletAdapter adapter;

    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            FilterBean filterBean = new FilterBean();
            filterBean.setDoc_no(etGeneralNumber.getText().toString());
            filterBean.setBarcode_no(etBarcodeNo.getText().toString());
            filterBean.setEmployee_no(etPerson.getText().toString());
            filterBean.setDepartment_no(etDepartment.getText().toString());
            filterBean.setDate_begin(startDate);
            filterBean.setDate_end(endDate);
            showLoadingDialog();
            commonLogic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    dismissLoadingDialog();
                    if (null != list && list.size() > 0) {
                         isSearching=true;
                        ryList.setVisibility(View.VISIBLE);
                        llSearchInput.setVisibility(View.GONE);
                        //查询成功隐藏筛选界面，展示汇总信息
                        sumShowBeanList = list;
                        adapter = new SaleOutletAdapter(activity, sumShowBeanList);
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
                        adapter = new SaleOutletAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "upDateList--getSum--onFailed" + e);
                    }
                }
            });

        } catch (Exception e) {
            LogUtils.e(TAG, "upDateList--getSum--Exception" + e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SUMCODE) {
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
