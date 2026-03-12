package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Roller;

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

    // private static Agitator agitator = null;
    // private static CommandSwerveDrivetrain drivetrain = null;
    // private static Flywheel flywheel = null;
    private static Pivot pivot = null;
    private static Roller roller = null;
    // private static Shroud shroud = null;

    private static LEDsController viewController = null;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    public static void createCommands(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        // agitator = robotContainer.getAgitator();
        // drivetrain = robotContainer.getDrivetrain();
        // flywheel = robotContainer.getFlywheel();
        pivot = robotContainer.getPivot();
        roller = robotContainer.getRoller();
        // shroud = robotContainer.getShroud();
        LEDs leds = robotContainer.getLEDs();

        LEDs.LEDView view = null;
        if(leds != null)
            view = leds.createView(0, 199);
        viewController = new LEDsController(view);

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Intake and activate LEDs
     * 
     * @author Jackson D.
     * @return Simple score command
     */
    public static Command intakeCommand()
    {
        if(pivot != null && roller != null)
        {
            return viewController.setGradientCommand(Color.kYellow, Color.kRed)
                    .andThen(roller.forwardCommand());
        } else
            return Commands.none();
    }

    /**
     * Turn off rollers and reset LEDs to default
     * 
     * @author Jackson D.
     * @return Intake stop command
     */
    public static Command stopIntakeCommand()
    {
        if(roller != null)
        {
            return Commands.parallel(
                    viewController.setSolidCommand(Color.kRed),
                    roller.stopCommand());
        } else
            return Commands.none();
    }

    public static Command extendIntakeCommand()
    {
        if(pivot != null)
        {
            return pivot.extendCommand();
        }
        else
        {
            return Commands.none();
        }
    }

    public static Command retractIntakeCommand()
    {
        if(pivot != null)
        {
            return pivot.retractCommand();
        }
        else
        {
            return Commands.none();
        }
    }
}
