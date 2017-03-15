package digiwin.smartdepot.module.adapter.sale;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;

/**
 * @des   销售出库列表清单
 * @author  xiemeng
 * @date    2017/3/13
 */
public class SaleOutletAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public SaleOutletAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_saleoutlet;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_general_number, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
    }
}
