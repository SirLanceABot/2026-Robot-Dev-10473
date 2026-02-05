package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import javax.lang.model.util.ElementScanner14;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;

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

  // *** CLASS VARIABLES & INSTANCE VARIABLES ***
  // Put all class variables and instance variables here
  private static Roller roller;
  private static Pivot pivot;
  private static Agitator agitator;
  private static Flywheel flywheel;
  private static Shroud shroud;
  private static Drivetrain drivetrain;


    public static void createCommands(RobotContainer robotContainer)
    {        
        System.out.println("  Constructor Started:  " + fullClassName);

        roller = robotContainer.getRoller();
        pivot = robotContainer.getPivot();
        agitator = robotContainer.getAgitator();
        flywheel = robotContainer.getFlywheel();
        shroud = robotContainer.getShroud();
        drivetrain = robotContainer.getDrivetrain();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    /**
     * Set shroud to 45 degrees, get flywheel to a speed of 15 RPS, and activates the agitator
     * @author Jackson D.
     * @return Simple score command
     */
    public static Command simpleScoreCommand()
    {
        if((agitator != null) && (flywheel != null) && (shroud != null))
        {
            return Commands.parallel(
                shroud.goToCommand(45),
                flywheel.shootCommand(() -> 15).until(flywheel.isAtSetSpeed(15))     //rps
            )
                .andThen(agitator.forwardCommand());
        }
        else 
        {
            return Commands.none();
        }
    }

    /**
     * Set the shroud to 0 degrees, stops the flywheel, and stops the agitator
     * @author Jackson D.
     * @return Simple intake stop command
     */
    public static Command simpleScoreStopCommand()
    {
        if((agitator != null) && (flywheel != null) && (shroud != null))
        {
            return Commands.parallel(
                shroud.goToCommand(0),
                flywheel.stopCommand()      
            )
                .andThen(agitator.stopCommand());
        }
        else 
        {
            return Commands.none();
        }
    }
    
}

