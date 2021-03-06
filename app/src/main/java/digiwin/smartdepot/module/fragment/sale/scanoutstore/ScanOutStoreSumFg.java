package digiwin.smartdepot.module.fragment.sale.scanoutstore;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.sale.scanout.ScanOutDetailActivity;
import digiwin.smartdepot.module.activity.sale.scanout.ScanOutStoreActivity;
import digiwin.smartdepot.module.adapter.sale.scanout.ScanOutStoreSumAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;
import digiwin.smartdepot.module.logic.sale.scanoutstore.ScanOutStoreLogic;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutStoreSumFg extends BaseFragment {

    @BindView(R.id.tv_head_post_order)
    TextView tvHeadPostOrder;
    @BindView(R.id.tv_head_date)
    TextView tvHeadDate;
    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.ll_put_in_store_head)
    LinearLayout llPutInStoreHead;
    @BindView(R.id.commit)
    Button commit;
    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                sureCommit();
            }
            @Override
            public void onCallback2() {

            }
        });
    }
    public void sureCommit(){
        if (!upDateFlag){
            showFailedDialog(R.string.nodate);
            return;
        }showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        sActivity.finish();
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
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    private ScanOutStoreActivity sActivity;

    private CommonLogic commonLogic;

    private ScanOutStoreLogic storeLogic;

    boolean upDateFlag;

    List<ListSumBean> list;

    ScanOutStoreSumAdapter adapter;
    /**
     * 单头数据
     */
    ClickItemPutBean mPutBean;


    private String order;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_scanout_store_sum;
    }

    @Override
    protected void doBusiness() {
        sActivity = (ScanOutStoreActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        upDateFlag = false;
        Bundle extras = sActivity.getIntent().getExtras();
        commonLogic = CommonLogic.getInstance(activity,sActivity.module,sActivity.mTimestamp.toString());
        storeLogic = ScanOutStoreLogic.getInstance(activity,sActivity.module,sActivity.mTimestamp.toString());
        tvCustom.setText(extras.getString(AddressContants.CUSTOM));
        tvHeadDate.setText(extras.getString(AddressContants.DATE));
        order = extras.getString(AddressContants.DOC_NO);
        tvHeadPostOrder.setText(order);
        mPutBean=new ClickItemPutBean();
        mPutBean.setDoc_no(order);
        mPutBean.setWarehouse_no(LoginLogic.getWare());
        list = new ArrayList<>();
    }

    public void upDateList() {
        try {
            list.clear();
            adapter = new ScanOutStoreSumAdapter(sActivity,list);
            ryList.setAdapter(adapter);
            showLoadingDialog();
            commonLogic.getOrderSumData(mPutBean, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> datas) {
                    dismissLoadingDialog();
                    list = datas;
                    adapter = new ScanOutStoreSumAdapter(sActivity,list);
                    ryList.setAdapter(adapter);
                    if (null != list && list.size() > 0) {
                        upDateFlag = true;
                        toDetail();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    list.clear();
                    adapter = new ScanOutStoreSumAdapter(context, list);
                    ryList.setAdapter(adapter);
                    showFailedDialog(error);
                }
            });
        }catch (Exception e){
            LogUtils.e(TAG, "updateList--getSum--Exception" + e);
        }
    }
    /**
     * 查看单笔料明细
     */
    public void toDetail() {
        try {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int pos) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AddressContants.ITEM_NO, list.get(pos).getItem_no());
                    bundle.putString(AddressContants.MODULEID_INTENT, sActivity.mTimestamp.toString());
                    ActivityManagerUtils.startActivityBundleForResult(activity, ScanOutDetailActivity.class,bundle,sActivity.DETAILCODE);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "toDetail-->" + e);
        }
    }
}
