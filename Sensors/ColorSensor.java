package frc.robot.subsystems.sensors;

// wpilib 
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

// revrobotics 
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

public class ColorSensor {

    public static I2C.Port m_i2cPort;
    public static ColorSensorV3 m_colorSensor;
    public static ColorMatch m_colorMatcher;

    public static final Color kBlueTarget   = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget  = ColorMatch.makeColor(0.197, 0.561, 0.240); 
    public static final Color kRedTarget    = ColorMatch.makeColor(0.561, 0.232, 0.114); 
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); 

    public static Color m_detectedColor;

    public static void init() {

        m_i2cPort = I2C.Port.kOnboard;
        m_colorMatcher = new ColorMatch();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

    }

    public static void update() {

        m_detectedColor = m_colorSensor.getColor();
        
    }

    public static String getColor (){

        ColorMatchResult match = m_colorMatcher.matchClosestColor(m_detectedColor);

        if (match.color == kBlueTarget) {

            return "Blue";

        } else if (match.color == kRedTarget) {

            return "Red";

        } else if (match.color == kGreenTarget) {

            return "Green";

        } else if (match.color == kYellowTarget) {

            return "Yellow";

        } else {

            return null;

            Log.println("no color detected"); 

        }

    }

}
