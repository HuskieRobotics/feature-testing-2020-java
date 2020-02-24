/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.AxisCamera;
//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
//import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  CameraServer camera1;
  CameraServer camera2;
  CameraServer camera3;
  UsbCamera usb1;
  UsbCamera usb2;
  AxisCamera limelight;
  VideoSink server;
  Joystick joy = new Joystick(0);
  DriverStation ds;
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  //private CvSink sink;
 // private CvSource source;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    ds = DriverStation.getInstance();
    
    // these variables seem to do nothing, but I am somehow unable to delete them without front camera from not displaying?
    camera1 = CameraServer.getInstance();
    camera2 = CameraServer.getInstance();
    camera3= CameraServer.getInstance();

    // usb 1
     usb1 = new UsbCamera("front camera",0);
    usb1.setFPS(30);
    usb1.setResolution(160,120);
    usb1.setPixelFormat(PixelFormat.kYUYV);
    CameraServer.getInstance().startAutomaticCapture(usb1);
    
    // usb 2
    usb2 = new UsbCamera("back camera", 1);
    usb2.setFPS(30);
    usb2.setResolution(160,120);
    usb2.setPixelFormat(PixelFormat.kYUYV);
    CameraServer.getInstance().startAutomaticCapture(usb2);
     
    // limelight
     limelight = new AxisCamera("lime camera", "limelight:5800");
     CameraServer.getInstance().startAutomaticCapture(limelight);
     
    server = CameraServer.getInstance().getServer();

    // delete this if there are bandwidth issues (deleting these may cause delays in switching tho)
    usb1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    usb2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    limelight.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

   
   
    
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }
  
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    //sink.grabFrame();

    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
   // if(ds.getMatchTime()<=30) camera2.startAutomaticCapture(2);

   // camera switching code
   // button 1 on xbox controller to switch
   if (joy.getTriggerPressed()){
     System.out.println("Setting camera 1");
     server.setSource(usb1);
   } else if (joy.getTriggerReleased()){
    System.out.println("Setting the LimeLite");
    server.setSource(limelight);
  }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
