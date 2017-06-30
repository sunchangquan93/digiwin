package digiwin.smartdepot.module.adapter.purchase;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.purchase.PurchaseCheckBean;

/**
 * @author xiemeng
 * @des  收货检验上方显示列表
 * @date 2017/6/29 10:05
 */

public class PurchaseCheckMainAdapter extends BaseRecyclerAdapter<PurchaseCheckBean> {

    private int selectPosition;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public PurchaseCheckMainAdapter(Context ctx, List<PurchaseCheckBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_purchase_check_main;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final PurchaseCheckBean item) {
        holder.setText(R.id.tv_send_goods_order, item.getDelivery_bill_no());
        holder.setText(R.id.tv_supplier,item.getSuppier_name());
        holder.setText(R.id.tv_receipt_goods_order,item.getReceipt_no());
        holder.setText(R.id.tv_get_material_time,item.getReceipt_date());
        holder.setText(R.id.tv_to_get_material_time,item.getWait_min());
        if (position+1==mItems.size()){
            holder.getView(R.id.down_line).setVisibility(View.GONE);
        }

        if (position == selectPosition) {
            holder.setBackground(R.id.item_ll, R.color.green7d);
        } else {
            holder.setBackground(R.id.item_ll, R.color.gray_da);
        }
    }
}
