package frc.robot.subsystems.sensors;

// wpilib 
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class Lidar {

    public static Counter m_Lidar;
    static final double kOffSet = Constants.kOffSet; // TODO: explain kOffSett
    private static double m_lastAverage;
    public static int port = 1; // set to port 

    public static void init() {

        m_Lidar = new Counter(new DigitalInput(port));

        m_Lidar.setMaxPeriod(1.00);

        m_Lidar.setSemiPeriodMode(true);

        m_Lidar.reset();

    }

    public static void update() {

        m_lastAverage = Math.round(m_lastAverage + getDistanceRaw()) / 2.0;

    }

    public static double getDistance() { // Returns in centimeters smoothed 

        return m_lastAverage; 

    }

    
    public static double getDistanceRaw() { // Returns in centimeters

        if (m_Lidar.get() < 1)

            return 0;

        else

            return (m_Lidar.getPeriod() * 100000.0) - kOffSet; 

    }


}
