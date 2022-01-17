/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.robot.Gains;
import frc.robot.Robot;
// import jaci.pathfinder.followers.EncoderFollower;
// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends MainSubsystem implements PIDOutput{
  private WPI_TalonSRX leftDriveMaster;
  private WPI_TalonSRX rightDriveMaster;
  private WPI_VictorSPX leftDriveSlave;
  private WPI_VictorSPX rightDriveslave;
  //private Encoder rightEncoder;
  //private Encoder leftEncoder;
  //private EncoderFollower rightEncoderFollower;
  //private EncoderFollower leftEncoderFollower;
  private final AHRS navx;
  //private Notifier followerNotifier;
  private DifferentialDrive differentialDrive;
  private SpeedControllerGroup leftDriveGroup;
  private SpeedControllerGroup rightDriveGroup;
  private Command defaultCommand;
  private final PIDController turnController;

  public DriveTrain(){
    leftDriveMaster = new WPI_TalonSRX(Constants.kLeftDriveMasterID);
    rightDriveMaster = new WPI_TalonSRX(Constants.kRightDriveMasterID);
    leftDriveSlave = new WPI_VictorSPX(Constants.kLeftDriveSlaveID);
    rightDriveslave = new WPI_VictorSPX(Constants.kRightDriveSlaveID);

    Robot.initTalonSRX(leftDriveMaster);
    Robot.initTalonSRX(rightDriveMaster);
    leftDriveSlave.follow(leftDriveMaster);
    rightDriveslave.follow(rightDriveMaster);

    //leftEncoder = new Encoder(Constants.kLeftEncoderPortA, Constants.kLeftEncoderPortB);
    //rightEncoder = new Encoder(Constants.kRightEncoderPortA, Constants.kRightEncoderPortB);
    navx = new AHRS(SPI.Port.kMXP);

    leftDriveGroup = new SpeedControllerGroup(leftDriveMaster, leftDriveSlave);
    rightDriveGroup = new SpeedControllerGroup(rightDriveMaster, rightDriveslave);

    turnController = new PIDController(Gains.kPTurn, Gains.kITurn, Gains.kDTurn, navx, this);
    turnController.setInputRange(-180.0f, 180.0f);
    turnController.setOutputRange(-.7, .7);
    turnController.setAbsoluteTolerance(0.5f);
    turnController.setContinuous();
    differentialDrive = new DifferentialDrive(leftDriveMaster, rightDriveMaster);
    differentialDrive.setDeadband(.1);
  }  

  public void rotateDegrees(double angle){
    navx.reset();
    turnController.reset();
    turnController.setPID(Gains.kPTurn, Gains.kITurn, Gains.kDTurn);
    turnController.setSetpoint(angle);
    turnController.enable();
  }
  
  public void setSpeed(double move, double turn){
    differentialDrive.arcadeDrive(move, turn);
  }

  public void resetGyro(){
    navx.reset();
  }

  public void disableTurnController(){
    turnController.disable();
  }

  public double getTurnControllerError(){
    return turnController.getError();
  }
  
  public void outputTelemetry() {
    SmartDashboard.putNumber("Gyro Angle", navx.getAngle());
    SmartDashboard.putNumber("Accel Z", navx.getRawAccelZ());
  }

  public void establishDefaultCommand(Command command) {
    this.defaultCommand = command;
    initDefaultCommand();
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(this.defaultCommand);
  }

  @Override
  public void pidWrite(double output) {
    setSpeed(0, output);
  }
}
