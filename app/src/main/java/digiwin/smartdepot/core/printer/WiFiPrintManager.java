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
 * @author xiemeng
 * @des 无线打印机管理
 * @date 2017/2/21
 */
public class WiFiPrintManager {
    private static final String TAG = "WiFiPrintManager";
    public static WiFiPrintManager manager;
    /**
     * 超时时间
     */
    private int Net_ReceiveTimeout = 1000;
    /**
     * 是否开启
     */
    public boolean isOpen = false;
    private Socket socket;

    private PrintSend printSend;

    private static Handler handler;


    public static WiFiPrintManager getManager() {
        // if (null == manager) {
        manager = new WiFiPrintManager();
        handler = new Handler(Looper.getMainLooper());
        //  }
        return manager;
    }

    public interface OpenWiFiPrintListener {
        public void isOpen(boolean isOpen);
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
                LogUtils.e(TAG, "openWiFi--");
                try {
                    SocketAddress ipe = new InetSocketAddress("192.168.9.161", 9100);
                    socket = new Socket();
                    socket.connect(ipe);
                    socket.setSoTimeout(Net_ReceiveTimeout);
                    printSend = new PrintSend(socket);
                    isOpen = socket.isConnected();
                    LogUtils.e(TAG, "isOpen成功" + isOpen);
                } catch (IOException e) {
                    isOpen = false;
                    LogUtils.e(TAG, "IOException--连接不成功");
                } catch (Exception e) {
                    isOpen = false;
                    LogUtils.e(TAG, "Exception--连接不成功" + e);
                }
                LogUtils.e(TAG, "isOpen--isOpen：" + isOpen);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.isOpen(isOpen);
                    }
                });
            }
        }, null);
    }

    /**
     * @param IP 打印机IP地址(含端口)
     * @return 是否开启
     */
    public void openWiFi(final String IP, final OpenWiFiPrintListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG, "openWiFi--");
                try {
                    int index = IP.indexOf(":");
                    //ip地址10.10.0.203:9100
                    String ip = IP.substring(0,index);
                    int duankou=Integer.parseInt(IP.substring(index+1, IP.length()));
                    LogUtils.e(TAG,"ip---"+ip+"duankou---"+duankou);
                    SocketAddress ipe = new InetSocketAddress(ip,duankou);
                    socket = new Socket();
                    socket.connect(ipe);
                    socket.setSoTimeout(Net_ReceiveTimeout);
                    printSend = new PrintSend(socket);
                    isOpen = socket.isConnected();
                } catch (IOException e) {
                    isOpen = false;
                    LogUtils.e(TAG, "IOException--连接不成功");
                }
                catch (Exception e) {
                    isOpen = false;
                    LogUtils.e(TAG, "Exception--连接不成功"+e);
                }
                LogUtils.e(TAG, "isOpen--isOpen："+isOpen);
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
//            + "%0H0040V0040L0202P02C9B" + "123" + "\n"
//                    + "%0H0040V0100BG02120>G" + "123"+ "\n"
//                    + "%0H0040V0250L0202P02C9B" + "123" + "\n"
//                    + "%0H0100V0280L0303P02C9B" + "  " + "\n"
            String encoding3 = "1231";
            String encoding2 = "A" +
                    "%0H0040V040BG02120>G" + "123" + "\n" +
                    "%0H0040V02202D30,M,05,1,0DN" + encoding3.length() + "," + encoding3 +
                    "H0040V350P2L0101K9B中文ABCD1234" +
                    "Z";
            printSend.sendMessage(encoding2);
            close();
        } catch (Exception e) {

        }
    }


}
