package frc.robot.commands;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LEDs.LEDView;

public class LEDsController {
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
    
    private static LEDView view = null;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Instantiates the LEDs controller
     * @param view {@link LEDView} The LED view to use
     */
    public LEDsController(LEDView view)
    {
        System.out.println("  Constructor Started:  " + fullClassName);

        LEDsController.view = view;

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Sets the pattern of the LED view to off
     * @return {@link Command} The command to set the leds in the LED view off
     */
    public static Command setOffCommand()
    {
        if (view == null)
            return Commands.none();
        return view.setOffCommand();
    }

    /**
     * Sets the pattern of the LED view to a solid color
     * @param color {@link Color} The color to set the LED view to
     * @return {@link Command} The command to set the leds in the LED view to a solid color
     */
    public static Command setSolidCommand(Color color)
    {
        if (view == null)
            return Commands.none();
        return view.setSolidCommand(color);
    }

    /**
     * Sets the pattern of the LED view to a scrolling gradient
     * @param colors {@link Color} The colors to set the LED view to
     * @return {@link Command} The command to set the leds in the LED view to a scrolling gradient
     */
    public static Command setGradientCommand(Color... colors)
    {
        if (view == null)
            return Commands.none();
        return view.setGradientCommand(colors);
    }

    /**
     * Sets the pattern of the LED view to a scrolling rainbow
     * @return {@link Command} The command to set the leds in the LED view to a scrolling rainbow
     */
    public static Command setRainbowCommand()
    {
        if (view == null)
            return Commands.none();
        return view.setRainbowCommand();
    }

    /**
     * Modifies the current pattern of the LED view to blink
     * @param seconds {@link Double} The amount of seconds between each blink
     * @return {@link Command} The command to set the leds in the LED view to blink
     */
    public static Command setBlinkCommand(double seconds)
    {
        if (view == null)
            return Commands.none();
        return view.setBlinkCommand(seconds);
    }

    /**
     * Modifies the current pattern of the LED view to blink
     * @param offSeconds {@link Double} The amount of seconds to stay off
     * @param onSeconds {@link Double} The amount of seconds to stay on
     * @return {@link Command} The command to set the leds in the LED view to blink
     */
    public static Command setBlinkCommand(double offSeconds, double onSeconds)
    {
        if (view == null)
            return Commands.none();
        return view.setBlinkCommand(offSeconds, onSeconds);
    }

    /**
     * Modifies the current pattern of the LED view to breathe
     * @param seconds {@link Double} The amount of seconds between each breathe
     * @return {@link Command} The command to set the leds in the LED view to breathe
     */
    public static Command setBreatheCommand(double seconds)
    {
        if (view == null)
            return Commands.none();
        return view.setBreatheCommand(seconds);
    }

    /**
     * Undos the last change to the LED view's pattern
     * @return {@link Command} The command to undo the last change
     */
    public static Command undoCommand()
    {
        if (view == null)
            return Commands.none();
        return view.undoCommand();
    }
}