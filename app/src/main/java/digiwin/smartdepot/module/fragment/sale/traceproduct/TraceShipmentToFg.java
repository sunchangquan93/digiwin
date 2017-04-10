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
import digiwin.smartdepot.module.adapter.sale.traceproduct.ShipmentToAdapter;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.sale.traceproduct.ShipmentToBean;
import digiwin.smartdepot.module.logic.sale.traceproduct.TraceProductLogic;

/**
 * @author maoheng
 * @des 产品质量追溯 出货流向
 * @date 2017/4/6
 */

public class TraceShipmentToFg extends BaseFragment {

    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.rc_list)
    RecyclerView rcList;

    private TraceProductLogic productLogic;

    private TraceProductActivity tactivity;

    private ShipmentToAdapter adapter;

    private List<ShipmentToBean> list;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_trace_shipment_to;
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
            tvItemNo.setText(backBean.getItem_no());
            tvItemName.setText(backBean.getItem_name());
            tvModel.setText(backBean.getItem_spec());
            upDateList();
        }
    }

    public void upDateList(){
        list.clear();
        adapter = new ShipmentToAdapter(tactivity,list);
        rcList.setAdapter(adapter);
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.BARCODE_NO,TraceProductActivity.backBean.getBarcode_no());
        map.put(AddressContants.ITEM_NO,TraceProductActivity.backBean.getItem_no());
        productLogic.shipmentToGet(map, new TraceProductLogic.ShipmentToGetListener() {
            @Override
            public void onSuccess(List<ShipmentToBean> datas) {
                dismissLoadingDialog();
                list = datas;
                adapter = new ShipmentToAdapter(tactivity,list);
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
