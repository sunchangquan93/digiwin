package digiwin.smartdepot.module.adapter.stock.miscellaneousactive;

import android.content.Context;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;

/**
 * 有源杂项收料  清单adapter
 * Created by 孙长权 on 2017/7/10
 */

public class MiscellaneousActiveInAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {
    private boolean isOut;
    private Context context;
    public MiscellaneousActiveInAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
        context=ctx.getApplicationContext();
    }
    public MiscellaneousActiveInAdapter(Context ctx,boolean isOut, List<FilterResultOrderBean> list) {
        this(ctx, list);
        this.isOut=isOut;
    }

    @Override
    protected int getItemLayout(int i) {
        return R.layout.ryitem_miscellaneousissues_in_order;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int i, FilterResultOrderBean item) {
        holder.setText(R.id.tv_item_miscellaneous_in_no, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_item_person,item.getEmployee_name());
        holder.setText(R.id.tv_item_department,item.getDepartment_name());
        if(isOut){
            holder.getTextView(R.id.tv_number_no).setText(context.getResources().getString(R.string.miscellaneous_out_no));
        }
    }
}
