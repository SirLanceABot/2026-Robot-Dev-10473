package frc.robot.subsystems;

import static frc.robot.Constants.LEDs.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;

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

    /**
     * An LED view with helpers to create and set patterns
     */
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
         * @return {@link Command} The command to set the leds in the LED view off
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
         * @return {@link Command} The command to set the leds in the LED view to a solid color
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
         * @return {@link Command} The command to set the leds in the LED view to a scrolling gradient
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
         * @return {@link Command} The command to set the leds in the LED view to a scrolling rainbow
         */
        public Command setRainbowCommand()
        {
            return Commands.runOnce(() -> setRainbow());
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * @param seconds {@link Double} The amount of seconds between each blink
         */
        private void setBlink(double seconds)
        {
            setPattern(this.pattern.blink(Units.Seconds.of(seconds)), true);
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * @param seconds {@link Double} The amount of seconds between each blink
         * @return {@link Command} The command to set the leds in the LED view to blink
         * @implNote THIS STACKS WITH OTHER MODIFIERS AND CURRENTLY THERE IS NO WAY TO REMOVE A MODIFIER
         */
        public Command setBlinkCommand(double seconds)
        {
            return Commands.runOnce(() -> setBlink(seconds));
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * @param offSeconds {@link Double} The amount of seconds to stay off
         * @param onSeconds {@link Double} The amount of seconds to stay on
         */
        private void setBlink(double offSeconds, double onSeconds)
        {
            setPattern(this.pattern.blink(Units.Seconds.of(offSeconds), Units.Seconds.of(onSeconds)), true);
        }

        /**
         * Modifies the current pattern of the LED view to blink
         * @param offSeconds {@link Double} The amount of seconds to stay off
         * @param onSeconds {@link Double} The amount of seconds to stay on
         * @return {@link Command} The command to set the leds in the LED view to blink
         * @implNote THIS STACKS WITH OTHER MODIFIERS AND CURRENTLY THERE IS NO WAY TO REMOVE A MODIFIER
         */
        public Command setBlinkCommand(double offSeconds, double onSeconds)
        {
            return Commands.runOnce(() -> setBlink(offSeconds, onSeconds));
        }

        /**
         * Modifies the current pattern of the LED view to breathe
         * @param seconds {@link Double} The amount of seconds between each breathe
         */
        private void setBreathe(double seconds)
        {
            setPattern(this.pattern.breathe(Units.Seconds.of(seconds)), true);
        }

        /**
         * Modifies the current pattern of the LED view to breathe
         * @param seconds {@link Double} The amount of seconds between each breathe
         * @return {@link Command} The command to set the leds in the LED view to breathe
         * @implNote THIS STACKS WITH OTHER MODIFIERS AND CURRENTLY THERE IS NO WAY TO REMOVE A MODIFIER
         */
        public Command setBreatheCommand(double seconds)
        {
            return Commands.runOnce(() -> setBreathe(seconds));
        }

        /**
         * Modifies the current pattern of the LED view to show a progress bar
         * @param progress {@link DoubleSupplier} The current progress
         */
        private void setProgress(DoubleSupplier progress)
        {
            setPattern(this.pattern.mask(LEDPattern.progressMaskLayer(progress)), false);
        }

        /**
         * Modifies the current pattern of the LED view to show a progress bar
         * @param progress {@link DoubleSupplier} The current progress
         * @return {@link Command} The command to set the leds in the LED view to show a progress bar
         * @implNote THIS STACKS WITH OTHER MODIFIERS AND CURRENTLY THERE IS NO WAY TO REMOVE A MODIFIER
         */
        public Command setProgressCommand(DoubleSupplier progress)
        {
            return Commands.run(() -> setProgress(progress));
        }

        /**
         * Modifies the current pattern of the LED view to show a progress bar
         * @param progress {@link Double} The current progress
         */
        private void setProgress(double progress)
        {
            setPattern(this.pattern.mask(LEDPattern.progressMaskLayer(() -> progress)), false);
        }

        /**
         * Modifies the current pattern of the LED view to show a progress bar
         * @param progress {@link Double} The current progress
         * @return {@link Command} The command to set the leds in the LED view to show a progress bar
         * @implNote THIS STACKS WITH OTHER MODIFIERS AND CURRENTLY THERE IS NO WAY TO REMOVE A MODIFIER
         */
        public Command setProgressCommand(double progress)
        {
            return Commands.runOnce(() -> setProgress(progress));
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
