package digiwin.smartdepot.module.fragment.produce.postmaterial;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
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
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.produce.postmaterial.PostMaterialSecondActivity;
import digiwin.smartdepot.module.adapter.produce.PostmaterialFiFoAdapter;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SaveBackBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 领料过账 扫码页面
 */
public class PostMaterialScanFg extends BaseFragment {

    /**
     * 品名
     */
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;

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
     * 库位
     */
    @BindView(R.id.tv_locator_string)
    TextView tv_locator_string;
    /**
     * 库位
     */
    @BindView(R.id.et_scan_locator)
    EditText et_scan_locator;
    /**
     * 锁定库位
     */
    @BindView(R.id.cb_locatorlock)
    CheckBox cb_locatorlock;
    /**
     * 数量
     */
    @BindView(R.id.et_input_num)
    EditText et_input_num;

    /**
     * 保存按钮
     */
    @BindView( R.id.save)
    Button save;

    @BindView(R.id.ll_scan_barcode)
    LinearLayout ll_scan_barcode;
    @BindView(R.id.ll_scan_locator)
    LinearLayout ll_scan_locator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator_string, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;
    /**
     * 从汇总界面带入的单身数据
     */
    ListSumBean sumshoubean;
    /**
     * 从汇总界面带入的单头数据
     */
    FilterResultOrderBean orderData;

    PostmaterialFiFoAdapter adapter;

    List<PostMaterialFIFOBean> fiFoList = new ArrayList<PostMaterialFIFOBean>();

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    PostMaterialSecondActivity pactivity;

    CommonLogic commonLogic;

    private List<ListSumBean> sumBeanList = new ArrayList<ListSumBean>();
    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            et_scan_locator.setKeyListener(null);
        } else {
            et_scan_locator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator, views);
        ModuleUtils.etChange(activity, et_scan_locator, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_string, textViews);
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

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    /**
     * 先进先出管控否
     */
    boolean fifo_check = false;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    if(!locatorFlag){
                        showFailedDialog(R.string.scan_locator, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                et_scan_barocde.setText("");
                                et_scan_locator.requestFocus();
                            }
                        });
                    }
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
                    barcodeMap.put(AddressContants.DOC_NO,orderData.getDoc_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            //管控建议
                            try{
                                if(barcodeBackBean.getFifo_check().equals(AddressContants.FIFOY)){
                                    fifo_check = true;
                                    if(null != fiFoList && fiFoList.size()>0){
                                        int n = 0;
                                        for (int i = 0;i<fiFoList.size();i++){
                                            if(barcodeBackBean.getBarcode_no().equals(fiFoList.get(i).getBarcode_no()) && saveBean.getStorage_spaces_out_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                                                n++;
                                            }
                                        }
                                        if(n ==0){
                                            showFailedDialog(pactivity.getResources().getString(R.string.barcode_not_in_fifo));
                                            et_scan_barocde.setText("");
                                            return;
                                        }
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            barcodeShow = barcodeBackBean.getShow();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            if(!StringUtils.isBlank(barcodeBackBean.getItem_name())){
                               tv_product_name.setText(barcodeBackBean.getItem_name());
                            }
                            barcodeFlag = true;
//                          show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            et_input_num.requestFocus();

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
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            if(null != fiFoList && fiFoList.size()>0){
                                int n = 0;
                                for (int i = 0;i<fiFoList.size();i++){
                                    if(locatorBackBean.getStorage_spaces_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                                        n++;
                                    }
                                }
                                if(n ==0){
                                    showFailedDialog(pactivity.getResources().getString(R.string.barcode_not_in_fifo));
                                    et_scan_barocde.setText("");
                                    return;
                                }
                            }
                            locatorShow = locatorBackBean.getShow();
                            locatorFlag = true;
//                            show();
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            et_scan_barocde.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator.setText("");
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

    private void getFIFO(ListSumBean sumshoubean ){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(AddressContants.ISSUING_NO,orderData.getDoc_no());
        map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
        showLoadingDialog();
        commonLogic.postMaterialFIFO(map, new CommonLogic.PostMaterialFIFOListener() {
            @Override
            public void onSuccess(List<PostMaterialFIFOBean> fiFoBeanList) {
                dismissLoadingDialog();
                fiFoList.clear();
                fiFoList = fiFoBeanList;
                adapter = new PostmaterialFiFoAdapter(pactivity,fiFoList);
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                fiFoList = new ArrayList<PostMaterialFIFOBean>();
                adapter = new PostmaterialFiFoAdapter(pactivity,fiFoList);
            }
        });
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
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderData.getDoc_no());
        saveBean.setQty(et_input_num.getText().toString().trim());
//        //管控建议
//        if(fifo_check){
//            if(null != fiFoList && fiFoList.size()>0){
//                int n = 0;
//                for (int i = 0;i<fiFoList.size();i++){
//                    if(saveBean.getStorage_spaces_out_no().equals(fiFoList.get(i).getStorage_spaces_no())
//                            && saveBean.getBarcode_no().equals(fiFoList.get(i).getBarcode_no())){
//                        n++;
//                    }
//                }
//                if(n ==0){
//                    showFailedDialog(pactivity.getResources().getString(R.string.locator_not_in_fifo));
//                    et_scan_locator.setText("");
//                    return;
//                }
//            }
//        }
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
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

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_post_material_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PostMaterialSecondActivity) activity;
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    /**
     * 保存完成之后的操作
     */
    private void clear() {
        //保存成功，重新获取FIFO
        getFIFO(sumshoubean);
        et_scan_barocde.setText("");
        et_scan_barocde.requestFocus();
        et_input_num.setText("");
        tv_product_name.setText("");
        fifo_check = false;
        if (!cb_locatorlock.isChecked()){
            locatorFlag = false;
            et_scan_locator.setText("");
            et_scan_locator.requestFocus();
            locatorShow="";
        }
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        et_scan_barocde.setText("");
        et_scan_locator.setText("");
        tv_product_name.setText("");
        et_scan_locator.requestFocus();
        cb_locatorlock.setChecked(false);
        barcodeShow = "";
        locatorShow = "";
        barcodeFlag = false;
        locatorFlag = false;
        fifo_check = false;
        saveBean = new SaveBean();
        orderData = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable("orderData");
        //获取FIFO
        getFIFO(sumshoubean);
    }
}
