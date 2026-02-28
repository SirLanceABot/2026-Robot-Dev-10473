package frc.robot.controls;

import java.lang.invoke.MethodHandles;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.commands.GeneralCommands;
import frc.robot.commands.IntakingCommands;
import frc.robot.commands.ScoringCommands;
import frc.robot.sensors.Camera;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.PoseEstimator;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Shroud;

public final class OperatorBindings 
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
    private static CommandSwerveDrivetrain drivetrain;

    private static Camera camera;
    private static PoseEstimator poseEstimator;

    private static DoubleSupplier leftYAxis;
    private static DoubleSupplier leftXAxis;
    private static DoubleSupplier rightXAxis;
    private static DoubleSupplier rightYAxis;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Creates button bindings for the operator
     * @param robotContainer
     * @author Jackson D.
     */

    public static void createBindings(RobotContainer robotContainer)
    {
        System.out.println("Creating Operator Bindings: " + fullClassName);

        controller = robotContainer.getOperatorController();

        drivetrain = robotContainer.getDrivetrain();
        flywheel = robotContainer.getFlywheel();
        agitator = robotContainer.getAgitator();
        roller = robotContainer.getRoller();
        pivot = robotContainer.getPivot();
        shroud = robotContainer.getShroud();

        camera = robotContainer.getCamera();
        poseEstimator = robotContainer.getPoseEstimator();

        if(controller != null)
        {
            configSuppliers();
            configDefaultCommands();
            configRumble();

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
        rightYAxis = () -> -controller.getRawAxis(5);
    }

    private static void configDefaultCommands()
    {}

    private static void configRumble()
    {
        configRumbleTime(135);
        configRumbleTime(110);
        configRumbleTime(85);
        configRumbleTime(60);
        configRumbleTime(35);
    }

    private static void configRumbleTime(int shiftTime)
    {
        //is true five seconds before the shift switch
        BooleanSupplier shiftSwitch = () -> (
                ((DriverStation.getMatchTime() <= shiftTime) && (DriverStation.getMatchTime()  >= shiftTime - 1.5)));

        Trigger rumble = new Trigger(shiftSwitch);

        rumble
            .onTrue(Commands.runOnce(() -> controller.getHID().setRumble(RumbleType.kBothRumble, 0.5)))
            .onFalse(Commands.runOnce(() -> controller.getHID().setRumble(RumbleType.kBothRumble, 0.0)));
    }

    private static void configAButton()
    {
        Trigger aButton = controller.a();
        aButton
            .onTrue(ScoringCommands.stationaryScoreCommand());
    }

    private static void configBButton()
    {
        Trigger bButton = controller.b();
        bButton
            .onTrue(ScoringCommands.stopScoringCommand());
    }

    private static void configXButton()
    {
        Trigger xButton = controller.x();
        if(roller != null)
        {
            xButton
                .onTrue(roller.intakeFuelCommand());
        }
    }

    private static void configYButton()
    {
        Trigger yButton = controller.y();
        if(roller != null)
        {
            yButton
                .onTrue(roller.stopCommand());
        }
    }

    private static void configLeftBumper()
    {
        Trigger leftBumper = controller.leftBumper();
        leftBumper
            .onTrue(ScoringCommands.passCommand());
    }

    private static void configRightBumper()
    {
        Trigger rightBumper = controller.rightBumper();
    }

    private static void configBackButton()
    {
        Trigger backButton = controller.back();
        backButton
            .onTrue(GeneralCommands.stopAllCommand());
    }

    private static void configStartButton()
    {
        Trigger startButton = controller.start();
    }

    private static void configLeftTrigger()
    {
        Trigger leftTrigger = controller.leftTrigger();
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
        dpadUp
            .onTrue(flywheel.shootCommand(() -> 87.5));
        // dpadUp
        //     .onTrue(pivot.extendCommand());
    }

    private static void configDpadDown()
    {
        Trigger dpadDown = controller.povDown();
        dpadDown
            .onTrue(flywheel.stopCommand());
        // dpadDown
        //     .onTrue(pivot.retractCommand());
    }

    private static void configDpadLeft()
    {
        Trigger dpadLeft = controller.povLeft();
        // dpadLeft
        //     .onTrue(agitator.forwardCommand());
    }

    private static void configDpadRight()
    {
        Trigger dpadRight = controller.povRight();
        // dpadRight
        //     .onTrue(agitator.stopCommand());
    }
}