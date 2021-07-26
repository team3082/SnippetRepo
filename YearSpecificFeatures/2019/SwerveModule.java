package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.*;

public class SwerveModule {

	public TalonSRX speed;
	public TalonSRX steer;
	private Boolean inverted;
	private double rotationOffset;
	public int id;
	private NetworkTableEntry position;

	public SwerveModule(int moduleId, int steerPort, int speedPort, double offset) {
		
		speed = new TalonSRX(speedPort);
		steer = new TalonSRX(steerPort);

		speed.setInverted(false);
		steer.setInverted(false);
		steer.setSensorPhase(false);
		speed.setSensorPhase(true);

		steer.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 10);
		speed.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

		inverted = false;
		rotationOffset = offset;
		id = moduleId;
		position = NetworkTableInstance.getDefault().getTable("SwerveModules").getEntry(String.valueOf(id));
	}

	public void drive(double inSpeed, double inAngle) {
		//WITHOUT absolute  encoders: double curPos = (steer.getSelectedSensorPosition() + rotationOffset)/RobotMap.steerCountsPerRotation*360; 
		//range of 0-1024 uses only 11 to 897
		double encoderPos = steer.getSelectedSensorPosition();


		//The current position, wrapped from 0 to 1024
		double curPos = encoderPos%1024;
		if(curPos < 0)
			curPos += 1024;

		//Change CurPos to a value between 0 and 360
		curPos = (curPos-11)%(897-11)/(897.0-11.0)*360.0; 
		

		
		//current position in degrees (-inf to inf)
		double wrappedCurPos = curPos;
		//if we flipped our pos is offset by 180
		if(inverted)
			wrappedCurPos += 180;
		wrappedCurPos %= 360;

		//current position in degrees (-360 to 360)
		// if the wraped cur pos is negative it breaks some of the following math
		// this makes the math work by making it positive 
		if(wrappedCurPos<0)
			wrappedCurPos += 360;
		//Vlad was here
		double inDir = inAngle;
		if(inDir < 0)
			inDir += 360;

		//find the smallest direction to go 
		//add, subtract, or leave the wrap int
		//for each "wrap around value", check the abs of the difference between the values
		//if it is smallest set some "actual position" variable to this position

		position.setDouble(inDir);

		double stay = inDir-wrappedCurPos;			
		double forward = inDir-wrappedCurPos+360;
		double back = inDir-wrappedCurPos-360;
		double flipBack = inDir-wrappedCurPos-180;
		double flipForward = inDir-wrappedCurPos+180;
		
		//Forward will calculate if the robot should wrap around 0 while turning clockwise
		double optimalPos = stay;
		double outputPosDeg = 0;
		boolean flip = false;
		
		if (Math.abs(forward) < Math.abs(optimalPos)) {
			flip = false;
			optimalPos = forward;
		}
		if (Math.abs(back) < Math.abs(optimalPos)) {
			flip = false;
			optimalPos = back;
		}
		if (Math.abs(flipBack) < Math.abs(optimalPos)) {
			flip = true;
			optimalPos = flipBack;
		}
		if (Math.abs(flipForward) < Math.abs(optimalPos)) {
			flip = true;
			optimalPos = flipForward;
		}

		outputPosDeg = curPos + optimalPos;
		//find the smallest direction to go 
		//add, subtract, or leave the wrap int
		//for each "wrap around value", check the abs of the difference between the values
		//if it is smallest set some "actual position" variable to this position
		
		double revolution = Math.floor(encoderPos/1024);
		
		if(outputPosDeg < 0) {
			revolution--;
			outputPosDeg += 360;
		}
		else if(outputPosDeg >= 360) {
			revolution++;
			outputPosDeg -= 360;
		}

        //Degrees back into crazy absolute encoder range
		double encoderIn = (outputPosDeg)/360*(897-11)+11;
		
		double outputPos = revolution*1024 + encoderIn;

		steer.set(ControlMode.Position, outputPos);
		
		double speedPercent = inSpeed;
		if(flip)
			inverted = !inverted;
		if(inverted)
			speedPercent *= -1;
		
		speed.set(ControlMode.PercentOutput, speedPercent);
	}


	public double getDir() {
		double curPos = (steer.getSelectedSensorPosition() + rotationOffset)/RobotMap.steerCountsPerRotation*360; 
		
		double wrapedCurPos = curPos;
		if(inverted)
			wrapedCurPos += 180;
		wrapedCurPos %= 360;

		if(wrapedCurPos<0)
			wrapedCurPos += 360;
		
		return wrapedCurPos;
	}
}