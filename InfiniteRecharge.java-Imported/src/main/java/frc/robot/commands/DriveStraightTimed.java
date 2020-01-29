/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class DriveStraightTimed extends TimedCommand {
  /**
   * Add your docs here.
   */
  public DriveStraightTimed(double timeout) {
    super(timeout);
    requires(Robot.drivetrain);
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.drivetrain.setLeftPower(.1);
    Robot.drivetrain.setRightPower(.1);
  }

  // Called once after timeout
  @Override
  protected void end() {
    Robot.drivetrain.setLeftPower(0);
    Robot.drivetrain.setRightPower(0);
  }
}
