package frc.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.mapping.RobotMap;
import frc.robot.constants.Constants;

public class Ultrasonic {

    public static AnalogInput ultrasonic;
    public static final double MetersPerVolt = Constants.kMilliMetersPerVolt;

    public static void init() {
        ultrasonic = new AnalogInput(RobotMap.kUltrasonicPort);
    }

    public static double getDistance () {
        return ultrasonic.getVoltage() * MetersPerVolt;
    }

}
