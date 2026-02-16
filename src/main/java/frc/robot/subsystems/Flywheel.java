package frc.robot.subsystems;

import static frc.robot.Constants.Flywheel.*;

import java.lang.invoke.MethodHandles;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.TalonFXLance;


/**
 * Shooter subsystem
 * @author Jackson D.
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

    private final double TOLERANCE = 0.3;

    private InterpolatingDoubleTreeMap distanceToSpeedMap = new InterpolatingDoubleTreeMap();

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
        configShotMap();

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

    private void configShotMap()
    {
        //for distance in meters
        //TODO: Tune these values with the real bot
        distanceToSpeedMap.put(1.0, 10473.0);
        distanceToSpeedMap.put(2.0, 10473.0);
        distanceToSpeedMap.put(3.0, 10473.0);
        distanceToSpeedMap.put(4.0, 10473.0);
        distanceToSpeedMap.put(5.0, 10473.0);
        distanceToSpeedMap.put(6.0, 10473.0);
        distanceToSpeedMap.put(7.0, 10473.0);
        distanceToSpeedMap.put(8.0, 10473.0);
        distanceToSpeedMap.put(9.0, 10473.0);
        distanceToSpeedMap.put(10.0, 10473.0);
        distanceToSpeedMap.put(11.0, 10473.0);
        distanceToSpeedMap.put(12.0, 10473.0);
        distanceToSpeedMap.put(13.0, 10473.0);
        distanceToSpeedMap.put(14.0, 10473.0);
        distanceToSpeedMap.put(15.0, 10473.0);
        distanceToSpeedMap.put(16.0, 10473.0);
        distanceToSpeedMap.put(17.0, 10473.0);
        distanceToSpeedMap.put(18.0, 10473.0);
        distanceToSpeedMap.put(19.0, 10473.0);
        distanceToSpeedMap.put(20.0, 10473.0);
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

    /**
     * Get the velocity of the flywheel, in RPS
     * @return The current shooter velocity
     */
    public double getVelocity()
    {
        return leadMotor.getVelocity();
    }

    /**
     * @param targetSpeed
     * @return Shooter is at target speed (RPS)
     */
    public BooleanSupplier isAtSetSpeed(double targetSpeed)
    {
        double currentSpeed = getVelocity();
        return () ->
        {
            if((currentSpeed + TOLERANCE > targetSpeed) && (currentSpeed - TOLERANCE < targetSpeed))
                return true;
            else
                return false;
        };
    }

    /**
     * Returns the appropriate shot speed for the given distance (meters).
     * @param distance Distance from target
     * @return Shot speed
     */
    public double getShotSpeed(double distance)
    {
        distance = Math.max(1.0, Math.min(20.0, distance));
        return distanceToSpeedMap.get(distance);
    }

    /**
     * Stops the flywheel
     * @return Stop command
     */
    public Command stopCommand()
    {
        return runOnce(() -> stop());
    }

    /**
     * Shoot the flywheel at a given speed (RPS)
     * @param speed
     * @return Shoot command
     */
    public Command shootCommand(DoubleSupplier speed)
    {
        return runOnce(() -> shoot(speed.getAsDouble()));
    }

    /**
     * Shoot the flywheel at the appropriate speed for the given distance
     * @param distance Distance in feet
     * @return Shoot from distance command
     */
    public Command shootFromDistanceCommand(double distance)
    {
        return runOnce(() -> shoot(getShotSpeed(distance)));
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
