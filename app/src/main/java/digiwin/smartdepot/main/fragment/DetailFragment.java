package digiwin.smartdepot.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.main.CustomRecyclerAdapter;
import digiwin.smartdepot.main.bean.ModuleBean;


/**
 * Created by ChangquanSun
 * 2017/1/5
 */

public class DetailFragment extends BaseFragment {

    private static DetailFragment instance;

    public static DetailFragment getInstance(List<ModuleBean> beans){
        instance=new DetailFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("beans",(Serializable)beans);
        instance.setArguments(bundle);
        return instance;
    }

    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;

    private List<ModuleBean> beanLists;
    private CustomRecyclerAdapter adapter;


    @Override
    protected int bindLayoutId() {
        beanLists= (List<ModuleBean>) getArguments().getSerializable("beans");
        return R.layout.base_fragment;
    }

    @Override
    protected void doBusiness() {
        adapter=new CustomRecyclerAdapter(beanLists,context);
        GridLayoutManager manager=new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        initRecyclerViewListener();
    }

    private void initRecyclerViewListener() {
        adapter.onItemClickListener(new CustomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    ActivityManagerUtils.startActivity(activity,beanLists.get(position).getIntent());
                }catch (Exception e){
                    showFailedDialog(R.string.toerror);
                }
            }
        });
    }


}
