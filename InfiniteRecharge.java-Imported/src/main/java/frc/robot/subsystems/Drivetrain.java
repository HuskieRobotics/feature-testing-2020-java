
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.JoystickDrive;
import frc.robot.Constants.DriveConstants;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import java.lang.Math;


public class Drivetrain extends Subsystem {
    
    private WPI_TalonSRX [] leftMotors = new WPI_TalonSRX [3];
    private WPI_TalonSRX [] rightMotors = new WPI_TalonSRX [3];
    private int [] leftCANIDs = {5,7,8};
    private int [] rightCANIDs = {2,3,4};

    private CANCoder rightEncoder = new CANCoder (12);
    private CANCoder leftEncoder = new CANCoder (13);

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

    // The gyro sensor
    private final PigeonIMU m_gyro =  new PigeonIMU(0);

    // Odometry class for tracking robot pose
    private final DifferentialDriveOdometry m_odometry;

    public Drivetrain() {
        //initialize all motors, set 2,4,6 as inverted, set all motor default mode to brake
        for (int i = 0; i < 3; i++) {
            leftMotors[i] = new WPI_TalonSRX(leftCANIDs[i]);
            leftMotors[i].setInverted(true);
            rightMotors[i] = new WPI_TalonSRX(rightCANIDs[i]);

        }

        resetEncoders();

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    }

    /**
    * Returns the current wheel speeds of the robot.
    *
    * @return The current wheel speeds.
    */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            leftEncoder.getVelocity()*DriveConstants.kEncoderDistancePerPulse, 
            rightEncoder.getVelocity()*DriveConstants.kEncoderDistancePerPulse);
    }

    /**
    * Returns the heading of the robot.
    *
    * @return the robot's heading in degrees, from -180 to 180
    */
    public double getHeading() {
        double [] ypr = new double [3];
        m_gyro.getYawPitchRoll(ypr);
        return Math.IEEEremainder(ypr[0], 360); //* (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), 
                            leftEncoder.getPosition() * DriveConstants.kEncoderDistancePerPulse,
                            rightEncoder.getPosition() * DriveConstants.kEncoderDistancePerPulse);
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
    * Controls the left and right sides of the drive directly with voltages.
    *
    * @param leftVolts  the commanded left output
    * @param rightVolts the commanded right output
    */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        m_leftMotors.setVoltage(leftVolts);
        m_rightMotors.setVoltage(-rightVolts);
        m_drive.feed();
    }

    /**
    * Resets the drive encoders to currently read a position of 0.
    */
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }


    public void initDefaultCommand() {
        setDefaultCommand(new JoystickDrive());
    }

    public void arcadeDrive(double joystickX, double joystickY)
    {
        //Apply raw motor values from joysticks
        double leftPower = joystickX + joystickY;
        double rightPower = joystickY - joystickX;

        // Square drive
        leftPower*=Math.abs(leftPower);
        rightPower*=Math.abs(rightPower);

        //80% of total motor power
        leftPower *= 0.8;
        rightPower *= 0.8;

        //Set power to motors
        setLeftPower(leftPower);
        setRightPower(rightPower);
    }
    public void setLeftPower (double power) {
        for (WPI_TalonSRX leftMotor : leftMotors)
            leftMotor.set(power);
    }
    public void setRightPower (double power) {
        for (WPI_TalonSRX rightMotor : rightMotors)
            rightMotor.set(power);
    }
}
