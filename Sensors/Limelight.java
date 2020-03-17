package frc.robot.subsystems.sensors;

import edu.wpi.first.networktables.*;

public class Limelight {

    // Whether the limelight has any valid targets
    public static NetworkTableEntry targetDetected;

    // Horizontal Offset From Crosshair To Target
    // (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
    public static NetworkTableEntry targetXOffset;

    // Vertical Offset From Crosshair To Target
    // (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
    public static NetworkTableEntry targetYOffset;

    // Skew or rotation (-90 degrees to 0 degrees)
    public static NetworkTableEntry targetRot;

    // Target Area (0% of image to 100% of image)
    public static NetworkTableEntry targetArea;

    // Sidelength of shortest side of the fitted bounding box (pixels)
    public static NetworkTableEntry targetShortSidelength;

    // Sidelength of longest side of the fitted bounding box (pixels)
    public static NetworkTableEntry targetLongSidelength;

    // Whether the limelight leds should be on 1 off 0 on
    public static NetworkTableEntry ledMode;

    public static void init() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("limelight");

        targetDetected = table.getEntry("tv");
        targetXOffset = table.getEntry("tx");
        targetYOffset = table.getEntry("ty");
        targetRot = table.getEntry("ts");
        targetArea = table.getEntry("ta");
        targetShortSidelength = table.getEntry("tshort");
        targetLongSidelength = table.getEntry("tlong");
	  ledMode = table.getEntry("ledMode");

	  setLedMode(false);
    }

    public static boolean isTargetValid() {
        return (targetDetected.getDouble(0) == 1);
    }

    public static double getTargetXOffset() {
        return targetXOffset.getDouble(0);
    }

    public static double getTargetYOffset() {
        return targetYOffset.getDouble(0);
    }

    public static double getTargetRotation() {
        return targetRot.getDouble(0);
    }

    public static double getTargetArea() {
        return targetArea.getDouble(0);
    }

    public static double getTargetShortSideLength() {
        return targetShortSidelength.getDouble(0);
    }

    public static double getTargetLongSideLength() {
        return targetLongSidelength.getDouble(0);
    }

    public static void setLedMode(boolean enabled) {
        ledMode.setDouble(enabled ? 3 : 1);
    }

}
