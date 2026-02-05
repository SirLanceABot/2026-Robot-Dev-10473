package frc.robot.subsystems;

import static frc.robot.Constants.LEDs.*;

import java.lang.invoke.MethodHandles;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class controling the LEDs
 * 
 * @author Mukul Kedia
 */
public class LEDs extends SubsystemBase
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

    private AddressableLED led = new AddressableLED(LED_PORT);
    private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LED_LENGTH);

    private final LEDPattern off = LEDPattern.solid(Color.kBlack);

    private LEDPattern currentPattern = null;

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Creates a new LEDs.
     */
    public LEDs()
    {
        super("LEDs");
        System.out.println("  Constructor Started:  " + fullClassName);

        configLEDs();
        setOff();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Configures the LEDs and starts them off.
     */
    private void configLEDs()
    {
        led.setLength(ledBuffer.getLength());
        led.start();
    }

    /**
     * Sets the leds to the pattern in currentPattern.
     */
    private void setPattern()
    {
        currentPattern.applyTo(ledBuffer);
        led.setData(ledBuffer);
    }

    /**
     * Turns off the leds.
     */
    private void setOff()
    {
        currentPattern = off;
        setPattern();
    }

    /**
     * Turns off the leds.
     */
    public Command setOffCommand()
    {
        return runOnce(() -> setOff());
    }

    /**
     * Sets the color of the leds to be solid.
     */
    private void setColor(Color color)
    {
        currentPattern = LEDPattern.solid(color);
        setPattern();
    }

    /**
     * Sets the color of the leds to be solid.
     */
    public Command setColorCommand(Color color)
    {
        return runOnce(() -> setColor(color));
    }

    /**
     * Sets the color of the leds to a gradient.
     */
    private void setGradient(Color... colors)
    {
        currentPattern = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, colors);
        setPattern();
    }
    
    /**
     * Sets the color of the leds to a gradient.
     */
    public Command setGradientCommand(Color... colors)
    {
        return runOnce(() -> setGradient(colors));
    }

    /**
     * Sets the color of the leds to a rainbow.
     */
    private void setRainbow()
    {
        currentPattern = LEDPattern.rainbow(255, 255);
        setPattern();
    }

    /**
     * Sets the color of the leds to a rainbow.
     */
    public Command setRainbowCommand()
    {
        return runOnce(() -> setRainbow());
    }

    /**
     * Makes the current pattern of the leds breathe.
     */
    private void setBreathe(double seconds)
    {
        currentPattern = currentPattern.breathe(Units.Second.of(seconds));
        setPattern();
    }

    /**
     * Makes the current pattern of the leds breathe.
     */
    public Command setBreatheCommand(double seconds)
    {
        return runOnce(() -> setBreathe(seconds));
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
