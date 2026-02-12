// package frc.robot.commands;

// import edu.wpi.first.wpilibj.util.Color;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import frc.robot.subsystems.LEDs;
// import frc.robot.subsystems.LEDs.ColorPattern;

// public class LEDsController
// {
//     /**
//      * Sets the leds to the pattern and color(s).
//      * @param pattern {@link ColorPattern} The pattern to set the leds to.
//      * @param colors {@link Color} The color(s) to set the leds to,
//      * @return {@link Command} The respective led command or none if the leds are not being used.
//      * @author Mukul Kedia
//      */
//     public static Command setLEDCommand(LEDs leds, ColorPattern pattern, Color... colors)
//     {
//         if (leds == null)
//             return Commands.none();

//         switch (pattern)
//         {
//             case kDefault:
//                 return leds.setColorSolidCommand(LEDs.RUNNING_COLOR);
//             case kSolid:
//                 return leds.setColorSolidCommand(colors[0]);
//             case kGradient:
//                 return leds.setColorGradientCommand(colors);
//             case kRainbow:
//                 return leds.setColorRainbowCommand();
//             default:
//                 return Commands.none();
//         }
//     }
// }
