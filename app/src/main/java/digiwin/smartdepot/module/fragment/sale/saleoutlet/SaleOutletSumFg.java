package digiwin.smartdepot.module.fragment.sale.saleoutlet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.activity.sale.saleoutlet.SaleOutletActivity;
import digiwin.smartdepot.module.adapter.sale.SaleOutletSumAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 销售出库汇总页
 * @date 2017/3/16
 */
public class SaleOutletSumFg extends BaseFragment {
    @BindView(R.id.tv_head_post_order)
    TextView tvHeadPostOrder;
    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
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
    /**
     * 出通单号
     */
    private ClickItemPutBean mPutBean;

    /**
     * 订阅成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(ClickItemPutBean bean) {
        mPutBean = bean;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    SaleOutletActivity pactivity;
    CommonLogic commonLogic;

    boolean upDateFlag;

    List<ListSumBean>  sumBeanList;
    private BaseRecyclerAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_saleoutlet_sum;
    }

    @Override
    protected void doBusiness() {
        upDateFlag=false;
        sumBeanList=new ArrayList<>();
        pactivity = (SaleOutletActivity) this.activity;
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    public void upDateList() {
        if (null == mPutBean) {
            showFailedDialog(R.string.scan_sale, new OnDialogClickListener() {
                @Override
                public void onCallback() {
                    pactivity.mZXVp.setCurrentItem(0);
                }
            });
            return;
        }
        showLoadingDialog();
        commonLogic.getOrderSumData(mPutBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                dismissLoadingDialog();
                sumBeanList=list;
                adapter = new SaleOutletSumAdapter(context, sumBeanList);
                ryList.setAdapter(adapter);
                if (null!=list&&list.size()>0){
                    upDateFlag=true;
                    toDetail();
                    tvHeadPostOrder.setText(list.get(0).getNotice_no());
                    tvCustom.setText(list.get(0).getCustomer_no()+"|"+list.get(0).getCustomer_name());
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                upDateFlag=false;
                sumBeanList.clear();
                adapter = new SaleOutletSumAdapter(context, sumBeanList);
                ryList.setAdapter(adapter);
                showFailedDialog(error);
            }
        });

    }

    /**
     * 查看单笔料明细
     */
    public void toDetail() {
        try {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int pos) {
                    getDetail(sumBeanList.get(pos));
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
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();

        float numb1 = StringUtils.string2Float(orderSumData.getReq_qty());
        float numb2 = StringUtils.string2Float(orderSumData.getStock_qty());
        if(numb1 > numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getStock_qty());
        }
        if(numb1 < numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getReq_qty());
        }
        if(numb1 == numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getStock_qty());
        }

        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setAvailable_in_qty(StringUtils.getMinQty(orderSumData.getStock_qty(),orderSumData.getReq_qty()));
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    private void sureCommit(){
        if (!upDateFlag){
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
                        tvCustom.setText("");
                        tvHeadPostOrder.setText("");
                        pactivity.scanFg.initData();
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


}
