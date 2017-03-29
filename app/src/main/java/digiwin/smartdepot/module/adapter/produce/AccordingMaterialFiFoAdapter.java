package digiwin.smartdepot.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.produce.FiFoBean;

/**
 * @author 赵浩然
 * @module 依成品发料扫描
 * @date 2017/3/3
 */

public class AccordingMaterialFiFoAdapter extends BaseRecyclerAdapter<FiFoBean>{

    public AccordingMaterialFiFoAdapter(Context ctx, List<FiFoBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_accordingmaterialfifo;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, FiFoBean item) {
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_barcode_no,item.getBarcode_no());
        holder.setText(R.id.tv_rdna_num, item.getRecommended_qty());
        holder.setText(R.id.tv_feeding_amount, item.getScan_sumqty());
    }
}
