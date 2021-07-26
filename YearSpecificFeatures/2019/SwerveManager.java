package frc.robot;

import java.util.Vector;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveManager {

    public static double xInput;
    public static double yInput;
    public static double left;
    public static double right;
    public static double rPower; //thats a lot of power
    public static double offset;
    public static double rotationConstant;
    public static double[] wheelSpeed;
    public static double[] wheelAngle;
    public static SwerveModule[] modules;
    public static boolean stop;
    public static double[] superStopWheelAngles = {135, 45, 45, 135};

    public SwerveManager() {
        rotationConstant = Math.hypot(RobotMap.robotLength, RobotMap.robotWidth);

        wheelSpeed = new double[4];
        wheelAngle = new double[4]; //cant use RobotMap.swerveCount due to hardcoded math\

        modules = new SwerveModule[RobotMap.swerveCount];
        for(int i = 0; i < RobotMap.swerveCount; i++) {
            int[] ports = RobotMap.swervePorts[i];
            double[] config = RobotMap.swerveConfig[i];
            modules[i] = new SwerveModule((int)config[0], ports[0], ports[1], config[1]);
            modules[i].steer.config_kP(0, config[2], 1);
            modules[i].steer.config_kI(0, config[3], 1);
            modules[i].steer.config_kD(0, config[4], 1);
        }
    }

    public void update() {
        if(stop)
            hyperHockeySuperStop();
        else
            swerve();
    }

    public void hyperHockeySuperStop(){
        for(int i = 0; i < 4; i++)
            modules[i].drive(0, superStopWheelAngles[i]);
    }
    public void swerve() {
        if(xInput == 0 && yInput == 0 && rPower == 0){
            for (int i = 0; i < RobotMap.swerveCount; i++)
                modules[i].speed.set(ControlMode.PercentOutput, 0);
            return;
        }

        double a,b,c,d,x,y;

        double radiansOffset = Math.toRadians(offset);
        x = -yInput*Math.sin(radiansOffset) + xInput*Math.cos(radiansOffset);
        y = yInput*Math.cos(radiansOffset) + xInput*Math.sin(radiansOffset); 

        
        a = x + rPower * (RobotMap.robotLength/rotationConstant);
        b = x - rPower * (RobotMap.robotLength/rotationConstant);
        c = y - rPower * (RobotMap.robotWidth/rotationConstant);
        d = y + rPower * (RobotMap.robotWidth/rotationConstant);

        wheelSpeed[0] = Math.hypot(b, d);
        wheelSpeed[1] = Math.hypot(b, c);
        wheelSpeed[2] = Math.hypot(a, d);
        wheelSpeed[3] = Math.hypot(a, c);

        wheelAngle[0] = Math.atan2(b, d);
        wheelAngle[1] = Math.atan2(b, c);
        wheelAngle[2] = Math.atan2(a, d);
        wheelAngle[3] = Math.atan2(a, c);

        
        //Speed Normalizer
        final double maxWheelSpeed = Math.max(Math.max(wheelSpeed[0], wheelSpeed[1]), Math.max(wheelSpeed[2], wheelSpeed[3]));
        if (maxWheelSpeed > 1.0)
            for (int i = 0; i < RobotMap.swerveCount; i++)
                wheelSpeed[i] /= maxWheelSpeed;
        
        for(int i = 0; i < RobotMap.swerveCount; i++)
            modules[i].drive(wheelSpeed[i], RobotMath.toDegreesWithoutTransform(wheelAngle[i]));
    }

    public void stop() {
        for (int i = 0; i < RobotMap.swerveCount; i++)
            modules[i].speed.set(ControlMode.PercentOutput, 0);
        xInput = 0;
        yInput = 0;
        rPower = 0;
    }

    static public double getSwerveDir() {
        double dir = 0;
        for(int i = 0; i < RobotMap.swerveCount; i++)
            dir += modules[i].getDir();
        return dir/RobotMap.swerveCount;
    }
}