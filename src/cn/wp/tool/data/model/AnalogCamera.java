package cn.wp.tool.data.model;

public abstract class AnalogCamera extends Camera {

	public Camera_Type getType() {
		return Camera_Type.Analog_Camera;
	}
	
	public abstract String getSupportColor();
	
	public abstract String getStructure();
	
	public abstract String getPhotoreceptor();
	
	public abstract String getMotionMode();
	
	public abstract boolean isInfrared();
	
}
