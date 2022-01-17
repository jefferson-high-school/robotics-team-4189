/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Gains;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveSensorConstants;

public class DriveTrain extends SubsystemBase implements PIDOutput {
  /**
   * Creates a new DriveTrain.
   */
  private final WPI_TalonFX mLeftMaster;
  private final WPI_TalonFX mLeftSlave;

  private final WPI_TalonFX mRightMaster;
  private final WPI_TalonFX mRightSlave;

  private final DifferentialDrive mDrive;

  private final AHRS mNavx;

  private final AnalogInput mLeftUltrasonic;
  private final AnalogInput mFrontUltrasonic;
  private final AnalogInput mRightTopUltrasonic;
  private final AnalogInput mRightBottomUltrasonic;

  private final PIDController mTurnController;

  //private final Orchestra mOrchestra;
  private final String[] mSongs;
  private final List<TalonFX> mMotors;
  private final ArrayList<TalonFX> mInstruments;

  private final TalonFX musicTalon1;
  private final TalonFX musicTalon2;
  private final TalonFX musicTalon3;
  private final TalonFX musicTalon4;

  private double leftUltrasonicValue;
  private double frontUltrasonicValue;
  private double rightTopUltrasonicValue;
  private double rightBottomUltrasonicValue;

  private int songNumber = 0;

