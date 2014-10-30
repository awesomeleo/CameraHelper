package cn.wp.device.camera;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.wp.tool.data.model.IPCamera;
import cn.wp.tool.data.model.ZAGIPCamera;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class ZAGCameraScaner extends IPCameraScaner {

	private static final String TAG = "ZAGCameraScaner";
	
	private static final int RECEIVE_DEVICE_MESSAGE_TIMEOUT = 5000;
	
	private static final byte[] discoverCmd1 = new byte[]{
    		0x4d, 0x4f, 0x5f, 0x49, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    		0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 
    		0x00, 0x00, 0x01
    };
    
    private static final byte[] discoverCmd2 = new byte[]{
    		(byte) 0xb4, (byte) 0x9a, 0x70, 0x4d,0x00
    };
	
    private DatagramSocket mDatagramSocket;
    
    public ZAGCameraScaner() {
    	try {
			mDatagramSocket = new DatagramSocket();
	        mDatagramSocket.setSoTimeout(RECEIVE_DEVICE_MESSAGE_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
    }
    
    /* (non-Javadoc)
	 * @see cn.ws.device.ipcamera.impl.IPCameraScaner#scanOnlineCameraList()
	 */
    @Override
	public List<IPCamera> scanOnlineCameraList(Context context,Handler mWorkHandler) {
    	
    	Log.i(TAG, "Start to Scan Online Camera.");
    	mIpCameras.clear();

		try {
			 Log.i(TAG, "Scan ZAGIPCamera, prepare to send");
			DatagramPacket dp = new DatagramPacket(discoverCmd1, discoverCmd1.length, 
					InetAddress.getByName("255.255.255.255"), 10000);
			mDatagramSocket.send(dp);
	        
	        dp.setData(discoverCmd2);

	        mDatagramSocket.send(dp);
		} catch (IOException e1) {
			 Log.i(TAG, "Scan ZAGIPCamera, receiving DatagramPacket");
			e1.printStackTrace();
		}

		try {
			 Log.i(TAG, "Scan ZAGIPCamera, prepare to receive");
			long begin = new Date().getTime();
			int cnt = 0;
			do {
				byte[] buf = new byte[1024];
		        DatagramPacket recvDatagramPacket = new DatagramPacket(buf, buf.length);
		        Log.i(TAG, "Scan ZAGIPCamera, receiving DatagramPacket");
		        mDatagramSocket.receive(recvDatagramPacket);
		        ZAGIPCamera zag = new ZAGIPCamera(recvDatagramPacket.getData(), 
						recvDatagramPacket.getAddress().getHostAddress());
		        if (!mIpCameras.contains(zag) && cnt < getMaxCameraCnt()) {
		        	mIpCameras.add(zag);
		        	cnt++;
		        }
			} while (new Date().getTime() - begin < RECEIVE_DEVICE_MESSAGE_TIMEOUT);
		} catch (SocketTimeoutException e) {
			Log.i(TAG, "Scan ZAGIPCamera, SocketTimeoutException");
			return mIpCameras;
		} catch (IOException e) {
			Log.i(TAG, "Scan ZAGIPCamera, IOException");
			e.printStackTrace();
		} finally{
			Log.i(TAG, "Scan ZAGIPCamera Finished. Found " + mIpCameras.size() + " ZAGCamera");
		}
    	
		return mIpCameras;
    }

	@Override
	public int getCgiVersion(String url) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
