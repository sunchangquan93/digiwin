package digiwin.smartdepot.module.activity.produce.accordingmaterial;

import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.adapter.produce.AccordingMaterialFiFoAdapter;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SaveBackBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.bean.produce.FiFoBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.library.utils.StringUtils.sum;
import static digiwin.smartdepot.R.id.et_input_num;
import static digiwin.smartdepot.R.id.et_scan_barocde;


/**
 * @author 赵浩然
 * @module 依成品发料扫描
 * @date 2017/3/2
 */

public class AccordingMaterialScanActivity extends BaseTitleActivity {

    AccordingMaterialScanActivity activity;

    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTv_item_name;

    /**
     * 规格
     */
    @BindView(R.id.et_format)
    TextView et_format;

    /**
     * 库存量
     */
    @BindView(R.id.tv_material_return_big)
    TextView tv_stock_balance;

    /**
     * 欠料量
     */
    @BindView(R.id.tv_material_return)
    TextView tv_under_feed;

    /**
     * 已扫
     */
    @BindView(R.id.tv_feeding_amount)
    TextView tv_actual_yield;

    @BindView(R.id.ry_list)
    RecyclerView mRc_list;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

    /**
     * 库位
     */
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;

    /**
     * 数量
     */
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({et_scan_barocde, R.id.et_scan_locator, et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    AccordingMaterialFiFoAdapter adapter;

    SaveBean saveBean;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 返回汇总页面使用
     */
    public final int SCANCODE = 0123;

    CommonLogic commonLogic;

    List<FiFoBean> localFifoList;

    boolean fifo_check = false;

    /**
     * 提交按钮
     */
    @BindView(R.id.save)
    Button mBtn_save;

    /**
     * 物料类型
     */
    String type;

    /**
     * 条码类型 料号类型
     */
    private String codetype = "1";

    @OnClick(R.id.save)
    void saveData(){
        if(!barcodeFlag){
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if(!locatorFlag){
            showFailedDialog(R.string.scan_locator);
            return;
        }

        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }

        showLoadingDialog();

        saveBean.setQty(etInputNum.getText().toString());
        //判断库存 欠料数量  哪个小取哪一个
        if(Float.valueOf(tv_under_feed.getText().toString()) > Float.valueOf(tv_stock_balance.getText().toString())){
            saveBean.setAvailable_in_qty(tv_stock_balance.getText().toString());
        }else if(Float.valueOf(tv_under_feed.getText().toString()) < Float.valueOf(tv_stock_balance.getText().toString())){
            saveBean.setAvailable_in_qty(tv_under_feed.getText().toString());
        }else{
            saveBean.setAvailable_in_qty(tv_under_feed.getText().toString());
        }
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                showToast(getResources().getString(R.string.save_success));
                float scan_sum = sum(etInputNum.getText().toString(),tv_actual_yield.getText().toString());
                tv_actual_yield.setText(StringUtils.deleteZero(String.valueOf(scan_sum)));
                if(localFifoList.size() > 0){
                    for (int i = 0; i < localFifoList.size(); i++) {
                        FiFoBean fifoBean = localFifoList.get(i);
                        if(fifoBean.getBarcode_no().equals(saveBean.getBarcode_no()) &&
                                fifoBean.getStorage_spaces_no().equals(saveBean.getStorage_spaces_out_no())){
                            Float sum = StringUtils.sum(fifoBean.getScan_sumqty(),etInputNum.getText().toString().trim());
                            Log.d("sum====:",sum+"");
                            fifoBean.setScan_sumqty(StringUtils.deleteZero(String.valueOf(sum)));
                            break;
                        }
                    }

                    adapter = new AccordingMaterialFiFoAdapter(activity,localFifoList);
                    mRc_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                clearData(type);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        clearData(type);
                    }
                });
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    @OnFocusChange(et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            showLoadingDialog();
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            showLoadingDialog();
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    private android.os.Handler mHandler = new android.os.Handler(new android.os.Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case BARCODEWHAT:
                HashMap<String, String> barcodeMap = new HashMap<>();
                barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                    @Override
                    public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                        dismissLoadingDialog();
                        if(fifo_check == true){
                            if(localFifoList.size() > 0){
                                for(int i = 0;i < localFifoList.size();i++){
                                    FiFoBean fifodata = localFifoList.get(i);
                                    Log.d(TAG,barcodeBackBean.getBarcode_no());
                                    Log.d(TAG,fifodata.getBarcode_no());
                                    if(barcodeBackBean.getBarcode_no().equals(fifodata.getBarcode_no())){
                                        showBarcode(barcodeBackBean);
                                        break;
                                    }else if(i == localFifoList.size() - 1 && !barcodeBackBean.getBarcode_no().equals(fifodata.getBarcode_no())){
                                        showFailedDialog(getResources().getString(R.string.fifo_scan_error), new OnDialogClickListener() {
                                            @Override
                                            public void onCallback() {
                                                etScanBarocde.setText("");
                                            }
                                        });
                                    }
                                }
                            }else{
                                showFailedDialog(getResources().getString(R.string.fifo_scan_error), new OnDialogClickListener() {
                                    @Override
                                    public void onCallback() {
                                        etScanBarocde.setText("");
                                    }
                                });
                            }
                        }else{
                            showBarcode(barcodeBackBean);
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        barcodeFlag = false;
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                etScanBarocde.setText("");
                            }
                        });
                    }
                });
                break;
            case LOCATORWHAT:
                HashMap<String, String> locatorMap = new HashMap<>();
                locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                    @Override
                    public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                        dismissLoadingDialog();
                        locatorFlag = true;
                        saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                        saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                        if(type.equals(codetype)){
                            etInputNum.requestFocus();
                        }else{
                            etScanBarocde.requestFocus();
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                etScanLocator.setText("");
                            }
                        });
                        locatorFlag = false;
                    }
                });
                break;
        }
        return false;
    }
});

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.according_material_scan));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ACCORDINGMATERIALCODE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_according_materialscan;
    }

    @Override
    protected void doBusiness() {

        ListSumBean data = (ListSumBean) getIntent().getSerializableExtra("sumdata");
        mTv_item_name.setText(data.getItem_name());
        et_format.setText(data.getLow_order_item_spec());
        tv_under_feed.setText(StringUtils.deleteZero(data.getShortage_qty()));
        tv_stock_balance.setText(StringUtils.deleteZero(data.getStock_qty()));
        tv_actual_yield.setText(StringUtils.deleteZero(data.getScan_sumqty()));

        String code = getIntent().getExtras().getString("modilecode");

        type = data.getItem_barcode_type();

        if(type.equals(codetype)){
            etScanBarocde.setText(data.getLow_order_item_no());
        }

        if(data.getFifo_check().equals("Y")){
            fifo_check = true;
        }else{
            fifo_check = false;
        }

        commonLogic = CommonLogic.getInstance(context, module, code);
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRc_list.setLayoutManager(fullyLinearLayoutManager);

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("item_no",data.getLow_order_item_no());
        map.put("lot_no","");
        map.put("warehouse_no", LoginLogic.getUserInfo().getWare());

        //判断库存 欠料数量  哪个小取哪一个 然后减去实发量
        if(Float.valueOf(tv_under_feed.getText().toString()) > Float.valueOf(tv_stock_balance.getText().toString())){
            float num = StringUtils.sub(tv_stock_balance.getText().toString(),tv_actual_yield.getText().toString());
            map.put("qty",String.valueOf(num));

        }else if(Float.valueOf(tv_under_feed.getText().toString()) < Float.valueOf(tv_stock_balance.getText().toString())){
            float num = StringUtils.sub(tv_under_feed.getText().toString(),tv_actual_yield.getText().toString());
            map.put("qty",String.valueOf(num));

        }else{
            float num = StringUtils.sub(tv_stock_balance.getText().toString(),tv_actual_yield.getText().toString());
            map.put("qty",String.valueOf(num));
        }

        commonLogic.getFifo(map, new CommonLogic.FIFOGETListener() {
            @Override
            public void onSuccess(List<FiFoBean> fiFoBeanList) {
                dismissLoadingDialog();
                localFifoList = new ArrayList<FiFoBean>();
                localFifoList = fiFoBeanList;
                adapter = new AccordingMaterialFiFoAdapter(activity,fiFoBeanList);
                mRc_list.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        activity.finish();
                    }
                });
            }
        });
    }

    public void clearData(String type){
        if(!cbLocatorlock.isChecked()){
            etScanLocator.setText("");
            locatorFlag = false;
            etScanBarocde.requestFocus();
        }
        if(type.equals(codetype)){
            etInputNum.setText("");
            etInputNum.requestFocus();
        }else{
            etScanBarocde.setText("");
            barcodeFlag = false;
            etScanBarocde.requestFocus();
            etInputNum.setText("");
        }
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        etScanBarocde.setText("");
        etScanBarocde.requestFocus();
        etScanLocator.setText("");
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 对比物料条码
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean){
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;
        saveBean = new SaveBean();
        Log.d("====",barcodeBackBean.getAvailable_in_qty());
        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        etInputNum.requestFocus();
    }
}
