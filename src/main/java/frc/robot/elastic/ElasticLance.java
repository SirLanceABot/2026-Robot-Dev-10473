package frc.robot.elastic;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shroud;
import frc.robot.subsystems.Drivetrain;

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

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    private static Drivetrain drivetrain = null;
    private static Shroud shroud = null;

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Gets the subsystems needed for Elastic
     * @param robotContainer {@link RobotContainer} The robot container to get subsystems from
     */
    public static void configElastic(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        drivetrain = robotContainer.getDrivetrain();
        shroud = robotContainer.getShroud();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Inits the SmartDashboard
     * @implNote Runs once
     */
    public static void initSmartDashboard()
    {
        if (drivetrain != null)
        {
            SmartDashboard.putData("Swerve Drive", new Sendable() 
            {
                @Override
                public void initSendable(SendableBuilder builder) 
                {
                    builder.setSmartDashboardType("SwerveDrive");

                    // Front Left Module (index 0)
                    builder.addDoubleProperty("Front Left Angle", 
                        () -> drivetrain.getModule(0).getCurrentState().angle.getRadians(), null);
                    builder.addDoubleProperty("Front Left Velocity", 
                        () -> drivetrain.getModule(0).getCurrentState().speedMetersPerSecond, null);

                    // Front Right Module (index 1)
                    builder.addDoubleProperty("Front Right Angle", 
                        () -> drivetrain.getModule(1).getCurrentState().angle.getRadians(), null);
                    builder.addDoubleProperty("Front Right Velocity", 
                        () -> drivetrain.getModule(1).getCurrentState().speedMetersPerSecond, null);

                    // Back Left Module (index 2)
                    builder.addDoubleProperty("Back Left Angle", 
                        () -> drivetrain.getModule(2).getCurrentState().angle.getRadians(), null);
                    builder.addDoubleProperty("Back Left Velocity", 
                        () -> drivetrain.getModule(2).getCurrentState().speedMetersPerSecond, null);

                    // Back Right Module (index 3)
                    builder.addDoubleProperty("Back Right Angle", 
                        () -> drivetrain.getModule(3).getCurrentState().angle.getRadians(), null);
                    builder.addDoubleProperty("Back Right Velocity", 
                        () -> drivetrain.getModule(3).getCurrentState().speedMetersPerSecond, null);

                    // Robot Angle
                    builder.addDoubleProperty("Robot Angle", 
                        () -> drivetrain.getState().Pose.getRotation().getRadians(), null);
                }
            });
        }
        else
            System.out.println("    WARNING: Drivetrain is null!");
    }

    /**
     * Updates the SmartDashboard
     * @implNote Runs continuously
     */
    public static void updateSmartDashboard()
    {
        //This doesn't make sense
        // if(shroud != null)
        // {
            // SmartDashboard.putNumber("Shroud Angle", shroud.getShotAngle(10));
        // }

        //DON'T DO THIS
        // else
            // System.out.println("    WARNING: Shroud is null!");
    }
}
