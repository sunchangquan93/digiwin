package digiwin.smartdepot.module.adapter.purchase;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.ListSumBean;

/**
 * @author 赵浩然
 * @module 采购收货 快速 适配器
 * @date 2017/3/8
 */
    public class MaterialReceiptAdapter extends BaseRecyclerAdapter<ListSumBean> {
    public List<ListSumBean> listData = new ArrayList<ListSumBean>();

    public MaterialReceiptAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();
    }

    public List<ListSumBean> getCheckData(){
        List<ListSumBean> checkedList = new ArrayList<ListSumBean>();
        for(ListSumBean detailData : listData){
            if("2".equals(detailData.getCheck())){
                checkedList.add(detailData);
            }else{
                checkedList.clear();
                return checkedList;
            }
        }
        Log.d("ListSumData==:",checkedList.size()+"");
        return checkedList;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_materialreceipt;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position,final ListSumBean item) {

        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_unit, item.getUnit_no());
        holder.setText(R.id.tv_item_name,item.getItem_name());
        holder.setText(R.id.tv_item_format, item.getStorage_spaces_no());
        holder.setText(R.id.tv_locator, item.getItem_spec());
        holder.setText(R.id.tv_delivery_quantity, StringUtils.deleteZero(item.getShortage_qty()));
        holder.setText(R.id.tv_actual_yield_num, StringUtils.deleteZero(item.getQty()));

        holder.getEditText(R.id.tv_actual_yield_num).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(StringUtils.isBlank(s.toString().trim())){
                    item.setQty("0");
                }else{
                    item.setQty(s.toString().trim());
                }
            }
        });

        final CheckBox checkBox = (CheckBox) holder.getView(R.id.cb_confirm_num);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("onCheckedChanged===:","onCheckedChanged");
                if(isChecked == true){
                    item.setCheck("2");
                    checkBox.setChecked(true);
                } else {
                    item.setCheck("1");
                    checkBox.setChecked(false);
                }
            }
        });
    }
}
