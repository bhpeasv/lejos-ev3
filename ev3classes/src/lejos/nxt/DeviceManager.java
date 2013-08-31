package lejos.nxt;

import lejos.internal.io.NativeDevice;

public class DeviceManager implements EV3SensorConstants
{
    protected static NativeDevice dev;
    
    static {
        initDeviceIO();
    }
    
    protected static DeviceManager localDeviceManager = new DeviceManager();

    /**
     * Return the device manager for the local EV3
     * @return The local device manager singleton.
     */
    public static DeviceManager getLocalDeviceManager()
    {
        return localDeviceManager;
    }

    /**
     * Get the type classification for the port. If a sensor is attached to the port
     * this will identify the connection type (UART/IIC/Analog)
     * @param port the port to check.
     * @return The type of the port. 
     */
    public int getPortType(int port)
    {

        return LocalAnalogPort.getPortType(port);
    }

    /**
     * This function returns information on the sensor that is attached to the
     * specified port. Note that only very basic sensor identification information
     * may be available for some sensor types. It may be necessary to actually open the
     * sensor to allow it to be identified in further detail.
     * @param port the port to check
     * @return the sensor type
     */
    public int getSensorType(int port)
    {
        return LocalAnalogPort.getAnalogSensorType(port);
    }

    /**
     * Set the basic operating mode of the various sensor pins to allow correct
     * operation of the attached sensor.
     * @param port port to set
     * @param mode the pin mode to use.
     */
    public void setPortMode(int port, int mode)
    {
        byte [] modes = new byte[PORTS];
        for(int i = 0; i < modes.length; i++)
            modes[i] = (byte)'-';
        modes[port] = (byte)mode;
        dev.write(modes, modes.length);
        
    }
    
    /**
     * Return a human readable version of the port type.
     * @param typ port type
     * @return string version of the type
     */
    public String getPortTypeName(int typ)
    {
        switch(typ)
        {
        case CONN_INPUT_UART:
            return "UART";
        case CONN_UNKNOWN:
            return "Unknown";
        case CONN_INPUT_DUMB:
            return "Dumb";
        case CONN_NXT_DUMB:
            return "NXT Dumb";
        case CONN_NXT_IIC:
            return "NXT_IIC";
        case CONN_NXT_COLOR:
            return "NXT Color";
        case CONN_NONE:
            return "None";
        default:
            return "Error";
        }
    }
    
    
    private static void initDeviceIO()
    {
        dev = new NativeDevice("/dev/lms_dcm"); 
    }
}
