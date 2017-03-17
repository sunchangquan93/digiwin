package digiwin.smartdepot.core.base;

import java.util.HashMap;
import java.util.Map;

import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.smartdepot.core.base.BaseFirstModuldeActivity.ExitMode.EXITD;
import static digiwin.smartdepot.core.base.BaseFirstModuldeActivity.ExitMode.EXITISD;

/**
 * @des      是否需要调用清空未保存数据时调用
 * @author  xiemeng
 * @date    2017/3/9
 */
public abstract class BaseFirstModuldeActivity extends  BaseTitleActivity{

    @Override
    public void onBackPressed() {
        isShowExitDialogAndDelete(exitOrDel());
    }

    /**
     * 子类执行弹出选择框是否删除ExitMode.EXITISD
     * 或者退出ExitMode.EXITD
     */
    public abstract ExitMode exitOrDel();

    /**
     * 是否退出
     */
    private void isShowExitDialogAndDelete(ExitMode flag){
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.FLAG, flag.name);
        CommonLogic commonLogic = CommonLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
        showLoadingDialog();
        commonLogic.exit(map, new CommonLogic.ExitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                if (AddressContants.ISDELETE.equals(msg)) {
                    delete();
                } else {
                    activity.finish();
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 删除且退出或者直接退出
     */
    private void delete() {
        showExitActivityDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                isShowExitDialogAndDelete(EXITD);
            }
            @Override
            public void onCallback2() {
                activity.finish();
            }
        });
    }

    /**
     * 退出调用接口
     */
    public enum ExitMode {
        /**
         * 直接删除
         */
        EXITD("1"),
        /**
         * 判断是否删除
         */
        EXITISD("0");
        // 成员变量
        private String name;
        // 构造方法
        private ExitMode(String name) {
            this.name = name;
        }

    }
}
