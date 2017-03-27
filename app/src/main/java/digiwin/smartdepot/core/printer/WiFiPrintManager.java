package digiwin.smartdepot.core.printer;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;


/**
 * @des      无线打印机管理
 * @author  xiemeng
 * @date    2017/2/21
 */
public class WiFiPrintManager {
    private static final String TAG = "WiFiPrintManager";
    public static WiFiPrintManager manager;
    /**
     * 超时时间
     */
    public int Net_ReceiveTimeout = 150000;
    /**
     * 是否开启
     */
    public boolean isOpen = false;
    public Socket socket;

    public PrintSend printSend;

    private static Handler handler;


    public static WiFiPrintManager getManager() {
        if (null == manager) {
            manager = new WiFiPrintManager();
            handler=new Handler(Looper.getMainLooper());
        }
        return manager;
    }

    public  interface OpenWiFiPrintListener{
        public void  isOpen(boolean isOpen);
    }

    /**
     * @param IP           打印机IP地址
     * @param OPEN_NETPORT 端口
     * @return 是否开启
     */
    public void openWiFi(final String IP, final int OPEN_NETPORT, final OpenWiFiPrintListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketAddress ipe = new InetSocketAddress("192.168.31.244", 9100);
                    socket = new Socket();
                    socket.connect(ipe);
                    socket.setSoTimeout(Net_ReceiveTimeout);
                    printSend = new PrintSend(socket);
                    isOpen = true;
                } catch (IOException e) {
                    isOpen = false;
                    LogUtils.e(TAG, "IOException--连接不成功");
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.isOpen(isOpen);
                    }
                });
            }
        },null);
    }

    /**
     * 关闭无线通信
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印测试内容
     */
    public void printText(String msg) {
        try {
            String encoding2="A"+
                    "H400V300P2L0101K9B中文ABCD1234"+
                    "%0H0040V0100BG02120>G" + "123"+ "\n"+
                    "Q1"+
                    "Z";
            String encoding3="1231";
            printSend.sendMessage(encoding3);
            close();
        } catch (Exception e) {

        }
    }


}
