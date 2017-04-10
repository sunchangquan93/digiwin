package digiwin.smartdepot.module.fragment.sale.traceproduct;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.sale.tranceproduct.TraceProductActivity;
import digiwin.smartdepot.module.adapter.sale.traceproduct.OrderInfoAdapter;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.sale.traceproduct.OrderInfoBean;
import digiwin.smartdepot.module.logic.sale.traceproduct.TraceProductLogic;

/**
 * @author maoheng
 * @des 产品质量追溯 工单信息
 * @date 2017/4/6
 */

public class TraceOrderInfoFg extends BaseFragment {
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_gongDan_no)
    TextView tvGongDanNo;
    @BindView(R.id.tv_make_department)
    TextView tvMakeDepartment;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_product_draw_no)
    TextView tvProductDrawNo;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_production)
    TextView tvProduction;
    @BindView(R.id.tv_craft_route)
    TextView tvCraftRoute;
    @BindView(R.id.rc_list)
    RecyclerView rcList;

    private TraceProductLogic productLogic;

    private TraceProductActivity tactivity;

    private OrderInfoAdapter adapter;

    private List<OrderInfoBean> list;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_trace_order_info;
    }

    @Override
    protected void doBusiness() {
        tactivity = (TraceProductActivity) activity;
        productLogic = TraceProductLogic.getInstance(tactivity,tactivity.module,tactivity.mTimestamp.toString());
        LinearLayoutManager manager = new LinearLayoutManager(tactivity);
        rcList.setLayoutManager(manager);
        list = new ArrayList<>();
    }
    public void initData(){
        ScanBarcodeBackBean backBean = TraceProductActivity.backBean;
        if(null != backBean){
//            tvItemNo.setText(backBean.getItem_no());
//            tvItemName.setText(backBean.getItem_name());
//            tvModel.setText(backBean.getItem_spec());
//            tvGongDanNo.setText(backBean.get);
//            tvMakeDepartment.setText(backBean.get);
//            tvVersion.setText(backBean.get);
            upDateList();
        }
    }
    public void upDateList(){
        list.clear();
        adapter = new OrderInfoAdapter(tactivity,list);
        rcList.setAdapter(adapter);
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.BARCODE_NO,TraceProductActivity.backBean.getBarcode_no());
        map.put(AddressContants.ITEM_NO,TraceProductActivity.backBean.getItem_no());
        productLogic.orderInfoGet(map, new TraceProductLogic.OrderInfoGetListener() {
            @Override
            public void onSuccess(List<OrderInfoBean> datas) {
                dismissLoadingDialog();
                list = datas;
                adapter = new OrderInfoAdapter(tactivity,list);
                rcList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

}
