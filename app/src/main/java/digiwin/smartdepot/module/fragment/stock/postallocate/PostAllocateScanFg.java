package digiwin.smartdepot.module.fragment.stock.postallocate;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
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
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.coreutil.CommonUtils;
import digiwin.smartdepot.core.coreutil.FiFoCheckUtils;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.stock.postallocate.PostAllocateScanActivity;
import digiwin.smartdepot.module.adapter.common.CommonDocNoFIFoAdapter;
import digiwin.smartdepot.module.bean.common.FifoCheckBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.SaveBackBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 唐孟宇
 * @des 调拨过账 扫码页面
 */

public class PostAllocateScanFg extends BaseFragment {

    /**
     * 条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;

    /**
     * 条码
     */
    @BindView(R.id.et_scan_barocde)
    EditText et_scan_barocde;

    /**
     * 拨出库位
     */
    @BindView(R.id.tv_locator_out)
    TextView tv_locator_out;
    /**
     * 拨出库位
     */
    @BindView(R.id.et_scan_locator_out)
    EditText et_scan_locator_out;
    /**
     * 锁定拨出库位
     */
    @BindView(R.id.cb_locatorlock_out)
    CheckBox cb_locatorlock_out;
    /**
     * 拨入库位
     */
    @BindView(R.id.tv_locator_in)
    TextView tv_locator_in;
    /**
     * 拨入库位
     */
    @BindView(R.id.et_scan_locator_in)
    EditText et_scan_locator_in;
    /**
     * 锁定拨入库位
     */
    @BindView(R.id.cb_locatorlock_in)
    CheckBox cb_locatorlock_in;
    /**
     * 数量
     */
    @BindView(R.id.et_input_num)
    EditText et_input_num;

    /**
     * 保存按钮
     */
    @BindView(R.id.save)
    Button save;

