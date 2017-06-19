package digiwin.smartdepot.module.activity.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.net.IUpdateCallBack;
import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.utils.UriToPathUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemLongClickListener;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.base.BaseTitleHActivity;
import digiwin.smartdepot.core.coreutil.PermissionUtils;
import digiwin.smartdepot.core.customview.RoundedProgressBar;
import digiwin.smartdepot.module.adapter.common.ChoosePicAdapter;
import digiwin.smartdepot.module.bean.common.ChoosePicBean;
import digiwin.smartdepot.module.bean.purchase.PurchaseCheckBean;
import digiwin.smartdepot.module.logic.common.CommonJsonLogic;

import static digiwin.smartdepot.R.id.tv_download;

/**
 * @author xiemeng
 * @des 选择图片界面
 * 每个模组传得data需要在里面单独获取
 * @date 2017/4/19 14:46
 */

public class ChoosePicActivity extends BaseTitleHActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.et_picdes)
    EditText etPicdes;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.commit)
    TextView commit;

    public static final String DATAKEY = "datakey";

    @OnClick(R.id.commit)
    void update() {
        sureCommit();
    }

    /**
     * 图片
     */
    List<ChoosePicBean> list;

    BaseRecyclerAdapter adapter;
    /**
     * 默认的
     */

    /**
     * 模组名
     */
    public static final String MODULECODE = "code";

    /**
     * 拍照
     */
    private final int CAMERA_WITH_DATA = 1;
    /**
     * 本地
     */
    private final int IMAGE_OPEN = 2;
    /**
     * 临时图片名称
     */
    public static String TMP_PATH;

    private String INTENT_TYPE = "image/*";

    private String fileName = "/mnt/sdcard/1digiwin/";

    private String pathImage;

    File file;

    CommonJsonLogic commonJsonLogic;
    /**
     * 照相机
     */
    ChoosePicBean defaultPic;
    /**
     * 本地图片
     */
    ChoosePicBean defaultPic2;
    /**
     * 上一页面传的bean
     */
    Object checkBean;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_choosepic;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.choose_pic);
        ivScan.setVisibility(View.GONE);
        iv_title_setting.setImageResource(R.drawable.check_num_on);
    }

    @Override
    protected void doBusiness() {
        PermissionUtils.verifyStoragePermissions(this);
        Bundle bundle = getIntent().getExtras();
        //TODO:每个模组可能参数不一样
        module = bundle.getString(MODULECODE, ModuleCode.OTHER);
        checkBean = bundle.getSerializable(DATAKEY);
        commit.setText(R.string.update_pic);
        pathImage = "";
        commonJsonLogic = CommonJsonLogic.getInstance(context, module, mTimestamp.toString());
        list = new ArrayList<>();
        defaultPic = new ChoosePicBean();
        defaultPic.setDrawId(R.drawable.photograph);
        list.add(defaultPic); //打开照相机

        defaultPic2 = new ChoosePicBean();
        defaultPic2.setDrawId(R.drawable.add_photo);
        list.add(defaultPic2); //打开本地图片

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        ryList.setLayoutManager(layoutManager);
        adapter = new ChoosePicAdapter(activity, list);
        ryList.setAdapter(adapter);
        itemClick();
        deletePic();
    }

    /**
     * 删除图片
     */
    private void deletePic() {
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, final int position) {
                if (position != 0 && position != list.size() - 1) {
                    AlertDialogUtils.showSureOrQuitDialogAndCall(activity, R.string.remove_pic, new OnDialogTwoListener() {
                        @Override
                        public void onCallback1() {
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCallback2() {
                        }
                    });
                }
            }
        });
    }

    /**
     * 点击事件
     */
    private void itemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                try {
                    //打开照相机
                    if (position == 0) {
                        TMP_PATH = "digiwin" + TelephonyUtils.getTime() + ".jpg";
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file = new File(fileName);
                        if (!file.exists()) {
                            file.mkdirs();
                        }// 创建文件夹
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileName, TMP_PATH)));
                        startActivityForResult(intent, CAMERA_WITH_DATA);
                    }
                    //打开本地图片ACTION_GET_CONTENT
                    else if (position == list.size() - 1) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType(INTENT_TYPE);
                        startActivityForResult(intent, IMAGE_OPEN);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "itemClick" + e);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //打开照相机
        pathImage = "";
        if (resultCode == RESULT_OK && requestCode == CAMERA_WITH_DATA) {
            pathImage = fileName + TMP_PATH;
        }
        //打开图片
        else if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            pathImage = UriToPathUtils.getImageAbsolutePath(activity, uri);
            LogUtils.i(TAG, "pathImage" + pathImage);
        }
        if (!StringUtils.isBlank(pathImage)) {
            ChoosePicBean tempPicBean = new ChoosePicBean();
            tempPicBean.setPicPath(pathImage);
            list.remove(list.size() - 1);
            list.add(tempPicBean);

            defaultPic2.setDrawId(R.drawable.add_photo);
            list.add(defaultPic2); //打开本地图片
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {

        return module;
    }

    private CustomDialog updateDialog;

    private void sureCommit() {
        if (list.size() > 2) {
            final ArrayList<ChoosePicBean> commitList = new ArrayList<>();
            for (int i = 1; i < list.size() - 1; i++) {
                commitList.add(list.get(i));
            }
            final CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                    .view(R.layout.dialog_updatepic)
                    .style(R.style.CustomDialog)
                    .backCancelTouchout(true);
            final RoundedProgressBar progressBar = (RoundedProgressBar) builder.getView(R.id.myProgressBar);
            final TextView text = (TextView) builder.getView(R.id.tv_update);
            progressBar.setMaxCount(100);
            builder.addViewOnclick(R.id.tv_update, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text.setEnabled(false);
                    text.setTextColor(context.getResources().getColor(R.color.Assist_color));
                    commonJsonLogic.update(commitList, checkBean, new CommonJsonLogic.UpdateImgListener() {
                        @Override
                        public void onProgressCallBack(long progress, long total) {
                            builder.cancelTouchout(false);
                            int pro = (int) (progress * 100 / total);
                            if (pro == 100) {
                                if (null != updateDialog) updateDialog.dismiss();
                            }
                            if (pro <= 3 && pro >= 1) {
                                text.setText(3 + "%");
                                progressBar.setCurrentCount(3);
                            } else {
                                text.setText(pro + "%");
                                progressBar.setCurrentCount(pro);
                            }
                        }

                        @Override
                        public void onSuccess(String msg) {
                            dismissLoadingDialog();
                            showCommitSuccessDialog(msg);
                        }

                        @Override
                        public void onFailed(final String errmsg) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissLoadingDialog();
                                    showFailedDialog(errmsg);
                                }
                            });
                        }
                    });
                }
            });
            builder.widthpx((int) (ViewUtils.getScreenWidth(context) * 0.8))
                    .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT);
            updateDialog = builder.build();
            updateDialog.show();
        }
    }


}
