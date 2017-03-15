package digiwin.smartdepot.module.fragment.dailywork.orderwork;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import digiwin.smartdepot.module.activity.produce.finishstorage.FinishedStorageActivity;
import digiwin.smartdepot.module.adapter.produce.FinishedStorageSumAdapter;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @des      报工人员关联页
 * @author  xiemeng
 * @date    2017/3/13
 */

public class OrderDailyWorkPeopleFg extends BaseFragment {
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

    FinishedStorageActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    FinishedStorageSumAdapter adapter;

    List<SumShowBean> sumShowBeanList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_finishedstorage_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (FinishedStorageActivity) activity;
        commonLogic = CommonLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
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
                        adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        upDateFlag = true;
                        toDetail();
                        dismissLoadingDialog();
                    }

                @Override
                public void onFailed(String error) {
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<SumShowBean>();
                        adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "upDateList--getSum--onFailed" + e);
                    }
                    dismissLoadingDialog();
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

    private void sureCommit(){
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
