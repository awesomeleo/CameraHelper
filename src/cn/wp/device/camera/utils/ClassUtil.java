/**   
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p>
 */
package cn.wp.device.camera.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.util.Log;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**  
 * <p><h1>Copyright:</h1><strong><a href="http://weshow.1v.cn">
 * BeiJing WePu Information Technology Co.Ltd. 2014 (c)</a></strong></p> 
 *
 * <p>
 * <h1>Reviewer:</h1> 
 * <a href="mailto:jiangjunjie@1v.cn">jjj</a>
 * </p>
 * 
 * <p>
 * <h1>History Trace:</h1>
 * <li>2014-04-01    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title ClassUtil.java 
 * @Package cn.ws.device.camera.utils 
 * @Description ����صĹ�����
 * @author jjj
 * @email <a href="wepu.1v.cn">jiangjunjie@1v.cn</a>
 * @date 2014��4��12�� ����5:43:40 
 * @version V1.0   
 */
public class ClassUtil {  
	
	private static final String TAG = "ClassUtil";
    
	/** 
     * �ж�ĳ���ָ�������Ƿ�ע��
     * @param field ָ������������
     * @param anno ע������
     * */  
    public static boolean isFieldAnnotationed(Field field, String anno) {
    	//Log.i(TAG, "isFieldAnnotationed, params field:" + field + ", anno:" + anno);
    	if (ObjectCheck.validParams(field, anno)) {
    		Annotation[] annos = field.getAnnotations();
    		if (annos != null) {
    			int annoCnt = annos.length;
    			for(int i=0;i<annoCnt;i++) {
    				//Log.i(TAG, "isFieldAnnotationed, ano.name:" + annos[i].annotationType().getName());
    				if (anno.equals(annos[i].annotationType().getName())) {
    					//Log.i(TAG, "isFieldAnnotationed, true");
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /** 
     * ȡ��ĳ���ӿ�������ʵ������ӿڵ��� 
     * */  
    public static List<Class> getAllClassByInterface(Class c) {  
            List<Class>  returnClassList = null;  
              
            if(c.isInterface()) {  
                // ��ȡ��ǰ�İ���  
                String packageName = c.getPackage().getName();  
                // ��ȡ��ǰ�����Լ��Ӱ������Ե���  
                List<Class<?>> allClass = getClasses(packageName);  
                if(allClass != null) {  
                    returnClassList = new ArrayList<Class>();  
                    for(Class classes : allClass) {  
                        // �ж��Ƿ���ͬһ���ӿ�  
                        if(c.isAssignableFrom(classes)) {  
                            // ���������ȥ  
                            if(!c.equals(classes)) {  
                                returnClassList.add(classes);          
                            }  
                        }  
                    }  
                }  
            }  
              
            return returnClassList;  
        }  
  
      
    /* 
     * ȡ��ĳһ�����ڰ����������� �������� 
     */  
    public static String[] getPackageAllClassName(String classLocation, String packageName){  
        //��packageName�ֽ�  
        String[] packagePathSplit = packageName.split("[.]");  
        String realClassLocation = classLocation;  
        int packageLength = packagePathSplit.length;  
        for(int i = 0; i< packageLength; i++){  
            realClassLocation = realClassLocation + File.separator+packagePathSplit[i];  
        }  
        File packeageDir = new File(realClassLocation);  
        if(packeageDir.isDirectory()){  
            String[] allClassName = packeageDir.list();  
            return allClassName;  
        }  
        return null;  
    }  
      
    /** 
     * �Ӱ�package�л�ȡ���е�Class 
     * @param pack 
     * @return 
     */  
    public static List<Class<?>> getClasses(String packageName){  
          
        //��һ��class��ļ���  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        //�Ƿ�ѭ������  
        boolean recursive = true;  
        //��ȡ�������� �������滻  
        String packageDirName = packageName.replace('.', '/');  
        //����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);  
            //ѭ��������ȥ  
            while (dirs.hasMoreElements()){  
                //��ȡ��һ��Ԫ��  
                URL url = dirs.nextElement();  
                //�õ�Э�������  
                String protocol = url.getProtocol();  
                //��������ļ�����ʽ�����ڷ�������  
                if ("file".equals(protocol)) {  
                    //��ȡ��������·��  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    //���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������  
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);  
                } else if ("jar".equals(protocol)){  
                    //�����jar���ļ�   
                    //����һ��JarFile  
                    JarFile jar;  
                    try {  
                        //��ȡjar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();  
                        //�Ӵ�jar�� �õ�һ��ö����  
                        Enumeration<JarEntry> entries = jar.entries();  
                        //ͬ���Ľ���ѭ������  
                        while (entries.hasMoreElements()) {  
                            //��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            //�������/��ͷ��  
                            if (name.charAt(0) == '/') {  
                                //��ȡ������ַ���  
                                name = name.substring(1);  
                            }  
                            //���ǰ�벿�ֺͶ���İ�����ͬ  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                //�����"/"��β ��һ����  
                                if (idx != -1) {  
                                    //��ȡ���� ��"/"�滻��"."  
                                    packageName = name.substring(0, idx).replace('/', '.');  
                                }  
                                //������Ե�����ȥ ������һ����  
                                if ((idx != -1) || recursive){  
                                    //�����һ��.class�ļ� ���Ҳ���Ŀ¼  
                                    if (name.endsWith(".class") && !entry.isDirectory()) {  
                                        //ȥ�������".class" ��ȡ����������  
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);  
                                        try {  
                                            //��ӵ�classes  
                                            classes.add(Class.forName(packageName + '.' + className));  
                                        } catch (ClassNotFoundException e) {  
                                            e.printStackTrace();  
                                        }  
                                      }  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }   
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
         
        return classes;  
    }  
      
    /** 
     * ���ļ�����ʽ����ȡ���µ�����Class 
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */  
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){  
        //��ȡ�˰���Ŀ¼ ����һ��File  
        File dir = new File(packagePath);  
        //��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        //������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
        //�Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  
              public boolean accept(File file) {  
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
              }  
            });  
        //ѭ�������ļ�  
        for (File file : dirfiles) {  
            //�����Ŀ¼ �����ɨ��  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),  
                                      file.getAbsolutePath(),  
                                      recursive,  
                                      classes);  
            }  
            else {  
                //�����java���ļ� ȥ�������.class ֻ��������  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                    //��ӵ�������ȥ  
                    classes.add(Class.forName(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}  
