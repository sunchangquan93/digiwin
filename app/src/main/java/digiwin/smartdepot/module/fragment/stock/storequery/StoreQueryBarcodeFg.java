package digiwin.smartdepot.module.fragment.stock.storequery;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.stock.storequery.StoreQueryActivity;
import digiwin.smartdepot.module.adapter.stock.StoreQueryBacodeAdapter;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
/**
 * @author xiemeng
 * @des 库存查询---条码库存
 * @date 2017/3/22
 */
public class StoreQueryBarcodeFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    StoreQueryActivity pactivity;

    @Override
    protected int bindLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fg_store_query_barcode;
    }

    @Override
    protected void doBusiness() {
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        pactivity = (StoreQueryActivity) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 订阅成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(List<ListSumBean> sumBeen) {
        StoreQueryBacodeAdapter adapter = new StoreQueryBacodeAdapter(activity, sumBeen);
        ryList.setAdapter(adapter);
    }

}
