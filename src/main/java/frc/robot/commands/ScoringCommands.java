package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LEDs;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Flywheel;
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
    private static Roller roller;
    private static Shroud shroud;

    private static PoseEstimator poseEstimator;

    private static LEDs.LEDView view = null;

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
        roller = robotContainer.getRoller();
        shroud = robotContainer.getShroud();

        poseEstimator = robotContainer.getPoseEstimator();

        view = LEDs.createView(0, 199);

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
        if (agitator != null && flywheel != null && shroud != null)
        {
            return Commands.parallel(
                    view.setGradientCommand(Color.kBlue, Color.kRed),
                    flywheel.shootCommand(() -> 80).until(flywheel.isAtSetSpeed(15)))
                    // shroud.goToCommand(2.0))
                    .andThen(agitator.forwardCommand());
        } else
            return Commands.none();
    }

    /**
     * Stops the flywheel, and stops the agitator
     * 
     * @author Jackson D.
     * @return Scoring stop command
     */
    public static Command stopScoringCommand()
    {
        if (agitator != null && flywheel != null)
        {
            return view.setSolidCommand(Color.kRed)
                    .andThen(flywheel.stopCommand())
                    .andThen(agitator.stopCommand());
        } else
            return Commands.none();
    }

    /**
     * Command to stop driving, rotate towards the hub, set the flywheel
     * appropriately, and score.
     * NOT TESTED(!!!!!!!)
     * 
     * @return Stationary score command
     * @author Jackson D.
     */
    public static Command stationaryScoreCommand()
    {
        if(flywheel != null && shroud != null && agitator != null && drivetrain != null && poseEstimator != null)
        {
            double distance = poseEstimator.getDistanceToAllianceHub().getAsDouble();

            return drivetrain.lockWheelsCommand().withTimeout(0.1)
                .andThen(Commands.parallel(
                    view.setRainbowCommand(),
                    drivetrain.angleLockDriveCommand(() -> 0, () -> 0, () -> 0.5, poseEstimator.getAngleToAllianceHub()),
                    // shroud.setAngleFromDistanceCommand(distance),
                    flywheel.shootFromDistanceCommand(distance)
                        .until(flywheel.isAtSetSpeed(flywheel.getShotSpeed(distance)))))
                    .andThen(agitator.forwardCommand());
        }
        else
            return Commands.none();
    }
}
