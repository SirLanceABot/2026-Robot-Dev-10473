package frc.robot.sensors;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

/**
 * Limelight camera to detect apriltags
 */
public class Camera extends SubsystemBase
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
    private String cameraName;


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /** 
     * Creates a new Camera
     */
    public Camera(String cameraName)
    {   
        super("Camera");
        this.cameraName = cameraName;
        System.out.println("  Constructor Started:  " + fullClassName);


        System.out.println("  Constructor Finished: " + fullClassName);
    }


    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here
    
    public double getTagID()
    {
        return LimelightHelpers.getFiducialID(cameraName);
    }

    public boolean isValidTagInFrame()
    {
        return LimelightHelpers.getTV(cameraName);
    }

    public String getCameraName()
    {
        return cameraName;
    }

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here
    @Override
    public String toString()
    {
        return "";
    }
}