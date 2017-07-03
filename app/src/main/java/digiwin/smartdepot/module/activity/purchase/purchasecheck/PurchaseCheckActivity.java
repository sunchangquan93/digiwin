package digiwin.smartdepot.module.activity.purchase.purchasecheck;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.constant.SharePreKey;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.library.zxing.MipcaActivityCapture;
import digiwin.library.zxing.camera.GetBarCodeListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseActivity;
import digiwin.smartdepot.module.activity.common.ChoosePicActivity;
import digiwin.smartdepot.module.adapter.purchase.PurchaseCheckMainAdapter;
import digiwin.smartdepot.module.bean.purchase.BadReasonBean;
import digiwin.smartdepot.module.bean.purchase.PurchaseCheckBean;
import digiwin.smartdepot.module.bean.purchase.PurchaseCheckDetailBean;
import digiwin.smartdepot.module.logic.purchase.PurcahseCheckLogic;

/**
 * 收获检验 主界面
 *
 * @author 唐孟宇
 */
public class PurchaseCheckActivity extends BaseActivity {

    /**
     * 返回按钮
     */
    @BindView(R.id.iv_back)
    ImageView iv_back;


    @OnClick(R.id.tv_title_name)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        onBackPressed();
    }

    /**
     * 模块名
     */
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    /**
     * 扫描框
     */
    @BindView(R.id.ll_et_input)
    LinearLayout ll_et_input;

    /**
     * 扫描框
     */
    @BindView(R.id.et_input)
    EditText et_input;

    @OnTextChanged(value = R.id.et_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void scanBarcode(CharSequence s) {
        //TODO 扫描条码
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }

    }

    @OnClick(R.id.rl_iv_input_scan)
    void ScanCamara() {
        MipcaActivityCapture.startCameraActivity(activity, new GetBarCodeListener() {
            @Override
            public void onSuccess(String msg) {
                View focusView = ViewUtils.getFocusView(activity);
                if (focusView instanceof EditText) {
                    EditText et = (EditText) focusView;
                    et.setText(msg);
                    et.setSelection(msg.length());
                }
            }
        });
    }

    /**
     * 不良原因
     */
    @OnClick(R.id.ll_bad_reason)
    void lookBadReason() {
        if (purchaseCheckDetailBean == null) {
            showToast(R.string.please_select_one_data);
            return;
        } else {
            //TODO 跳转到不良原因界面
            Bundle bundle = new Bundle();
            bundle.putSerializable("purchaseCheckBean", purchaseCheckBean);
            bundle.putSerializable("purchaseCheckDetailBean", purchaseCheckDetailBean);
            if (null != badReasonMap && badReasonMap.size() > 0) {
                List<BadReasonBean> list = badReasonMap.get(purchaseCheckDetailBean.getItem_no()
                        +purchaseCheckDetailBean.getHead_seq()+purchaseCheckDetailBean.getSeq());
                if (null != list && list.size() > 0) {
                    bundle.putSerializable("badReasonList", (Serializable) list);
                }
            }
            bundle.putString(AddressContants.MODULEID_INTENT, module);
            ActivityManagerUtils.startActivityForBundleData(pactivity, BadReasonActivity.class, bundle);
        }
    }

    /**
     * 检查图纸
     */
    @OnClick(R.id.ll_check_pic)
    void checkPic() {
        //TODO 跳转到检查图纸界面
        if (purchaseCheckBean == null) {
            showToast(R.string.please_select_one_data);
            return;
        }
        Bundle bundle = new Bundle();
        purchaseCheckBean.setGls_bv08("IQC");
        bundle.putSerializable(CheckShowImageActivity.DATAKEY, purchaseCheckBean);
        bundle.putString(AddressContants.MODULEID_INTENT, module);
        ActivityManagerUtils.startActivityForBundleData(pactivity, CheckShowImageActivity.class, bundle);
    }

    /**
     * 拍照上传
     */
    @OnClick(R.id.ll_take_photo)
    void takePhoto() {
        //TODO 跳转到拍照上传
        if (purchaseCheckBean == null) {
            showToast(R.string.please_select_one_data);
            return;
        }
        purchaseCheckBean.setGls_bv08("IQC");
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChoosePicActivity.DATAKEY, purchaseCheckBean);
        bundle.putString(AddressContants.MODULEID_INTENT, module);
        ActivityManagerUtils.startActivityForBundleData(pactivity, ChoosePicActivity.class, bundle);
    }

    @BindView(R.id.rc_list)
    RecyclerView rc_list;

    @BindView(R.id.rc_main_list)
    RecyclerView rcMainList;

    /**
     * 提交
     */
    @OnClick(R.id.commit)
    void commit() {
        if (null != purchaseCheckAllBeen && purchaseCheckAllBeen.size() > 0) {
            final List<Map<String, String>> mapList = new ArrayList<>();
            //不良原因
            final List<List<Map<String, String>>> details = new ArrayList<>();
            for (PurchaseCheckBean tempBean : purchaseCheckAllBeen)
                if (AddressContants.Y.equals(tempBean.getCb_ischoose())
                        && tempBean.getQc_state().equals(AddressContants.Y)) {
                    if (null != allDataMap &&
                            null != allDataMap.get(tempBean.getSeq() + tempBean.getReceipt_no())) {
                        List<PurchaseCheckDetailBean> detailBeen = allDataMap.get(tempBean.getSeq() + tempBean.getReceipt_no());

                        for (int i = 0; i < detailBeen.size(); i++) {
                            PurchaseCheckDetailBean tempDetailBean = detailBeen.get(i);
                            tempDetailBean.setQc_state(tempBean.getQc_state());
                            mapList.add(ObjectAndMapUtils.getValueMap(tempDetailBean));
                            //不良原因组入
                            List<BadReasonBean> list = badReasonMap.get(tempDetailBean.getItem_no()
                                    + tempDetailBean.getHead_seq() + tempDetailBean.getSeq());
                            List<Map<String, String>> tempDetails = new ArrayList<>();
                            if (null != list) {
                                for (int j = 0; j < list.size(); j++) {
                                    Map<String, String> valueMap1 = ObjectAndMapUtils.getValueMap(list.get(j));
                                    tempDetails.add(valueMap1);
                                }
                            }
                            details.add(tempDetails);
                        }
                    }
                } else if (AddressContants.Y.equals(tempBean.getCb_ischoose())
                        && tempBean.getQc_state().equals("N")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("qc_state", tempBean.getQc_state());
                    map.put(AddressContants.RECEIPT_NO, tempBean.getReceipt_no());
                    map.put("receipt_seq", tempBean.getSeq());
                    map.put("ok_qty", tempBean.getOk_qty());
                    float unqua_qty = StringUtils.string2Float(tempBean.getQty()) - StringUtils.string2Float(tempBean.getOk_qty());
                    map.put("unqua_qty", String.valueOf(unqua_qty));
                    mapList.add(map);
                    List<Map<String, String>> tempDetails = new ArrayList<>();
                    details.add(tempDetails);

                }
            showCommitSureDialog(new OnDialogTwoListener() {
                @Override
                public void onCallback1() {
                    showLoadingDialog();
                    logic.updateQcData(mapList, details, new PurcahseCheckLogic.UpdateQCDataListener() {
                        @Override
                        public void onSuccess(String msg) {
                            dismissLoadingDialog();
                            showCommitSuccessDialog(msg);
                            createNewModuleId(module);
                            //TODO 清空本地临时数据
                            clearData();
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showCommitFailDialog(error);
                        }
                    });
                }

                @Override
                public void onCallback2() {

                }
            });
        }
    }

    private void clearData() {
        purchaseCheckMainBeen = new ArrayList<>();
        mainAdapter = new PurchaseCheckMainAdapter(pactivity, purchaseCheckMainBeen);
        rcMainList.setAdapter(mainAdapter);
        purchaseCheckAllBeen = new ArrayList<>();
        purchaseCheckBeanList = new ArrayList<>();
        purchaseCheckBean = new PurchaseCheckBean();
        purchaseCheckDetailList = null;
        purchaseCheckDetailBean = new PurchaseCheckDetailBean();

        adapter1 = new PurchaseCheckAdapter(pactivity, purchaseCheckBeanList);
        rc_list.setAdapter(adapter1);
        detailAdapter = new PurchaseCheckDetailAdapter(pactivity, purchaseCheckDetailList);
        if (null != adapter1.rc_detail_list) {
            adapter1.rc_detail_list.setAdapter(detailAdapter);
        }
        selectPosition = -1;
//        seq = "";
        allDataMap.clear();
        positionMap.clear();
        badReasonMap.clear();
        defectMap.clear();
        badReasonList = null;
        isShow = false;
        defect_num = 0;
        logic = PurcahseCheckLogic.getInstance(pactivity, module, mTimestamp.toString());
    }

    PurchaseCheckActivity pactivity;

    PurcahseCheckLogic logic;
    private PurchaseCheckMainAdapter mainAdapter;
    /**
     * 下方外层adapter
     */
    private PurchaseCheckAdapter adapter1;
    /**
     * 下方里层adapter
     */
    private PurchaseCheckDetailAdapter detailAdapter;
    /**
     * 扫描送货单号/物料条码
     */
    final int BARCODEWHAT = 1234;

    private String PAGESIZE = "pagesize";

    /**
     * 是否展示item明细
     */
    boolean isShow = false;
    // 迭代出展示的数据
    /**
     * 上方主数据
     */
    List<PurchaseCheckBean> purchaseCheckMainBeen;
    /**
     * 外层的所有数据
     */
    List<PurchaseCheckBean> purchaseCheckAllBeen;
    /**
     * 下方外层当前显示的数据
     */
    List<PurchaseCheckBean> purchaseCheckBeanList;
    /**
     * 下方里层当前显示的数据
     */
    List<PurchaseCheckDetailBean> purchaseCheckDetailList;
    /**
     * 所有的detail数据
     * key值seq+receipt_no
     */
    Map<String, List<PurchaseCheckDetailBean>> allDataMap = new HashMap<>();
    /**
     * 所有不良原因数据
     * key值item_no+head_seq+seq
     */
    Map<String, List<BadReasonBean>> badReasonMap = new HashMap<>();

    List<BadReasonBean> badReasonList;


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_check;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        EventBus.getDefault().register(activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 来自不良原因界面的不良数量
     */
    float defect_num = 0;

    Map<Integer, Integer> defectMap = new HashMap<>();

    /**
     * 接收来自不良原因界面的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainT(List<BadReasonBean> badReasonBeanList) {
        if (badReasonBeanList.size() > 0) {
            badReasonList = new ArrayList<>();
            badReasonList.addAll(badReasonBeanList);
            badReasonMap.put(purchaseCheckDetailBean.getItem_no() + purchaseCheckDetailBean.getHead_seq() + purchaseCheckDetailBean.getSeq(), badReasonList);
            for (int i = 0; i < badReasonBeanList.size(); i++) {
                defect_num += StringUtils.string2Float(badReasonBeanList.get(i).getDefect_qty());
            }
            defectMap.put(positionMap.get(selectPosition), (int) defect_num);
            defect_num = 0;
            detailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseCheckActivity) activity;
        logic = PurcahseCheckLogic.getInstance(pactivity, module, mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        rc_list.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(pactivity);
        rcMainList.setLayoutManager(linearLayoutManager2);
        et_input.requestFocus();
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASECHECK;
        return module;
    }

    protected void initNavigationTitle() {
        tv_title_name.setText(R.string.purchase_check);
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    Map<String, String> map = new HashMap<>();
                    map.put(AddressContants.BARCODE_NO, msg.obj.toString().trim());
                    map.put(PAGESIZE, (String) SharedPreferencesUtils.get(pactivity, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM));
                    showLoadingDialog();
                    logic.getMaterialToCheck(map, new PurcahseCheckLogic.GetMaterialToCheckListener() {
                        @Override
                        public void onSuccess(List<PurchaseCheckBean> purchaseCheckBeen) {
                            dismissLoadingDialog();
                            et_input.setText("");
                            if (purchaseCheckBeen.size() > 0) {
                                purchaseCheckAllBeen = new ArrayList<PurchaseCheckBean>();
                                purchaseCheckAllBeen = purchaseCheckBeen;
                                initMainAdapter(purchaseCheckAllBeen);


                            } else {
                                purchaseCheckMainBeen = new ArrayList<PurchaseCheckBean>();
                                mainAdapter = new PurchaseCheckMainAdapter(pactivity, purchaseCheckMainBeen);
                                rcMainList.setAdapter(mainAdapter);

                                purchaseCheckBeanList = new ArrayList<PurchaseCheckBean>();
                                adapter1 = new PurchaseCheckAdapter(pactivity, purchaseCheckBeanList);
                                rc_list.setAdapter(adapter1);
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_input.setText("");
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    /**
     * 先将数据按送货单分类
     *
     * @param checkBeen
     */
    private void initMainAdapter(final List<PurchaseCheckBean> checkBeen) {
        allDataMap = new HashMap<String, List<PurchaseCheckDetailBean>>();
        Map<String, PurchaseCheckBean> map = new HashMap<String, PurchaseCheckBean>();
        if (checkBeen != null) {
            for (PurchaseCheckBean data : checkBeen) {
                if (map.get(data.getDelivery_bill_no()) == null) {
                    map.put(data.getDelivery_bill_no(), data);
                }
            }
        }
        purchaseCheckMainBeen = new ArrayList<PurchaseCheckBean>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            PurchaseCheckBean alldata = map.get(it.next());
            purchaseCheckMainBeen.add(alldata);
        }
        mainAdapter = new PurchaseCheckMainAdapter(activity, purchaseCheckMainBeen);
        rcMainList.setAdapter(mainAdapter);
        if (purchaseCheckAllBeen.size() > 0) {
            initCheck(0, purchaseCheckMainBeen.get(0));
        }
        mainAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                initCheck(position, purchaseCheckMainBeen.get(position));
            }
        });

    }


    /**
     * 下方外层布局
     */
    private void initCheck(int mainPosition, PurchaseCheckBean mainItemBean) {
        int clickPosition = -1;
        if (mainPosition != clickPosition) {
            clickPosition = mainPosition;
        } else {
            clickPosition = -1;
        }
        mainAdapter.setSelectPosition(clickPosition);
        mainAdapter.notifyDataSetChanged();


        purchaseCheckBeanList = new ArrayList<>();
        for (PurchaseCheckBean tempBean : purchaseCheckAllBeen) {
            if (null != tempBean.getDelivery_bill_no()
                    && tempBean.getDelivery_bill_no().equals(mainItemBean.getDelivery_bill_no())) {
                if (!StringUtils.isBlank(tempBean.getOk_qty())&&null == tempBean.getCb_ischoose()) {
                    tempBean.setCb_ischoose("Y");
                }
                purchaseCheckBeanList.add(tempBean);
            }
        }
        adapter1 = new PurchaseCheckAdapter(pactivity, purchaseCheckBeanList);
        rc_list.setAdapter(adapter1);
        isShow = false;
        purchaseCheckDetailList = new ArrayList<PurchaseCheckDetailBean>();
        detailAdapter = new PurchaseCheckDetailAdapter(pactivity, purchaseCheckDetailList);
        badReasonList = new ArrayList<BadReasonBean>();
        clickItem();
    }


    /**
     * 物料信息点击位置
     */
    int selectPosition = -1;
    /**
     * 下方里面一层选中标记
     */
    Map<Integer, Integer> positionMap = new HashMap<>();

    PurchaseCheckBean purchaseCheckBean;

    PurchaseCheckDetailBean purchaseCheckDetailBean;

    /**
     * 外层item点击调用接口
     */
    public void clickItem() {
        adapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                selectPosition = position;
                positionMap.put(selectPosition, -1);
                purchaseCheckBean = purchaseCheckBeanList.get(selectPosition);
                if (!isShow) {
                    List<PurchaseCheckDetailBean> checkDetailBeen = allDataMap.get(purchaseCheckBean.getSeq() + purchaseCheckBean.getReceipt_no());
                    if (null != checkDetailBeen) {
                        detailAdapter = new PurchaseCheckDetailAdapter(pactivity, checkDetailBeen);
                        isShow = true;
                        adapter1.notifyDataSetChanged();
                        detailAdapterClick();
                        return;
                    }
                    final Map<String, String> map = new HashMap<String, String>();
//                    receipt_no   string          收货单号
//                    turn_order   string          分批顺序
//                    seq          string          项次号
                    map.put(AddressContants.RECEIPT_NO, purchaseCheckBean.getReceipt_no());
                    map.put("turn_order", purchaseCheckBean.getTurn_order());
                    map.put("seq", purchaseCheckBean.getSeq());
                    showLoadingDialog();
                    logic.getIQCDetailList(map, new PurcahseCheckLogic.GetIQCListListener() {
                        @Override
                        public void onSuccess(List<PurchaseCheckDetailBean> detailList) {
                            dismissLoadingDialog();
                            purchaseCheckDetailList = new ArrayList<PurchaseCheckDetailBean>();
                            purchaseCheckDetailList.addAll(detailList);
                            for (int i = 0; i < purchaseCheckDetailList.size(); i++) {
                                purchaseCheckDetailList.get(i).setHead_seq(map.get("seq"));
                                purchaseCheckDetailList.get(i).setTurn_order(purchaseCheckBean.getTurn_order());
                                purchaseCheckDetailList.get(i).setReceipt_no(purchaseCheckBean.getReceipt_no());
                                purchaseCheckDetailList.get(i).setItem_no(purchaseCheckBean.getItem_no());
                            }
                            detailAdapter = new PurchaseCheckDetailAdapter(pactivity, purchaseCheckDetailList);
                            allDataMap.put(map.get("seq") + map.get(AddressContants.RECEIPT_NO), purchaseCheckDetailList);
                            isShow = true;
                            adapter1.notifyDataSetChanged();
                            detailAdapterClick();
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error);
                        }
                    });
                } else {
                    isShow = false;
                    adapter1.notifyDataSetChanged();
                }
            }

        });
    }

    /**
     * 里面的item点击
     */
    private void detailAdapterClick() {
        try {
            detailAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    int clickPosition = -1;
                    if (position != clickPosition) {
                        clickPosition = position;
                        positionMap.put(selectPosition, clickPosition);
                        purchaseCheckDetailBean = purchaseCheckDetailList.get(position);
                    } else {
                        clickPosition = -1;
                        positionMap.put(selectPosition, clickPosition);
                        purchaseCheckDetailBean = new PurchaseCheckDetailBean();
                    }
                    detailAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {

        }
    }

    class PurchaseCheckAdapter extends BaseRecyclerAdapter<PurchaseCheckBean> {

        RecyclerViewHolder viewHolder = null;

        public RecyclerView rc_detail_list = null;

        public PurchaseCheckAdapter(Context ctx, List<PurchaseCheckBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_purchase_check_form1;
        }

        @Override
        protected void bindData(final RecyclerViewHolder holder, int position, final PurchaseCheckBean item) {
            viewHolder = holder;
            //合格量栏位：如果返回的QC单检验否=N，则该栏位可以输入，
            // 如果不是，则该栏位不能输入；该栏位需大于等于0；
            // 如果合格量=0，则判定状态图标为P，否则为N
            holder.setText(R.id.et_ok_num, item.getOk_qty());
            final EditText et_ok_num = holder.getEditText(R.id.et_ok_num);
            if (!StringUtils.isBlank(item.getOk_qty())) {
                et_ok_num.setText(item.getOk_qty());
            }
            et_ok_num.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            if (!item.getQc_state().equals("N")) {
                et_ok_num.setOnKeyListener(null);
            }
            CheckBox cbIsChoose = (CheckBox) holder.getView(R.id.cb_ischoose);
            cbIsChoose.setTag(position);
            if (AddressContants.Y.equals(item.getCb_ischoose())) {
                cbIsChoose.setChecked(true);
            } else {
                cbIsChoose.setChecked(false);
            }
            cbIsChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int tag = (int) et_ok_num.getTag();
                    if (isChecked) {
                        purchaseCheckBeanList.get(tag).setCb_ischoose(AddressContants.Y);
                    } else {
                        purchaseCheckBeanList.get(tag).setCb_ischoose(AddressContants.N);
                    }
                }
            });
            et_ok_num.setTag(position);
            et_ok_num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int tag = (int) et_ok_num.getTag();
                    if (!StringUtils.isBlank(s.toString().trim())) {
                        purchaseCheckBeanList.get(tag).setOk_qty(s.toString().trim());
                        if (StringUtils.string2Float(et_ok_num.getText().toString().trim()) == 0) {
                            holder.setText(R.id.tv_check_state, "P");
                        } else {
                            holder.setText(R.id.tv_check_state, "N");
                        }
                    } else {
                        purchaseCheckBeanList.get(tag).setOk_qty("0");
                    }
                }
            });
            holder.setText(R.id.tv_item_seq, item.getSeq());
            holder.setText(R.id.tv_item_name, item.getItem_name());
            holder.setText(R.id.tv_model, item.getItem_spec());
            holder.setText(R.id.tv_picture_no, item.getDrawing_no());
            holder.setText(R.id.tv_send_check_num, item.getQty());

            if (isShow) {
                if (selectPosition == position) {
                    holder.setVisibility(R.id.ll_form_detail, View.VISIBLE);
                    rc_detail_list = holder.findViewById(R.id.rc_list_detail);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
                    rc_detail_list.setLayoutManager(linearLayoutManager);
                    rc_detail_list.setAdapter(detailAdapter);
                }
            } else {
                holder.setVisibility(R.id.ll_form_detail, View.GONE);
            }
        }

        public RecyclerViewHolder getHolder() {
            if (null != viewHolder) {
                return viewHolder;
            } else {
                return null;
            }
        }
    }

    /**
     * RecycleView二级Adapter
     */
    class PurchaseCheckDetailAdapter extends BaseRecyclerAdapter<PurchaseCheckDetailBean> {

        public RecyclerViewHolder viewHolder = null;

        public PurchaseCheckDetailAdapter(Context ctx, List<PurchaseCheckDetailBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_purchase_check_form_detail;
        }

        @Override
        protected void bindData(final RecyclerViewHolder holder, int position, final PurchaseCheckDetailBean item) {
            viewHolder = holder;
            final TextView tv_project_check = holder.findViewById(R.id.tv_project_check);
            tv_project_check.setTag(position);
            final EditText et_defect_num = holder.getEditText(R.id.et_defect_num);
            et_defect_num.setTag(position);
            et_defect_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        et_defect_num.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                try {
                                    if (!StringUtils.isBlank(s.toString().trim())) {
                                        int tag = (int) et_defect_num.getTag();
                                        purchaseCheckDetailList.get(tag).setDefect_qty(s.toString().trim());
                                        /**
                                         * 当单身任何一个缺点数超过对应RE值时，
                                         * 则该笔明细项目的“项目判定”栏位就变成退货；
                                         * 然后单头的合格量=0，判定状态就变成N;
                                         */
                                        if (StringUtils.string2Float(s.toString().trim()) > StringUtils.string2Float(item.getRe_qty())) {
                                            holder.setText(R.id.tv_project_check, getResources().getString(R.string.goods_return));
                                            purchaseCheckDetailList.get(tag).setItem_deter(getResources().getString(R.string.goods_return));
                                            for (int i = 0; i < purchaseCheckBeanList.size(); i++) {
                                                if (purchaseCheckBeanList.get(i).getSeq().equals(item.getHead_seq())) {
                                                    purchaseCheckBeanList.get(i).setOk_qty("0");
                                                }
                                            }
                                        } else {
                                            holder.setText(R.id.tv_project_check, item.getItem_deter());
                                        }
//                                    et_defect_num.clearFocus();
//                                    allDataMap.put(selectPosition, purchaseCheckDetailList);
                                    }
                                } catch (Exception e) {

                                }
                            }

                        });
                    }
                }
            });


            /**
             * 不良数量大于Ac值，项目判定为退货
             */

            try {
                if (defectMap.size() > 0) {
                    if (position == positionMap.get(selectPosition)) {
                        if (null != defectMap.get(position)) {
                            if (defectMap.get(position) > StringUtils.string2Float(purchaseCheckDetailBean.getAc_qty())) {
                                purchaseCheckDetailList.get(position).setDefect_qty(String.valueOf(defectMap.get(position)));
                                purchaseCheckDetailList.get(position).setItem_deter(getResources().getString(R.string.goods_return));
                            }
                        }
                    }
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

            holder.setText(R.id.et_defect_num, item.getDefect_qty());
            holder.setText(R.id.tv_project_check, item.getItem_deter());
            holder.setText(R.id.tv_item_seq, item.getSeq());
            holder.setText(R.id.tv_check_item, item.getInspection_item());
            holder.setText(R.id.tv_aql, item.getAql());
            holder.setText(R.id.tv_select_check_num, item.getSample_qty());
            holder.setText(R.id.tv_ac, item.getAc_qty());
            holder.setText(R.id.tv_re, item.getRe_qty());

            if (position == positionMap.get(selectPosition)) {
                holder.setBackground(R.id.item_ll, R.color.green7d);
            } else {
                holder.setBackground(R.id.item_ll, R.color.gray_da);
            }
        }

        public RecyclerViewHolder getHolder() {
            if (null != viewHolder) {
                return viewHolder;
            } else {
                return null;
            }
        }

    }

}
