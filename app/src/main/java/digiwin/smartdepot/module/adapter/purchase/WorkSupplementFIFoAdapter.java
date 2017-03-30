package digiwin.smartdepot.module.adapter.purchase;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.produce.PostMaterialFIFOBean;

/**
 * @author 赵浩然
 * @module 依工单补料 FIFO适配器
 * @date 2017/3/24
 */

public class WorkSupplementFIFoAdapter extends BaseRecyclerAdapter<PostMaterialFIFOBean> {

    public WorkSupplementFIFoAdapter(Context ctx, List<PostMaterialFIFOBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_worksupplementfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, PostMaterialFIFOBean item) {
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_locator,item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}