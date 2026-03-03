package frc.robot.subsystems;

import static frc.robot.Constants.Roller.*;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;

/**
 * This is an example of what a subsystem should look like.
 */
public class Roller extends SubsystemBase
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
    private final TalonFXLance motor = new TalonFXLance(MOTOR, MOTOR_CAN_BUS, "Roller Motor");

    private final double kP = 0.0;
    private final double kI = 0.0;
    private final double kD = 0.0;
    private final double kS = 0.0;
    private final double kV = 0.0;
    private final double kA = 0.0;


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /** 
     * Creates a Roller
     */
    public Roller()
    {
        super("Roller");
        System.out.println("  Constructor Started:  " + fullClassName);

        configMotors();

        System.out.println("  Constructor Finished: " + fullClassName);
    }


    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    private void configMotors()
    {
        motor.setupFactoryDefaults();

        motor.setupCoastMode();

        motor.setSafetyEnabled(false);

        motor.setupPIDController(0, kP, kI, kD, kS, kV, kA);
    }

    public void stop()
    {
        motor.setControlVelocity(MOTOR);;
    }

    //only used for testing
    public Command basicSetCommand(double speed)
    {
        return run( () -> motor.set(speed));
    }

    public Command stopCommand()
    {
        return runOnce( () -> stop() );
    }

    public Command intakeFuelCommand()
    {
        //TODO: fine-tune these values
        return runOnce(() -> motor.set(0.5));
    }

    public Command reverseCommand()
    {
        //TODO: fine-tune
        return runOnce(() -> motor.setControlVelocity(-10));
    }


    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    @Override
    public void periodic()
    {
        // This method will be called once per scheduler run
        // Use this for sensors that need to be read periodically.
        // Use this for data that needs to be logged.
    }

    @Override
    public String toString()
    {
        return "";
    }
}
