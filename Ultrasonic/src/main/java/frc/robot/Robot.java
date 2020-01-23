/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Ultrasonic sensor demo                                                     */
/* This code takes the maxSonar EZ sensor and outputs a distance in mmm to    */
/* the dashboard.                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  
  private AnalogInput m_US;
  @Override
  public void robotInit() {
    m_US = new AnalogInput(0);
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    double sensorValue = m_US.getVoltage();
    final double scaleFactor = 1/(5./1024.); //scale converting voltage to distance
    double distance = 5*sensorValue*scaleFactor; //convert the voltage to distance
    SmartDashboard.putNumber("DB/Slider 0", distance); //write the value to the LabVIEW DriverStation
  
  }

}
