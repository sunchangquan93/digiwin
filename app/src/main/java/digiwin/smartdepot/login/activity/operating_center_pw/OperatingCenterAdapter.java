package digiwin.smartdepot.login.activity.operating_center_pw;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digiwin.smartdepot.R;


/**
 * 营运中心适配器
 * @Author 毛衡
 * Created by Administrator on 2017/1/11 0011.
 */

public class OperatingCenterAdapter extends RecyclerView.Adapter<OperatingCenterAdapter.OCViewHolder> {

    private String TAG = "StorageAdapter";
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private OperatingCenterOnItemClickListener listener;

    public OperatingCenterAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public interface OperatingCenterOnItemClickListener{
        public void onClick(View view,int position);
    }
    public void setClick(OperatingCenterOnItemClickListener click) {
        this.listener = click;
    }
    @Override
    public OCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.operatingcenter_item_layout, null);
        return new OCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OCViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(view,position);
                }else {
                }
            }
        });
        if (position == 0){
            holder.tv_item_operatingCenter.setTextColor(Color.WHITE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Base_color));
        }else {
            holder.tv_item_operatingCenter.setTextColor(context.getResources().getColor(R.color.black_32));
            holder.itemView.setBackgroundResource(R.drawable.text_click_bg);
        }
        if(list.get(position)!=null){
            holder.tv_item_operatingCenter.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 运营中心
     * RecyclerView.ViewHolder
     * @Author 毛衡
     */
    class OCViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_item_operatingCenter)
        public TextView tv_item_operatingCenter;
        public OCViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
