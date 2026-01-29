package frc.robot.tests;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shroud;

@SuppressWarnings("unused")
public class MukulKTest implements Test
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

    // *** CLASS & INSTANCE VARIABLES ***
    // Put all class and instance variables here.
    private final RobotContainer robotContainer;
    private final Shroud shroud;
    private final CommandXboxController controller = new CommandXboxController(0);

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Use this class to test your code using Test mode
     * <p>Modify the {@link frc.robot.TestMode} class to run your test code
     * @param robotContainer The container of all robot components
     */
    public MukulKTest(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        this.robotContainer = robotContainer;
        this.shroud = robotContainer.getShroud();

        System.out.println("  Constructor Finished: " + fullClassName);
    }


    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    // *** OVERRIDDEN METHODS ***
    // Put all methods that are Overridden here

    /**
     * This method runs one time before the periodic() method.
     */
    public void init()
    {
        controller.a().onTrue(this.shroud.goToCommand(12.5)); // Should be at lowest position.
        controller.b().onTrue(this.shroud.goToCommand(38.0)); // Should be at 1/3 position.
        controller.x().onTrue(this.shroud.goToCommand(76.0)); // Should be at 2/3 position.
        controller.y().onTrue(this.shroud.goToCommand(90.0)); // Should be straight up.

        System.out.println("A button moves shroud to lowest position");
        System.out.println("B button moves shroud to 1/3 position");
        System.out.println("X button moves shroud to 2/3 position");
        System.out.println("Y button moves shroud to be straight up");
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {
        System.out.println("Position: " + this.shroud.getPosition());
    }
    
    /**
     * This method runs one time after the periodic() method.
     */
    public void exit()
    {} 
}
