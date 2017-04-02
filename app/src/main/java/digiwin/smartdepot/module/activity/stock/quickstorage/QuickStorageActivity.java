package digiwin.smartdepot.module.activity.stock.quickstorage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.adapter.stock.QuickStorageAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 快速入库
 * @date 2017/3/31
 */

public class QuickStorageActivity extends BaseFirstModuldeActivity{

    private QuickStorageActivity activity;

    /**
     * 扫描入库单
     */
    public final int QUICKSTORAGECODE = 1003;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 单号
     */
    @BindView(R.id.tv_post_material_order)
    TextView tv_post_material_order;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;

    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    CommonLogic commonLogic;

    /**
     * 提交
     */
    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit(){
        final List<ListSumBean> checkedList;
        try {
            checkedList = adapter.getCheckData();
            if(checkedList.size() > 0){
                showCommitSureDialog(new OnDialogTwoListener() {
                    @Override
                    public void onCallback1() {
                        showLoadingDialog();
                        commitData(checkedList);
                    }
                    @Override
                    public void onCallback2() {

                    }
                });
            }else{
                showFailedDialog(getResources().getString(R.string.nodate));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showFailedDialog(getResources().getString(R.string.nodate));
        }
    }

    QuickStorageAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_quickstorage;
    }

    @Override
    protected void doBusiness() {

        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullylinearlayoutmanager = new FullyLinearLayoutManager(activity);
        ry_list.setLayoutManager(fullylinearlayoutmanager);

        FilterResultOrderBean data = (FilterResultOrderBean) getIntent().getSerializableExtra("data");
        tv_post_material_order.setText(data.getDoc_no());
        tv_date.setText(data.getCreate_date());
        tv_supplier.setText(data.getSupplier_name());

        mHandler.sendMessageDelayed(mHandler.obtainMessage(QUICKSTORAGECODE, data.getDoc_no()), AddressContants.DELAYTIME);

    }

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == QUICKSTORAGECODE){
                showLoadingDialog();
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setDoc_no(String.valueOf(msg.obj));
                putBean.setWarehouse_in_no(LoginLogic.getWare());
                commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        adapter = new QuickStorageAdapter(activity,list);
                        ry_list.setAdapter(adapter);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailed(String error) {
                        Log.d(TAG,error);
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                            }
                        });
                    }
                });
            }
            return false;
        }
    });

    public void clearData(){
        tv_post_material_order.setText("");
        tv_date.setText("");
        tv_supplier.setText("");
        ArrayList<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new QuickStorageAdapter(activity,list);
        ry_list.setAdapter(adapter);
    }

    public void commitData(final List<ListSumBean> checkedList){
        final List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(checkedList);
        commonLogic.commitList(listMap, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        createNewModuleId(module);
                        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
                        activity.finish();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.QUICKSTORAGE;
        return module;
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.title_quickstorage);
    }
}
