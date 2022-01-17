/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.ManualDriveCommand;
import frc.robot.subsystems.Camera3;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.Autofix;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // public static ExampleSubsystem subsystem = new ExampleSubsystem();
  public static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static OI oi;
  public static Camera3 camera3 = new Camera3();
  
  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //Robot.driveSubsystem.encA.reset();
    oi = new OI();
    chooser.setDefaultOption("Default Auto", new ManualDriveCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    driveSubsystem.dashData();
    OI.camera  = CameraServer.getInstance().startAutomaticCapture(0);
    OI.camera.setVideoMode(VideoMode.getPixelFormatFromInt(1), 100, 100, 20);
    Robot.driveSubsystem.compressor.setClosedLoopControl(true);
    //Robot.driveSubsystem.drive.setSafetyEnabled(false);
    Robot.driveSubsystem.drive.feedWatchdog();
    Robot.driveSubsystem.controlHatchO_C.set(true);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Robot.driveSubsystem.drive.feedWatchdog();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  
  public void disabledInit() {
    //Robot.driveSubsystem.encA.reset();
  }

  @Override
  public void disabledPeriodic() {
    Robot.driveSubsystem.drive.feedWatchdog();
    Scheduler.getInstance().run();
    driveSubsystem.dashData();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    Scheduler.getInstance().add(new ManualDriveCommand());
    //Scheduler.getInstance().add(new Autofix());
    Robot.driveSubsystem.drive.feedWatchdog();
    Robot.driveSubsystem.controlHatchO_C.set(true);

    driveSubsystem.dashData();
    
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    driveSubsystem.dashData();
    Scheduler.getInstance().run();
    Robot.driveSubsystem.drive.feedWatchdog();
  }

  @Override
  public void teleopInit() {
    // Robot.driveSubsystem.encA.reset();
    Scheduler.getInstance().add(new ManualDriveCommand());
    Scheduler.getInstance().add(new Autofix());
    Robot.driveSubsystem.drive.feedWatchdog();

    driveSubsystem.dashData();
    
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Robot.driveSubsystem.drive.feedWatchdog();
    driveSubsystem.dashData();
    Scheduler.getInstance().run();
    // if(Robot.driveSubsystem.encAGet() >= 50) {
    //   SpeedControllerGroup motorGroup = new SpeedControllerGroup(Robot.driveSubsystem.leftFront, Robot.driveSubsystem.leftBack, Robot.driveSubsystem.rightFront, Robot.driveSubsystem.rightBack);
    //   motorGroup.set(0);
    // }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
