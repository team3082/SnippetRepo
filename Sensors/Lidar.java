package frc.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.mapping.RobotMap;
import frc.robot.constants.Constants;

public class Lidar {

    public static Counter m_Lidar;
    static final double kOffSet = Constants.kOffSet;
    private static double m_lastAverage;

    public static void init() {
        m_Lidar = new Counter(new DigitalInput(RobotMap.kLidarPort));
        m_Lidar.setMaxPeriod(1.00);
        m_Lidar.setSemiPeriodMode(true);
        m_Lidar.reset();
    }

    public static void update() {
        m_lastAverage = Math.round(m_lastAverage + getDistanceRaw()) / 2.0;
    }

    // Returns in centimeters smoothed
    public static double getDistance() {
        return m_lastAverage;
    }

    // Returns in centimeters
    public static double getDistanceRaw() {
        if (m_Lidar.get() < 1)
            return 0;
        else
            return (m_Lidar.getPeriod() * 1000000.0 / 10.0) - kOffSet; //convert to distance. sensor is high 10 us for every centimeter.
    }


}
