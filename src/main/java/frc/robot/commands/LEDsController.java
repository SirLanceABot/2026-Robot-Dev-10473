package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.LEDs.LEDView;

public class LEDsController {
    /**
     * The LED view patterns
     */
    public enum LEDPatternType {
        OFF,
        SOLID,
        GRADIENT,
        RAINBOW
    }

    /**
     * Sets a specific LED View to a pattern
     * 
     * @param view {@link LEDView} The LED view to use
     * @param pattern {@link LEDPatternType} The pattern type to use
     * @param colors {@link Color} Optional color(s) used for patterns that need them
     * @return {@link Command} The command to update the led view to the new pattern
     */
    public static Command setLEDCommand(LEDView view, LEDPatternType pattern, Color... colors) {
        if (view == null)
            return Commands.none();

        switch (pattern) {
            case OFF:
                return view.setOffCommand();
            case SOLID:
                return view.setSolidCommand(colors[0]);
            case GRADIENT:
                return view.setGradientCommand(colors);
            case RAINBOW:
                return view.setRainbowCommand();
            default:
                return Commands.none();
        }
    }
}
