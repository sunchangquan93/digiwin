package digiwin.smartdepot.module.adapter.produce;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.activity.produce.accordingmaterial.AccordingMaterialActivity;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SumShowBean;

/**
 * @author 赵浩然
 * @module 依成品发料 汇总数据
 * @date 2017/3/2
 */

public class AccordingMaterialSumAdapter extends BaseRecyclerAdapter<ListSumBean>{

    public AccordingMaterialSumAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_accodingmaterial;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {
        //判断实发量 和 欠料量
        float numb1 = StringUtils.string2Float(item.getShortage_qty());
        float numb2 = StringUtils.string2Float(item.getScan_sumqty());

        holder.setText(R.id.tv_item_no, item.getLow_order_iitem_no());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_name, item.getLow_order_item_name());
        holder.setText(R.id.tv_item_format, item.getLow_order_item_spec());
        holder.setText(R.id.tv_under_feed, StringUtils.deleteZero(item.getShortage_qty()));
        holder.setText(R.id.tv_stock_balance, StringUtils.deleteZero(item.getStock_qty()));
        holder.setText(R.id.tv_actual_yield, StringUtils.deleteZero(item.getScan_sumqty()));

        //跳转到明细
        holder.setClickListener(R.id.img_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumShowBean bean = new SumShowBean();
                bean.setItem_no(item.getLow_order_iitem_no());
                bean.setItem_name(item.getItem_name());
                Log.d("bean.setItem_no====:",bean.getItem_no());
                Log.d("bean.setItem_name==:",bean.getItem_name());
                AccordingMaterialActivity activity = (AccordingMaterialActivity) mContext;
                activity.ToDetailAct(bean);
            }
        });


        if (numb2 == 0) {
            holder.setBackground(R.id.item_ll,R.drawable.red_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_unit, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_name, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_under_feed, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_stock_balance, mContext.getResources().getColor(R.color.red));
            holder.setTextColor(R.id.tv_actual_yield, mContext.getResources().getColor(R.color.red));
        } else if (numb1 > numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.yellow_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_under_feed,mContext.getResources().getColor( R.color.outside_yellow));
            holder.setTextColor(R.id.tv_stock_balance, mContext.getResources().getColor(R.color.outside_yellow));
            holder.setTextColor(R.id.tv_actual_yield,mContext.getResources().getColor( R.color.outside_yellow));
        } else if (numb1 ==numb2) {
            holder.setBackground(R.id.item_ll,R.drawable.green_scandetail_bg);
            holder.setTextColor(R.id.tv_item_no,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_unit,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_name,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_item_format, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_under_feed,mContext.getResources().getColor( R.color.Base_color));
            holder.setTextColor(R.id.tv_stock_balance, mContext.getResources().getColor(R.color.Base_color));
            holder.setTextColor(R.id.tv_actual_yield,mContext.getResources().getColor( R.color.Base_color));
        }
    }
}
