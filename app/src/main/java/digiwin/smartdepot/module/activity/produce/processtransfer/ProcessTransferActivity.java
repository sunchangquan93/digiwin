package digiwin.smartdepot.module.activity.produce.processtransfer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.bean.common.ScanEmployeeBackBean;
import digiwin.smartdepot.module.bean.produce.ProcessTransferBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;
import digiwin.smartdepot.module.logic.produce.ProcessTransferLogic;

/**
 * @des 工序转移
 * @auther 毛衡
 * @date 2017-06-01
 */

public class ProcessTransferActivity extends BaseTitleActivity {


    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * moveInScan
     */
    @BindView(R.id.tv_in_moveOut)
    TextView tvInMoveOut;
    @BindView(R.id.et_in_moveOut)
    EditText etInMoveOut;
    @OnTextChanged(value = R.id.et_in_moveOut, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inMoveOutChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(INMOVEOUTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(INMOVEOUTWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.cb_in_moveOut)
    CheckBox cbInMoveOut;
    @OnCheckedChanged(R.id.cb_in_moveOut)
    void inMoveOutLock(boolean checked) {
        if (checked) {
            etInMoveOut.setKeyListener(null);
        } else {
            etInMoveOut.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @BindView(R.id.ll_in_moveOut)
    LinearLayout llInMoveOut;

    @BindView(R.id.tv_in_moveIn)
    TextView tvInMoveIn;
    @BindView(R.id.et_in_moveIn)
    EditText etInMoveIn;
    @OnTextChanged(value = R.id.et_in_moveIn, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inMoveInChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(INMOVEINWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(INMOVEINWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.cb_in_moveIn)
    CheckBox cbInMoveIn;
    @OnCheckedChanged(R.id.cb_in_moveIn)
    void inMoveInLock(boolean checked) {
        if (checked) {
            etInMoveIn.setKeyListener(null);
        } else {
            etInMoveIn.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @BindView(R.id.ll_in_moveIn)
    LinearLayout llInMoveIn;

    @BindView(R.id.tv_in_cardNumber)
    TextView tvInCardNumber;
    @BindView(R.id.et_in_cardNumber)
    EditText etInCardNumber;
    @OnTextChanged(value = R.id.et_in_cardNumber, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inCardNumChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(INCARDNUMWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(INCARDNUMWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.ll_in_cardNumber)
    LinearLayout llInCardNumber;

    @BindView(R.id.tv_in_get_num)
    TextView tvInGetNum;
    @BindView(R.id.et_in_get_num)
    EditText etInGetNum;
    @BindView(R.id.ll_in_get_num)
    LinearLayout llInGetNum;

    @BindView(R.id.ll_moveIn_scan)
    LinearLayout llMoveInScan;
    /**
     * moveOutScan
     */
    @BindView(R.id.tv_out_change)
    TextView tvOutChange;
    @BindView(R.id.et_out_change)
    EditText etOutChange;
    @OnTextChanged(value = R.id.et_out_change, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void outChangeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(OUTCHANGEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(OUTCHANGEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.cb_out_change)
    CheckBox cbOutChange;
    @OnCheckedChanged(R.id.cb_out_change)
    void outChangeLock(boolean checked) {
        if (checked) {
            etOutChange.setKeyListener(null);
        } else {
            etOutChange.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @BindView(R.id.ll_out_change)
    LinearLayout llOutChange;

    @BindView(R.id.tv_out_cardNumber)
    TextView tvOutCardNumber;
    @BindView(R.id.et_out_cardNumber)
    EditText etOutCardNumber;
    @OnTextChanged(value = R.id.et_out_cardNumber, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void outCardNumChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(OUTCARDNUMWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(OUTCARDNUMWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.ll_out_cardNumber)
    LinearLayout llOutCardNumber;

    @BindView(R.id.tv_out_change_num)
    TextView tvOutChangeNum;
    @BindView(R.id.et_out_change_num)
    EditText etOutChangeNum;
    @BindView(R.id.ll_out_change_num)
    LinearLayout llOutChangeNum;

    @BindView(R.id.ll_moveOut_scan)
    LinearLayout llMoveOutScan;

    @BindViews({R.id.et_in_moveOut, R.id.et_in_moveIn, R.id.et_in_cardNumber, R.id.et_in_get_num, R.id.et_out_change, R.id.et_out_cardNumber, R.id.et_out_change_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_in_moveOut, R.id.ll_in_moveIn, R.id.ll_in_cardNumber, R.id.ll_in_get_num, R.id.ll_out_change, R.id.ll_out_cardNumber, R.id.ll_out_change_num})
    List<View> views;
    @BindViews({R.id.tv_in_moveOut, R.id.tv_in_moveIn, R.id.tv_in_cardNumber, R.id.tv_in_get_num, R.id.tv_out_change, R.id.tv_out_cardNumber, R.id.tv_out_change_num})
    List<TextView> textViews;

    @OnFocusChange(R.id.et_in_moveOut)
    void inMoveOutFocusChange() {
        ModuleUtils.viewChange(llInMoveOut, views);
        ModuleUtils.etChange(activity, etInMoveOut, editTexts);
        ModuleUtils.tvChange(activity, tvInMoveOut, textViews);
    }
    @OnFocusChange(R.id.et_in_moveIn)
    void inMoveInFocusChange() {
        ModuleUtils.viewChange(llInMoveIn, views);
        ModuleUtils.etChange(activity, etInMoveIn, editTexts);
        ModuleUtils.tvChange(activity, tvInMoveIn, textViews);
    }
    @OnFocusChange(R.id.et_in_cardNumber)
    void inCardNumberFocusChange() {
        ModuleUtils.viewChange(llInCardNumber, views);
        ModuleUtils.etChange(activity, etInCardNumber, editTexts);
        ModuleUtils.tvChange(activity, tvInCardNumber, textViews);
    }
    @OnFocusChange(R.id.et_in_get_num)
    void inGetNumFocusChange() {
        ModuleUtils.viewChange(llInGetNum, views);
        ModuleUtils.etChange(activity, etInGetNum, editTexts);
        ModuleUtils.tvChange(activity, tvInGetNum, textViews);
    }
    @OnFocusChange(R.id.et_out_change)
    void outChangeFocusChange() {
        ModuleUtils.viewChange(llOutChange, views);
        ModuleUtils.etChange(activity, etOutChange, editTexts);
        ModuleUtils.tvChange(activity, tvOutChange, textViews);
    }
    @OnFocusChange(R.id.et_out_cardNumber)
    void outCardNumberFocusChange() {
        ModuleUtils.viewChange(llOutCardNumber, views);
        ModuleUtils.etChange(activity, etOutCardNumber, editTexts);
        ModuleUtils.tvChange(activity, tvOutCardNumber, textViews);
    }
    @OnFocusChange(R.id.et_out_change_num)
    void outChangeNumFocusChange() {
        ModuleUtils.viewChange(llOutChangeNum, views);
        ModuleUtils.etChange(activity, etOutChangeNum, editTexts);
        ModuleUtils.tvChange(activity, tvOutChangeNum, textViews);
    }

    //以下部分是movein的扫描结果
    @BindView(R.id.ll_moveIn_scanValue)
    LinearLayout llMoveInScanValue;
    @BindView(R.id.tv_resin_moveOut)
    TextView tvResinMoveOut;
    @BindView(R.id.tv_resin_moveOutValue)
    TextView tvResinMoveOutValue;
    @BindView(R.id.tv_resin_moveIn)
    TextView tvResinMoveIn;
    @BindView(R.id.tv_resin_moveInValue)
    TextView tvResinMoveInValue;
    @BindView(R.id.tv_resin_orderNumber)
    TextView tvResinOrderNumber;
    @BindView(R.id.tv_resin_orderumberValue)
    TextView tvResinOrderumberValue;
    @BindView(R.id.tv_resin_workNo)
    TextView tvResinWorkNo;
    @BindView(R.id.tv_resin_workNoValue)
    TextView tvResinWorkNoValue;
    @BindView(R.id.tv_resin_workName)
    TextView tvResinWorkName;
    @BindView(R.id.tv_resin_workNameValue)
    TextView tvResinWorkNameValue;
    @BindView(R.id.tv_resin_canGetNum)
    TextView tvResinCanGetNum;
    @BindView(R.id.tv_resin_canGetNumValue)
    TextView tvResinCanGetNumValue;
    @BindView(R.id.tv_resin_unit)
    TextView tvResinUnit;
    @BindView(R.id.tv_resin_unitValue)
    TextView tvResinUnitValue;
    @BindView(R.id.tv_resin_itemNo)
    TextView tvResinItemNo;
    @BindView(R.id.tv_resin_itemNoValue)
    TextView tvResinItemNoValue;
    @BindView(R.id.tv_resin_itemName)
    TextView tvResinItemName;
    @BindView(R.id.tv_resin_itemNameValue)
    TextView tvResinItemNameValue;
    @BindView(R.id.tv_resin_model)
    TextView tvResinModel;
    @BindView(R.id.tv_resin_modelValue)
    TextView tvResinModelValue;

    //以下部分是moveoutr的扫描结果
    @BindView(R.id.ll_moveOut_scanValue)
    LinearLayout llMoveOutScanValue;
    @BindView(R.id.tv_resout_changePeople)
    TextView tvResoutChangePeople;
    @BindView(R.id.tv_resout_changePeopleValue)
    TextView tvResoutChangePeopleValue;
    @BindView(R.id.tv_resout_orderNumber)
    TextView tvResoutOrderNumber;
    @BindView(R.id.tv_resout_orderumberValue)
    TextView tvResoutOrderumberValue;
    @BindView(R.id.tv_resout_thisWorkNo)
    TextView tvResoutThisWorkNo;
    @BindView(R.id.tv_resout_thisWorkNoValue)
    TextView tvResoutThisWorkNoValue;
    @BindView(R.id.tv_resout_thisWorkName)
    TextView tvResoutThisWorkName;
    @BindView(R.id.tv_resout_thisWorkNameValue)
    TextView tvResoutThisWorkNameValue;
    @BindView(R.id.tv_resout_nextWorkNo)
    TextView tvResoutNextWorkNo;
    @BindView(R.id.tv_resout_nextWorkNoValue)
    TextView tvResoutNextWorkNoValue;
    @BindView(R.id.tv_resout_nextWorkName)
    TextView tvResoutNextWorkName;
    @BindView(R.id.tv_resout_nextWorkNameValue)
    TextView tvResoutNextWorkNameValue;
    @BindView(R.id.tv_resout_canGetNum)
    TextView tvResoutCanGetNum;
    @BindView(R.id.tv_resout_canGetNumValue)
    TextView tvResoutCanGetNumValue;
    @BindView(R.id.tv_resout_unit)
    TextView tvResoutUnit;
    @BindView(R.id.tv_resout_unitValue)
    TextView tvResoutUnitValue;
    @BindView(R.id.tv_resout_itemNo)
    TextView tvResoutItemNo;
    @BindView(R.id.tv_resout_itemNoValue)
    TextView tvResoutItemNoValue;
    @BindView(R.id.tv_resout_itemName)
    TextView tvResoutItemName;
    @BindView(R.id.tv_resout_itemNameValue)
    TextView tvResoutItemNameValue;
    @BindView(R.id.tv_resout_model)
    TextView tvResoutModel;
    @BindView(R.id.tv_resout_modelValue)
    TextView tvResoutModelValue;

    /**
     * 提交
     */
    @BindView(R.id.commit)
    Button commit;

    private CommonLogic commonLogic;

    private ProcessTransferLogic processTransferLogic;

    /**
     * 获取转出人员信息WHAT
     */
    private final int INMOVEOUTWHAT = 1001;
    /**
     * 获取接受人员信息WHAT
     */
    private final int INMOVEINWHAT = 1002;
    /**
     * 接收流转卡号WHAT
     */
    private final int INCARDNUMWHAT = 1003;
    /**
     * 转移人员WHAT
     */
    private final int OUTCHANGEWHAT = 1004;
    /**
     * 转出流转卡号WHAT
     */
    private final int OUTCARDNUMWHAT = 1005;

    /**
     * handler
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case INMOVEOUTWHAT:
                    getEmployeeInfo((String) msg.obj,INMOVEOUTWHAT);
                    break;
                case INMOVEINWHAT:
                    getEmployeeInfo((String) msg.obj,INMOVEINWHAT);
                    break;
                case INCARDNUMWHAT:
                    getScanRunCardInfo((String) msg.obj,INCARDNUMWHAT);
                    break;
                case OUTCHANGEWHAT:
                    getEmployeeInfo((String) msg.obj,OUTCHANGEWHAT);
                    break;
                case OUTCARDNUMWHAT:
                    getScanRunCardInfo((String) msg.obj,OUTCARDNUMWHAT);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取员工信息方法
     * @param scanStr
     */
    private void getEmployeeInfo(String scanStr, final int scanType){
        HashMap<String,String> map = new HashMap<>();
        map.put(AddressContants.EMPLOYEENO,scanStr);
        commonLogic.scanEmployeeCode(map, new CommonLogic.ScanEmployeementListener() {
            @Override
            public void onSuccess(ScanEmployeeBackBean scanDepartmentBackBeann) {
                switch (scanType){
                    case INMOVEOUTWHAT:
                        tvResinMoveOutValue.setText(scanDepartmentBackBeann.getEmployee_name());
                        break;
                    case INMOVEINWHAT:
                        tvResinMoveInValue.setText(scanDepartmentBackBeann.getEmployee_name());
                        break;
                    case OUTCHANGEWHAT:
                        tvResoutChangePeopleValue.setText(scanDepartmentBackBeann.getEmployee_name());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });
    }

    /**
     * 获取扫描流转卡号信息
     * @param scanStr
     * @param scanType
     */
    private void getScanRunCardInfo(String scanStr, final int scanType){
        HashMap<String,String> map = new HashMap<>();
        map.put(AddressContants.PROCESSCARDNO,scanStr);
        processTransferLogic.scanRunCardCode(map, new ProcessTransferLogic.ScanRunCardListener() {
            @Override
            public void onSuccess(ProcessTransferBean scanBackBean) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PROCESSTRANSFER;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_process_transfer;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.work_change);
    }


    @Override
    protected void doBusiness() {
        commonLogic = CommonLogic.getInstance(activity,module,mTimestamp.toString());
        processTransferLogic = ProcessTransferLogic.getInstance(activity,module,mTimestamp.toString());
        ChooseMoveDailog.showChooseMoveDailog(activity, new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                //moveIn
                llMoveInScanValue.setVisibility(View.VISIBLE);
                llMoveInScan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCallback2() {
                //moveOut
                llMoveOutScanValue.setVisibility(View.VISIBLE);
                llMoveOutScan.setVisibility(View.VISIBLE);
            }
        });
    }

}
