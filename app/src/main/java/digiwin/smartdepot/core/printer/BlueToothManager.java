package digiwin.smartdepot.core.printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.UUID;

import digiwin.library.utils.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepot.R;

/**
 * @des      蓝牙设置
 * @author  xiemeng
 * @date    2017/1/17
 */

public class BlueToothManager {
    private static final String TAG = "BlueToothManager";
    public static BlueToothManager manager;
    public static Context mContext;
    /**
     * 蓝牙系统适配
     */
    private BluetoothAdapter mBTAdapter;
    /**
     * 蓝牙连接服务
     */
    private static BluetoothDevice mBTDevice;
    public static BluetoothSocket mBTSocket;

    public PrintSend mPrintSend;
    private static final String NAME = "BTPrinter";

    private UUID dvcUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String mDeviceName="";

    private static Handler handler;

    private BlueToothManager(Context context) {
        mContext = context;
    }

    public static BlueToothManager getManager(Context context) {
        if (null == manager) {
            manager = new BlueToothManager(context);
            handler=new Handler(Looper.getMainLooper());
        }
        return manager;
    }

    /**
     * 连接设备
     */
    public interface ConnectListener{
        public void onSuccess();
        public void onFailed(int msg);
    }

    /**
     * 连接设备
     * @param address    蓝牙地址
     * @param deviceName 蓝牙名称
     */
    public void connect(String address, final String deviceName, final ConnectListener listener) {
      try
        {
            mBTAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null == mBTAdapter) {
                listener.onFailed(R.string.connect_device_failed);
                return;
            }
            mBTDevice = mBTAdapter.getRemoteDevice(address);
            //android 2.3以下使用的
//            mBTSocket = mBTDevice.createRfcommSocketToServiceRecord(dvcUUID);
            //安卓新版本使用
            mBTSocket = mBTDevice.createInsecureRfcommSocketToServiceRecord(dvcUUID);

            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBTSocket.connect();
                         mPrintSend = new PrintSend(mBTSocket);
                        if (null != mPrintSend) {
                            mDeviceName=deviceName;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccess();
                                }
                            });
                            return;
                        }
                    }
                    catch (IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFailed(R.string.connect_device_failed);
                            }
                        });
                        LogUtils.e(TAG,"connect异常---IOException---");
                    }
                }
            }, null);
        }
       catch (Exception e)
       {
            listener.onFailed(R.string.connect_device_failed);
            LogUtils.e(TAG,"connect异常");
        }
    }

    /**
     * 确认蓝牙是否开启并连接成功
     */
    public boolean isOpen()
    {
        boolean flag = true;
        //没有开启
        if (null == mBTDevice)
        {
            flag = false;
            return flag;
        }
        //没有连接
        if (null == mPrintSend)
        {
            flag = false;
            return flag;
        }
        return flag;
    }

    /**
     * 获取连接设备名称
     * @return
     */
    public String getDeviceName(){
        return mDeviceName;
    }


    /**
     * 关闭蓝牙
     */
    public void close() {
        try {
            mBTAdapter.disable();
            mBTSocket.close();
        } catch (Exception e) {
            LogUtils.i(TAG, " close()异常");
        }

    }


    /**
     * 打印出通单
     * @see [类、类#方法、类#成员]
     */
    public  boolean printOutNumber(String out_number,String num2)
    {
        boolean flag = false;
        if (null == mBTDevice)
        {
            AlertDialogUtils.showFailedDialog(mContext,"请连接蓝牙设备");
            flag = false;
            return flag;
        }

        if (null == mPrintSend)
        {
            AlertDialogUtils.showFailedDialog(mContext,"蓝牙设备连接式失败");
            flag = false;
            return flag;
        }
        String encoding2="A"+
                "H040V300P2L0101K9B中文ABCD1234"+
                "%0H0040V0100BG02120>G" + "123"+ "\n"+
                "Q1"+
                "Z";
        mPrintSend.sendBtMessage(encoding2);
        return flag;
    }

}