    @BindView(R.id.ll_scan_barcode)
    LinearLayout ll_scan_barcode;
    @BindView(R.id.ll_scan_locator_in)
    LinearLayout ll_scan_locator_in;
    @BindView(R.id.ll_scan_locator_out)
    LinearLayout ll_scan_locator_out;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator_in, R.id.et_scan_locator_out, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator_in, R.id.ll_scan_locator_out, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator_in, R.id.tv_locator_out, R.id.tv_number})
    List<TextView> textViews;

    @BindView(R.id.ry_list)
    RecyclerView ryList;


    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scaned_num)
    TextView tv_scaned_num;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 拨出库位展示
     */
    String outLocatorShow;
    /**
     * 拨入库位展示
     */
    String inLocatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 拨出库位扫描
     */
    boolean outLocatorFlag;
    /**
     * 拨入库位扫描
     */
    boolean inLocatorFlag;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHATIN = 1002;
    /**
     * 库位
     */
    final int LOCATORWHATOUT = 1003;

    CommonLogic commonLogic;


    List<FifoCheckBean> fiFoList;

    @OnCheckedChanged(R.id.cb_locatorlock_in)
    void isLock_in(boolean checked) {
        if (checked) {
            et_scan_locator_in.setKeyListener(null);
        } else {
            et_scan_locator_in.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_locatorlock_out)
    void isLock_out(boolean checked) {
        if (checked) {
            et_scan_locator_out.setKeyListener(null);
        } else {
            et_scan_locator_out.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator_in)
    void inLocatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator_in, views);
        ModuleUtils.etChange(activity, et_scan_locator_in, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_in, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator_out)
    void outLocatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator_out, views);
        ModuleUtils.etChange(activity, et_scan_locator_out, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_out, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator_in, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inLocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHATIN);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHATIN, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator_out, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void outLocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHATOUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHATOUT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (!outLocatorFlag) {
            showFailedDialog(R.string.scan_outlocator);
            return;
        }
        if (!inLocatorFlag) {
            showFailedDialog(R.string.scan_inlocator);
            return;
        }
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getDoc_no());
        saveBean.setQty(et_input_num.getText().toString());
        String fifoCheck = FiFoCheckUtils.fifoCheck(saveBean, fiFoList);
        if (!StringUtils.isBlank(fifoCheck)){
            showFailedDialog(fifoCheck);
            return;
        }
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                saveBean.setScan_sumqty(saveBackBean.getScan_sumqty());
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    PostAllocateScanActivity pactivity;

    CommonDocNoFIFoAdapter adapter;

    FilterResultOrderBean orderBean;

    /**
     * 先进先出管控否
     */
    boolean fifo_check;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    barcodeMap.put(AddressContants.DOC_NO, orderBean.getDoc_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShow();
                            if (!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())) {
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            if (!StringUtils.isBlank(barcodeBackBean.getScan_sumqty())) {
                                tv_scaned_num.setText(StringUtils.deleteZero(barcodeBackBean.getScan_sumqty()));
                            }
                            barcodeFlag = true;
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setFifo_check(barcodeBackBean.getFifo_check());
                            et_input_num.requestFocus();
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            if (CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_barocde.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHATIN:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            inLocatorShow = locatorBackBean.getShow();
                            inLocatorFlag = true;
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            et_scan_barocde.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator_in.setText("");
                                }
                            });
                            inLocatorFlag = false;
                        }
                    });
                    break;
                case LOCATORWHATOUT:
                    HashMap<String, String> locatorMap1 = new HashMap<>();
                    locatorMap1.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap1, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            if (!LoginLogic.getWare().equals(locatorBackBean.getWarehouse_no())){
                                showFailedDialog(R.string.ware_error, new OnDialogClickListener() {
                                    @Override
                                    public void onCallback() {
                                        et_scan_locator_out.setText("");
                                    }
                                });
                                return;
                            }
                            outLocatorShow = locatorBackBean.getShow();
                            outLocatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            if (cb_locatorlock_in.isChecked()) {
                                et_scan_barocde.requestFocus();
                            } else {
                                et_scan_locator_in.requestFocus();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator_out.setText("");
                                }
                            });
                            outLocatorFlag = false;
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_post_allocate_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PostAllocateScanActivity) activity;
        fiFoList = new ArrayList<FifoCheckBean>();
        initData();
    }

    public void getFIFO() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AddressContants.ISSUING_NO, orderBean.getDoc_no());
        map.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
        commonLogic.postMaterialFIFO(map, new CommonLogic.PostMaterialFIFOListener() {
            @Override
            public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                fiFoList.clear();
                fiFoList = fiFoBeanList;
                adapter = new CommonDocNoFIFoAdapter(pactivity, fiFoList);
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                fiFoList = new ArrayList<FifoCheckBean>();
                adapter = new CommonDocNoFIFoAdapter(pactivity, fiFoList);
            }
        });
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        getFIFO();
        tv_scaned_num.setText(saveBean.getScan_sumqty());
        et_scan_barocde.setText("");
        et_input_num.setText("");
        barcodeFlag = false;
        if (!cb_locatorlock_out.isChecked()) {
            outLocatorShow = "";
            outLocatorFlag = false;
            et_scan_locator_out.setText("");
            et_scan_locator_out.requestFocus();
        }
        if (cb_locatorlock_in.isChecked()) {
            et_scan_barocde.requestFocus();
        } else {
            inLocatorShow = "";
            inLocatorFlag = false;
            et_scan_locator_in.setText("");
            et_scan_locator_in.requestFocus();
        }
        barcodeShow = "";
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        et_scan_barocde.setText("");
        et_scan_locator_in.setText("");
        et_scan_locator_out.setText("");
        et_input_num.setText("");
        tv_scaned_num.setText("");
        barcodeShow = "";
        inLocatorShow = "";
        outLocatorShow = "";
        fifo_check = false;
        barcodeFlag = false;
        outLocatorFlag = false;
        inLocatorFlag = false;
        cb_locatorlock_in.setChecked(false);
        cb_locatorlock_out.setChecked(false);
        saveBean = new SaveBean();
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        et_scan_locator_out.requestFocus();
        getFIFO();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
