package digiwin.smartdepot.module.activity.produce.directstorage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
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
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 孙长权
 * @des 直接入库
 */
public class DirectStorageActivity extends BaseFirstModuldeActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    //物料条码
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    //库位
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    //数量
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    //工单单号
    @BindView(R.id.tv_gongDan_no)
    TextView tvWorkOrder;
    @BindView(R.id.et_gongDan)
    EditText etWorkOrder;
    @BindView(R.id.ll_gongDan_no)
    LinearLayout llWorkOrder;

    @BindView(R.id.includedetail)
    View includeDetail;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 工单号
     */
    final int WORKORDER = 1002;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1003;

    CommonLogic commonLogic;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 工单号
     */
    String workOrderShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 工单号
     */
    boolean workOrderFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;

    @BindViews({R.id.et_scan_barocde, R.id.et_gongDan, R.id.et_scan_locator, R.id.et_input_num,})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_gongDan_no, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_gongDan_no, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_gongDan)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(llWorkOrder, views);
        ModuleUtils.etChange(activity, etWorkOrder, editTexts);
        ModuleUtils.tvChange(activity, tvWorkOrder, textViews);
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

    //TODO 等待后台接口

    /**
     * 工单号扫描
     */
    @OnTextChanged(value = R.id.et_gongDan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void workOrderNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WORKORDER, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.DIRECTSTORAGE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.direct_storage);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_direct_storage;
    }

    @Override
    protected void doBusiness() {
        commonLogic = CommonLogic.getInstance(context, module, mTimestamp.toString());
        initData();
    }

    @OnClick(R.id.commit)
    void commit() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (!workOrderFlag) {
            showFailedDialog(R.string.scan_work_order);
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

        showLoadingDialog();

        Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);

        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShow();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            barcodeFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());

                            if (cbLocatorlock.isChecked()) {
                                etInputNum.requestFocus();
                            } else {
                                etScanLocator.requestFocus();
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
                            locatorShow = locatorBackBean.getShow();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etInputNum.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
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
                case WORKORDER:
//                    commonLogic.getOrderSumData(bean, new CommonLogic.GetOrderSumListener() {
//                        @Override
//                        public void onSuccess(List<ListSumData> list) {
//                            workOrderShow = list.get(0).getShow();
//                            workOrderFlag = true;
//                            show();
//                            etScanLocator.requestFocus();
//                        }
//
//                        @Override
//                        public void onFailed(String error) {
//                            showFailedDialog(error, new OnDialogClickListener() {
//                                @Override
//                                public void onCallback() {
//                                    etWorkOrder.setText("");
//                                }
//                            });
//                            locatorFlag = false;
//                        }
//                    });
                    break;
            }
            return false;
        }
    });

    /**
     * 保存完成之后的操作
     */
    private void clear() {
        etInputNum.setText("");
        if (!cbLocatorlock.isChecked()) {
            locatorFlag = false;
            etScanLocator.setText("");
            locatorShow = "";
        }
        barcodeFlag = false;
        etScanBarocde.setText("");
        barcodeShow = "";
        etScanBarocde.requestFocus();
        show();
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        barcodeShow = "";
        workOrderShow = "";
        locatorShow = "";
        barcodeFlag = false;
        workOrderFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + workOrderShow + "\\n" + locatorShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
