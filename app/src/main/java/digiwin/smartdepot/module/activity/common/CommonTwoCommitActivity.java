package digiwin.smartdepot.module.activity.common;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.module.adapter.produce.FinishedStorageSumAdapter;
import digiwin.smartdepot.module.adapter.stock.MiscellaneousInSumAdapter;
import digiwin.smartdepot.module.adapter.stock.MiscellaneousOutSumAdapter;
import digiwin.smartdepot.module.adapter.stock.storeallot.StoreAllotSumAdapter;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 无来源未完事项单个提交界面
 *      列表界面不一样需要不同类型的adapter
 * @date 2017/3/1
 */
public class CommonTwoCommitActivity extends BaseTitleActivity {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

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
     * 提交数据
     */
    public static final String COMMITLIST = "commitlist";
    public static final String MODULUECODE = "code";
    List<SumShowBean> sumShowBeanList;
    CommonLogic logic;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module =bundle.getString(MODULUECODE,ModuleCode.OTHER);
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_twocommit_sum;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.again_commit);
    }

    @Override
    protected void doBusiness() {
        try {
            FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
            ryList.setLayoutManager(linearLayoutManager);
            Bundle bundle = getIntent().getExtras();
            sumShowBeanList = (List<SumShowBean>) bundle.getSerializable(COMMITLIST);
            BaseRecyclerAdapter adapter = null;
            switch (module) {
                //TODO:每个无来源模块列表可能不一样
                case ModuleCode.FINISHEDSTORAGE:
                    adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
                    break;
                case ModuleCode.NOCOMESTOREALLOT:
                    adapter = new StoreAllotSumAdapter(context, sumShowBeanList);
                    break;
                case ModuleCode.MISCELLANEOUSISSUESIN:
                    adapter = new MiscellaneousInSumAdapter(context, sumShowBeanList);
                    break;
                case ModuleCode.MISCELLANEOUSISSUESOUT:
                    adapter = new MiscellaneousOutSumAdapter(context, sumShowBeanList);
                    break;
                default:
                    break;
            }
            if (null != adapter) {
                ryList.setAdapter(adapter);
            }
            logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        }catch (Exception e){
            LogUtils.e(TAG,"doBusiness()" +e);
        }
    }

    private void sureCommit(){
        try {
            if (null == sumShowBeanList || sumShowBeanList.size() == 0) {
                showFailedDialog(R.string.nodate);
                return;
            }
            showLoadingDialog();
            Map<String, String> map = new HashMap<>();
            logic.commit(map, new CommonLogic.CommitListener() {
                @Override
                public void onSuccess(String msg) {
                    dismissLoadingDialog();
                    showCommitSuccessDialog(msg, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            activity.finish();
                        }
                    });
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    showCommitFailDialog(error);
                }
            });

        } catch (Exception e) {
            LogUtils.e(TAG, "commit-->" + e);
        }
    }

}
