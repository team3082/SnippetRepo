package frc.robot.subsystems;

// ctre 
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// wpilib 
import edu.wpi.first.wpilibj.Relay;

//to prevent the lift from falling, this subsystem uses an electric solenoid controlled by a relay to lock itself

public class Lift {

    private static TalonSRX m_lift;
    private static Relay m_lock;

    public static double m_power;
    public static double m_delay;
    public static double m_unlocked;

    public static void init(int motorPort, int relayPort) {
        m_lift = new TalonSRX(motorPort);
        m_lift.setInverted(false);
		
        m_lock = new Relay(relayPort);
        clear();
    }


    public static void clear() {
        m_power = 0;
        
        m_lift.set(ControlMode.PercentOutput, 0);

        m_lock.set(Relay.Value.kOff);

    }

    public static void update() {
        if(m_power > 0) {
            if(m_unlocked == 0) {
                m_unlocked = 1;
                m_delay = RT.getTime() + 1;
                m_lock.set(Relay.Value.kReverse);
            }
            else if(RT.getTime() > m_delay && m_unlocked == 1) {

                m_lift.set(ControlMode.PercentOutput, m_power);

            }

        }
        else if(m_power < 0) {
            m_lift.set(ControlMode.PercentOutput, m_power);
            m_unlocked = 0;
        }
        else {
            m_lift.set(ControlMode.PercentOutput, 0);
            m_lock.set(Relay.Value.kOff);
            m_unlocked = 0;
        }
    }

}
