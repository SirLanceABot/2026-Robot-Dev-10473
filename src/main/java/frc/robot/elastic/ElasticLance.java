package frc.robot.elastic;

import java.lang.invoke.MethodHandles;

import frc.robot.RobotContainer;

/**
 * Implements code to send data to elastic
 * 
 * @author Mukul Kedia
 */
public class ElasticLance
{
    // This string gets the full name of the class, including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded

    static
    {
        System.out.println("Loading: " + fullClassName);
    }

    // *** INNER ENUMS and INNER CLASSES ***
    // Put all inner enums and inner classes here

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    public static void configElastic(RobotContainer robotContainer)
    {}

    public static void updateSmartDashboard()
    {}

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here
}
