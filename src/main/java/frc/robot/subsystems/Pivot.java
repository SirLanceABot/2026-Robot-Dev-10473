package frc.robot.subsystems;

import static frc.robot.Constants.Pivot.*;

import java.lang.invoke.MethodHandles;
// import java.util.Currency;               //VERY IMPORTANT DO NOT REMOVE
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;

/**
 * Intake pivot arm subsystem
 * @author Jackson D.
 */
public class Pivot extends SubsystemBase
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
    private final TalonFXLance leadMotor = new TalonFXLance(LEADMOTOR, MOTOR_CAN_BUS, "Pivot Lead Motor");
    private final TalonFXLance followMotor = new TalonFXLance(FOLLOWMOTOR, MOTOR_CAN_BUS, "Pivot Follow Motor");

    private static final double RETRACTED = 0.0;
    private static final double EXTENDED = 10.0;
    private static final double TOLERANCE = 0.2;

    private static final double kP = 3.5;
    private static final double kI = 0.0;
    private static final double kD = 0.0;


    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /** 
     * Creates a new Pivot. 
     */
    public Pivot()
    {
        super("Pivot");
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

        leadMotor.setupBrakeMode();
        followMotor.setupBrakeMode();

        leadMotor.setPosition(0.0);
        followMotor.setPosition(0.0);

        leadMotor.setupPIDController(0, kP, kI, kD);

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

    public void stop()
    {
        set(0.0);
    }

    /**
     * Returns pivot position
     * @return Encoder position in rotations
     */
    public double getPosition()
    {
        return leadMotor.getPosition();
    }

    // public BooleanSupplier isAtSetPosition(double targetPosition)
    // {
    //     double currentPosition = getPosition();

    //     return () ->
    //     {
    //         if((currentPosition + TOLERANCE > targetPosition) && (currentPosition - TOLERANCE < targetPosition))
    //             return true;
    //         else
    //             return false;
    //     };
    // }

    // public BooleanSupplier isAtSetPosition(double targetPosition)
    // {
    //     return () -> 
    //     {
    //         if(MathUtil.isNear(targetPosition, getPosition(), TOLERANCE))
    //             return true;
    //         else
    //             return false;
    //     };
    // }

    /**
     * @return Pivot is retracted
     */
    public BooleanSupplier isRetracted()
    {
        return () -> (getPosition() - TOLERANCE) < RETRACTED;
    } 

    /**
     * @return Pivot is extended
     */
    public BooleanSupplier isExtended()
    {
        return () -> (getPosition() + TOLERANCE) > EXTENDED;
    }

    private void retract()
    {
        leadMotor.setControlPosition(RETRACTED);
    }

    private void extend()
    {
        leadMotor.setControlPosition(EXTENDED);
    }

    /**
     * Retract the pivot arm
     * @return Retract command
     */
    public Command retractCommand()
    {
        // return run(() -> retract()).until(() -> isAtSetPosition(RETRACTED).getAsBoolean())
        //         .andThen(stopCommand());
        return run(() -> retract()).until(isRetracted())
                .andThen(stopCommand());
    }

    /**
     * Extend the pivot arm
     * @return Extend command
     */
    public Command extendCommand()
    {
        // return run(() -> extend()).until(() -> isAtSetPosition(EXTENDED).getAsBoolean())
        //         .andThen(stopCommand());
        return run(() -> extend()).until(isExtended())
                .andThen(stopCommand());
    }

    public Command stopCommand()
    {
        return runOnce(() -> stop());
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
