package digiwin.smartdepot.module.activity.produce.accordingmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.adapter.produce.AccordingMaterialSumAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.DetailShowBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 依成品发料
 * @date 2017/3/2
 */

public class AccordingMaterialActivity extends BaseFirstModuldeActivity {

    AccordingMaterialActivity activity;

    /**
     * 料号
     */
    final int BARCODEWHAT = 1001;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 料号条码
     */
    @BindView(R.id.et_item_no_scan)
    EditText mEt_barcode_scan;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTv_item_name;

    @BindView(R.id.ry_list)
    RecyclerView mRc_list;

    AccordingMaterialSumAdapter adapter;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 跳转扫描页面使用
     */
    public final int SCANCODE = 0123;

    CommonLogic managerCommon;

    @BindViews({R.id.et_item_no_scan})
    List<EditText> editTexts;
    @BindViews({R.id.tv_item_no})
    List<TextView> textViews;

    /**
     * 提交按钮
     */
    @BindView(R.id.commit)
    Button mBtn_commit;

    @OnClick(R.id.commit)
    void commit(){

        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                if(StringUtils.isBlank(mEt_barcode_scan.getText().toString().trim())){
                    dismissLoadingDialog();
                    showFailedDialog(getResources().getString(R.string.please_scan_item_no));
                    return;
                }
                commitData();
            }
            @Override
            public void onCallback2() {

            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @OnFocusChange(R.id.et_item_no_scan)
    void barcodeFocusChange() {
        ModuleUtils.etChange(activity, mEt_barcode_scan, editTexts);
    }

    @OnTextChanged(value = R.id.et_item_no_scan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    private android.os.Handler mHandler = new android.os.Handler(new android.os.Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case BARCODEWHAT:
                updateList(String.valueOf(msg.obj));
                break;
        }
        return false;
    }
});

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.according_material));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ACCORDINGMATERIALCODE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_according_material;
    }

    @Override
    protected void doBusiness() {
        Log.d(TAG,activity.module);
        managerCommon = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRc_list.setLayoutManager(fullyLinearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCANCODE){
            if(!StringUtils.isBlank(mEt_barcode_scan.getText().toString().trim())){
                updateList(mEt_barcode_scan.getText().toString().trim());
            }
        }
    }

    /**
     * 清楚栏位
     */
    public  void clearData(){
        mTv_item_name.setText("");
        mEt_barcode_scan.setText("");
        mEt_barcode_scan.requestFocus();
        List<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new AccordingMaterialSumAdapter(activity,list);
        mRc_list.setAdapter(adapter);
    }

    /**
     * 根据料号跟新汇总数据
     * @param item_no
     */
    void updateList(String item_no){
        showLoadingDialog();

        ClickItemPutBean putBean = new ClickItemPutBean();
        putBean.setItem_no(item_no);
        putBean.setWarehouse_no(LoginLogic.getUserInfo().getWare());

        managerCommon.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                mTv_item_name.setText(list.get(0).getItem_name());

                adapter = new AccordingMaterialSumAdapter(activity,list);
                mRc_list.setAdapter(adapter);
                dismissLoadingDialog();
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Bundle bundle = new Bundle();
                        ListSumBean data = list.get(position);
                        bundle.putSerializable("sumdata", data);
                        bundle.putString("modilecode",mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity,AccordingMaterialScanActivity.class,bundle,SCANCODE);
                        //调用新的FIFO
//                        ActivityManagerUtils.startActivityBundleForResult(activity,AccordingMaterialScanNewActivity.class,bundle,SCANCODE);
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        clearData();
                    }
                });
            }
        });
    }

    /**
     * 跳转到明细页面
     */
    public void ToDetailAct(final SumShowBean bean){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("item_no",bean.getItem_no());
        managerCommon.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(final List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CommonDetailActivity.ONESUM,bean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, activity.module);
                ActivityManagerUtils.startActivityBundleForResult(activity,CommonDetailActivity.class,bundle,SCANCODE);
            }

            @Override
            public void onFailed(String error) {
                showToast(error);
            }
        });
    }

    public void commitData(){
        HashMap<String, String> barcodeMap = new HashMap<String, String>();
        managerCommon.commit(barcodeMap, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg);
                createNewModuleId(module);
                clearData();
                managerCommon = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}