package frc.robot;

// java util 
import java.util.ArrayList;

// lib
import frc.lib.RT;

public class AutoRunner {

    private static ArrayList<Object> m_rightPower;
    private static ArrayList<Object> m_rightTiming;
    private static ArrayList<Object> m_leftPower;
    private static ArrayList<Object> m_leftTiming;

    public static int locationInRight; 
    public static int locationInLeft; 

    public static void init () {

        m_rightPower = new ArrayList<Object>(); 
        m_rightTiming = new ArrayList<Object>(); 
        m_leftPower = new ArrayList<Object>(); 
        m_leftTiming = new ArrayList<Object>(); 

        // paste generated code here

        locationInRight = 0; 
        locationInLeft = 0; 

    }

    public static void update () {

        if (RT.isTime( (double) m_leftTiming.get(locationInLeft), .25 ) ) {

            Drivetrain.m_leftPower = (double) m_leftPower.get(locationInLeft); 

            locationInLeft++; 

        }

        if (RT.isTime( (double) m_rightTiming.get(locationInRight), .25 ) ) {

            Drivetrain.m_rightPower = (double) m_rightPower.get(locationInRight); 

            locationInRight++; 

        }

    }


}