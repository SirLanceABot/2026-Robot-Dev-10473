package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Drivetrain;
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

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Extend pivot, then score
     * @author Jackson D.
     * @return Simple score command
     */
    public static Command simpleIntakeCommand()
    {
        if (leds != null && pivot != null && roller != null)
        {
            return leds.setColorCommand(Color.kRed)
                .andThen(pivot.extendCommand())
                .andThen(roller.intakeFuelCommand());
        }
        else
        {
            return Commands.none();
        }
    }

    /**
     * Retracts the pivot and turns off the roller
     * @author Jackson D.
     * @return Simple intake stop command
     */
    public static Command simpleIntakeStopCommand()
    {
        if (leds != null && pivot != null && roller != null)
        {
            return leds.setColorCommand(LEDs.RUNNING_COLOR)
                .andThen(pivot.retractCommand())
                .andThen(roller.stopCommand());
        }
        else
        {
            return Commands.none();
        }
    }
    
}
