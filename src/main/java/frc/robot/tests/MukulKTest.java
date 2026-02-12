package frc.robot.tests;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LEDs;
// import frc.robot.subsystems.Shroud;

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
    private final CommandXboxController controller = new CommandXboxController(0);
    private final LEDs leds;
    // private final Shroud shroud;

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
        leds = robotContainer.getLEDs();
        // shroud = robotContainer.getShroud();

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
        LEDs.LEDView leftView = leds.createView(0, 99);
        LEDs.LEDView rightView = leds.createView(100, 199);
        controller.a().onTrue(leftView.setSolidCommand(Color.kWhite));
        controller.b().onTrue(rightView.setRainbowCommand());
        controller.x().onTrue(leftView.setGradientCommand(Color.kBlack, Color.kWhite));
        controller.y().onTrue(rightView.setGradientCommand(Color.kRed, Color.kBlue));

        // controller.a().onTrue(shroud.goToCommand(12.5));
        // controller.b().onTrue(shroud.goToCommand(38.0));
        // controller.x().onTrue(shroud.goToCommand(76.0));
        // controller.y().onTrue(shroud.goToCommand(90.0));
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {}
    
    /**
     * This method runs one time after the periodic() method.
     */
    public void exit()
    {}
}
