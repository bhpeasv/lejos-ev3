package lejos.nxt;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

/**
 * An abstraction for a port that supports Analog/Digital sensors.
 * 
 * @author Lawrie Griffiths.
 * 
 */
public interface ADSensorPort extends SensorPort {

	public boolean readBooleanValue();
	
	public int readRawValue();
	
	public int readValue();
	
	public int getPin6();
	
	public int getPin1();
}
