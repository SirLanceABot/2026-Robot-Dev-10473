package frc.robot.subsystems;

import static frc.robot.Constants.LEDs.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class controlling the LED strip
 * 
 * @author Mukul Kedia
 */
public final class LEDs extends SubsystemBase implements AutoCloseable
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

    /**
     * An LED view with helpers to create and set patterns
     */
    public static final class LEDView
    {
        private final int startIndex;
        private final int endIndex;
        private final AddressableLEDBufferView bufferView;

        private LEDPattern basePattern = LEDPattern.solid(DEFAULT_COLOR);
        private boolean baseIsAnimated = false;
        private LEDPattern activePattern = basePattern;
        private boolean activeIsAnimated = false;

        private Dimensionless brightness = Units.Percent.of(1.0);
        private boolean isDirty = true;

        /**
         * Creates the LED view
         * 
         * @param startIndex {@link Integer} The start index of the view
         * @param endIndex {@link Integer} The end index of the view
         */
        private LEDView(final int startIndex, final int endIndex, final AddressableLEDBufferView bufferView)
        {
            Objects.requireNonNull(bufferView);

            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.bufferView = bufferView;
        }

        /**
         * Sets the pattern of the LED view
         * 
         * @param pattern {@link LEDPattern} The pattern to set to
         * @param isAnimated {@link Boolean} Whether the pattern needs to be updated
         *            constantly
         */
        private void setPattern(final LEDPattern pattern, final boolean isAnimated, final boolean isModifier)
        {
            Objects.requireNonNull(pattern);

            this.activePattern = pattern;
            this.activeIsAnimated = isAnimated;
            this.isDirty = true;

            if (!isModifier)
            {
                this.basePattern = pattern;
                this.baseIsAnimated = isAnimated;
            }
        }

        /**
         * Sets the pattern of the LED view to off
         * 
         * @return {@link Command} The command to set the leds in the LED view off
         */
        public Command setOffCommand()
        {
            return Commands.runOnce(() -> setSolid(Color.kBlack));
        }

        /**
         * Sets the pattern of the LED view to the default color
         * 
         * @return {@link Command} The command to set the leds in the LED view to the
         *         default color
         */
        public Command setDefaultCommand()
        {
            return Commands.runOnce(() -> setSolid(DEFAULT_COLOR));
        }

        /**
         * Sets the pattern of the LED view to a solid color
         * 
         * @param color {@link Color} The color to set the LED view to
         */
        private void setSolid(final Color color)
        {
            Objects.requireNonNull(color);

            setPattern(LEDPattern.solid(color), false, false);
        }

        /**
         * Sets the pattern of the LED view to a solid color
         * 
         * @param color {@link Color} The color to set the LED view to
         * @return {@link Command} The command to set the leds in the LED view to a
         *         solid color
         */
        public Command setSolidCommand(final Color color)
        {
            return Commands.runOnce(() -> setSolid(color));
        }

        /**
         * Sets the pattern of the LED view to a scrolling gradient
         * 
         * @param colors {@link Color} The colors to set the LED view to
         */
        private void setGradient(final Color... colors)
        {
            Objects.requireNonNull(colors);
            if (colors.length < 2)
            {
                throw new IllegalArgumentException("colors must have at least 2 colors");
            }
            for (Color color : colors)
            {
                Objects.requireNonNull(color);
            }

            setPattern(
                    LEDPattern.gradient(LEDPattern.GradientType.kContinuous, colors)
                            .scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(100)),
                    true, false);
        }

        /**
         * Sets the pattern of the LED view to a scrolling gradient
         * 
         * @param colors {@link Color} The colors to set the LED view to
         * @return {@link Command} The command to set the leds in the LED view to a
         *         scrolling gradient
         */
        public Command setGradientCommand(final Color... colors)
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
                    true, false);
        }

        /**
         * Sets the pattern of the LED view to a scrolling rainbow
         * 
         * @return {@link Command} The command to set the leds in the LED view to a
         *         scrolling rainbow
         */
        public Command setRainbowCommand()
        {
            return Commands.runOnce(() -> setRainbow());
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * 
         * @param seconds {@link Double} The amount of seconds between each blink
         */
        private void setBlink(final double seconds)
        {
            if (seconds <= 0.0)
            {
                throw new IllegalArgumentException("seconds must be positive");
            }

            setPattern(this.basePattern.blink(Units.Seconds.of(seconds)), true, true);
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * 
         * @param seconds {@link Double} The amount of seconds between each blink
         * @return {@link Command} The command to set the leds in the LED view to blink
         */
        public Command setBlinkCommand(final double seconds)
        {
            return Commands.runOnce(() -> setBlink(seconds));
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * 
         * @param offSeconds {@link Double} The amount of seconds to stay off
         * @param onSeconds {@link Double} The amount of seconds to stay on
         */
        private void setBlink(final double offSeconds, final double onSeconds)
        {
            if (offSeconds <= 0.0)
            {
                throw new IllegalArgumentException("seconds must be positive");
            }
            if (onSeconds <= 0.0)
            {
                throw new IllegalArgumentException("seconds must be positive");
            }

            setPattern(this.basePattern.blink(Units.Seconds.of(offSeconds), Units.Seconds.of(onSeconds)), true, true);
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * 
         * @param offSeconds {@link Double} The amount of seconds to stay off
         * @param onSeconds {@link Double} The amount of seconds to stay on
         * @return {@link Command} The command to set the leds in the LED view to blink
         */
        public Command setBlinkCommand(final double offSeconds, final double onSeconds)
        {
            return Commands.runOnce(() -> setBlink(offSeconds, onSeconds));
        }

        /**
         * Modifies the current pattern of the LED view to breathe
         * 
         * @param seconds {@link Double} The amount of seconds between each breathe
         */
        private void setBreathe(final double seconds)
        {
            if (seconds <= 0.0)
            {
                throw new IllegalArgumentException("seconds must be positive");
            }

            setPattern(this.basePattern.breathe(Units.Seconds.of(seconds)), true, true);
        }

        /**
         * Modifies the current pattern of the LED view to breathe
         * 
         * @param seconds {@link Double} The amount of seconds between each breathe
         * @return {@link Command} The command to set the leds in the LED view to
         *         breathe
         */
        public Command setBreatheCommand(final double seconds)
        {
            return Commands.runOnce(() -> setBreathe(seconds));
        }

        /**
         * Sets the brightness of the LED view
         * 
         * @param brightness {@link Double} The brightness to set
         */
        private void setBrightness(double brightness)
        {
            if (brightness < 0.0 || brightness > 1.0)
            {
                throw new IllegalArgumentException("seconds must be between 0 and 1");
            }

            this.brightness = Units.Percent.of(brightness);
        }

        /**
         * Sets the brightness of the LED view
         * 
         * @param brightness {@link Double} The brightness to set
         * @return {@link Command} The command to set the brightness of the LED view
         */
        public Command setBrightnessCommand(double brightness)
        {
            return Commands.runOnce(() -> setBrightness(brightness));
        }

        /**
         * Removes the current modifier of the LED view
         */
        public void removeModifier()
        {
            activePattern = basePattern;
            activeIsAnimated = baseIsAnimated;
            isDirty = true;
        }

        /**
         * Removes the current modifier of the LED view
         * 
         * @return {@link Command} The command to remove the current modifer of the LED
         *         view
         */
        public Command removeModifierCommand()
        {
            return Commands.runOnce(() -> removeModifier());
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
     * 
     * @param startIndex {@link Integer} The start index of the view
     * @param endIndex {@link Integer} The end index of the view
     * @return {@link LEDView} The created view
     */
    public LEDView createView(final int startIndex, final int endIndex)
    {
        if (startIndex < 0 || endIndex >= ledBuffer.getLength() || startIndex > endIndex)
        {
            throw new IllegalArgumentException("Invalid LED view bounds");
        }

        for (LEDView existing : views)
        {
            if (existing.startIndex == startIndex && existing.endIndex == endIndex)
            {
                return existing;
            }

            if (startIndex <= existing.endIndex && endIndex >= existing.startIndex)
            {
                throw new IllegalArgumentException(String.format("View [%s, %s] overlaps with existing view [%s, %s]",
                        startIndex, endIndex, existing.startIndex, existing.endIndex));
            }
        }

        AddressableLEDBufferView bufferView = ledBuffer.createView(startIndex, endIndex);
        LEDView view = new LEDView(startIndex, endIndex, bufferView);
        views.add(view);

        return view;
    }

    /**
     * Deletes a LED view
     * 
     * @param view {@link LEDView} The view to delete
     */
    public void deleteView(final LEDView view)
    {
        Objects.requireNonNull(view);
        if (!views.remove(view))
        {
            throw new IllegalArgumentException("View not found");
        }

        LEDPattern.solid(Color.kBlack).applyTo(view.bufferView);
        led.setData(ledBuffer);
    }

    // *** OVERRIDEN METHODS ***
    // Put all methods that are Overridden here

    /**
     * Runs periodically every 20ms
     */
    @Override
    public void periodic()
    {
        boolean dirty = false;

        for (LEDView view : views)
        {
            if (view.activeIsAnimated || view.isDirty)
            {
                view.activePattern.atBrightness(view.brightness).applyTo(view.bufferView);
                view.isDirty = false;
                dirty = true;
            }
        }

        if (dirty)
        {
            led.setData(ledBuffer);
        }
    }

    /**
     * Runs once right before garbage collection
     */
    @Override
    public void close()
    {
        LEDPattern.solid(Color.kBlack).applyTo(ledBuffer);
        led.setData(ledBuffer);

        led.stop();
        led.close();
    }
}
