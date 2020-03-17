package frc.robot.subsystems;

// wpilib 
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DriverStation;

public class Lights {

    private static Spark m_blinkin;

    private static double solidColor;
    private static double pulsingColor;

    public static void init(){

        m_blinkin = new Spark(RobotMap.kBlinkinPort);

        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();

        if (alliance == DriverStation.Alliance.Blue){

            solidColor = 0.87;

            pulsingColor = -0.41;

        } else if (alliance == DriverStation.Alliance.Red){

            solidColor = 0.61;

            pulsingColor = -0.39;

        } else {

            solidColor = 0.93;

            pulsingColor = 0.93;
        }

      Blinkin.setSolid();
      
    }

    public static void setSolid(){

        m_blinkin.set(solidColor);

    }

    public static void setPulsing(){

        m_blinkin.set(pulsingColor);
        
    }

}