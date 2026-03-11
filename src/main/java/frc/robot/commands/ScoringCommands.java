package frc.robot.commands;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.PoseEstimator;

public class ScoringCommands
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

    private static Agitator agitator;
    private static CommandSwerveDrivetrain drivetrain;
    private static Flywheel flywheel;
    private static Pivot pivot;
    // private static Roller roller;
    // private static Shroud shroud;

    private static PoseEstimator poseEstimator;

    private static LEDsController viewController = null;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    public static void createCommands(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        agitator = robotContainer.getAgitator();
        drivetrain = robotContainer.getDrivetrain();
        flywheel = robotContainer.getFlywheel();
        pivot = robotContainer.getPivot();
        // roller = robotContainer.getRoller();
        // shroud = robotContainer.getShroud();

        poseEstimator = robotContainer.getPoseEstimator();

        LEDs leds = robotContainer.getLEDs();

        LEDs.LEDView view = null;
        if (leds != null)
            view = leds.createView(0, 199);
        viewController = new LEDsController(view);

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Shoots at a fixed rate in the direction we are facing, in order to pass fuel
     * 
     * @author Jackson D.
     * @return Pass Command
     */
    public static Command passCommand()
    {
        if (agitator != null && flywheel != null && pivot != null)
        {
            return Commands.parallel(
                    viewController.setGradientCommand(Color.kBlue, Color.kRed),
                    flywheel.shootCommand(() -> 80).withTimeout(0.3), 
                    pivot.shootPositionCommand())
                    .andThen(Commands.parallel(
                                agitator.forwardCommand(), 
                                pivot.shimmyCommand()));
        } else
            return Commands.none();
    }

    /**
     * Stops the flywheel, and stops the agitator
     * 
     * @author Jackson D.
     * @return Scoring stop command
     */
    public static Command stopScoreCommand()
    {
        if (agitator != null && flywheel != null)
        {
            return viewController.setSolidCommand(Color.kRed)
                    .andThen(flywheel.stopCommand())
                    .andThen(Commands.parallel(agitator.stopCommand()), 
                                pivot.extendCommand());
        } else
            return Commands.none();
    }

    /**
     * Command to stop driving, rotate towards the hub, set the flywheel
     * appropriately, and score.
     * 
     * @return Stationary score command
     * @author Jackson D.
     */
    public static Command stationaryScoreCommand()
    {
        if(flywheel != null && pivot != null && agitator != null && drivetrain != null && poseEstimator != null)
        {
            DoubleSupplier distance = () -> (poseEstimator.getDistanceToTarget(drivetrain.getState().Pose, poseEstimator.getAllianceHubPose()).getAsDouble());
            DoubleSupplier shotSpeed = () -> (flywheel.getShotSpeed(distance.getAsDouble()));

            return drivetrain.lockWheelsCommand().withTimeout(0.1)
                    .andThen(Commands.parallel(
                        viewController.setRainbowCommand().withTimeout(0.01),
                        drivetrain.angleLockDriveCommand(() -> 0, () -> 0, () -> 0.5, () -> poseEstimator.getAngleToAllianceHub().getAsDouble()).withTimeout(0.75),
                        // shroud.setAngleFromDistanceCommand(distance),
                        flywheel.shootCommand(() -> (shotSpeed.getAsDouble())).withTimeout(0.3),
                        pivot.shootPositionCommand()))   
                    .andThen(Commands.parallel(
                                agitator.forwardCommand(), 
                                pivot.shimmyCommand())).withTimeout(10.0);
        }
        else
            return Commands.none();
    }
}
