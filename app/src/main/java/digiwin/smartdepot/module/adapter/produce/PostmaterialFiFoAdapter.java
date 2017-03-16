package digiwin.smartdepot.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.produce.FiFoBean;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;

/**
 * @des 领料过账 FIFO adapter
 * @author  唐孟宇
 */
public class PostmaterialFiFoAdapter extends BaseRecyclerAdapter<PostMaterialFIFOBean>{

    public PostmaterialFiFoAdapter(Context ctx, List<PostMaterialFIFOBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_postmaterialfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, PostMaterialFIFOBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_lot_no,item.getLot_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_actual_yield, item.getScan_sumqty());
    }
}