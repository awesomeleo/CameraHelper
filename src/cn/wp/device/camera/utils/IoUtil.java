/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 * @Title IoUtils.java 
 * @Package cn.ws.device.camera.utils 
 * @Description input/output
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��3��31�� ����7:31:14 
 * @version V1.0   
 */
public class IoUtil {
	
	private static final String TAG = "IoUtil";
	
	/**
	 * save data as a file
	 * @param data data byte
	 * @param saveFileName  the file to be created
	 * @return the file saved data
	 * @author jjj
	 * @see
	 */
	public static final File saveToFile(byte[] data, String saveFileName) throws IOException {
		Log.i(TAG, "WsUtils saveToFile:"+saveFileName);
		File saveFile = new File(saveFileName);
		if(saveFile.exists() == false)
			saveFile.createNewFile();
		FileOutputStream fw= new FileOutputStream(saveFile);
		fw.write(data);
		fw.close();
		
		return saveFile;
	}
	
	/**
	 * InputStream to String
	 * @param in
	 * @param encoding  encode mode
	 * @return
	 * @author jjj
	 * @see
	 */
	public static String inputStreamToStringByByteArrayOutputStream(InputStream in, String encoding) {
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// ��ȡ����
		byte[] buffer = new byte[2048];
		int length = 0,count=0;
		try {
			//���������ж�ȡһ���������ֽڣ�������洢�ڻ���������buffer ��
			while ((length = in.read(buffer)) != -1) {
				// ��ָ�� byte �����д�ƫ���� off ��ʼ�� len ���ֽ�д��������
				bos.write(buffer, 0, length);
				count+=length;
			}
			in.close();// ��ȡ��ϣ��ر�������
			// ��������������ַ�������
			Log.i(TAG, "datalenth = : " + count);
			result = new String(bos.toByteArray(), encoding);
		} catch (IOException e) {
			result = "";
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * InputStream to String
	 * @param in
	 * @param encoding  encode mode
	 * @return
	 * @author jjj
	 * @see
	 */
	public static String inputStreamToStringByBufferedReader(InputStream in, String encoding)
		throws Exception {
		StringBuilder sBuilder = new StringBuilder();
		InputStreamReader inputStreamReader = new InputStreamReader(in, encoding);
		//����һ��ʹ��ָ����С���뻺�����Ļ����ַ�������
		BufferedReader reader = new BufferedReader(inputStreamReader, 8192);
		String line = null;
		while ((line = reader.readLine()) != null) {
			sBuilder.append(line).append("\n");
		}
		reader.close();
		Log.i(TAG, "sBuilder : " + sBuilder.toString());
		return sBuilder.toString();
	}
	
}
