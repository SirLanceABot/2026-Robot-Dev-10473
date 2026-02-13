package frc.robot.controls;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.PoseEstimator;

public final class DriverBindings
{
    // This string gets the full name of the class, including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded
    static
    {
        System.out.println("Loading: " + fullClassName);
    }

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here
    private static CommandXboxController controller;

    private static Drivetrain drivetrain;
    private static Flywheel flywheel;
    private static LEDs leds;
    private static PoseEstimator poseEstimator;

    private static DoubleSupplier leftYAxis;
    private static DoubleSupplier leftXAxis;
    private static DoubleSupplier rightXAxis;
    private static DoubleSupplier scaleFactorSupplier;

    private static double scaleFactor = 0.5;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Creates button bindings for the operator
     * @param robotContainer
     * @author Mukul Kedia
     */

    public static void createBindings(RobotContainer robotContainer)
    {
        System.out.println("Creating Operator Bindings: " + fullClassName);

        controller = robotContainer.getDriverController();
        drivetrain = robotContainer.getDrivetrain();
        flywheel = robotContainer.getFlywheel();
        leds = robotContainer.getLEDs();
        poseEstimator = robotContainer.getPoseEstimator();

        if(controller != null)
        {
            configSuppliers();
            configDefaultCommands();
        }
    }

    private static void configSuppliers()
    {
        leftYAxis = () -> -controller.getRawAxis(1);
        leftXAxis = () -> -controller.getRawAxis(0);
        rightXAxis = () -> -controller.getRawAxis(4);
        scaleFactorSupplier = () -> scaleFactor;
    }

    private static void configDefaultCommands()
    {
        if(drivetrain != null)
        {
            drivetrain.setDefaultCommand(drivetrain.driveCommand(leftYAxis, leftXAxis, rightXAxis, scaleFactorSupplier));     
        }
        System.out.println("Config Default Commands Driver Controller");
    }
}