package digiwin.smartdepot.module.fragment.sale.pickupshipment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.sale.pickupshipment.PickUpShipmentActivity;
import digiwin.smartdepot.module.adapter.sale.pickupshipment.PickUpShipmentFIFoAdapter;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.smartdepot.R.id.et_barcode;
import static digiwin.smartdepot.R.id.et_input_num;


/**
 * @author 赵浩然
 * @des 捡料出货扫描页
 * @date 2017/3/23
 */
public class PickUpShipmentScanFg extends BaseFragment {

    private PickUpShipmentActivity pactivity;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;
    /**
     * FIFO
     */
    final int FIFOWHAT = 1003;


    private List<PostMaterialFIFOBean> fiFoList;

    FilterResultOrderBean localData;

    CommonLogic commonLogic;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    PickUpShipmentFIFoAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView mRy_list;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode_string)
    TextView tvBarcode;
    @BindView(et_barcode)
    EditText etScanBarocde;
    @BindView(R.id.ll_barcode)
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
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({et_barcode, R.id.et_scan_locator, et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode_string, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    @BindView(R.id.tv_storage)
    TextView tv_storage;

    @BindView(R.id.tv_locator_tv)
    TextView tv_locator_tv;

    @BindView(R.id.tv_item_name)
    TextView tv_item_name;

    @BindView(R.id.tv_item_format)
    TextView tv_item_format;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_barcode)
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

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = et_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
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


    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }

        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }

        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        boolean isSave=true;
        saveBean.setQty(etInputNum.getText().toString());
        if (AddressContants.FIFOY.equals(saveBean.getFifo_check())) {
            isSave=false;
            if(fiFoList.size() > 0){
                for (PostMaterialFIFOBean bean : fiFoList) {
                    if (saveBean.getBarcode_no().equals(bean.getBarcode_no())
                            &&saveBean.getStorage_spaces_out_no().equals(bean.getStorage_spaces_no())){
                        float canNum = StringUtils.string2Float(bean.getRecommended_qty())-StringUtils.string2Float(bean.getScan_sumqty());
                        if (StringUtils.string2Float(etInputNum.getText().toString()) > canNum){
                            showFailedDialog(R.string.input_num_toobig);
                            return;
                        }
                        Float sum = StringUtils.sum(bean.getScan_sumqty(),etInputNum.getText().toString().trim());
                        bean.setScan_sumqty(StringUtils.deleteZero(String.valueOf(sum)));
                        isSave=true;
                        break;
                    }
                }
            }

        }
        if (!isSave){
            showFailedDialog(R.string.fifo_scan_error);
            return;
        }
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                clear();
                adapter = new PickUpShipmentFIFoAdapter(activity,fiFoList);
                mRy_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    public void clear(){
        tv_item_name.setText("");
        tv_item_format.setText("");
        tv_storage.setText("");
        tv_locator_tv.setText("");
        etInputNum.setText("");
        etScanBarocde.setText("");
        etScanLocator.setText("");
        etScanBarocde.requestFocus();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
        switch (msg.what){
            case BARCODEWHAT:
                HashMap<String, String> barcodeMap = new HashMap<>();
                barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                barcodeMap.put(AddressContants.DOC_NO, localData.getDoc_no());
                commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                    @Override
                    public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                        dismissLoadingDialog();
                        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                        barcodeFlag = true;
                        tv_item_name.setText(barcodeBackBean.getItem_name());
                        tv_item_format.setText(barcodeBackBean.getItem_spec());

                        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                        saveBean.setItem_no(barcodeBackBean.getItem_no());
                        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                        saveBean.setLot_no(barcodeBackBean.getLot_no());
                        saveBean.setDoc_no(localData.getDoc_no());
                        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
                        etScanLocator.requestFocus();
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
                        tv_storage.setText(locatorBackBean.getWarehouse_no());
                        tv_locator_tv.setText(locatorBackBean.getStorage_spaces_no());
                        locatorFlag = true;
                        saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                        saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                        etInputNum.requestFocus();
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

            case FIFOWHAT:

                showLoadingDialog();
                Map<String,String> map = new HashMap<String,String>();
                map.put("issuing_no",String.valueOf(msg.obj));
                map.put("warehouse_no", LoginLogic.getUserInfo().getWare());
                commonLogic.postMaterialFIFO(map, new CommonLogic.PostMaterialFIFOListener() {
                    @Override
                    public void onSuccess(List<PostMaterialFIFOBean> fiFoBeanList) {
                        dismissLoadingDialog();
                        fiFoList = fiFoBeanList;
                        adapter = new PickUpShipmentFIFoAdapter(context,fiFoBeanList);
                        mRy_list.setAdapter(adapter);
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {

                            }
                        });
                    }
                });
                break;
        }
        return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_pickupshipment_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PickUpShipmentActivity) activity;
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        mRy_list.setLayoutManager(linearLayoutManager);
        initData();
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

        FilterResultOrderBean data = (FilterResultOrderBean) getActivity().getIntent().getSerializableExtra("data");
        localData = new FilterResultOrderBean();
        localData = data;

        mHandler.sendMessageDelayed(mHandler.obtainMessage(FIFOWHAT, data.getDoc_no()), AddressContants.DELAYTIME);
    }
}

