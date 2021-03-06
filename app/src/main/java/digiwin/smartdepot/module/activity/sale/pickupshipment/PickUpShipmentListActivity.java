package digiwin.smartdepot.module.activity.sale.pickupshipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.adapter.sale.pickupshipment.PickUpShipmentListAdapter;
import digiwin.smartdepot.module.bean.common.FilterBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 捡料出货清单 出货过账
 * @date 2017/3/22
 */

public class PickUpShipmentListActivity extends BaseTitleActivity{

    private PickUpShipmentListActivity activity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;
    /**
     * 列表布局
     */
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindViews({R.id.ll_shipping_order,R.id.ll_item_no,R.id.ll_custom,
            R.id.ll_salesman,R.id.ll_operating_department,R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_shipping_order,R.id.tv_item_no,R.id.tv_custom,
            R.id.tv_salesman,R.id.tv_operating_department,R.id.tv_plan_date})
    List<TextView> textViews;
    @BindViews({R.id.et_shipping_order,R.id.et_item_no,R.id.et_custom,
            R.id.et_salesman,R.id.et_operating_department,R.id.et_plan_date})
    List<EditText> editTexts;

    /**
     * 出货单号
     */
    @BindView(R.id.ll_shipping_order)
    LinearLayout ll_shipping_order;
    @BindView(R.id.tv_shipping_order)
    TextView tv_shipping_order;
    @BindView(R.id.et_shipping_order)
    EditText et_shipping_order;

    @OnFocusChange(R.id.et_shipping_order)
    void shipping_orderFocusChanage() {
        ModuleUtils.viewChange(ll_shipping_order, views);
        ModuleUtils.tvChange(activity, tv_shipping_order, textViews);
        ModuleUtils.etChange(activity, et_shipping_order, editTexts);
    }

    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout ll_item_no;
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    @BindView(R.id.et_item_no)
    EditText et_item_no;

    @OnFocusChange(R.id.et_item_no)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(ll_item_no, views);
        ModuleUtils.tvChange(activity, tv_item_no, textViews);
        ModuleUtils.etChange(activity, et_item_no, editTexts);
    }

    /**
     * 客户
     */
    @BindView(R.id.ll_custom)
    LinearLayout ll_custom;
    @BindView(R.id.tv_custom)
    TextView tv_custom;
    @BindView(R.id.et_custom)
    EditText et_custom;

    @OnFocusChange(R.id.et_custom)
    void customFocusChanage() {
        ModuleUtils.viewChange(ll_custom, views);
        ModuleUtils.tvChange(activity, tv_custom, textViews);
        ModuleUtils.etChange(activity, et_custom, editTexts);
    }

    /**
     * 业务员
     */
    @BindView(R.id.ll_salesman)
    LinearLayout ll_salesman;
    @BindView(R.id.tv_salesman)
    TextView tv_salesman;
    @BindView(R.id.et_salesman)
    EditText et_salesman;

    @OnFocusChange(R.id.et_salesman)
    void salesmanFocusChanage() {
        ModuleUtils.viewChange(ll_salesman, views);
        ModuleUtils.tvChange(activity, tv_salesman, textViews);
        ModuleUtils.etChange(activity, et_salesman, editTexts);
    }

    /**
     * 业务部门
     */
    @BindView(R.id.ll_operating_department)
    LinearLayout ll_operating_department;
    @BindView(R.id.tv_operating_department)
    TextView tv_operating_department;
    @BindView(R.id.et_operating_department)
    EditText et_operating_department;

    @OnFocusChange(R.id.et_operating_department)
    void operating_departmentFocusChanage() {
        ModuleUtils.viewChange(ll_operating_department, views);
        ModuleUtils.tvChange(activity, tv_operating_department, textViews);
        ModuleUtils.etChange(activity, et_operating_department, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.iv_plan_date)
    ImageView iv_plan_date;
    @BindView(R.id.tv_plan_date)
    TextView tv_plan_date;
    @BindView(R.id.ll_plan_date)
    LinearLayout ll_plan_date;

    @BindView(R.id.et_plan_date)
    EditText et_plan_date;
    @OnFocusChange(R.id.et_plan_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(ll_plan_date, views);
        ModuleUtils.tvChange(activity, tv_plan_date, textViews);
        ModuleUtils.etChange(activity, et_plan_date, editTexts);
    }

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick(){
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_plan_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_plan_date.setText(showDate);
            }
        });
    }

    PickUpShipmentListAdapter adapter;

    CommonLogic commonLogic;

    private final int SCANCODE = 1234;

    private List<FilterResultOrderBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;
    @OnClick(R.id.btn_search_sure)
    void search(){
        //待办事项展示
        FilterBean filterBean = new FilterBean();
        try {
            showLoadingDialog();
            filterBean.setDoc_no(et_shipping_order.getText().toString().trim());
            filterBean.setWarehouse_out_no(LoginLogic.getWare());
            if(!StringUtils.isBlank(et_item_no.getText().toString().trim())){
                filterBean.setItem_no(et_item_no.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_custom.getText().toString().trim())){
                filterBean.setCustomer_no(et_custom.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_salesman.getText().toString().trim())){
                filterBean.setEmployee_no(et_salesman.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_operating_department.getText().toString().trim())){
                filterBean.setDepartment_no(et_operating_department.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_plan_date.getText().toString())){
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        commonLogic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if(null != list && list.size() > 0){
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    dataList = new ArrayList<FilterResultOrderBean>();
                    dataList = list;
                    adapter = new PickUpShipmentListAdapter(activity,list);
                    ryList.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            Bundle bundle = new Bundle();
                            FilterResultOrderBean data = list.get(position);
                            bundle.putSerializable("data",data);
                            ActivityManagerUtils.startActivityBundleForResult(activity,PickUpShipmentActivity.class,bundle,SCANCODE);
                        }
                    });
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                dataList = new ArrayList<FilterResultOrderBean>();
                adapter = new PickUpShipmentListAdapter(activity,dataList);
                ryList.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void doBusiness() {
        et_plan_date.setKeyListener(null);
        commonLogic = CommonLogic.getInstance(activity,module,mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pickupshipmentlist;
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
        activity = this;
        mName.setText(getString(R.string.title_pickupshipment_list)+getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog(){
        if(ll_search_dialog.getVisibility() == View.VISIBLE){
            if(null != dataList && dataList.size()>0){
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
//                adapter = new PickUpShipmentListAdapter(activity,dataList);
//                ryList.setAdapter(adapter);
//                onItemClick(dataList);
            }
        }else{
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == SCANCODE){
                dataList.clear();
                adapter = new PickUpShipmentListAdapter(activity,dataList);
                ryList.setAdapter(adapter);
                search();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
