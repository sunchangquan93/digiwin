package digiwin.smartdepot.module.fragment.dailywork.orderwork;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 报工扫描页
 * @date 2017/3/13
 */

public class OrderDailyWorkScanFg extends BaseFragment {
    /**
     * 工单号
     */
    @BindView(R.id.ll_scan_order)
    LinearLayout llScanOrder;
    @BindView(R.id.tv_scan_order)
    TextView tvScanOrder;
    @BindView(R.id.et_scan_order)
    EditText etScanOrder;
    @BindView(R.id.cb_order_numlock)
    CheckBox cbOrderNumlock;
    /**
     * 流转卡号
     */
    @BindView(R.id.tv_scan_roam)
    TextView tvScanRoam;
    @BindView(R.id.et_scan_roam)
    EditText etScanRoam;
    @BindView(R.id.ll_scan_roam)
    LinearLayout llScanRoam;
    /**
     * 机器编号
     */
    @BindView(R.id.tv_scan_machine)
    TextView tvScanMachine;
    @BindView(R.id.et_scan_machine)
    EditText etScanMachine;
    @BindView(R.id.ll_scan_machine)
    LinearLayout llScanMachine;
    /**
     * 报工数
     */
    @BindView(R.id.tv_worknum)
    TextView tvWorknum;
    @BindView(R.id.et_worknum)
    EditText etWorknum;
    @BindView(R.id.ll_worknum)
    LinearLayout llWorknum;
    /**
     * 报废数
     */
    @BindView(R.id.tv_scrapnum)
    TextView tvScrapnum;
    @BindView(R.id.et_scrapnum)
    EditText etScrapnum;
    @BindView(R.id.ll_scrapnum)
    LinearLayout llScrapnum;
    /**
     * 超出转移
     */
    @BindView(R.id.tv_beyond_move)
    TextView tvBeyondMove;
    @BindView(R.id.et_beyond_move)
    EditText etBeyondMove;
    @BindView(R.id.ll_beyond_move)
    LinearLayout llBeyondMove;
    /**
     * 展示
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @BindViews({R.id.et_scan_order, R.id.et_scan_roam, R.id.et_scan_machine,
            R.id.et_worknum, R.id.et_scrapnum, R.id.et_beyond_move})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_order, R.id.ll_scan_roam, R.id.ll_scan_machine,
            R.id.ll_worknum,R.id.ll_scrapnum,R.id.ll_beyond_move})
    List<View> views;
    @BindViews({R.id.tv_scan_order, R.id.tv_scan_roam, R.id.tv_scan_machine,
            R.id.tv_worknum, R.id.tv_scrapnum, R.id.tv_beyond_move})
    List<TextView> textViews;

    @OnCheckedChanged(R.id.cb_order_numlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanOrder.setKeyListener(null);
        } else {
            etScanOrder.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_order)
    void orderFocusChanage() {
        ModuleUtils.viewChange(llScanOrder, views);
        ModuleUtils.etChange(activity, etScanOrder, editTexts);
        ModuleUtils.tvChange(activity, tvScanOrder, textViews);
    }

    @OnFocusChange(R.id.et_scan_roam)
    void roamFocusChanage() {
        ModuleUtils.viewChange(llScanRoam, views);
        ModuleUtils.etChange(activity, etScanRoam, editTexts);
        ModuleUtils.tvChange(activity, tvScanRoam, textViews);
    }

    @OnFocusChange(R.id.et_scan_machine)
    void machineFocusChanage() {
        ModuleUtils.viewChange(llScanMachine, views);
        ModuleUtils.etChange(activity, etScanMachine, editTexts);
        ModuleUtils.tvChange(activity, tvScanMachine, textViews);
    }
    @OnFocusChange(R.id.et_scrapnum)
    void scrapFocusChanage() {
        ModuleUtils.viewChange(llScrapnum, views);
        ModuleUtils.etChange(activity, etScrapnum, editTexts);
        ModuleUtils.tvChange(activity, tvScrapnum, textViews);
    }
    @OnFocusChange(R.id.et_beyond_move)
    void beyonMoveFocusChanage() {
        ModuleUtils.viewChange(llBeyondMove, views);
        ModuleUtils.etChange(activity, etBeyondMove, editTexts);
        ModuleUtils.tvChange(activity, tvBeyondMove, textViews);
    }
    /**
     * 工单号
     */
    final int ORDERWHAT = 1001;
    /**
     * 流转卡号
     */
    final int ROAMWHAT = 1002;
    /**
     * 机器编号
     */
    final int MACHINEWHAT=1003;

    /**
     * 工单展示
     */
    String orderShow;
    /**
     * 流转展示
     */
    String roamShow;
    /**
     * 机器展示
     */
    String machineShow;
    /**
     * 工单扫描
     */
    boolean orderFlag;
    /**
     * 流转扫描
     */
    boolean roamFlag;
    /**
     * 机器扫描
     */
    boolean machineFlag;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ORDERWHAT:
                    HashMap<String, String> orderMap = new HashMap<>();
                    orderMap.put("", String.valueOf(msg.obj));

                    break;
                case ROAMWHAT:
                    HashMap<String, String> roamMap = new HashMap<>();
                    roamMap.put("", String.valueOf(msg.obj));

                    break;
                case MACHINEWHAT:
                    HashMap<String, String> machineMap = new HashMap<>();
                    machineMap.put("", String.valueOf(msg.obj));

                    break;
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_dailywork_scan;
    }

    @Override
    protected void doBusiness() {

    }


    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(orderShow + "\\n" + roamShow+"\\n"+machineShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            includeDetail.setVisibility(View.VISIBLE);}else {
            includeDetail.setVisibility(View.GONE);
        }
    }

}
