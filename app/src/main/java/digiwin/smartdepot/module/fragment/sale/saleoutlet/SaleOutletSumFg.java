package digiwin.smartdepot.module.fragment.sale.saleoutlet;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.sale.saleoutlet.SaleOutletActivity;
import digiwin.smartdepot.module.bean.common.ClickItemPutBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 销售出库汇总页
 * @date 2017/3/16
 */
public class SaleOutletSumFg extends BaseFragment {
    @BindView(R.id.tv_head_post_order)
    TextView tvHeadPostOrder;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    /**
     * 出通单号
     */
    private String salenum;

    /**
     * 订阅成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(String sale) {
        salenum = sale;
    }

    SaleOutletActivity pactivity;
    CommonLogic commonLogic;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_saleoutlet_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (SaleOutletActivity) this.activity;
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }

    public void update() {
        if (StringUtils.isBlank(salenum)) {
            showFailedDialog(R.string.scan_sale, new OnDialogClickListener() {
                @Override
                public void onCallback() {
                    pactivity.mZXVp.setCurrentItem(0);
                }
            });
            return;
        }
        ClickItemPutBean clickItemPutBean = new ClickItemPutBean();
        clickItemPutBean.setDoc_no(salenum);
        commonLogic.getOrderSumData(clickItemPutBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {

            }
            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });

    }

}
