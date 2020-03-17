package frc.robot;

public class AutoAlign {

    // Constants  
    public static final double kSpeedMultiplier = 0.035;
    public static final double kMinStopValue = -0.25;
    public static final double kMaxStopValue = 0.25;
    public static final double kMinDistanceToWall = 0.1;
    public static final double kMaxPowerOut = 0.5;

    public static boolean m_enabled;

	public static void init() {

        clear();

    }

    public static void clear() {

        m_enabled = false;

    }

    public static void update() {

        Limelight.setLedMode(m_enabled);

        double rotation = Limelight.getTargetXOffset();

        double distanceToWall = Lidar.getDistance() / 100;

        distanceToWall *= Math.cos(Math.abs(Math.toRadians(rotation)));

        double distanceMultiplier = RMath.clamp(0.45, 0.9, (6 - distanceToWall/2) / 6);

        if (distanceToWall > kMinDistanceToWall) {

            double distanceToTargetX = Limelight.getTargetXOffset();

            if (distanceToTargetX > kMaxStopValue || distanceToTargetX < kMinStopValue) {

                double unclampedLeftPower = (distanceToTargetX * kSpeedMultiplier * distanceMultiplier);

                double unclampedRightPower = -(distanceToTargetX * kSpeedMultiplier * distanceMultiplier);

                Drivetrain.m_leftPower += RMath.clamp(-1, 1, unclampedLeftPower) * kMaxPowerOut;
                
                Drivetrain.m_rightPower += RMath.clamp(-1, 1, unclampedRightPower) * kMaxPowerOut;

            }
        }

    }

    public static boolean atSetpoint() {

        double distanceToTargetX = Limelight.getTargetXOffset();

        return !(distanceToTargetX > kMaxStopValue || distanceToTargetX < kMinStopValue);

    }

}
