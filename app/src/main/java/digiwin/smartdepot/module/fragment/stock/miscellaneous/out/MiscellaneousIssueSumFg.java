package digiwin.smartdepot.module.fragment.stock.miscellaneous.out;


import android.os.Bundle;
import android.os.Message;
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
import digiwin.smartdepot.module.activity.stock.miscellaneousissues.MiscellaneousissuesOutActivity;
import digiwin.smartdepot.module.adapter.stock.MiscellaneousOutSumAdapter;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.fragment.stock.miscellaneous.in.MiscellaneousIssueInScanFg;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 杂项发料 数据汇总界面
 */
public class MiscellaneousIssueSumFg extends BaseFragment {
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

    MiscellaneousissuesOutActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    MiscellaneousOutSumAdapter adapter;

    List<SumShowBean> sumShowBeanList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_miscellaneous_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (MiscellaneousissuesOutActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();

    }


    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            HashMap<String,String> map = new HashMap<>();
            showLoadingDialog();
            commonLogic.getSum(map, new CommonLogic.GetSumListener() {
                @Override
                public void onSuccess(List<SumShowBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = list;
                    if (list.size() > 0) {
                        adapter = new MiscellaneousOutSumAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        upDateFlag = true;
                        toDetail();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<SumShowBean>();
                        adapter = new MiscellaneousOutSumAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });
//            commonLogic.getOrderSumData(clickItemPutData, new CommonLogic.GetOrderSumListener() {
//                @Override
//                public void onSuccess(List<ListSumBean> list) {
//                    dismissLoadingDialog();
//                    sumShowBeanList = list;
//                    if(list.size()>0){
//                        adapter = new MiscellaneousOutSumAdapter(pactivity, sumShowBeanList);
//                        ryList.setAdapter(adapter);
//                        upDateFlag = true;
//                        toDetail();
//                    }
//                }
//
//                @Override
//                public void onFailed(String error) {
//                   dismissLoadingDialog();
//                    upDateFlag = false;
//                    try {
//                        showFailedDialog(error);
//                        sumShowBeanList = new ArrayList<ListSumBean>();
//                        adapter = new MiscellaneousOutSumAdapter(pactivity, sumShowBeanList);
//                        ryList.setAdapter(adapter);
//                    } catch (Exception e) {
//                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
//                    }
//                }
//            });
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
    private void getDetail(final SumShowBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put("item_no", orderSumData.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, orderSumData);
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
        List<Map<String,String>> maps = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("employee_no", MiscellaneousIssueScanFg.employee_no);
        map.put("department_no",MiscellaneousIssueScanFg.department_no);
        maps.add(map);
        commonLogic.commitListMap(maps, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.mZXVp.setCurrentItem(0);
                        pactivity.createNewModuleId(pactivity.module);
                        pactivity.scanFg.initData();
                        initData();
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
        commonLogic = CommonLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
    }

}
