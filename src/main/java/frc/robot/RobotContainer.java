// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.lang.invoke.MethodHandles;

import frc.robot.generated.TunerConstants;
import frc.robot.sensors.Camera;
import frc.robot.subsystems.Agitator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Roller;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shroud;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer 
{
    // This string gets the full name of the class including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded
    static
    {
        System.out.println("Loading: " + fullClassName);
    }

    // Select the robot components to use
    private boolean useFullRobot = false;

    private boolean useExampleSubsystem = false;
    private boolean useFlywheel = false;
    private boolean useAgitator = true;
    private boolean useRoller = false;
    private boolean usePivot = false;
    private boolean useDrivetrain = false;
    private boolean useShroud = false;
    private boolean useLEDs = false;
    private boolean useCamera = true;

    // Robot components
    private ExampleSubsystem exampleSubsystem = null;
    private Flywheel flywheel = null;
    private Agitator agitator = null;
    private Roller roller = null;
    private Pivot pivot = null;
    private Drivetrain drivetrain = null;
    private Shroud shroud = null;
    private LEDs leds = null;
    private Camera camera = null;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() 
    {
        // Instantiate ONLY the components selected above
        if(useFullRobot || useExampleSubsystem)
            exampleSubsystem = new ExampleSubsystem();

        if(useFullRobot || useFlywheel)
            flywheel = new Flywheel();

        if(useFullRobot || useAgitator)
            agitator = new Agitator();

        if(useFullRobot || useRoller)
            roller = new Roller();
        
        if(useFullRobot || usePivot)
            pivot = new Pivot();

        if(useFullRobot || useShroud)
            shroud = new Shroud();

        if(useFullRobot || useLEDs)
            leds = new LEDs();
        
        if(useFullRobot || useDrivetrain)
            drivetrain = TunerConstants.createDrivetrain();

        if(useFullRobot || useCamera)
            camera = new Camera();
    }

    public ExampleSubsystem getExampleSubsystem()
    {
        return exampleSubsystem;
    }

    public Flywheel getFlywheel()
    {
        return flywheel;
    }

    public Agitator getAgitator()
    {
        return agitator;
    }

    public Roller getRoller()
    {
        return roller;
    }

    public Pivot getPivot()
    {
        return pivot;
    }

    public Drivetrain getDrivetrain()
    {
        return drivetrain;
    }

    public Shroud getShroud()
    {
        return shroud;
    }

    public LEDs getLEDs()
    {
        return leds;
    }

    public Camera getCamera()
    {
        return camera;
    }
}
