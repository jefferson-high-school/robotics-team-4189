/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;



import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Autofix;
import frc.robot.commands.ManualDriveCommand;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {


  //Subsystem Talons
  public WPI_TalonSRX onBoarder = new WPI_TalonSRX(2);
  
  //Victor SPX
  public WPI_VictorSPX climberWheelRight = new WPI_VictorSPX(6);
  public WPI_VictorSPX climberWheelLeft = new WPI_VictorSPX(7);
  

  //Check Set Screws!!!!!
  //Drive Talons
  public WPI_TalonSRX leftFront = new WPI_TalonSRX(5);
  public WPI_TalonSRX leftBack = new WPI_TalonSRX(3);
  public WPI_TalonSRX rightFront = new WPI_TalonSRX(4);
  public WPI_TalonSRX rightBack = new WPI_TalonSRX(1);

  //Solenoids
  public Solenoid controlOnboarder = new Solenoid(RobotMap.pneumaticOnBoarderSolePort);
  public Solenoid controlHatchO_C = new Solenoid(RobotMap.pneumaticHatchO_CSolePort);
  public Solenoid controlHatchF_B = new Solenoid(RobotMap.pneumaticHatchF_BSolePort);
  public Solenoid controlClimbFront = new Solenoid(RobotMap.pneumaticFrontClimbSolePort);
  public Solenoid controlClimbRear = new Solenoid(RobotMap.pneumaticBackClimbSolePort);
  public Solenoid controlLauncher = new Solenoid(RobotMap.pneumaticLauncher);
  public Solenoid controlLauncherHeight = new Solenoid(RobotMap.pneumaticLauncherHeight); 

  // //Compressor
  public Compressor compressor = new Compressor(0);

  //Constants
  //public double kP = .3;

  public MecanumDrive drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);

  public void onBoarderSetSpeed(double x) {
    Robot.driveSubsystem.onBoarder.set(x);
  }
  public void frontClimbWheelDriveSetSpeed(double x) {
    Robot.driveSubsystem.climberWheelLeft.set(x);
    Robot.driveSubsystem.climberWheelRight.set(x);
  }
  public void hatchSetO_C(boolean x) {
    Robot.driveSubsystem.controlHatchO_C.set(x);
  }
  public void hatchSetF_B(boolean x) {
    Robot.driveSubsystem.controlHatchF_B.set(x);
  }
  public void onBoarderSet(boolean x) {
    Robot.driveSubsystem.controlOnboarder.set(x);
  }
  public void climberFrontSet(boolean x) {
    Robot.driveSubsystem.controlClimbFront.set(x);
  }
  public void climberBackSet(boolean x) {
    Robot.driveSubsystem.controlClimbRear.set(x);
  }
  public void launcherSet(boolean x) {
    Robot.driveSubsystem.controlLauncher.set(x);
  }
  public void launcherHeightSet(boolean x) {
    Robot.driveSubsystem.controlLauncherHeight.set(x);
  }

  public void manualDrive(double move, double translate, double turn) {
    // if(move >= .7) move = .7;
    // if(move <= -.7) move = -.7;
    // if(translate >= .7) translate = .7;
    // if(translate <= -.7) translate = -.7;
    if(turn >= .7) turn = .7;
    if(turn <= -.7) turn = -.7;

    if(move <= .2 && move > 0) move = 0;
    if(move >= -.2 && move < 0) move = 0;
    if(translate <= .3 && translate > 0) translate = 0;
    if(translate >= -.3 && translate < 0) translate = 0;
    if(turn <= .3 && turn > 0) turn = 0;
    if(turn >= -.3 && turn < 0) turn = 0;

    drive.driveCartesian(move, translate, turn);
    //leftFront.setInverted(true);
    //rightBack.setInverted(true);
    //rightFront.setInverted(true);
    //leftBack.setInverted(true);
  }

  public void dashData() {
    SmartDashboard.putNumber("Gyro Angle", OI.ahrs.getAngle());
    SmartDashboard.putBoolean("PSV", compressor.getPressureSwitchValue());
    SmartDashboard.putBoolean("Connected?", compressor.getCompressorNotConnectedFault());
    SmartDashboard.putBoolean("Closed Loop?", compressor.getClosedLoopControl());
    SmartDashboard.putNumber("Compressor Current", compressor.getCompressorCurrent());
    SmartDashboard.putString("Camera", OI.Usb.readString());
    // SmartDashboard.putBoolean("Safety Enabled?", Robot.driveSubsystem.drive.isSafetyEnabled());
    // SmartDashboard.putBoolean("Alive", Robot.driveSubsystem.drive.isAlive());
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ManualDriveCommand());
  }
}
