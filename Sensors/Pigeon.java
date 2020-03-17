package frc.robot.subsystems.sensors;

// ctre 
import com.ctre.phoenix.sensors.PigeonIMU;

public class Pigeon {

	public static double m_heading;
	public static PigeonIMU m_pigeon;
	public static int port = 1; 

	public static void init() {

		m_pigeon = new PigeonIMU(port);

		reset();

	}

	public static void update() {

		m_heading = m_pigeon.getFusedHeading() % 360;

		if (m_heading < 0) {

			m_heading += 360; 

		}

	}

	public static double getHeading() {

		return m_heading;

	}

	public static void reset() {

		m_pigeon.setFusedHeading(0);

	}

}
