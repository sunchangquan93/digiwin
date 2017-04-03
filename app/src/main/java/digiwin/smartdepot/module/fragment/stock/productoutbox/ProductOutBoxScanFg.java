package digiwin.smartdepot.module.fragment.stock.productoutbox;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.activity.stock.productoutbox.ProductOutBoxActivity;
import digiwin.smartdepot.module.bean.stock.ProductBinningBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 孙长权
 * @des 产品装箱--扫描
 * @date 2017/3/23
 */
public class ProductOutBoxScanFg extends BaseFragment {

    @BindViews({R.id.et_pack_boxnumber, R.id.et_product_number})
    List<EditText> editTexts;
    @BindViews({R.id.ll_pack_boxnumber, R.id.ll_product_number})
    List<View> views;
    @BindViews({R.id.tv_pack_boxnumber, R.id.tv_product_number})
    List<TextView> textViews;

    /**
     * 箱内数量
     */
    @BindView(R.id.tv_box_number)
    TextView tvBoxNumber;
    /**
     * 包装箱号
     */
    @BindView(R.id.tv_pack_boxnumber)
    TextView tvPackBoxNumber;
    @BindView(R.id.et_pack_boxnumber)
    EditText etPackBoxNumber;
    @BindView(R.id.cb_inlocatorlock)
    CheckBox cbInlocatorlock;
    @BindView(R.id.ll_pack_boxnumber)
    LinearLayout llPackBoxNumber;
    /**
     * 产品条码
     */
    @BindView(R.id.tv_product_number)
    TextView tvProductBarcode;
    @BindView(R.id.et_product_number)
    EditText etProductBarcode;
    @BindView(R.id.ll_product_number)
    LinearLayout llProductBarcode;

    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @OnFocusChange(R.id.et_pack_boxnumber)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llPackBoxNumber, views);
        ModuleUtils.etChange(activity, etPackBoxNumber, editTexts);
        ModuleUtils.tvChange(activity, tvPackBoxNumber, textViews);
    }

    @OnFocusChange(R.id.et_product_number)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llProductBarcode, views);
        ModuleUtils.etChange(activity, etProductBarcode, editTexts);
        ModuleUtils.tvChange(activity, tvProductBarcode, textViews);
    }

    @OnCheckedChanged(R.id.cb_inlocatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etPackBoxNumber.setKeyListener(null);
        } else {
            etPackBoxNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnTextChanged(value = R.id.et_pack_boxnumber, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PACKBOXNUMBER);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PACKBOXNUMBER, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_product_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PRODUCTBARCODE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PRODUCTBARCODE, s.toString()), AddressContants.DELAYTIME);
        }
    }

    /**
     * 包装箱号
     */
    final int PACKBOXNUMBER = 1001;
    /**
     * 产品条码
     */
    final int PRODUCTBARCODE = 1002;
    /**
     * 条码展示
     */
    String barcodeShow;

    ProductOutBoxActivity pactivity;

    /**
     * 包装箱号扫描
     */
    boolean boxFlag;

    CommonLogic commonLogic;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PACKBOXNUMBER://扫描包装箱号
                    boxFlag = true;
                    String number = msg.obj.toString().trim();
                    //获取包装箱号
                    if (!StringUtils.isBlank(number)) {
                        //赋值，用于明细界面调接口得数据
                        pactivity.packBoxNumber = number;
                        HashMap<String, String> map = new HashMap<>();
                        map.put(AddressContants.PACKAGENO, number);
                        commonLogic.scanPackBoxNumber(map, new CommonLogic.ScanPackBoxNumberListener() {
                            @Override
                            public void onSuccess(List<ProductBinningBean> productBinningBeans) {
                                if (productBinningBeans != null && productBinningBeans.size() > 0) {
                                    ProductBinningBean bean = productBinningBeans.get(productBinningBeans.size() - 1);
                                    tvBoxNumber.setText(bean.getItem_qty());
                                    etProductBarcode.requestFocus();
                                }
                            }
                            @Override
                            public void onFailed(String error) {
                                showFailedDialog(error);
                            }
                        });
                    }
                    break;
                case PRODUCTBARCODE:
                    showLoadingDialog();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("package_no", pactivity.packBoxNumber);
                    map.put("barcode_no", msg.obj.toString().trim());
                    map.put("flag", "d");
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(map);
                    commonLogic.insertAndDelete(list, new CommonLogic.InsertAndDeleteListener() {
                        @Override
                        public void onSuccess(String show) {
                            dismissLoadingDialog();
                            barcodeShow = show;
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error);
                        }
                    });
                    break;
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_productbinning_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ProductOutBoxActivity) activity;
        initData();
    }


    /**
     * 初始化一些变量
     */
    private void initData() {
        tvBoxNumber.setText("");
        etPackBoxNumber.setText("");
        etProductBarcode.setText("");

        boxFlag = false;

        cbInlocatorlock.setChecked(false);
        etPackBoxNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());

        barcodeShow = "";

        etPackBoxNumber.requestFocus();
        show();
    }


    public void upDate(){
        if(boxFlag){
            etProductBarcode.requestFocus();
        }
    }
    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


}
