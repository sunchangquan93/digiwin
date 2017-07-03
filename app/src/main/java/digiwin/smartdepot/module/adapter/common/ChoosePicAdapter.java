package digiwin.smartdepot.module.adapter.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepot.R;
import digiwin.smartdepot.module.bean.common.ChoosePicBean;

/**
 * @author xiemeng
 * @des 选择图片
 * @date 2017/4/19
 */
public class ChoosePicAdapter extends BaseRecyclerAdapter<ChoosePicBean> {
    private static final String TAG = "ChoosePicAdapter";
    public ChoosePicAdapter(Context ctx, List<ChoosePicBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_choosepic;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, ChoosePicBean item) {
        ImageView imageView = holder.getImageView(R.id.img_choose);
        if (position == 0 || position == mItems.size()-1) {
            Picasso.with(mContext).load(item.getDrawId()).into(imageView);
        } else {
            Picasso.with(mContext).load(new File(item.getPicPath())).config(Bitmap.Config.RGB_565).into(imageView);
        }
    }
}
