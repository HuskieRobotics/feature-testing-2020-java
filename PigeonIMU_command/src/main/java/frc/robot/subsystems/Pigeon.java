/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.sensors.PigeonIMU;

/**
 * Add your docs here.
 */
public class Pigeon extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private PigeonIMU pig;

  public Pigeon() {
    this.pig = new PigeonIMU(13); // TODO check device ID
  }

  public Pigeon(int deviceID) {
    this.pig = new PigeonIMU(deviceID);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    double[] ypr = new double[3];
    this.pig.getYawPitchRoll(ypr);

    SmartDashboard.putNumber("Yaw", ypr[0]);
    SmartDashboard.putNumber("Pitch", ypr[1]);
    SmartDashboard.putNumber("Roll", ypr[2]);
  }
}
