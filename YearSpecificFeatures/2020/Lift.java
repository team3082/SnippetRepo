package frc.robot.subsystems;

// imports

// ctre 
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// wpilib 
import edu.wpi.first.wpilibj.Relay;

public class Lift {

    private static TalonSRX m_lift;
    private static Relay m_lock;

    public static double m_power;
    public static boolean m_valid;
    public static double m_delay;
    public static double m_state;

    public static void init() {

        m_valid = true;

        m_lift = new TalonSRX(RobotMap.kLiftMotor);
        m_lift.setInverted(false);
        try {
            m_lock = new Relay(RobotMap.kLiftSolenoid);

        }
        catch(Exception e)
        {
            m_valid = false;
            System.out.println("LIFT INVALID " + e);
        }
        clear();
    }


    public static void clear() {

        if(!m_valid)

            return;

        m_power = 0;
        
        m_lift.set(ControlMode.PercentOutput, 0);

        m_lock.set(Relay.Value.kOff);

    }

    public static void update() {

        if(!m_valid)
            return;
        if(m_power > 0) {
            if(m_state == 0 ) {
                m_state = 1;
                m_delay = RT.getTime() + 1;
                m_lock.set(Relay.Value.kReverse);
            }
            else if(RT.getTime() > m_delay && m_state == 1) {

                m_lift.set(ControlMode.PercentOutput, m_power);

            }

        }
        else if(m_power < 0) {
            m_lift.set(ControlMode.PercentOutput, m_power);
            m_state = 0;
        }
        else {
            m_lift.set(ControlMode.PercentOutput, 0);
            m_lock.set(Relay.Value.kOff);
            m_state = 0;
        }
    }

}
