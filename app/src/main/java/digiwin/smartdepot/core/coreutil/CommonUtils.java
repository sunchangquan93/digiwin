package digiwin.smartdepot.core.coreutil;

import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.core.base.BaseApplication;
import digiwin.smartdepot.module.bean.common.SaveBean;

/**
 * @author xiemeng
 * @des  模块公共方法
 * @date 2017/6/26 09:41
 */

public class CommonUtils {

    /**
     * @param saveBean 保存对象
     * @return true时可以自动保存
     */
    public static boolean isAutoSave(SaveBean saveBean){
        boolean isAutoSave=false;
        BaseApplication instance = BaseApplication.getInstance();
        if (null!=saveBean&&null!=saveBean.getItem_barcode_type()){
            if ("3".equals(saveBean.getItem_barcode_type())
                    ||"4".equals(saveBean.getItem_barcode_type())){
                isAutoSave=true;
            }
        }
        return  isAutoSave;
    }

    /**
     * 检查库位是否与所选仓库一致
     * @return
     */
    public static boolean checkStore(String moulde,String stock){
        boolean isCheck=true;
        return  isCheck;
    }
}
