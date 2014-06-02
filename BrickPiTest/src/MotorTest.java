import lejos.hardware.brickpi.BrickPi;
import lejos.hardware.brickpi.LocalBrickPi;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class MotorTest {
	
	public static void main(String[] args) {
		BrickPi pi = LocalBrickPi.get();	
		Port mA = pi.getPort("A");
		Port mB= pi.getPort("B");
		
		NXTMotor left = new NXTMotor(mA);
		NXTMotor right = new NXTMotor(mB);
		
		left.setPower(50);
		right.setPower(50);
		
		left.resetTachoCount();
		right.resetTachoCount();
		
		left.backward();
		right.backward();
		
		Delay.msDelay(1000);
		
		System.out.println("Left tacho:" + left.getTachoCount());
		System.out.println("Right tacho:" + right.getTachoCount());
		
		left.stop();
		right.stop();
		
		left.close();
		right.close();
	
	}
}
