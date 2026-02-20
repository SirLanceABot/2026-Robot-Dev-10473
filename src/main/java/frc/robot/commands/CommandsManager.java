// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;

/** 
 * An example command that uses an example subsystem. 
 */
public final class CommandsManager
{
    // This string gets the full name of the class, including the package name
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();

    // *** STATIC INITIALIZATION BLOCK ***
    // This block of code is run first when the class is loaded
    static
    {
        System.out.println("Loading: " + fullClassName);
    }

    // *** CLASS AND INSTANCE VARIABLES ***
    // private final ExampleSubsystem exampleSubsystem;

    /**
     * Creates the CommandsManager
     */
    private CommandsManager() 
    {}

    public static void createCommands(RobotContainer robotContainer)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        GeneralCommands.createCommands(robotContainer);

        IntakingCommands.createCommands(robotContainer);

        ScoringCommands.createCommands(robotContainer);

        createNamedCommands();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    private static void createNamedCommands()
    {

        //GeneralCommands
        NamedCommands.registerCommand("Stop All Command", GeneralCommands.stopAllCommand());
        NamedCommands.registerCommand("Eject Fuel Command", GeneralCommands.ejectFuelCommand());

        //IntakingCommands
        NamedCommands.registerCommand("Simple Intake Command", IntakingCommands.simpleIntakeCommand());
        NamedCommands.registerCommand("Stop Intaking Command", IntakingCommands.stopIntakingCommand());

        //ScoringCommands
        NamedCommands.registerCommand("Pass Command", ScoringCommands.passCommand());
        NamedCommands.registerCommand("Stationary Score Command", ScoringCommands.stationaryScoreCommand());
        NamedCommands.registerCommand("Stop Scoring Command", ScoringCommands.stopScoringCommand());

        SmartDashboard.putData("Command Scheduler", CommandScheduler.getInstance());
    }
}
