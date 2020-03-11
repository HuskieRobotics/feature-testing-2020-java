package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.ShooterConstants;


public class Shooter extends PIDSubsystem {
  
  private final WPI_TalonFX shooterMotor1 = new WPI_TalonFX(ShooterConstants.shooterMotor1CanID);
  private WPI_TalonFX  shooterMotor2 = new WPI_TalonFX (ShooterConstants.shooterMotor2CanID);
  private final SimpleMotorFeedforward shooterFeedforward =
      new SimpleMotorFeedforward(ShooterConstants.kSVolts,
                                 ShooterConstants.kVVoltSecondsPerRotation);

  public Shooter () {
    super(new PIDController(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD));
    getController().setTolerance(ShooterConstants.kShooterToleranceRPS);
    setSetpoint(ShooterConstants.kShooterTargetRPS);

    shooterMotor1.setNeutralMode(NeutralMode.Brake);
    shooterMotor2.setNeutralMode(NeutralMode.Brake);
    shooterMotor1.setInverted(true);
  }
  
  @Override
  public void useOutput(double output, double setpoint) {
    double outputVolts = output + shooterFeedforward.calculate(setpoint);
    shooterMotor1.setVoltage(outputVolts);
    shooterMotor2.setVoltage(outputVolts);
  }

  @Override
  public double getMeasurement() {
    return shooterMotor2.getSelectedSensorVelocity()*ShooterConstants.kEncoderDistancePerPulse;
  }

   
}