package cn.wp.device.camera.utils;

public class ObjectCheck {
	
	/**
	 * �ж�String�Ƿ�������, ������ʱ����true
	 * */
	public static boolean validString(String s) {
		if (s != null && !s.trim().equals(""))
			return true;
		return false;
	}
	
	/**
	 * �ж϶����Ƿ�Ϊ��, ��Ϊ��ʱ����true
	 * */
	public static boolean validObject(Object o) {
		if (o != null)
			return true;
		return false;
	}
	
	/**
	 * �жϲ����Ƿ�Ϸ�, ȫ��Ϊ����String���Ͳ�Ϊ""ʱΪ�Ϸ�
	 * */
	public static boolean validParams(Object... params) {
		for(Object o:params) {
			if (o == null) {
				return false;
			}
			if (o instanceof String && "".equals((String)o)) {
				return false;
			}
		}
		return true;
	}
}
