package digiwin.smartdepot.module.adapter.produce;

import android.content.Context;
import android.view.View;

import java.util.List;

import digiwin.smartdepot.R;
import digiwin.smartdepot.module.activity.produce.endproductallot.EndProductAllotActivity;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;

/**
 * @author xiemeng
 * @module 依成品调拨
 * @date 2017/4/13
 */
public class EndProductAllotSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public EndProductAllotSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_endproductallot_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        float numb1 = StringUtils.string2Float(item.getShortage_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getLow_order_item_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_format, item.getLow_order_item_spec());
        holder.setText(R.id.tv_material_return, StringUtils.deleteZero(item.getShortage_qty()));
        holder.setText(R.id.tv_material_return_big, StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_feeding_amount, StringUtils.deleteZero(item.getScan_sumqty()));
        holder.setText(R.id.tv_target_qty, StringUtils.deleteZero(item.getW_stock_qty()));

        //跳转到明细
        holder.setClickListener(R.id.img_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumShowBean bean = new SumShowBean();
                bean.setItem_no(item.getLow_order_item_no());
                bean.setItem_name(item.getItem_name());
                EndProductAllotActivity activity = (EndProductAllotActivity) mContext;
                activity.ToDetailAct(bean);
            }
        });


        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_target_qty, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_feeding_amount, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_target_qty, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_material_return,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_target_qty, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_material_return_big, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_feeding_amount,mContext.getResources().getColor( R.color.Base_color));
        }
    }
}
