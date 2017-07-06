package digiwin.smartdepot.core.net;

import android.content.Context;

import digiwin.library.net.IDownLoadCallBack;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.ToastUtils;
import digiwin.smartdepot.R;

/**
 * Created by qGod
 * 2017/1/5
 */

public  abstract class IDownLoadCallBackImp implements IDownLoadCallBack{
    @Override
    public void onFailure(Context context,Throwable throwable) {
        throwable.printStackTrace();
//        ToastUtils.showToastByString(context,throwable.getMessage());
        AlertDialogUtils.dismissDialog();
//        AlertDialogUtils.showFailedDialog(context,R.string.check_network_error);
        ToastUtils.showToastByString(context,context.getString(R.string.network_response_error)+throwable.getMessage());

    }
}
