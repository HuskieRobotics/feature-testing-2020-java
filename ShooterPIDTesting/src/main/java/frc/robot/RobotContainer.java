
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.StorageConstants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final Shooter shooter = new Shooter (); 
  private final Storage storage = new Storage (); 

  private final XboxController operatorController = new XboxController(2);
  private final Button[] operatorButtons = new Button [13];
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    for(int i = 1; i <= operatorButtons.length; i++) {
      operatorButtons[i-1] = new JoystickButton(operatorController, i);
    }
    configureButtonBindings();
  }


  private void configureButtonBindings() {
    operatorButtons[5].whenPressed(new InstantCommand(() -> shooter.enable(), shooter));
    operatorButtons[4].whenPressed(new InstantCommand(() -> shooter.disable(), shooter));
    operatorButtons[7].whenHeld(new RunCommand(() -> storage.setPower(StorageConstants.manualStorageSpeed), storage));
    operatorButtons[6].whenHeld(new RunCommand(() -> storage.setPower(-StorageConstants.manualStorageSpeed), storage));
  }



  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
