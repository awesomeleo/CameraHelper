package cn.wp.device.camera.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
 
/**
 * ???�?�?�?�?ksoapClient???�????
 * @see IServiceUtil
 * @author �???????
 * @version 1.1
 */

public class SoapClient {
	
	private static final String TAG = SoapClient.class.getSimpleName();
	
	public SoapClient(String soapAction, String methodName, String namespace, String uri, Boolean dotNet){
		setSoapAction(soapAction);
		setMethodName(methodName);
		setNamespace(namespace);
		setUri(uri);
		setDotNet(dotNet);
	}
	
	private String soapAction; 		//webservice ??��?????�?
	private String methodName; 		//�???��??webservice??��??
	private String namespace; 		//??��??空�??
	private String uri;				//wsdl, asmx..�????	
	private Boolean dotNet;			//?????��???????????dotnet�????
	private Map<String,Object> parameters;	//�??????��?��???????????
	private Map<String,Class>  entityClass; //�???????�?�?�?map
	public boolean debug=false;
	
	
	
	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public Map<String, Class> getEntityClass(){
		return entityClass;
	}
	
	public void setEntityClass(Map<String, Class> entityClass) {
		this.entityClass = entityClass;
	}
	//used to set the params of the method will be called
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = new LinkedHashMap<String, Object>(parameters);
	}

	public Boolean getDotNet() {
		return dotNet;
	}

	public void setDotNet(Boolean dotNet) {
		this.dotNet = dotNet;
	}

	public void addEntityClass(String entityClassName, Class entityClassValue){
		if(getEntityClass() == null){
			this.entityClass = new HashMap<String, Class>();
		}		
		getEntityClass().put(entityClassName, entityClassValue);
	}
	
	public void addParameter(String parameterName, Object parameterValue){
		if(getParameters() == null){
			this.parameters = new LinkedHashMap<String, Object>();
		}		
		getParameters().put(parameterName, parameterValue);
	}
	

	
	
	public Object executeCallResponse() throws Exception{
		
		//�?�?ksoap???connect??�没???设置�???��?????以�?????�?�?�???��??�??????????以�?????
        URL url = new URL(getUri());
        HttpURLConnection httpConnection;
        httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.setConnectTimeout(5000);
        /*httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("connection", "close");
        httpConnection.addRequestProperty("Content-Type", "application/soap+xml");
        httpConnection.addRequestProperty("action", "http://www.onvif.org/ver10/media/wsdl/GetStreamUri");
        httpConnection.connect();	
        int lenghtOfFile = httpConnection.getContentLength();
    	InputStream input = new BufferedInputStream(httpConnection.getInputStream());
    	byte[] b = new byte[lenghtOfFile];
    	input.read(b);
    	Log.i(TAG, "content:" + new String(b));
    	input.close();*/
//        httpConnection.setRequestProperty("RANGE", "bytes=" + 0 + "-");                           
//        httpConnection.setReadTimeout(20000); 
        httpConnection.connect();		
        httpConnection.disconnect();

        Object result = null;
		
		SoapObject request = new SoapObject(getNamespace(), getMethodName()); //create the soap method	            
		
		if(getParameters() != null){
			for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
				String parameter = property.getKey();  
		        Object value = property.getValue();	            	
		        request.addProperty(parameter, value);	            	
			}	                                
			getParameters().clear();
		}
			            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serialize the envelope 
	    envelope.xsd = "http://www.w3.org/2001/XMLSchema";
	    envelope.xsi = "http://www.w3.org/2001/XMLSchema-instance";
		if(getEntityClass() != null){
			for (Entry<String, Class> property : getEntityClass().entrySet()) {  
				String entityClassName = property.getKey();  
				Class value = property.getValue();	            	
				envelope.addMapping(namespace, entityClassName, value);
				
			}	                                
			getEntityClass().clear();
		}
	    
	    envelope.encodingStyle = "UTF-8";
	    envelope.dotNet= getDotNet();
	    envelope.setOutputSoapObject(request); //this method will return a response
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //open the requisition
	    androidHttpTransport.debug=false;
	    androidHttpTransport.call(getSoapAction(), envelope);// call
	    try{
	    	result = envelope.getResponse(); // returns result of method called	 
	    }catch(SoapFault e){
	    	throw e;
	    }
		return result;
	}
	
	public void executeCall() throws Exception{
		SoapObject request = new SoapObject(getNamespace(), getMethodName()); //create the soap method	            
		
		if(getParameters() != null){
			for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
				String parameter = property.getKey();  
		        Object value = property.getValue();	            	
		        request.addProperty(parameter, value);	            	
			}	                                
			getParameters().clear();
		}
			            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serialize the envelope 
	    envelope.dotNet= getDotNet();
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //open the requisition
	    androidHttpTransport.call(getSoapAction(), envelope);// call

	}
	
}