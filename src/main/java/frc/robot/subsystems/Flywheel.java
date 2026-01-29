package frc.robot.subsystems;

import static frc.robot.Constants.Flywheel.*;

import java.lang.invoke.MethodHandles;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;


/**
 * Shooter subsystem
 */
public class Flywheel extends SubsystemBase
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
    private final TalonFXLance leadMotor = new TalonFXLance(LEADMOTOR, MOTOR_CAN_BUS, "Flywheel Lead Motor");
    private final TalonFXLance followMotor = new TalonFXLance(FOLLOWMOTOR, MOTOR_CAN_BUS, "Flywheel Follow Motor");

    // PID constants
    private final double kP = 0.401;
    private final double kI = 0.0;
    private final double kD = 0.0;
    private final double kS = 0.0;
    private final double kV = 0.2;
    private final double kA = 0.0;


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /** 
     * Creates a new flywheel. 
     */
    public Flywheel()
    {
        super("Flywheel");
        System.out.println("  Constructor Started:  " + fullClassName);

        configMotors();

        System.out.println("  Constructor Finished: " + fullClassName);
    }


    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    private void configMotors()
    {
        leadMotor.setupFactoryDefaults();
        followMotor.setupFactoryDefaults();

        leadMotor.setSafetyEnabled(true);
        followMotor.setSafetyEnabled(true);

        leadMotor.setupCoastMode();
        followMotor.setupCoastMode();

        leadMotor.setupPIDController(0, kP, kI, kD, kS, kV, kA);

        followMotor.setupFollower(LEADMOTOR, false);
    }

    /**
     * This sets the speed of the motors.
     * @param speed The motor speed (-1.0 to 1.0)
     */
    private void set(double speed)
    {
        leadMotor.set(speed);
    }

    private void stop()
    {
        set(0.0);
    }

    private void shoot(double speed)
    {
        leadMotor.setControlVelocity(speed);
    }

    public double getVelocity()
    {
        return leadMotor.getVelocity();
    }

    public BooleanSupplier isAtSetSpeed(double targetSpeed, double tolerance)
    {
        double currentSpeed = getVelocity();
        return () ->
        {
            if((currentSpeed + tolerance > targetSpeed) && (currentSpeed - tolerance < targetSpeed))
                return true;
            else
                return false;
        };
    }

    public Command stopCommand()
    {
        return run( () -> stop() );
    }

    public Command shootCommand(DoubleSupplier speed)
    {
        return run( () -> shoot(speed.getAsDouble()) );
    }

    public Command onCommand()
    {
        return run( () -> leadMotor.set(0.1));
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
