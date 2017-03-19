package digiwin.smartdepot.core.coreutil;

import android.content.Context;

import digiwin.library.constant.SharePreKey;
import digiwin.library.constant.SystemConstant;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepot.R;

/**
 * Created by xiemeng on 2017/3/17.
 */

public class GetVoicer {

    public static  String getVoice(Context context){
        String voicer = "";
        String chooseVoiceType = (String) SharedPreferencesUtils.get(context, SharePreKey.VOICER_SELECTED, context.getString(R.string.voicer_not));
        if (chooseVoiceType.equals(context.getString(R.string.voicer_male))) {
            voicer = SystemConstant.VIXF;
        } else if (chooseVoiceType.equals(context.getString(R.string.voicer_female))) {
            voicer = SystemConstant.VIXY;
        } else {
            voicer = SystemConstant.NOMETION;
        }
        return  voicer;
    }
}
