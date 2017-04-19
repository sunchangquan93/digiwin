package digiwin.smartdepot.module.fragment.stock.productoutbox;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.module.activity.stock.productoutbox.ProductOutBoxActivity;
import digiwin.smartdepot.module.adapter.stock.ProductBinningDetailAdapter;
import digiwin.smartdepot.module.bean.stock.ProductBinningBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * @author 孙长权
 * @des 产品装箱--明细
 * @date 2017/3/23
 */
public class ProductOutBoxDetailFg extends BaseFragment {

    private ProductOutBoxActivity pactivity;

    private ProductBinningDetailAdapter adapter;

    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.ry_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_item_no)
    TextView tvPackNumber;

    public List<ProductBinningBean> mDetailShowBeen;

    public CommonLogic commonLogic;

    @OnClick(R.id.delete)
    void delete() {
        Map<Integer, Boolean> map = adapter.getMap();
        Set<Map.Entry<Integer, Boolean>> sets = map.entrySet();
        List<ProductBinningBean> deletelist = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : sets) {
            Integer key = entry.getKey();
            Boolean val = entry.getValue();
            if (null != val && val) {
                deletelist.add(mDetailShowBeen.get(key));
            }
        }
        if (deletelist.size() == 0) {
            showFailedDialog(R.string.delete_choose);
            return;
        }
        toDelete(deletelist, AddressContants.DELETETPYE);
    }
    @OnClick(R.id.cb_all)
    void onCheckChange() {
        boolean checked = cbAll.isChecked();
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int i = 0; i < mDetailShowBeen.size(); i++) {
            map.put(i, checked);
        }
        updateUI(map);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_productbinning_detail;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ProductOutBoxActivity) activity;
        mDetailShowBeen=new ArrayList<>();
        commonLogic = CommonLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        Map<Integer,Boolean> map = new HashMap<>();
        updateUI(map);
    }


    public void upDateList(){
        //获取包装箱号
        String number=pactivity.packBoxNumber;
        if(!StringUtils.isBlank(number)){
            tvPackNumber.setText(number);
            HashMap<String,String> map=new HashMap<>();
            map.put(AddressContants.PACKAGENO,number);
            commonLogic.scanPackBoxNumber(map, new CommonLogic.ScanPackBoxNumberListener() {
                @Override
                public void onSuccess(List<ProductBinningBean> productBinningBeans) {
                    mDetailShowBeen=productBinningBeans;
                    cbAll.setChecked(false);
                    boolean checked = cbAll.isChecked();
                    HashMap<Integer, Boolean> map = new HashMap<>();
                    for (int i = 0; i < mDetailShowBeen.size(); i++) {
                        map.put(i, checked);
                    }
                    updateUI(map);
                }

                @Override
                public void onFailed(String error) {
                    showFailedDialog(error);
                }
            });
        }
    }

    /**
     * 更新界面
     */
    private void updateUI(Map<Integer,Boolean> map) {
        adapter=new ProductBinningDetailAdapter(pactivity,mDetailShowBeen) {
            @Override
            public CheckBox getPCheckBox() {
                return cbAll;
            }
        };
        adapter.setMap(map);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new DividerItemDecoration(pactivity,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 删除
     */
    private void toDelete(List<ProductBinningBean> deletelist, String type) {
        try {
            for (ProductBinningBean detail : deletelist) {
                detail.setFlag("i");//新增删除标志
            }
            deleteAndUpdate(deletelist);
        } catch (Exception e) {
            LogUtils.e(TAG, "toDelete---" + e);
        }

    }
    /**
     * 修改删除
     */
    private void deleteAndUpdate(final List<ProductBinningBean> list) {
        showLoadingDialog();
        commonLogic.insertAndDelete(ObjectAndMapUtils.getListMap(list), new CommonLogic.InsertAndDeleteListener() {
            @Override
            public void onSuccess(String show) {
                dismissLoadingDialog();
                mDetailShowBeen.removeAll(list);
                Map<Integer, Boolean> map = new HashMap<>();
                updateUI(map);
                cbAll.setChecked(false);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

}
