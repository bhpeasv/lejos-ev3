package lejos.remote.ev3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIEV3 extends Remote {

	public RMIAnalogPort openAnalogPort(String portName) throws RemoteException;
	
	public RMII2CPort openI2CPort(String portName) throws RemoteException;

	public RMIBattery getBattery() throws RemoteException;

	public RMIUARTPort openUARTPort(String portName) throws RemoteException;

	public RMIMotorPort openMotorPort(String portName) throws RemoteException;
	
	public RMISampleProvider createSampleProvider(String portName, String sensorName, String modeName) throws RemoteException;
	
	public RMIRegulatedMotor createRegulatedMotor(String portName) throws RemoteException;

	public RMISound getSound() throws RemoteException;
	
	public RMIWifi getWifi() throws RemoteException;
	
	public RMIBluetooth getBluetooth() throws RemoteException;
}
