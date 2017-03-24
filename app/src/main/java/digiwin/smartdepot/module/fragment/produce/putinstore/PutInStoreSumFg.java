package digiwin.smartdepot.module.fragment.produce.putinstore;


import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.activity.produce.putinstore.PutInStoreSecondActivity;
import digiwin.smartdepot.module.adapter.produce.PutInStoreSumAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 入库上架 数据汇总界面
 * @date 2017/2/23
 */
public class PutInStoreSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 单号
     */
    @BindView(R.id.tv_head_post_order)
    TextView tv_head_post_order;
    /**
     * 日期
     */
    @BindView(R.id.tv_head_date)
    TextView tv_head_date;
    /**
     * 部门
     */
    @BindView(R.id.tv_head_department)
    TextView tv_head_department;

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

    PutInStoreSecondActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    PutInStoreSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    FilterResultOrderBean orderData;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_putinstore_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PutInStoreSecondActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            ClickItemPutBean clickItemPutData = new ClickItemPutBean();
            clickItemPutData.setDoc_no(orderData.getDoc_no());
            AccoutBean accoutBean = LoginLogic.getUserInfo();
            if (null != accoutBean) {
                clickItemPutData.setWarehouse_in_no(accoutBean.getWare());
            }
            showLoadingDialog();
            commonLogic.getOrderSumData(clickItemPutData, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = list;
                    adapter = new PutInStoreSumAdapter(activity, sumShowBeanList);
                    ryList.setAdapter(adapter);
                    upDateFlag = true;
                    toDetail();
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<ListSumBean>();
                        adapter = new PutInStoreSumAdapter(activity, sumShowBeanList);
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

    /**
     * 查看单笔料明细
     */
    public void toDetail() {
        try {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int pos) {
                    getDetail(sumShowBeanList.get(pos));
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "toDetail-->" + e);
        }
    }


    /**
     * 查看明细
     */
    private void getDetail(final ListSumBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put("item_no", orderSumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setItem_no(orderSumData.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    private void sureCommit() {
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.mZXVp.setCurrentItem(0);
                        pactivity.createNewModuleId(pactivity.module);
                        initData();
                        pactivity.scanFg.initData();
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

    private void initData() {
        upDateFlag = false;
        commonLogic = CommonLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        Bundle bundle = getActivity().getIntent().getExtras();
        orderData = (FilterResultOrderBean) bundle.getSerializable("orderData");
        tv_head_date.setText(orderData.getCreate_date());
        tv_head_post_order.setText(orderData.getDoc_no());
        tv_head_department.setText(orderData.getDepartment_name());
    }
}
