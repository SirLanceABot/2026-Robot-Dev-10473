package frc.robot.subsystems;

import static frc.robot.Constants.LEDs.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class controling the LED strip
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

    public class LEDView
    {
        private final int startIndex;
        private final int endIndex;
        private final AddressableLEDBufferView bufferView;
        private LEDPattern pattern = LEDPattern.solid(Color.kBlack);
        private boolean isAnimated = false;
        private boolean needsUpdate = true;
        
        /**
         * Creates the LED view
         * @param startIndex {@link Integer} The start index of the view
         * @param endIndex {@link Integer} The end index of the view
         */
        private LEDView(int startIndex, int endIndex)
        {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.bufferView = ledBuffer.createView(startIndex, endIndex);
        }

        /**
         * Sets the pattern of the LED view
         * @param pattern {@link LEDPattern} The pattern to set to
         * @param isAnimated {@link Boolean} Whether the pattern needs to be updated constantly
         */
        private void setPattern(LEDPattern pattern, boolean isAnimated)
        {
            if (pattern == null)
            {
                throw new IllegalArgumentException("Pattern cannot be null");
            }

            this.pattern = pattern;
            this.isAnimated = isAnimated;
            this.needsUpdate = true;
        }

        /**
         * Sets the pattern of the LED view to off
         * @return {@link Command}
         */
        public Command setOffCommand()
        {
            return Commands.runOnce(() -> setSolid(Color.kBlack));
        }

        /**
         * Sets the pattern of the LED view to a solid color
         * @param color {@link Color} The color to set the LED view to
         */
        private void setSolid(Color color)
        {
            Objects.requireNonNull(color, "Color cannot be null");
            setPattern(LEDPattern.solid(color), false);
        }

        /**
         * Sets the pattern of the LED view to a solid color
         * @param color {@link Color} The color to set the LED view to
         * @return {@link Command}
         */
        public Command setSolidCommand(Color color)
        {
            return Commands.runOnce(() -> setSolid(color));
        }

        /**
         * Sets the pattern of the LED view to a scrolling gradient
         * @param colors {@link Color} The colors to set the LED view to
         */
        private void setGradient(Color... colors)
        {
            Objects.requireNonNull(colors, "Colors cannot be null");
            setPattern(
                LEDPattern.gradient(LEDPattern.GradientType.kContinuous, colors)
                    .scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(100)),
                true
            );
        }

        /**
         * Sets the pattern of the LED view to a scrolling gradient
         * @param colors {@link Color} The colors to set the LED view to
         * @return {@link Command}
         */
        public Command setGradientCommand(Color... colors)
        {
            return Commands.runOnce(() -> setGradient(colors));
        }

        /**
         * Sets the pattern of the LED view to a scrolling rainbow
         */
        private void setRainbow()
        {
            setPattern(
                LEDPattern.rainbow(255, 255)
                    .scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(100)),
                true
            );
        }

        /**
         * Sets the pattern of the LED view to a scrolling rainbow
         * @return {@link Command}
         */
        public Command setRainbowCommand()
        {
            return Commands.runOnce(() -> setRainbow());
        }
    }

    // *** CLASS VARIABLES & INSTANCE VARIABLES ***
    // Put all class variables and instance variables here

    private final AddressableLED led = new AddressableLED(LED_PORT);
    private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LED_LENGTH);
    private final List<LEDView> views = new ArrayList<>();

    // *** CLASS CONSTRUCTORS ***
    // Put all class constructors here

    /**
     * Creates the LEDs subsystem
     */
    public LEDs()
    {
        super("LEDs");
        System.out.println("  Constructor Started:  " + fullClassName);

        configLEDStrip();

        System.out.println("  Constructor Finished: " + fullClassName);
    }

    // *** CLASS METHODS & INSTANCE METHODS ***
    // Put all class methods and instance methods here

    /**
     * Configures the LED strip for use
     */
    private void configLEDStrip()
    {
        led.setLength(ledBuffer.getLength());
        led.start();
    }

    /**
     * Creates a LED view
     * @param startIndex {@link Integer} The start index of the view
     * @param endIndex {@link Integer} The end index of the view
     * @return {@link LEDView}
     */
    public LEDView createView(int startIndex, int endIndex)
    {
        for (LEDView existing : views)
        {
            if (startIndex <= existing.endIndex && endIndex >= existing.startIndex)
            {
                throw new IllegalArgumentException(
                    "View [" + startIndex + ", " + endIndex +
                    "] overlaps with existing view [" + existing.startIndex + ", " + existing.endIndex + "]");
            }
        }

        LEDView view = new LEDView(startIndex, endIndex);
        views.add(view);

        return view;
    }

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    @Override
    public void periodic()
    {
        boolean dirty = false;

        for (LEDView view : views)
        {
            if (view.isAnimated || view.needsUpdate)
            {
                view.pattern.applyTo(view.bufferView);
                view.needsUpdate = false;
                dirty = true;
            }
        }

        if (dirty)
        {
            led.setData(ledBuffer);
        }
    }
}
