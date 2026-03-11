package frc.robot.elastic;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
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

    private static Color allianceColor = new Color();
    private static Color shootDistanceColor = new Color();

    private static CommandSwerveDrivetrain drivetrain = null;
    private static Flywheel flywheel = null;
    private static PoseEstimator poseEstimator = null;
    private static boolean useFullRobot;


    private static Alert useFullRobotAlert = new Alert("NOT using Full Robot!", AlertType.kError);
    private static final Alert lowVoltageAlert = new Alert("Battery voltage is LOW", AlertType.kError);

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
        poseEstimator = robotContainer.getPoseEstimator();
        useFullRobot = robotContainer.useFullRobot();

        lowVoltageAlert.set(true);
        useFullRobotAlert.set(true);
        
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

        updateAllianceColorBox();
        updateFlywheelSpeedBox();
        updateShootDistanceColorBox();
        updateAlerts();
    }

    public static void updateAllianceColorBox()
    {
        if(DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == DriverStation.Alliance.Red)
        {
            allianceColor = Color.kRed;
        }
        else if(DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == DriverStation.Alliance.Blue)
        {
            allianceColor = Color.kBlue;
        }
        else
        {
            allianceColor = Color.kGray;
        }

        SmartDashboard.putString("Alliance Color", allianceColor.toHexString());
    }

    public static void updateFlywheelSpeedBox()
    {
        double flywheelSpeed = 0.0;
        if(flywheel != null)
        {
            flywheelSpeed = flywheel.getVelocity();
        }

        SmartDashboard.putString("Flywheel Speed", String.valueOf(flywheelSpeed));
    }

    public static void updateShootDistanceColorBox()
    {
        if(poseEstimator != null && drivetrain != null)
        {
            DoubleSupplier distance = () -> (poseEstimator.getDistanceToTarget(drivetrain.getState().Pose, poseEstimator.getAllianceHubPose()).getAsDouble());

            if(distance.getAsDouble() <= 4.0 && distance.getAsDouble() >= 2.0)
            {
                shootDistanceColor = Color.kGreen;
            }
            else
            {
                shootDistanceColor = Color.kRed;
            }
        }
        else
        {
            shootDistanceColor = Color.kRed;
        }

        SmartDashboard.putString("Within Shooter Distance", shootDistanceColor.toHexString());
    }

    public static void updateAlerts()
    {
        if(RobotController.getBatteryVoltage() < 12.0)
            lowVoltageAlert.set(true);
        else
            lowVoltageAlert.set(false);

        if(!useFullRobot && DriverStation.isDisabled())
            useFullRobotAlert.set(true);
        else
            useFullRobotAlert.set(false);
    }
}
