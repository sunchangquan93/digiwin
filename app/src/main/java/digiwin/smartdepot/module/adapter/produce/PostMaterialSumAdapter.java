package digiwin.smartdepot.module.adapter.produce;

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;

/**
 * @author 唐孟宇
 * @des  领料过账汇总adapter
 * @date 2017/3/02
 */

public class PostMaterialSumAdapter extends BaseRecyclerAdapter<ListSumBean> {

    public PostMaterialSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_post_material_sum;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getShortage_qty());
        float numb3 = StringUtils.string2Float(item.getStock_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_item_model, item.getItem_spec());
        holder.setText(R.id.tv_material_numb, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_locator_numb, StringUtils.deleteZero(String.valueOf(numb3)));
        holder.setText(R.id.tv_act_send_numb, StringUtils.deleteZero(String.valueOf(numb2)));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll, R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_material_numb, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_locator_numb, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_act_send_numb, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll, R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_material_numb, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_locator_numb, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_act_send_numb, mContext.getResources().getColor(R.color.outside_yellow));
        } else if (numb1 == numb2) {
            holder.setBackground(R.id.item_ll, R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_model, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_material_numb, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_locator_numb, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_act_send_numb, mContext.getResources().getColor(R.color.Base_color));
        }
    }
}
