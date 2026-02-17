package frc.robot.controls;

import java.lang.invoke.MethodHandles;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.commands.GeneralCommands;
import frc.robot.sensors.Camera;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.PoseEstimator;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;

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

    private static Flywheel flywheel;
    private static Agitator agitator;
    private static Roller roller;
    private static Pivot pivot;
    private static Shroud shroud;
    private static Drivetrain drivetrain;
    private static LEDs leds;

    private static Camera camera;
    private static PoseEstimator poseEstimator;

    private static final double CRAWL_SPEED = 0.225;
    private static final double WALK_SPEED = 0.675;
    private static final double RUN_SPEED = 1.0;
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
     * @author Jackson D.
     */

    public static void createBindings(RobotContainer robotContainer)
    {
        System.out.println("Creating Driver Bindings: " + fullClassName);

        controller = robotContainer.getDriverController();

        drivetrain = robotContainer.getDrivetrain();
        flywheel = robotContainer.getFlywheel();
        agitator = robotContainer.getAgitator();
        pivot = robotContainer.getPivot();
        shroud = robotContainer.getShroud();
        leds = robotContainer.getLEDs();

        camera = robotContainer.getCamera();
        poseEstimator = robotContainer.getPoseEstimator();

        if(controller != null)
        {
            configSuppliers();
            configDefaultCommands();

            configAButton();
            configBButton();
            configXButton();
            configYButton();
            configLeftBumper();
            configRightBumper();
            configBackButton();
            configStartButton();
            configLeftTrigger();
            configRightTrigger();
            configLeftStick();
            configRightStick();
            configDpadUp();
            configDpadDown(); 
            configDpadLeft();
            configDpadRight();
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

    private static void configAButton()
    {
        Trigger aButton = controller.a();
        aButton
            .whileTrue(drivetrain.angleLockDriveCommand(leftYAxis, leftXAxis, scaleFactorSupplier, () -> (poseEstimator.getAngleToTarget(poseEstimator.getEstimatedPose(), poseEstimator.getAllianceHubPose()).getAsDouble())));
    }

    private static void configBButton()
    {
        Trigger bButton = controller.b();
    }

    private static void configXButton()
    {
        Trigger xButton = controller.x();
    }

    private static void configYButton()
    {
        Trigger yButton = controller.y();
    }

    private static void configLeftBumper()
    {
        Trigger leftBumper = controller.leftBumper();
        leftBumper
            .onTrue(Commands.runOnce(() -> scaleFactor = (scaleFactor > (CRAWL_SPEED + WALK_SPEED) / 2.0) ? CRAWL_SPEED : WALK_SPEED));
    }

    private static void configRightBumper()
    {
        Trigger rightBumper = controller.rightBumper();
    }

    private static void configBackButton()
    {
        Trigger backButton = controller.back();
    }

    private static void configStartButton()
    {
        Trigger startButton = controller.start();
        startButton
        .onTrue(Commands.runOnce(() -> drivetrain.resetForFieldCentric(), drivetrain));
    }

    private static void configLeftTrigger()
    {
        Trigger leftTrigger = controller.leftTrigger();
        leftTrigger
            .onTrue(Commands.runOnce(() -> scaleFactor = (scaleFactor > (WALK_SPEED + RUN_SPEED) / 2.0) ? WALK_SPEED : RUN_SPEED));
    }

    private static void configRightTrigger()
    {
        Trigger rightTrigger = controller.rightTrigger();
    }

    private static void configLeftStick()
    {
        Trigger leftStick = controller.leftStick();
    }

    private static void configRightStick()
    {
        Trigger rightStick = controller.rightStick();
    }

    private static void configDpadUp()
    {
        Trigger dpadUp = controller.povUp();
    }

    private static void configDpadDown()
    {
        Trigger dpadDown = controller.povDown();
        dpadDown
            .onTrue(GeneralCommands.stopAllCommand());
    }

    private static void configDpadLeft()
    {
        Trigger dpadLeft = controller.povLeft();
    }

    private static void configDpadRight()
    {
        Trigger dpadRight = controller.povRight();
    }

}