/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Compressor compressor = new Compressor(21);
  private TalonFX motor1 = new TalonFX (1);
  private TalonFX motor2 = new TalonFX (2);
  private double motor1Power = 0;
  private double motor2Power = 0;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    SmartDashboard.putNumber("Motor 1 Power", 0);
    SmartDashboard.putNumber("Motor 2 Power", 0);
  }

  
  @Override
  public void robotPeriodic() {
    motor1Power = SmartDashboard.getNumber("Motor 1 Power", 0);
    motor2Power = SmartDashboard.getNumber("Motor 2 Power", 0);
  }

 
  @Override
  public void autonomousInit() {
    
  }

 
  @Override
  public void autonomousPeriodic() {
  
  }

  @Override
  public void teleopInit() {
    // generated example code
    compressor.start();
    
  }

  @Override
  public void teleopPeriodic() {
    motor1.set(ControlMode.PercentOutput, motor1Power);
    motor2.set(ControlMode.PercentOutput, motor2Power);
  }


  @Override
  public void testPeriodic() {
  }
}
