package frc.robot.tests;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;

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
    // private final Drivetrain drivetrain;
    private final Shroud shroud;
    private final Pivot pivot;
    private final Roller roller;
    // private final Joystick joystick = new Joystick(0);
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
        // drivetrain = robotContainer.getDrivetrain();
        shroud = robotContainer.getShroud();
        pivot = robotContainer.getPivot();
        roller = robotContainer.getRoller();

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
        Trigger aButton = controller.a();
        Trigger bButton = controller.b();
        // controller.a().whileTrue(drivetrain.lockWheelsCommand());
        // controller.a().onTrue(pivot.extendCommand());
        // controller.b().onTrue(pivot.retractCommand());
        // controller.x().onTrue(pivot.stopCommand());
        // aButton
            // .whileTrue(roller.basicSetCommand(0.1))
            // .onFalse(roller.basicSetCommand(0.0));
            // .whileTrue(pivot.setCommand(0.05))
            // .onFalse(pivot.setCommand(0.0));
        // bButton
        //     .whileTrue(pivot.setCommand(-0.05))
        //     .onFalse(pivot.setCommand(0.0));

        // drivetrain.setDefaultCommand(drivetrain.driveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> joystick.getRawAxis(4), () -> 0.5));
    }

    /**
     * This method runs periodically (every 20ms).
     */
    public void periodic()
    {
        // roller.intakeFuelCommand();
        // System.out.println(shroud.getLimitSwitchState().getAsBoolean());
        // CommandSwerveDrivetrain.driveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> joystick.getRawAxis(4), () -> 0.5).schedule();
        // drivetrain.pointWheelsCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0)).schedule();
        // drivetrain.angleLockDriveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), () -> 0.5, () -> 90).schedule();
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
