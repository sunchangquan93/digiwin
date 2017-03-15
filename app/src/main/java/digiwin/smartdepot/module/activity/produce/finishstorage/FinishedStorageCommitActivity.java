package digiwin.smartdepot.module.activity.produce.finishstorage;

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
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.module.adapter.produce.FinishedStorageSumAdapter;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 未完事项单个提交界面
 * @date 2017/3/1
 */
public class FinishedStorageCommitActivity extends BaseTitleActivity {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @OnClick(R.id.commit)
    void commit(){
        try {
            if (null==sumShowBeanList||sumShowBeanList.size()==0){
                showFailedDialog(R.string.nodate);
                return;
            }
            showCommitSureDialog(new OnDialogTwoListener() {
                @Override
                public void onCallback1() {
                    commitData();
                }

                @Override
                public void onCallback2() {

                }
            });

        }catch (Exception e){
            LogUtils.e(TAG,"commit-->"+ e);
        }
    }

    private void commitData() {
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        logic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        createNewModuleId(module);
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
    }

    /**
     * 提交数据
     */
    static  final String COMMITLIST="commitlist";

    List<SumShowBean> sumShowBeanList;

    FinishedStorageSumAdapter adapter;

    CommonLogic logic;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.FINISHEDSTORAGE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_finishedstorage_sum;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.again_commit);
    }

    @Override
    protected void doBusiness() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        Bundle bundle = getIntent().getExtras();
        sumShowBeanList= (List<SumShowBean>) bundle.getSerializable(COMMITLIST);
        adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
        ryList.setAdapter(adapter);
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
    }

}
