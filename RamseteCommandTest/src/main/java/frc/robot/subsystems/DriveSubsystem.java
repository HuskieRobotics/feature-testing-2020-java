/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.
  private CANCoder m_rightEncoder = new CANCoder (12);
  private CANCoder m_leftEncoder = new CANCoder (13);

  private final SpeedControllerGroup m_leftMotors =
      new SpeedControllerGroup(new WPI_TalonSRX(5),
                               new WPI_TalonSRX(7),
                               new WPI_TalonSRX(8));

  // The motors on the right side of the drive.
  private final SpeedControllerGroup m_rightMotors =
      new SpeedControllerGroup(new WPI_TalonSRX(2),
                               new WPI_TalonSRX(3),
                               new WPI_TalonSRX(4));

  
  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  
  /*
  private final Encoder m_leftEncoder =
      new Encoder(new (new CAN(12))DigitalSource,new DigitalSource(new CAN(12)),
                  DriveConstants.kLeftEncoderReversed);

  // The right-side drive encoder
  private final Encoder m_rightEncoder =
      new Encoder(DriveConstants.kRightEncoderPorts[0], DriveConstants.kRightEncoderPorts[1],
                  DriveConstants.kRightEncoderReversed);
  */

  // The gyro sensor
  private final PigeonIMU m_gyro = new PigeonIMU(0);

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    // Sets the distance per pulse for the encoders
    //m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
    //m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
    m_leftMotors.setInverted(true);
    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    SmartDashboard.putString("Pose", getPose().toString());
    m_odometry.update(Rotation2d.fromDegrees(getHeading()), 
                      m_leftEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse,
                      -m_rightEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse);
    String leftTest = " " + (m_leftEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse);
    String rightTest = " " + (-m_rightEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse);
    SmartDashboard.putString("Left Encoder", leftTest);
    SmartDashboard.putString("Right Encoder", rightTest);

  }

  public void setPower (double power) {
    m_rightMotors.set(power);
    m_leftMotors.set(power);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity()*DriveConstants.kEncoderDistancePerPulse, 
                                            -m_rightEncoder.getVelocity()*DriveConstants.kEncoderDistancePerPulse);
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
  }

  /**
   * Resets the drive encoders to currently read a position of 0.
   */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse +
            m_rightEncoder.getPosition()*DriveConstants.kEncoderDistancePerPulse) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public CANCoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public CANCoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    m_gyro.setYaw(0);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    double [] ypr = new double [3];
    m_gyro.getYawPitchRoll(ypr);
    return Math.IEEEremainder(ypr[0], 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  /*
  public double getTurnRate() {
    return m_gyro.get * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }
  */
}