  public DriveTrain() {
    mLeftMaster = new WPI_TalonFX(DriveConstants.kLeftMasterID);
    mLeftSlave = new WPI_TalonFX(DriveConstants.kLeftSlaveID);

    mRightMaster = new WPI_TalonFX(DriveConstants.kRightMasterID);
    mRightSlave = new WPI_TalonFX(DriveConstants.kRightSlaveID);

    musicTalon1 = new TalonFX(DriveConstants.kLeftMasterID);
    musicTalon2 = new TalonFX(DriveConstants.kRightMasterID);
    musicTalon3 = new TalonFX(DriveConstants.kLeftSlaveID);
    musicTalon4 = new TalonFX(DriveConstants.kRightSlaveID);

    mInstruments = new ArrayList<TalonFX>();

    mSongs = new String[]{
      //Filesystem.getDeployDirectory();
      "oot.chrp",
      "allstar.chrp",
      "back_in_black.chrp",
      "bonetrousle.chrp",
      "duel_of_fates.chrp",
      "hg_ss_battle_theme",
      "imperial_march.chrp",
      "losing_my_religion.chrp",
      "megalovania.chrp",
      "mii_channel.chrp",
      "ssbu.chrp",
      "thunderstruck.chrp"
    };

    mMotors = Arrays.asList(musicTalon1, musicTalon2, musicTalon3, musicTalon4);
    mInstruments.addAll(mMotors);
    //mOrchestra = new Orchestra(mInstruments);

    mLeftSlave.follow(mLeftMaster);
    mRightSlave.follow(mRightMaster);

    zeroEncoders();
    mDrive = new DifferentialDrive(mLeftMaster, mRightMaster);
    
    mLeftUltrasonic = new AnalogInput(DriveSensorConstants.kLeftUltrasonicPort);
    mFrontUltrasonic = new AnalogInput(DriveSensorConstants.kFrontUltrasonicPort);
    mRightTopUltrasonic = new AnalogInput(DriveSensorConstants.kRightTopUltrasonicPort);
    mRightBottomUltrasonic = new AnalogInput(DriveSensorConstants.kRightBottomUltrasonicPort);

    leftUltrasonicValue = 0.0;
    frontUltrasonicValue = 0.0;
    rightTopUltrasonicValue = 0.0;
    rightBottomUltrasonicValue = 0.0;

    mNavx = new AHRS(DriveSensorConstants.kNavxPort);

    mTurnController = new PIDController(Gains.kPTurn, Gains.kITurn, Gains.kDTurn, mNavx, this);
    mTurnController.setInputRange(-180.0f, 180.0f);
    mTurnController.setOutputRange(-.7, .7);
    mTurnController.setAbsoluteTolerance(0.5f);
    mTurnController.setContinuous();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void openLoopControl(double move, double turn){
    mDrive.arcadeDrive(move, turn);
  }

  public void rotateDegrees(double angle){
    //mNavx.reset();
    mTurnController.reset();
    mTurnController.setPID(Gains.kPTurn, Gains.kITurn, Gains.kDTurn);
    mTurnController.setSetpoint(angle);
    mTurnController.enable();
  }

  public double getGyroAngle(){
    return mNavx.getAngle();
  }

  public void resetNavx(){
    mNavx.reset();
  }

  public void disableTurnController(){
    mTurnController.disable();
  }

  public double getTurnControllerError(){
    return mTurnController.getError();
  }

  public void initTalonFXOLC(WPI_TalonFX driveFalcon){
    driveFalcon.configFactoryDefault();
    //driveFalcon.setInverted(true);
    driveFalcon.setNeutralMode(NeutralMode.Coast);
    //driveFalcon.neutralOutput();
    //driveFalcon.setSensorPhase(false);
    driveFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, DriveConstants.kPIDLoopIdx, DriveConstants.kTimeoutMs);
    driveFalcon.configNominalOutputForward(0.0);
    driveFalcon.configNominalOutputReverse(0.0);
    driveFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, DriveConstants.kPIDLoopIdx,DriveConstants.kTimeoutMs);
    driveFalcon.configNominalOutputForward(0.0);
    driveFalcon.configNominalOutputReverse(0.0);
    driveFalcon.configOpenloopRamp(1.5);
    driveFalcon.configClosedloopRamp(.2);
    

  }

  public double getVelocity(){
    return mNavx.getVelocityX();
  }

  public void setRightToBrake(){
    mRightSlave.setNeutralMode(NeutralMode.Brake);
  }

  public void setFollowersDeadband(){
    mRightSlave.configNeutralDeadband(0);
    mLeftSlave.configNeutralDeadband(0);
  }

  public void invertRightEncoder(){
    mRightMaster.setSensorPhase(true);
  }

  public void initTalonFXMM(WPI_TalonFX driveFalcon){
    driveFalcon.configFactoryDefault();
    //driveFalcon.setInverted(true);
    driveFalcon.setNeutralMode(NeutralMode.Brake);
    driveFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, DriveConstants.kPIDLoopIdx, DriveConstants.kTimeoutMs);
    driveFalcon.configNeutralDeadband(0, DriveConstants.kTimeoutMs);
    driveFalcon.setSensorPhase(false);
    driveFalcon.setInverted(false);
    driveFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 1, DriveConstants.kTimeoutMs);
    driveFalcon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 1, DriveConstants.kTimeoutMs);
    driveFalcon.configNominalOutputForward(0, DriveConstants.kTimeoutMs);
    driveFalcon.configNominalOutputReverse(0, DriveConstants.kTimeoutMs);
		driveFalcon.configPeakOutputForward(1, DriveConstants.kTimeoutMs);
    driveFalcon.configPeakOutputReverse(-1, DriveConstants.kTimeoutMs);
    driveFalcon.selectProfileSlot(DriveConstants.kSlotIdx, DriveConstants.kPIDLoopIdx);
		driveFalcon.config_kF(DriveConstants.kSlotIdx, AutoConstants.kGains.kFMM, DriveConstants.kTimeoutMs);
		driveFalcon.config_kP(DriveConstants.kSlotIdx, AutoConstants.kGains.kPMM, DriveConstants.kTimeoutMs);
		driveFalcon.config_kI(DriveConstants.kSlotIdx, AutoConstants.kGains.kIMM, DriveConstants.kTimeoutMs);
    driveFalcon.config_kD(DriveConstants.kSlotIdx, AutoConstants.kGains.kDMM, DriveConstants.kTimeoutMs);
    driveFalcon.configMotionCruiseVelocity(AutoConstants.kCruiseVelocity, DriveConstants.kTimeoutMs);
    driveFalcon.configMotionAcceleration(AutoConstants.kAcceleration, DriveConstants.kTimeoutMs);
    driveFalcon.setSelectedSensorPosition(0, DriveConstants.kPIDLoopIdx, DriveConstants.kTimeoutMs);
  }

  public void zeroEncoders(){
    mLeftMaster.setSelectedSensorPosition(0);
    mRightMaster.setSelectedSensorPosition(0);
  }

  public void tankDrive(double left, double right){
    mDrive.tankDrive(left, right);
  }

  public void initOLC(){
    initTalonFXOLC(mLeftMaster);
    initTalonFXOLC(mRightMaster);
  }

  public void initMM(){
    initTalonFXMM(mLeftMaster);
    //initTalonFXMM(mRightMaster);
  }

  public double getFrontUltraDistance(){
    if(Timer.getFPGATimestamp() -.2 >= (int) Timer.getFPGATimestamp())
    return frontUltrasonicValue = mFrontUltrasonic.getAverageVoltage() * DriveSensorConstants.kVoltToDistance;
    return frontUltrasonicValue;
  }
  public double getLeftUltraDistance(){
    //mLeftUltrasonic.
    leftUltrasonicValue = (Math.floor(mLeftUltrasonic.getAverageVoltage() * 10000) / 10000) * DriveSensorConstants.kVoltToDistance;
    return leftUltrasonicValue;
  }
  public double getRightTopUltraDistance(){
    if(Timer.getFPGATimestamp() -.2 >= (int) Timer.getFPGATimestamp())
    return mRightTopUltrasonic.getAverageVoltage() * DriveSensorConstants.kVoltToDistance;
    return rightTopUltrasonicValue;
  }
  public double getRightBottomUltraDistance(){
    if(Timer.getFPGATimestamp() -.2 >= (int) Timer.getFPGATimestamp())
    return mRightBottomUltrasonic.getAverageVoltage() * DriveSensorConstants.kVoltToDistance;
    return rightBottomUltrasonicValue;
  }
  public double getLeftDriveEncoderDistance(){
    return mLeftMaster.getSelectedSensorPosition();
  }
  public double getRightDriveEncoderDistance(){
    return mRightMaster.getSelectedSensorPosition();
  }
  public double getLeftDriveEncoderDistanceInches(){
    return (getLeftDriveEncoderDistance() / DriveSensorConstants.kTicksPerRev) * DriveSensorConstants.kWheelCir;
  }
  public double getRightDriveEncoderDistanceInches(){
    return (getRightDriveEncoderDistance() / DriveSensorConstants.kTicksPerRev) * DriveSensorConstants.kWheelCir;
  }
  public void setMotionMagicPosition(double position) {
    mLeftMaster.set(ControlMode.MotionMagic, position);
    //mRightMaster.set(ControlMode.MotionMagic, -position);
  }
  public double getLeftClosedLoopTarget(){
    return mLeftMaster.getClosedLoopTarget();
  }
  public double getRightClosedLoopTarget(){
    return mRightMaster.getClosedLoopTarget();
  }

  public void stopMM(){
    mLeftMaster.stopMotor();
    //mRightMaster.stopMotor();
  }

  public boolean isMMDone(){
    return (Math.abs(getLeftClosedLoopTarget() - getLeftDriveEncoderDistance()) < 20);
  }

  public void leftSlaveFollowMaster(){
    mLeftSlave.follow(mLeftMaster);
  }

  public void rightSLaveFollowMaster(){
    mRightSlave.follow(mRightMaster);
  }

  public void outputTelemetry(){
    SmartDashboard.putNumber("Gyro Angle", getGyroAngle());
    SmartDashboard.putNumber("Velocity", getVelocity());
    SmartDashboard.putNumber("Left Encoder Distance (in)", (getLeftDriveEncoderDistance() / DriveSensorConstants.kTicksPerRev) * DriveSensorConstants.kWheelCir);
    SmartDashboard.putNumber("Right Encoder Distance (in)", (getRightDriveEncoderDistance() / DriveSensorConstants.kTicksPerRev) * DriveSensorConstants.kWheelCir);

    // SmartDashboard.putNumber("Front Ultrasonic Distance", getFrontUltraDistance());
    // SmartDashboard.putNumber("Left Ultrasonic Distance", getLeftUltraDistance());
    // SmartDashboard.putNumber("Right Top Ultrasonic Distance", getRightTopUltraDistance());
    // SmartDashboard.putNumber("Right Bottom Ultrasonic Distance", getRightBottomUltraDistance());
    //SmartDashboard.putNumber("Left Encoder Distance (in)", getLeftDriveEncoderDistance());
    //SmartDashboard.putNumber("Right Encoder Distance (in)", getRightDriveEncoderDistance());
    // SmartDashboard.putNumber("left", mLeftMaster.getMotorOutputPercent());
    // SmartDashboard.putNumber("left2", mLeftSlave.getMotorOutputPercent());
    // SmartDashboard.putNumber("right", mRightMaster.getMotorOutputPercent());
    // SmartDashboard.putNumber("right2", mRightSlave.getMotorOutputPercent());

  }

  /*public void music(int songNum){
    if(songNum > mSongs.length || songNum <0){
      songNum = 0;
    }
    else{
      mOrchestra.loadMusic(mSongs[songNum]);
      mOrchestra.play();
    }
  }*/

  public int getNumberOfSongs(){
    return mSongs.length;
  }

  public void nextSong(){
    songNumber++;
  }

  public int getSong(){
    return songNumber;
  }

  @Override
  public void pidWrite(double output) {
    openLoopControl(0, -output);
  }
}
