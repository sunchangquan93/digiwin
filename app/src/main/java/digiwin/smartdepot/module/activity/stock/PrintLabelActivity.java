package digiwin.smartdepot.module.activity.stock;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.core.printer.BlueToothManager;
import digiwin.smartdepot.main.logic.ToSettingLogic;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.stock.PrintBarcodeBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @des  标签补打
 * @author  唐孟宇
 * @date    2017/3/24
 */
public class PrintLabelActivity extends BaseTitleActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 来源条码
     */
    @BindView(R.id.et_resource_barcode)
    EditText et_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.et_sum_num)
    EditText et_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.et_single_package_num)
    EditText et_single_package_num;

    /**
     * 来源条码
     */
    @BindView(R.id.tv_resource_barcode)
    TextView tv_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.tv_sum_num)
    TextView tv_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.tv_single_package_num)
    TextView tv_single_package_num;
    /**
     * 来源条码
     */
    @BindView(R.id.ll_resource_barcode)
    LinearLayout ll_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.ll_sum_num)
    LinearLayout ll_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.ll_single_package_num)
    LinearLayout ll_single_package_num;

    @BindViews({R.id.et_resource_barcode,R.id.et_sum_num,R.id.et_single_package_num})
    List<EditText> editTexts;
    @BindViews({R.id.tv_resource_barcode,R.id.tv_sum_num,R.id.tv_single_package_num})
    List<TextView> textViews;
    @BindViews({R.id.ll_resource_barcode,R.id.ll_sum_num,R.id.ll_single_package_num})
    List<View> views;

    @OnFocusChange(R.id.et_single_package_num)
    void singlePackageFocusChanage() {
        ModuleUtils.viewChange(ll_single_package_num, views);
        ModuleUtils.etChange(activity, et_single_package_num, editTexts);
        ModuleUtils.tvChange(activity, tv_single_package_num, textViews);
    }
    @OnFocusChange(R.id.et_sum_num)
    void sumNumFocusChanage() {
        ModuleUtils.viewChange(ll_sum_num, views);
        ModuleUtils.etChange(activity, et_sum_num, editTexts);
        ModuleUtils.tvChange(activity, tv_sum_num, textViews);
    }
    @OnFocusChange(R.id.et_resource_barcode)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_resource_barcode, views);
        ModuleUtils.etChange(activity, et_resource_barcode, editTexts);
        ModuleUtils.tvChange(activity, tv_resource_barcode, textViews);
    }

    final int BARCODEWHAT = 1234;


    @OnTextChanged(value = R.id.et_resource_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_single_package_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void singlePackageChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            if(!StringUtils.isBlank(et_sum_num.getText().toString().trim())){
                if(StringUtils.string2Float(et_sum_num.getText().toString())!= 0){
                    int sum_num = (int)StringUtils.string2Float(et_sum_num.getText().toString());
                    int single_num = (int)StringUtils.string2Float(et_single_package_num.getText().toString());
                    if(single_num != 0){
                        int num = StringUtils.div(sum_num,single_num);
                        tv_num_of_print_pieces.setText(String.valueOf(num));
                        printBarcodeBean.setQty(String.valueOf(single_num));
                        printBarcodeBean.setPrint_num(String.valueOf(num));
                    }
                }
            }
        }
    }

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;
    /**
     * 规格
     */
    @BindView(R.id.tv_model)
    TextView tv_model;
    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    /**
     * 条码类型
     */
    @BindView(R.id.tv_barcode_type)
    TextView tv_barcode_type;
    /**
     * 打印张数
     */
    @BindView(R.id.tv_num_of_print_pieces)
    TextView tv_num_of_print_pieces;

    CommonLogic logic;

    PrintBarcodeBean printBarcodeBean;
    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRINTLABEL;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.print_label);
        ivScan.setVisibility(View.GONE);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_print_label;
    }

    @Override
    protected void doBusiness() {
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        initData();
    }

    public void initData(){
        et_resource_barcode.requestFocus();
        et_resource_barcode.setText("");
        tv_item_name.setText("");
        tv_model.setText("");
        tv_item_no.setText("");
        tv_barcode_type.setText("");
        et_sum_num.setText("");
        et_single_package_num.setText("");
        tv_num_of_print_pieces.setText("");
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case BARCODEWHAT:
                    Map<String,String> map = new HashMap<>();
                    final String barcode = msg.obj.toString().trim();
                    map.put("barcode_no",msg.obj.toString());
                    logic.scanBarcode(map, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            tv_item_name.setText(barcodeBackBean.getItem_name());
                            tv_model.setText(barcodeBackBean.getItem_spec());
                            tv_item_no.setText(barcodeBackBean.getItem_no());
                            tv_barcode_type.setText(barcodeBackBean.getItem_barcode_type());
                            et_sum_num.setText(barcodeBackBean.getBarcode_qty());

                            printBarcodeBean = new PrintBarcodeBean();
                            printBarcodeBean.setBarcode(barcode);
                            printBarcodeBean.setItem_name(barcodeBackBean.getItem_name());
                            printBarcodeBean.setItem_no(barcodeBackBean.getItem_no());
                            printBarcodeBean.setItem_spec(barcodeBackBean.getItem_spec());
                            printBarcodeBean.setBarcode_type(barcodeBackBean.getItem_barcode_type());
                            printBarcodeBean.setSum_qty(barcodeBackBean.getBarcode_qty());
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 打印
     */
    @OnClick(R.id.btn_print)
    void print(){
        if(printBarcodeBean == null){
            showFailedDialog(R.string.please_scan_barcode_first);
            return;
        }
        if(StringUtils.isBlank(et_single_package_num.getText().toString())){
            showFailedDialog(R.string.input_num);
            return;
        }
        boolean isOpen = BlueToothManager.getManager(activity).isOpen();
        if (!isOpen){
            ToSettingLogic.showToSetdialog(activity,R.string.title_set_bluttooth);
            return;
        }else {
            BlueToothManager.getManager(activity).printMaterialCode(printBarcodeBean,Integer.valueOf(printBarcodeBean.getSum_qty()));
        }
        printBarcodeBean = new PrintBarcodeBean();
        initData();
    }

}