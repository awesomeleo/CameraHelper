/*
 ***************************************************************************************************
 *  FileName    : PingHelper.java
 *  Author      : mengsk
 *  Date        : 2014-06-24
 *  Description : use for application to check network status with hub, server and device
 *--------------------------------------------------------------------------------------------------
 *  History     :
 *  <time></time>        <version></version>   <author></author>    <desc></desc>
 *  2014-06-24                  V1.0.0              mengsk          first release
 *  2014-06-27                  V1.0.1              mengsk          (+) send handle message delay 200ms, add stopCheck interface, change statusCheck name to startCheck
 *  2014-07-01                  V1.1.0              mengsk          (x) realize interface
 *  2014-07-02                  V1.1.1              mengsk          (+) add interface for processing batch 
 ***************************************************************************************************
 */

package cn.wp.device.camera.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class PingHelper {
    private static final String TAG = "PingHelper";
    private ExecutorService executor = null;
    private static PingHelper instance = null;
    private PendingListener listener = null;
    
    public static final class PendEventType {
        public static final int INFO = 0;
        public static final int ERROR = 1;
    }

    public interface PendingListener {
        void PendValue(int eventType, PendingValue value);
    }

    public synchronized void setListener(PendingListener pendListener) {
        if (listener == null) {
            listener = pendListener;
        }
    }
    
    public synchronized void clrListener() {
        listener = null;
    }

    public synchronized static PingHelper getInstance() {
        if (instance == null) {
            instance = new PingHelper();
        }
        return instance;
    }

    private PingHelper() {
        executor = Executors.newCachedThreadPool();
    }
    
    public boolean addTask(String host) {
        boolean value = false;
        if (host == null || !isIpv4(host)) {
            Log.w(TAG, "invalid host address[" + host + "]");
            return value;
        }
        executor.execute(new Target(host,false));
        return value;
    }
    private boolean isIpv4(String host) {
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(host);
        return matcher.matches();
    }

    public class PendingValue {
        public String host;
        public boolean isReachable;
        public double lastExecuteTime;
        public double lastAverageTime;
        public Exception exception;
    }

    private class Target extends Thread {
        private static final int DEFAULT_PORT = 0;
        private static final int MAX_TRY_TIME = 4;
        private InetSocketAddress address;
        private Exception failure = null;
        private long lastExecutetime = 0;
        private double lastAverageTime = 0;
        private boolean isReachable = true;

        public Target(String host, boolean isImExec) {
            try {
                address = new InetSocketAddress(InetAddress.getByName(host),
                        DEFAULT_PORT);
            } catch (IOException e) {
                failure = e;
            }
        }

        private void pending() {
            try {
                lastExecutetime = System.currentTimeMillis();
                Process pro = Runtime.getRuntime().exec("ping " + this.address.getHostName());
                InputStreamReader reader = new InputStreamReader(pro.getInputStream());
                BufferedReader buf = new BufferedReader(reader);
                lastAverageTime = 0;
                int tryTime = 0;
                while (tryTime <= MAX_TRY_TIME) {
                    String value = buf.readLine();
                    if (value != null) {
                    	if (value.contains("Destination Host Unreachable")) {
                            Log.w(TAG, Thread.currentThread().getId() + ": this address[" + this.address.getHostName() + "] is not reachable");
                            failure = null;
                            lastAverageTime = System.currentTimeMillis() - lastExecutetime;
                            isReachable = false;
                            if (listener != null) {
                                synchronized (listener) {
                                    notifyListener(null);
                                }
                            }
                            return;
                        }
                        if (value.contains("from")) {
                            value = value.substring(value.indexOf("time=") + 5, value.length() - 3);
                            lastAverageTime += Double.valueOf(value);
                        }
                    }
                    tryTime++;
                }
                lastAverageTime = lastAverageTime / MAX_TRY_TIME;
                buf.close();
                reader.close();
                pro.destroy();
                synchronized (listener) {
                    if (listener != null) {
                        notifyListener(null);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, Thread.currentThread().getId() + ": this address[" + this.address.getHostName() + "] has exception");
                lastAverageTime = System.currentTimeMillis() - lastExecutetime;
                failure = e;
                isReachable = false;
                if (listener != null) {
                    synchronized (listener) {
                        notifyListener(null);
                    }
                }
            }
        }

        private void notifyListener(Object obj) {
            PendingValue value = new PendingValue();
            value.isReachable = isReachable;
            value.host = address.getHostName();
            value.lastAverageTime = lastAverageTime;
            value.lastExecuteTime = lastExecutetime;
            value.exception = failure;
            synchronized(listener) {
                listener.PendValue((isReachable == true ? PendEventType.INFO : PendEventType.ERROR), value);
            }
        }

        @Override
        public void run() {
            pending();
        }
    }
}
