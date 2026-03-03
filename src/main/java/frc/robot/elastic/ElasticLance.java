package frc.robot.elastic;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.PoseEstimator;

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

    private static Color shootDistanceColor = new Color();

    private static CommandSwerveDrivetrain drivetrain = null;
    private static Flywheel flywheel = null;
    private static PoseEstimator poseEstimator = null;

    private static Field2d autofield = new Field2d();

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Gets the subsystems needed for Elastic
     * 
     * @param robotContainer {@link RobotContainer} The robot container to get
     *            subsystems from
     */
    public static void configElastic(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        drivetrain = robotContainer.getDrivetrain();
        flywheel = robotContainer.getFlywheel();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Updates the SmartDashboard
     * 
     * @implNote Runs continuously
     */
    public static void updateSmartDashboard()
    {
        SmartDashboard.putNumber("Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
    }

    public static void updateShootDistanceColorBox()
    {
        if(poseEstimator != null)
        {
            if(poseEstimator.getDistanceToAllianceHub().getAsDouble() < 3.0)
            {
                shootDistanceColor = Color.kGreen;
            }
            else
            {
                shootDistanceColor = Color.kOrange;
            }
        }
        else
        {
            shootDistanceColor = Color.kRed;
        }

        SmartDashboard.putString("Within Shooter Distance", shootDistanceColor.toHexString());
    }
}
