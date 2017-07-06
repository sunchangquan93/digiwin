package digiwin.smartdepot.module.adapter.common;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.FifoCheckBean;

/**
 * @des  有来源单据统一fifo
 * @date 2017/6/19
 * @author xiemeng
 */

public class CommonDocNoFIFoAdapter extends BaseRecyclerAdapter<FifoCheckBean> {

    public CommonDocNoFIFoAdapter(Context ctx, List<FifoCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_common_doc_no_fifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FifoCheckBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
        if(position == mItems.size() - 1){
            holder.setVisibility(R.id.fifo_bottom_line, View.GONE);
        }else{
            holder.setVisibility(R.id.fifo_bottom_line, View.VISIBLE);
        }
    }
}
