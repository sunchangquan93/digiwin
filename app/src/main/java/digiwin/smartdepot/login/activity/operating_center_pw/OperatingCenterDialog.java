package digiwin.smartdepot.login.activity.operating_center_pw;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import digiwin.library.utils.DialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepot.R;

/**
 * 运营中心对话框
 * @author 毛衡
 */

public class OperatingCenterDialog {

    private static String TAG = "StorageDialog";
    /**
     * 营运中心变化回调接口
     */
    private static OperatingCenterCallBack callBack;

    public static void setCallBack(OperatingCenterCallBack callBack) {
        OperatingCenterDialog.callBack = callBack;
    }
    public OperatingCenterDialog() {

    }
    /**
     * 弹出营运中心Dialog
     * @Author 毛衡
     */
    public static void showOperatingCenterDialog(final Activity context,final String plant, final List<String> list){
        View view = LayoutInflater.from(context).inflate(R.layout.login_operatingcenter_layout, null);
        final DialogUtils mDialog = new DialogUtils(context,view);
        final RecyclerView rv_operatingCenter = (RecyclerView) view.findViewById(R.id.rv_operatingCenter);
        if (!StringUtils.isBlank(plant)) {
            if (list.contains(plant)) {
                list.remove(plant);
                list.add(0, plant);
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_operatingCenter.setLayoutManager(manager);
        OperatingCenterAdapter adapter = new OperatingCenterAdapter(list,context);
        rv_operatingCenter.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context)*0.75);
        int height = (int) (ViewUtils.getScreenHeight(context)*0.4);
        mDialog.showDialog(width,height);
        //设置监听
        adapter.setClick(new OperatingCenterAdapter.OperatingCenterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                //营运中心变化,刷新UI
                    if(callBack != null){
                    callBack.operatingCenterCallBack( list.get(position));
                }else {
                    LogUtils.i(TAG,"接口回调对象为空");
                }
            }
        });
    }
    /**
     * 营运中心变化回调接口
     */
    public interface OperatingCenterCallBack{
        void operatingCenterCallBack(String choosePlant);
    }
}
