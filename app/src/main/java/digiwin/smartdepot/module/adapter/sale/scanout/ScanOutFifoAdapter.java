package digiwin.smartdepot.module.adapter.sale.scanout;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutFifoAdapter extends BaseRecyclerAdapter<PostMaterialFIFOBean> {
    public ScanOutFifoAdapter(Context ctx, List<PostMaterialFIFOBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_scanoutfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, PostMaterialFIFOBean item) {
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_model,item.getItem_spec());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_date,item.getLot_date());
        holder.setText(R.id.tv_rdna_num,item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount,item.getScan_sumqty());
    }
}
