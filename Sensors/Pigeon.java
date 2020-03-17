package frc.robot.subsystems.sensors;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.robot.mapping.RobotMap;

public class Pigeon {

	public static double m_heading;
	public static PigeonIMU m_pigeon;

	public static void init() {
		m_pigeon = new PigeonIMU(RobotMap.kPidgeonPort);
		reset();
	}

	// Updates m_heading variable
	public static void update() {
		m_heading = m_pigeon.getFusedHeading() % 360;
		if (m_heading < 0)
			m_heading += 360;
	}

	// Returns robot direction
	public static double getHeading() {
		return m_heading;
	}

	// Resets Pigeon's heading
	public static void reset() {
		m_pigeon.setFusedHeading(0);
	}

}
