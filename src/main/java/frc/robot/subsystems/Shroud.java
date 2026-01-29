package frc.robot.subsystems;

import static frc.robot.Constants.Shroud.*;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
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

    private final TalonFXLance angle_motor = new TalonFXLance(MOTOR, MOTOR_CAN_BUS, "angleMotor");

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

    /**
     * This method resets the motor to factory defaults.
     */
    private void configMotors()
    {
        angle_motor.setupFactoryDefaults();
        angle_motor.setPosition(0.0);
        // TODO: Set PID (Check A bot's Climb Code for example).
    }

    /**
     * Helper method to convert degrees to the encoder position.
     * @param degrees
     */
    private double degreesToPosition(double degrees)
    {
        // TODO: Implement
        return 0.0;
    }

    /**
     * This method moves the shroud to the specified degrees.
     * @param degrees The degrees the shroud should be set to
     */
    private void goTo(double degrees)
    {
        angle_motor.setControlPosition(degreesToPosition(degrees));
    }

    /**
     * This command moves the shroud to the specified degrees.
     * @param degrees The degrees the shroud should be set to (clamped between 12.5 and 90.0 degrees)
     */
    public Command goToCommand(DoubleSupplier degrees)
    {
        return run(() -> goTo(MathUtil.clamp(degrees.getAsDouble(), 12.5, 90.0))); // Shround's lowest possible position is ~12.5 degrees.
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
