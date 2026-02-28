package frc.robot.tests;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.LEDs;
import frc.robot.RobotContainer;

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

    // *** CLASS & INSTANCE VARIABLES ***
    // Put all class and instance variables here.

    private final RobotContainer robotContainer;
    private final CommandXboxController controller = new CommandXboxController(0);

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Use this class to test your code using Test mode
     * <p>
     * Modify the {@link frc.robot.TestMode} class to run your test code
     * 
     * @param robotContainer The container of all robot components
     */
    public MukulKTest(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        this.robotContainer = robotContainer;

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * This method runs one time before the periodic() method.
     */
    public void init()
    {
        LEDs.init();

        var leftView = LEDs.createView(0, 99);
        var rightView = LEDs.createView(100, 199);
        controller.a().onTrue(leftView.setSolidCommand(Color.kWhite));
        controller.b().onTrue(rightView.setRainbowCommand());
        controller.x().onTrue(leftView.setBlinkCommand(0.5));
        controller.y().onTrue(rightView.setBreatheCommand(0.5));
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {
        LEDs.periodic();
    }

    /**
     * This method runs one time after the periodic() method.
     */
    public void exit()
    {
        LEDs.exit();
    }
}
