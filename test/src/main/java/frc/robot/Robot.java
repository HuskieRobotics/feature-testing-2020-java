/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
//import com.ctre.phoenix.sensors.CANCoderFaults;
//import com.ctre.phoenix.sensors.CANCoderStickyFaults;
//import com.ctre.phoenix.sensors.MagnetFieldStrength;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  final int PRINTOUT_DELAY = 200; // in Milliseconds
  CANCoder _CANCoder = new CANCoder(0);
  CANCoderConfiguration _canCoderConfiguration = new CANCoderConfiguration();
  class Instrument extends Thread{
    void printValue(double val, String units, double timestamp){
      System.out.printf("%20f %-20s @ %f%n", val, units, timestamp);
    }

    public void run(){
      double posValue = _CANCoder.getPosition();
      String posUnits = _CANCoder.getLastUnitString();
      double posTstmp = _CANCoder.getLastTimestamp();

      System.out.print("Position:");
      printValue(posValue, posUnits, posTstmp);

      System.out.println();
      System.out.println();
    }
  }
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    _CANCoder.configAllSettings(_canCoderConfiguration);
  }
  
  int count = 0;
  @Override
  public void robotPeriodic() {
    if(count++ >= PRINTOUT_DELAY / 10) {
      count = 0;

    new Instrument().start();
  }
  //if(joy.getRawButton(1)) {
    //_CANCoder.clearStickyFaults();
  //}
}

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double posValue = _CANCoder.getPosition();
    SmartDashboard.putNumber("encoder value", posValue);
    System.out.println(posValue);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
