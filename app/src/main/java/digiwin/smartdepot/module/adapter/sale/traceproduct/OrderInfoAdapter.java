package digiwin.smartdepot.module.adapter.sale.traceproduct;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.sale.traceproduct.OrderInfoBean;

/**
 * @author maoheng
 * @des 产品质量追溯 生产过程adapter
 * @date 2017/4/6
 */

public class OrderInfoAdapter extends BaseRecyclerAdapter<OrderInfoBean> {

    public OrderInfoAdapter(Context ctx, List<OrderInfoBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_trace_order_info;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, OrderInfoBean item) {
        holder.setText(R.id.tv_item_no,item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_spec,item.getItem_spec());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_biaozhun_use, StringUtils.deleteZero(item.getStandard_qpa()));
        holder.setText(R.id.tv_relatity_use,StringUtils.deleteZero(item.getActual_qpa()));
        holder.setText(R.id.tv_is_should,StringUtils.deleteZero(item.getRecommended_qty()));
        holder.setText(R.id.tv_relatity,StringUtils.deleteZero(item.getQty()));
    }
}
