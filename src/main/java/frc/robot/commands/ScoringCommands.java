package frc.robot.commands;

import java.lang.invoke.MethodHandles;

// import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
// import frc.robot.subsystems.LEDs.ColorPattern;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.PoseEstimator;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;

public class ScoringCommands
{
    // This string gets the full name of the class, including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded
    static {
        System.out.println("Loading: " + fullClassName);
    }

    // *** INNER ENUMS and INNER CLASSES ***
    // Put all inner enums and inner classes here

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    private static Agitator agitator;
    private static Drivetrain drivetrain;
    private static Flywheel flywheel;
    private static LEDs leds;
    private static Pivot pivot;
    private static Roller roller;
    private static Shroud shroud;

    private static PoseEstimator poseEstimator;

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
        leds = robotContainer.getLEDs();
        pivot = robotContainer.getPivot();
        roller = robotContainer.getRoller();
        shroud = robotContainer.getShroud();
        
        poseEstimator = robotContainer.getPoseEstimator();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Set shroud to lowest position, set flywheel to a speed of 15 RPS, and activates the agitator
     * @author Jackson D.
     * @return Simple score command
     */
    public static Command simpleScoreCommand()
    {
        if (agitator != null && flywheel != null && leds != null && shroud != null)
        {
            return Commands.parallel(
                    flywheel.shootCommand(() -> 15.0).until(flywheel.isAtSetSpeed(15)),
                    shroud.goToCommand(0.0)
                )
                .andThen(agitator.forwardCommand());
        }
        else 
            return Commands.none();
    }

    /**
     * Stops the flywheel, and stops the agitator
     * @author Jackson D.
     * @return Scoring stop command
     */
    public static Command stopScoringCommand()
    {
        if (agitator != null && flywheel != null)
        {
            return flywheel.stopCommand()
                   .andThen(agitator.stopCommand());
        }
        else 
            return Commands.none();
    }

    /**
     * Command to stop driving, rotate towards the hub, set the flywheel and shroud appropriately, and score.
     * NOT TESTED(!!!!!!!)
     * @return Stationary score command
     * @author Jackson D.
     */
    public static Command stationaryScoreCommand()
    {
        double distance = poseEstimator.getDistanceToAllianceHub().getAsDouble();
        if(flywheel != null && shroud != null && agitator != null && drivetrain != null && poseEstimator != null)
        {
            return drivetrain.lockWheelsCommand()
            .andThen(
                    drivetrain.angleLockDriveCommand(() -> 0, () -> 0, () -> 0.5, poseEstimator.getAngleToAllianceHub()))
            .andThen(
                    shroud.setAngleFromDistanceCommand(distance))
            .andThen(
                    flywheel.shootFromDistanceCommand(distance))
                    .until(flywheel.isAtSetSpeed(flywheel.getShotSpeed(distance)))
            .andThen(agitator.forwardCommand());           
        }
        else
            return Commands.none();
    }
}
