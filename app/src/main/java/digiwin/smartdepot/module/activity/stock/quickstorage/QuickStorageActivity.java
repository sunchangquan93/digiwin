package digiwin.smartdepot.module.activity.stock.quickstorage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.main.logic.GetStorageLogic;
import digiwin.smartdepot.module.activity.common.WareHouseDialog;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 快速入库
 * @date 2017/3/31
 */

public class QuickStorageActivity extends BaseFirstModuldeActivity{

    private QuickStorageActivity activity;

    /**
     * 扫描入库单
     */
    public final int QUICKSTORAGECODE = 1003;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 单号
     */
    @BindView(R.id.tv_post_material_order)
    TextView tv_post_material_order;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;

    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    CommonLogic commonLogic;

    List<ListSumBean> checkedList;

    /**
     * 提交
     */
    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit(){
        try {
            if(checkedList.size() > 0){
                showCommitSureDialog(new OnDialogTwoListener() {
                    @Override
                    public void onCallback1() {
                        showLoadingDialog();
                        List<ListSumBean> commitList = new ArrayList<ListSumBean>();
                        for(int i = 0;i < checkedList.size();i++){
                            if(!StringUtils.isBlank(checkedList.get(i).getMatch_qty()) ||
                                    Float.valueOf(checkedList.get(i).getMatch_qty()) > 0){
                                checkedList.get(i).setReceipt_qty(StringUtils.deleteZero(checkedList.get(i).getReq_qty()));
                                checkedList.get(i).setQty(StringUtils.deleteZero(checkedList.get(i).getMatch_qty()));
                                commitList.add(checkedList.get(i));
                            }
                        }
                        commitData(commitList);
                    }
                    @Override
                    public void onCallback2() {

                    }
                });
            }else{
                showFailedDialog(getResources().getString(R.string.nodate));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showFailedDialog(getResources().getString(R.string.nodate));
        }
    }

    QuickStorageAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_quickstorage;
    }

    @Override
    protected void doBusiness() {

        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullylinearlayoutmanager = new FullyLinearLayoutManager(activity);
        ry_list.setLayoutManager(fullylinearlayoutmanager);

        FilterResultOrderBean data = (FilterResultOrderBean) getIntent().getSerializableExtra("data");
        tv_post_material_order.setText(data.getDoc_no());
        tv_date.setText(data.getCreate_date());
        tv_supplier.setText(data.getSupplier_name());

        mHandler.sendMessageDelayed(mHandler.obtainMessage(QUICKSTORAGECODE, data.getDoc_no()), AddressContants.DELAYTIME);

    }

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == QUICKSTORAGECODE){
                showLoadingDialog();
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setDoc_no(String.valueOf(msg.obj));
                putBean.setWarehouse_in_no(LoginLogic.getWare());
                commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        for (int i = 0;i < list.size();i++) {
                            list.get(i).setWarehouse_no(LoginLogic.getWare());
                        }
                        checkedList = new ArrayList<ListSumBean>();
                        checkedList = list;
                        adapter = new QuickStorageAdapter(activity,checkedList);
                        ry_list.setAdapter(adapter);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailed(String error) {
                        Log.d(TAG,error);
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                            }
                        });
                    }
                });
            }
            return false;
        }
    });

    public void clearData(){
        tv_post_material_order.setText("");
        tv_date.setText("");
        tv_supplier.setText("");
        ArrayList<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new QuickStorageAdapter(activity,list);
        ry_list.setAdapter(adapter);
    }

    public void commitData(final List<ListSumBean> checkedList){
        final List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(checkedList);
        commonLogic.commitList(listMap, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
                        activity.finish();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.QUICKSTORAGE;
        return module;
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.title_quickstorage);
    }

    public class QuickStorageAdapter extends BaseRecyclerAdapter<ListSumBean> {

        public QuickStorageAdapter(final Context ctx,List<ListSumBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_quickstorage;
        }

        @Override
        protected void bindData(final RecyclerViewHolder holder, final int position, final ListSumBean item) {

            holder.setText(R.id.tv_item_seq, item.getReceipt_seq());
            holder.setText(R.id.tv_item_name, item.getItem_name());
            holder.setText(R.id.tv_unit,item.getUnit_no());
            holder.setText(R.id.tv_item_format, item.getItem_spec());
            holder.setText(R.id.tv_item_no, item.getItem_no());
            holder.setText(R.id.tv_storage, item.getWarehouse_no());
            holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getReq_qty()));
            holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getMatch_qty()));

            final TextView wareHouseTv = holder.findViewById(R.id.tv_storage);
            wareHouseTv.setTag(position);
            final List<String> list = GetStorageLogic.getWareString();
            wareHouseTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WareHouseDialog.showWareHouseDialog(activity,LoginLogic.getWare(),list);
                }
            });

            //更新库位
            WareHouseDialog.setCallBack(new WareHouseDialog.WareHouseCallBack() {
                @Override
                public void wareHouseCallBack(String wareHouse) {
                    LogUtils.i("wareHouse===:",wareHouse);
                    LogUtils.i("getTag===:",(int) wareHouseTv.getTag());
                    checkedList.get((int) wareHouseTv.getTag()).setWarehouse_no(wareHouse);
                    notifyDataSetChanged();
                }
            });

            final EditText match_numberEt = holder.findViewById(R.id.tv_match_number);
            match_numberEt.setTag(position);
            holder.getEditText(R.id.tv_match_number).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(StringUtils.isBlank(s.toString().trim())){
                        checkedList.get((int) match_numberEt.getTag()).setMatch_qty("0");
                        notifyDataSetChanged();
                    }else if(Float.valueOf(item.getReq_qty()) < Float.valueOf(s.toString())){
                        showFailedDialog(getResources().getString(R.string.match_so_big));
                        checkedList.get((int) match_numberEt.getTag()).setMatch_qty(item.getMatch_qty());
                        notifyDataSetChanged();

                    }else{
                        checkedList.get((int) match_numberEt.getTag()).setMatch_qty(s.toString());
                    }
                }
            });
        }
    }
}
