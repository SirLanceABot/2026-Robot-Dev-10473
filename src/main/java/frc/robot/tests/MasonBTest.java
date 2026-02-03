package frc.robot.tests;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Roller;

@SuppressWarnings("unused")
public class MasonBTest implements Test
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
    private final Drivetrain drivetrain;
    // private final Roller roller;
    private final Joystick joystick = new Joystick(0);
    private final CommandXboxController controller = new CommandXboxController(0);


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here


    /**
     * Use this class to test your code using Test mode
     * <p>Modify the {@link frc.robot.TestMode} class to run your test code
     * @param robotContainer The container of all robot components
     */
    public MasonBTest(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        this.robotContainer = robotContainer;
        drivetrain = robotContainer.getDrivetrain();
        // roller = robotContainer.getRoller();

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
        // controller.a().whileTrue(drivetrain.lockWheelsCommand());
        // drivetrain.setDefaultCommand(drivetrain.driveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> joystick.getRawAxis(4), () -> 0.5));
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {
        // drivetrain.driveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> joystick.getRawAxis(4), () -> 0.5).schedule();
        // drivetrain.pointWheelsCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0)).schedule();
        drivetrain.angleLockDriveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> 0.5, () -> 90).schedule();
        // System.out.println(-joystick.getRawAxis(1));
        // System.out.println(-joystick.getRawAxis(0));
        // controller.a().onTrue(roller.intakeFuelCommand());
        // controller.b().onTrue(roller.reverseCommand());
    }
    
    /**
     * This method runs one time after the periodic() method.
     */
    public void exit()
    {} 
}
