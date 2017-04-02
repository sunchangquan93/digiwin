package digiwin.smartdepot.module.adapter.stock;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.main.logic.GetStorageLogic;
import digiwin.smartdepot.module.bean.common.ListSumBean;

/**
 * @author 赵浩然
 * @module 快速入库 适配器
 * @date 2017/3/31
 */

public class QuickStorageAdapter extends BaseRecyclerAdapter<ListSumBean> {
    public List<ListSumBean> listData = new ArrayList<ListSumBean>();

    public QuickStorageAdapter(Context ctx, List<ListSumBean> list) {
        super(ctx, list);
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();
    }

    public List<ListSumBean> getCheckData(){
        List<ListSumBean> checkedList = new ArrayList<ListSumBean>();
        for(ListSumBean detailData : listData){
            if(Float.valueOf(detailData.getMatch_qty()) > 0){
                checkedList.add(detailData);
            }else{
                checkedList.clear();
                return checkedList;
            }
        }

        return checkedList;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_quickstorage;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final ListSumBean item) {

        holder.setText(R.id.tv_item_seq, item.getReceipt_seq());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_format, item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getReq_qty()));
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getMatch_qty()));

        holder.getEditText(R.id.tv_match_number).addTextChangedListener(new TextWatcher() {
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
                }else if(Float.valueOf(item.getReq_qty()) < Float.valueOf(item.getMatch_qty())){
                    item.setQty(item.getMatch_qty());
                }else{
                    item.setQty(s.toString().trim());
                }
            }
        });

        final List<String> listWare = GetStorageLogic.getWareString();
        for(int i = 0; i < listWare.size();i++){
            if(LoginLogic.getWare().equals(listWare.get(i))){
                listWare.add(0,listWare.remove(i));
            }
        }
        holder.setText(R.id.tv_storage, listWare.get(0));

        holder.setClickListener(R.id.tv_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
