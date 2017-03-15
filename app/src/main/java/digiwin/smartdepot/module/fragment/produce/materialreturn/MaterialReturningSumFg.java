package digiwin.smartdepot.module.fragment.produce.materialreturn;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.activity.produce.materialreturning.MaterialReturningActivity;
import digiwin.smartdepot.module.adapter.produce.MaterialReturningSumAdapter;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.bean.produce.MaterialReturningHeaderBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 孙长权
 * @des 生产退料数据汇总界面
 */
public class MaterialReturningSumFg extends BaseFragment {
    private static MaterialReturningSumFg instance;

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    /**
     * 退料单号
     */
    @BindView(R.id.tv_material_returning_number)
    TextView tvMaterialNumber;
    /**
     * 申请部门
     */
    @BindView(R.id.tv_apply_branch)
    TextView tvBaranch;
    /**
     * 申请日期
     */
    @BindView(R.id.tv_apply_date)
    TextView tvDate;

    MaterialReturningActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    MaterialReturningSumAdapter adapter;

    List<SumShowBean> sumShowBeanList;


    public static MaterialReturningSumFg getInstanceFg(){
        if(instance == null){
            instance=new MaterialReturningSumFg();
        }
        return instance;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_material_returning_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (MaterialReturningActivity) activity;
        commonLogic = CommonLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 订阅成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(MaterialReturningHeaderBean bean){
        tvMaterialNumber.setText(bean.getMaterialNumber());
        tvBaranch.setText(bean.getBaranch());
        tvDate.setText(bean.getDate());
    }
    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            Map<String, String> map = new HashMap<>();
            showLoadingDialog();
            commonLogic.getSum(map, new CommonLogic.GetSumListener() {
                    @Override
                    public void onSuccess(List<SumShowBean> list) {
                        sumShowBeanList = list;
                        adapter = new MaterialReturningSumAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        upDateFlag = true;
                        toDetail();
                        dismissLoadingDialog();
                    }

                @Override
                public void onFailed(String error) {
                    upDateFlag = false;
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<SumShowBean>();
                        adapter = new MaterialReturningSumAdapter(activity, sumShowBeanList);
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
    private void getDetail(final SumShowBean sumShowBean) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put("item_no", sumShowBean.getItem_no());
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
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        //// TODO: 2017/3/10 为何只传空map 
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
}
