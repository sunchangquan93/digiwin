package digiwin.smartdepot.module.adapter.sale;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;

/**
 * @des      销售出库fifo
 * @author  xiemeng
 * @date    2017/3/16
 */

public class SaleOutletFiFoAdapter extends BaseRecyclerAdapter<PostMaterialFIFOBean>{

    public SaleOutletFiFoAdapter(Context ctx, List<PostMaterialFIFOBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_saleoutletfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, PostMaterialFIFOBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_no,item.getLot_no());
        holder.setText(R.id.tv_barcode,item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}
