package digiwin.smartdepot.module.adapter.sale;

/**
 * Created by xiemeng on 2017/3/16.
 */

import android.content.Context;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;
/**
 * @des      销售出库汇总列表
 * @author  xiemeng
 * @date    2017/3/16
 */
public class SaleOutletSumAdapter extends BaseRecyclerAdapter<ListSumBean> {


    public SaleOutletSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_fininshedstorage;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ListSumBean item) {
        float numb1 = StringUtils.string2Float(item.getAvailable_in_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_plan_numb, StringUtils.deleteZero(String.valueOf(numb1)));
        holder.setText(R.id.tv_scaned_numb, StringUtils.deleteZero(String.valueOf(numb2)));
        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_scaned_numb, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_plan_numb,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_scaned_numb, mContext.getResources().getColor(R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_plan_numb, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_scaned_numb,mContext.getResources().getColor( R.color.Base_color));
        }

    }


}
