package frc.robot.tests;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Pivot;

@SuppressWarnings("unused")
public class JacksonDTest implements Test
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
    private final Flywheel flywheel;
    private final Pivot pivot;
    private final CommandXboxController controller = new CommandXboxController(0);


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Use this class to test your code using Test mode
     * <p>Modify the {@link frc.robot.TestMode} class to run your test code
     * @param robotContainer The container of all robot components
     */
    public JacksonDTest(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        this.robotContainer = robotContainer;
        
        flywheel = robotContainer.getFlywheel();
        pivot = robotContainer.getPivot();

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

        // controller.a().onTrue(flywheel.shootCommand(() -> 2.0));
        // controller.b().onTrue(flywheel.stopCommand());

        controller.a().onTrue(pivot.extendCommand());
        controller.b().onTrue(pivot.retractCommand());

        controller.x().onTrue(pivot.stopCommand());
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {
        // if(flywheel.getVelocity() > 0)
            // System.out.println(flywheel.getVelocity());

        // if(flywheel.isAtSetSpeed(2, 0.1).getAsBoolean())
            // System.out.println("At speed");

        
        if(pivot.getPosition() > 0.0)
            System.out.println(pivot.getPosition());

        // System.out.println(pivot.isAtSetPosition(10.0).getAsBoolean());
        // System.out.println(pivot.isAtSetPosition(0.0).getAsBoolean());


    }
    
    /**
     * This method runs one time after the periodic() method.
     */
    public void exit()
    {} 
}
