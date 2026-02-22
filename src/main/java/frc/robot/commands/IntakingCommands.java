package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;

public class IntakingCommands
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

    private static Agitator agitator = null;
    private static CommandSwerveDrivetrain drivetrain = null;
    private static Flywheel flywheel = null;
    private static LEDs leds = null;
    private static Pivot pivot = null;
    private static Roller roller = null;
    private static Shroud shroud = null;

    private static LEDs.LEDView view = null;
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
        leds = robotContainer.getLEDs();
        pivot = robotContainer.getPivot();
        roller = robotContainer.getRoller();
        shroud = robotContainer.getShroud();

        if (leds != null)
            leds.createView(0, 199);
        viewController = new LEDsController(view);

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Extend pivot, then score
     * 
     * @author Jackson D.
     * @return Simple score command
     */
    public static Command simpleIntakeCommand()
    {
        if (pivot != null && roller != null)
        {
            return viewController.setGradientCommand(Color.kYellow, Color.kRed)
                    .andThen(pivot.extendCommand())
                    .andThen(roller.intakeFuelCommand());
        } else
            return Commands.none();
    }

    /**
     * Retracts the pivot and turns off the roller
     * 
     * @author Jackson D.
     * @return Intake stop command
     */
    public static Command stopIntakingCommand()
    {
        if (pivot != null && roller != null)
        {
            return Commands.parallel(
                    viewController.setSolidCommand(Color.kRed),
                    pivot.retractCommand(),
                    roller.stopCommand());
        } else
            return Commands.none();
    }
}
