package frc.robot.subsystems;

import static frc.robot.Constants.LEDs.*;

import java.lang.invoke.MethodHandles;
import java.util.Map;

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

    public enum ColorPattern
    {
        kDefault,
        kSolid,
        kGradient,
        kRainbow
    }

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    public static final Color RUNNING_COLOR = Color.kYellow;

    private AddressableLED led = new AddressableLED(LED_PORT);
    private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LED_LENGTH);

    private LEDPattern currentPattern = null;
    private boolean currentPatternIsAnimated = false;

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
        setColorSolid(RUNNING_COLOR);

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
     * Sets the color of the leds to be solid.
     */
    private void setColorSolid(Color color)
    {
        currentPattern = LEDPattern.solid(color);
        currentPatternIsAnimated = false;
        setPattern();
    }

    /**
     * Sets the color of the leds to be solid.
     */
    public Command setColorSolidCommand(Color color)
    {
        return runOnce(() -> setColorSolid(color));
    }

    /**
     * Sets the color of the leds to a gradient.
     */
    private void setColorGradient(Color... colors)
    {
        currentPattern = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, colors).scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(100));
        currentPatternIsAnimated = true;
        setPattern();
    }
    
    /**
     * Sets the color of the leds to a gradient.
     */
    public Command setColorGradientCommand(Color... colors)
    {
        return runOnce(() -> setColorGradient(colors));
    }

    /**
     * Sets the color of the leds to a rainbow.
     */
    private void setColorRainbow()
    {
        currentPattern = LEDPattern.rainbow(255, 255).scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(100));
        currentPatternIsAnimated = true;
        setPattern();
    }

    /**
     * Sets the color of the leds to a rainbow.
     */
    public Command setColorRainbowCommand()
    {
        return runOnce(() -> setColorRainbow());
    }

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    @Override
    public void periodic()
    {
        // This method will be called once per scheduler run
        // Use this for sensors that need to be read periodically.
        // Use this for data that needs to be logged.

        if (currentPattern != null && currentPatternIsAnimated)
            setPattern();
    }

    @Override
    public String toString()
    {
        return "";
    }
}
