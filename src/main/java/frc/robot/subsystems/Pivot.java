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
    private static final double SHOOT = 4.09;
    private static final double AGITATE = 6.08;
    private static final double EXTENDED = 9.97;

    private static final double TOLERANCE = 0.2;

    //PID slots for moving forward/backwards
    private static final int FORWARD = 0;
    private static final int REVERSE = 1;

    private static final double kP = 0.45;
    private static final double kI = 0.0;
    private static final double kD = 0.0;

    //TODO: Tune kF values
    private static final double kFForward = 0.0;
    private static final double kFReverse = 0.5;



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

        leadMotor.setSafetyEnabled(false);
        followMotor.setSafetyEnabled(false);

        //Two PID controllers to account for the effect of gravity when moving inward
        leadMotor.setupPIDController(FORWARD, kP, kI, kD, kFForward);
        leadMotor.setupPIDController(REVERSE, kP, kI, kD, kFReverse);

        followMotor.setupFollower(LEADMOTOR, false);

        leadMotor.setupForwardHardLimitSwitch(true, true);
        leadMotor.setupReverseHardLimitSwitch(true, true);

        leadMotor.setupForwardSoftLimit(9.5, true);
        leadMotor.setupReverseSoftLimit(0.2, true);
    }

    public void stop()
    {
        leadMotor.setVoltage(0.0);
    }

    /**
     * Returns pivot position
     * @return Encoder position in rotations
     */
    public double getPosition()
    {
        return leadMotor.getPosition();
    }

    /**
     * @return Pivot is retracted
     */
    public BooleanSupplier isRetracted()
    {
        return () -> (getPosition() - TOLERANCE) < RETRACTED;
    } 

    /**
     * @return Pivot is at shooting position
     */
    public BooleanSupplier isAtShootPosition()
    {
        return () -> ((getPosition() + TOLERANCE) > SHOOT) && ((getPosition() - TOLERANCE) < SHOOT);
    }

    /**
     * @return Pivot is at agitating position
     */
    public BooleanSupplier isAtAgitatePosition()
    {
        return () -> ((getPosition() + TOLERANCE) > AGITATE) && ((getPosition() - TOLERANCE) < AGITATE);
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
        //always use reverse PID when retracting
        leadMotor.setControlPosition(RETRACTED, REVERSE);
    }

    private void shootPosition(int slot)
    {
        leadMotor.setControlPosition(SHOOT, slot);
    }

    private void agitatePosition(int slot)
    {
        leadMotor.setControlPosition(AGITATE, slot);
    }

    private void extend()
    {
        leadMotor.setControlPosition(EXTENDED);
    }

    private int getPIDSlot(double targetPosition)
    {
        //If the encoder position is past the target position,
        //then use the reverse PID
        if(targetPosition > getPosition())
            return REVERSE;
        else 
            return FORWARD;
    }

    /**
     * Retract the pivot arm
     */
    public Command retractCommand()
    {
        return run(() -> retract()).until(isRetracted())
                .andThen(stopCommand());
    }

    /**
     * Set pivot arm to the shooting position
     */
    public Command shootPositionCommand()
    {
        return run(() -> shootPosition(getPIDSlot(SHOOT))).until(isAtShootPosition())
                .andThen(stopCommand());
    }

     /**
     * Set pivot arm to the agitating position
     */
    public Command agitatePositionCommand()
    {
        return run(() -> agitatePosition(getPIDSlot(AGITATE))).until(isAtAgitatePosition())
                .andThen(stopCommand());
    }

    /**
     * Extend the pivot arm
     */
    public Command extendCommand()
    {
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
