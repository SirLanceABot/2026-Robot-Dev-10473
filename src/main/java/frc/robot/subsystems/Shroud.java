package frc.robot.subsystems;

import static frc.robot.Constants.Shroud.*;

import java.lang.invoke.MethodHandles;

import javax.lang.model.util.ElementScanner14;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;

/**
 * Class controling the angle of the shroud 
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

    private final TalonFXLance angleMotor = new TalonFXLance(MOTOR, MOTOR_CAN_BUS, "Shroud Angle Motor");

    // TODO: Tune later
    private static final double kP = 3.5;
    private static final double kI = 0.0;
    private static final double kD = 0.0;

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
     * Resets the motor to factory defaults
     */
    private void configMotors()
    {
        angleMotor.setupFactoryDefaults();
        angleMotor.setPosition(0.0);
        
        angleMotor.setupPIDController(0, kP, kI, kD);
    }

    /**
     * Helper to convert degrees to the encoder position
     * @param degrees {@link Double} The degrees
     * @return {@link Double} The encoder position
     * @implNote PLACEHOLDER (this method returns the value it recieves) (!!!!!!!!!!)
     */
    private double degreesToPosition(double degrees)
    {
        // TODO: Tune later
        return degrees;
    }

    /**
     * Moves the shroud to the specified degrees
     * @param degrees {@link Double} The degrees the shroud should be set to
     */
    private void goTo(double degrees)
    {
        angleMotor.setControlPosition(degreesToPosition(degrees));
    }

    /**
     * Moves the shroud to the specified degrees
     * @param degrees {@link Double} The degrees the shroud should be set to
     * @return {@link Command} The command to move the shroud
     */
    public Command goToCommand(double degrees)
    {
        return runOnce(() -> goTo(degrees));
    }

    /**
     * Moves the shroud to the appropriate angle for the distance in meters
     * @param distance {@link Double} The degree
     * @return {@link Command} Distance to angle command
     */
    public Command distanceToAngleCommand(double distance)
    {
        return runOnce(() -> goTo(getShotAngle(distance)));
    }

    /**
     * Returns the appropriate angle to shoot from a given distance.
     * @param distance {@link Double} The distance from the target in meters
     * @return {@link Double} The shot angle
     * @author Jackson D.
     * @implNote PLACEHOLDER VALUES (!!!!!!!!!!)
     */
    public double getShotAngle(double distance)
    {
        distance = Math.max(1.0, Math.min(20.0, distance));
        // TODO: Tune later
        if(distance < 5.0)
            return degreesToPosition(75);
        else if(distance < 10.0)
            return degreesToPosition(65);
        else
            return degreesToPosition(55);
    }

    /**
     * Stops the shroud from moving
     * @return {@link Command} The command to stop the shroud
     */
    public Command stopCommand()
    {
        return runOnce(() -> angleMotor.set(0.0));
    }

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    @Override
    public String toString()
    {
        return "";
    }
}
