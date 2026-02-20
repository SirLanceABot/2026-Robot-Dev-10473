package frc.robot.elastic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.pathplanner.PathPlannerLance;
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

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Inits the SmartDashboard
     * 
     * @implNote Runs once
     */
    public static void initSmartDashboard()
    {
        initAutoField();
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

    /**
     * Initalizes the Auto Field Widget
     * 
     * @author Unknown
     * @implNote ~stolen~ taken from last year's code:
     *           https://github.com/SirLanceABot/2025-Robot-Dev-10473/commit/1e412475e1989f0d6bb7df463b50acd20b2d4c8c
     */
    private static void initAutoField()
    {
        if (drivetrain != null)
        {
            SmartDashboard.putData("AutoField", autofield);
            Pose2d pose = drivetrain.getState().Pose;
            autofield.setRobotPose(pose);
        }
    }

    /**
     * Helper to extract poses from paths
     * 
     * @param pathPlannerPaths {@link List}<{@link PathPlannerPath}> The paths to
     *            extract poses from
     * @return {@link List}<{@link Pose2d}> The extracted poses from the input paths
     * @author Unknown
     * @implNote ~stolen~ taken from last year's code:
     *           https://github.com/SirLanceABot/2025-Robot-Dev-10473/commit/1e412475e1989f0d6bb7df463b50acd20b2d4c8c
     */
    private static List<Pose2d> extractPosesFromPaths(List<PathPlannerPath> pathPlannerPaths)
    {
        List<Pose2d> poses = new ArrayList<>();
        for (PathPlannerPath path : pathPlannerPaths)
        {
            poses.addAll(path.getAllPathPoints().stream()
                    .map(point -> new Pose2d(point.position.getX(), point.position.getY(), new Rotation2d()))
                    .collect(Collectors.toList()));
        }
        return poses;
    }
}
