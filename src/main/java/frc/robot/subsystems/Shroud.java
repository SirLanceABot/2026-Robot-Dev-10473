package frc.robot.subsystems;

import static frc.robot.Constants.Shroud.*;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;

/**
 * Class controling the angle of the shroud and shooter
 * 
 * @author Mukul Kedia
 */
public class Shroud extends SubsystemBase
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

    private final TalonFXLance angle_motor = new TalonFXLance(ANGLE_MOTOR, MOTOR_CAN_BUS, "angleMotor");

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Creates a new Shroud.
     */
    public Shroud()
    {
        super("Shroud");
        System.out.println("  Constructor Started:  " + fullClassName);

        configMotors();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    private void configMotors()
    {
        angle_motor.setupFactoryDefaults();
    }

    /**
     * This method starts the motor.
     */
    private void start()
    {
        angle_motor.set(ANGLE_MOTOR_SPEED);
    }

    /**
     * This method stops the motor.
     */
    private void stop()
    {
        angle_motor.set(0.0);
    }

    /*
     * This command starts the motor.
     */
    public Command startCommand()
    {
        return runOnce(this::start);
    }

    /*
     * This command stops the motor.
     */
    public Command stopCommand()
    {
        return runOnce(this::stop);
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
