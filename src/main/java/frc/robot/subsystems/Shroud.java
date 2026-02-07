package frc.robot.subsystems;

import static frc.robot.Constants.Shroud.*;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

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

    private final TalonFXLance angleMotor = new TalonFXLance(MOTOR, MOTOR_CAN_BUS, "Shroud Angle Motor");

    // TODO: Tune later.
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
     * This method resets the motor to factory defaults.
     */
    private void configMotors()
    {
        angleMotor.setupFactoryDefaults();
        angleMotor.setPosition(0.0);
        
        angleMotor.setupPIDController(0, kP, kI, kD);
    }

    /**
     * Helper method to convert degrees to the encoder position.
     * @param degrees The degrees to convert into position.
     */
    private double degreesToPosition(double degrees)
    {
        // TODO: Tune later.
        return degrees;
    }

    /**
     * This method moves the shroud to the specified degrees.
     * @param degrees The degrees the shroud should be set to.
     */
    private void goTo(double degrees)
    {
        angleMotor.setControlPosition(degreesToPosition(degrees));
    }

    /**
     * Returns the appropriate angle to shoot from a given distance.
     * PLACEHOLDER VALUE (always 45 degrees) (!!!!!!!!!!)
     * @param distance Distance from target
     * @return Shot angle
     * @author Jackson D.
     */
    public double getShotAngle(double distance)
    {
        return 45.0;
    }

    /**
     * This command moves the shroud to the specified degrees.
     * @param degrees The degrees the shroud should be set to.
     */
    public Command goToCommand(double degrees)
    {
        return runOnce(() -> goTo(degrees));
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
