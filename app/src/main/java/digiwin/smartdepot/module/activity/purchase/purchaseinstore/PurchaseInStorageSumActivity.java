package digiwin.smartdepot.module.activity.purchase.purchaseinstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.adapter.purchase.PurchaseInStorageSumAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * 采购入库 汇总界面
 * @author 唐孟宇
 */
public class PurchaseInStorageSumActivity extends BaseFirstModuldeActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.ll_post_order)
    LinearLayout ll_post_order;

    /**
     * 单头 发料单号
     */
    @BindView(R.id.tv_head_post_order)
    TextView tv_post_order;
    /**
     * 单头 日期
     */
    @BindView(R.id.tv_head_plan_date)
    TextView tv_plan_date;
    /**
     * 单头 供应商
     */
    @BindView(R.id.tv_head_provider)
    TextView tv_provider;

    @BindView(R.id.commit)
    Button commit;

    /**
     * 提交
     */
    @OnClick(R.id.commit)
    void commit(){
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                commitData();
            }

            @Override
            public void onCallback2() {

            }
        });

    }

    private void commitData() {
        showLoadingDialog();
        HashMap<String,String> map = new HashMap<>();
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                adapter = new PurchaseInStorageSumAdapter(pactivity,new ArrayList<ListSumBean>());
                ryList.setAdapter(adapter);
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        createNewModuleId(module);
                        //提交成功，退出汇总界面
                        pactivity.finish();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    /**
     * 跳转扫描页使用
     */
    public final int SCANCODE = 1211;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1212;

    CommonLogic commonLogic;

    PurchaseInStorageSumActivity pactivity;

    PurchaseInStorageSumAdapter adapter;

    List<ListSumBean> sumDataList = new ArrayList<>();
    /**
     * 从待办事项界面带过来的单头数据
     */
    FilterResultOrderBean orderData;
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
        return R.layout.activity_purchase_in_store_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseInStorageSumActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.purchase_in_store);
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        sumDataList = (List<ListSumBean>) getIntent().getExtras().getSerializable("list");
        adapter = new PurchaseInStorageSumAdapter(pactivity,sumDataList);
        ryList.setAdapter(adapter);
        ryList.setFocusable(false);
        try{
            tv_post_order.setText(sumDataList.get(0).getReceipt_no());
            tv_plan_date.setText(sumDataList.get(0).getReceipt_date());
            tv_provider.setText(sumDataList.get(0).getSupplier_name());
        }catch(Exception e){
            e.printStackTrace();
        }
        ll_post_order.requestFocus();
        onItemClick();
    }

    /**
     * 点击跳转到扫描界面
     */
    public void onItemClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ListSumBean orderSumData = sumDataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderData",orderData);
                bundle.putSerializable("orderSumData",orderSumData);
                bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                ActivityManagerUtils.startActivityBundleForResult(pactivity,PurchaseInStorageScanActivity.class,bundle,SCANCODE);
            }
        });
    }

    /**
     * 跳转到明细界面
     */
    public void getDetail(ListSumBean sumData){
        HashMap<String,String> map = new HashMap<>();
        map.put("item_no",sumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(sumData.getItem_no());
        sumShowBean.setItem_name(sumData.getItem_name());
        showLoadingDialog();
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                if(null != detailShowBeen && detailShowBeen.size() >0){
                Bundle bundle = new Bundle();
                bundle.putSerializable(CommonDetailActivity.ONESUM,sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE,module);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(pactivity,CommonDetailActivity.class,bundle,DETAILCODE);
                }else{
                    dismissLoadingDialog();
                    showFailedDialog(getResources().getString(R.string.nodate));
                    return;
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if(requestCode == DETAILCODE || requestCode ==SCANCODE){
                upDateList();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void upDateList(){
        ClickItemPutBean clickItemPutBean = new ClickItemPutBean();
        clickItemPutBean.setDoc_no(orderData.getDoc_no());
        clickItemPutBean.setReceipt_date(orderData.getCreate_date());
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
           clickItemPutBean.setWarehouse_out_no(accoutBean.getWare());
        }
        showLoadingDialog();
        commonLogic.getOrderSumData(clickItemPutBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                sumDataList.clear();
                sumDataList = list;
                adapter = new PurchaseInStorageSumAdapter(pactivity,sumDataList);
                ryList.setAdapter(adapter);
                dismissLoadingDialog();
                onItemClick();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                adapter = new PurchaseInStorageSumAdapter(pactivity,new ArrayList<ListSumBean>());
                ryList.setAdapter(adapter);
            }
        });
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
