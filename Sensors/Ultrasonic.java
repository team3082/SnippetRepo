package frc.robot.subsystems.sensors;

// wpilib 
import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {

    public static AnalogInput ultrasonic;
    public static final double MetersPerVolt = 1;
    public static final int port = 1; 

    public static void init() {

        ultrasonic = new AnalogInput(port);

    }

    public static double getDistance () {

        return ultrasonic.getVoltage() * MetersPerVolt;
        
    }

}
