package digiwin.smartdepot.module.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.activity.common.CommonDetailActivity;
import digiwin.smartdepot.module.bean.common.DetailShowBean;


/**
 * @author xiemeng
 * @des 生产完工入库明细adapter
 * @date 2017/2/24
 */
public class CommonDetailAdapter extends BaseDetailRecyclerAdapter<DetailShowBean> {
    private static final String TAG = "CommonDetailAdapter";

    public CommonDetailAdapter(Context ctx, List<DetailShowBean> list) {
        super(ctx, list);
    }
    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_common_detail;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final DetailShowBean item) {
        holder.setText(R.id.tv_barcode, item.getBarcode_no());
        holder.setText(R.id.tv_locator, item.getStorage_spaces_no());
        holder.setText(R.id.tv_number, StringUtils.deleteZero(item.getBarcode_qty()));
        holder.setClickListener(R.id.tv_number, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (null!=listener){
                   listener.update(item,position,holder);
               }
            }
        });
        CheckBox cb = (CheckBox) holder.getView(R.id.cb_ischoose);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position,isChecked);
                isCheckAll();
            }
        });
        if (null != map.get(position)) {
            cb.setChecked(map.get(position));
        }else{
            cb.setChecked(false);
        }

    }
    @Override
    public  CheckBox getPCheckBox() {
        CommonDetailActivity pactivity = (CommonDetailActivity) mContext;
        return pactivity.cbAll;
    }
}
