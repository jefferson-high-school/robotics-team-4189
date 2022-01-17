/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.ElevatorSensorConstants;

public class ClimbElevator extends SubsystemBase {
  /**
   * Creates a new ClimbElevator.
   */
  private final WPI_TalonSRX mElevatorTalon;
  private final WPI_VictorSPX mAdjustmentTalon;
  private final Solenoid mElevatorSolenoid;

  private boolean isClamped;
  public ClimbElevator() {
    mElevatorTalon = new WPI_TalonSRX(ElevatorConstants.kElevatorTalonID);
    mAdjustmentTalon = new WPI_VictorSPX(ElevatorConstants.kAdjusmentTalonID);
    mElevatorSolenoid = new Solenoid(ElevatorConstants.kElevatorSolenoidPort);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void initElevatorTalonOLC(WPI_TalonSRX elevatorTalon){
    elevatorTalon.configFactoryDefault();
    elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ElevatorConstants.kPIDLoopIdx, ElevatorConstants.kTimeoutMs);
    //elevatorTalon.configOpenloopRamp(5);
    elevatorTalon.setNeutralMode(NeutralMode.Brake);
    // elevatorTalon.configForwardSoftLimitThreshold(20000);
    // elevatorTalon.configReverseSoftLimitThreshold(-100);
    // elevatorTalon.configForwardSoftLimitEnable(true);
    // elevatorTalon.configReverseSoftLimitEnable(true);

  }

  public void initElevatorTalonMM(WPI_TalonSRX elevatorTalon){
    elevatorTalon.configFactoryDefault();
    elevatorTalon.setNeutralMode(NeutralMode.Brake);
    elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ElevatorConstants.kPIDLoopIdx, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configNeutralDeadband(0.001, ElevatorConstants.kTimeoutMs);
    elevatorTalon.setSensorPhase(false);
    elevatorTalon.setInverted(false);
    elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, ElevatorConstants.kTimeoutMs);
    elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configNominalOutputForward(0, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configNominalOutputReverse(0, ElevatorConstants.kTimeoutMs);
		elevatorTalon.configPeakOutputForward(1, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configPeakOutputReverse(-1, ElevatorConstants.kTimeoutMs);
    elevatorTalon.selectProfileSlot(ElevatorConstants.kSlotIdx, ElevatorConstants.kPIDLoopIdx);
		elevatorTalon.config_kF(ElevatorConstants.kSlotIdx, ElevatorConstants.kGains.kFMM, ElevatorConstants.kTimeoutMs);
		elevatorTalon.config_kP(ElevatorConstants.kSlotIdx, ElevatorConstants.kGains.kPMM, ElevatorConstants.kTimeoutMs);
		elevatorTalon.config_kI(ElevatorConstants.kSlotIdx, ElevatorConstants.kGains.kIMM, ElevatorConstants.kTimeoutMs);
    elevatorTalon.config_kD(ElevatorConstants.kSlotIdx, ElevatorConstants.kGains.kDMM, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configMotionCruiseVelocity(ElevatorConstants.kCruiseVelocity, ElevatorConstants.kTimeoutMs);
    elevatorTalon.configMotionAcceleration(ElevatorConstants.kAcceleration, ElevatorConstants.kTimeoutMs);
    elevatorTalon.setSelectedSensorPosition(0, ElevatorConstants.kPIDLoopIdx, ElevatorConstants.kTimeoutMs);
  } 

  public void initAdjustmentTalonOLC(WPI_VictorSPX adjustmentTalon){
    adjustmentTalon.configFactoryDefault();
    adjustmentTalon.setNeutralMode(NeutralMode.Brake);
    adjustmentTalon.configOpenloopRamp(5);
  }

  public void initOLC(){
    initElevatorTalonOLC(mElevatorTalon);
  }

  public void initMM(){
    initElevatorTalonMM(mElevatorTalon);
  }

  public void zeroElevatorEncoder(){
    mElevatorTalon.setSelectedSensorPosition(0);
  }

  public void openLoopControl(double speed){
    mElevatorTalon.set(ControlMode.PercentOutput, speed);
    // if(getElevatorEncoderDistance() < -10) speed = 0;
    // if(getElevatorEncoderDistance() > 20000) speed = 0;
  }

  public void initAdjustmentOLC(){
    initAdjustmentTalonOLC(mAdjustmentTalon);
  }

  public void adjustRight(double speed){
    mAdjustmentTalon.set(ControlMode.PercentOutput, speed);
  }

  public void adjustLeft(double speed){
    mAdjustmentTalon.set(ControlMode.PercentOutput, -speed);
  }

  public double getElevatorEncoderDistance(){
    return mElevatorTalon.getSelectedSensorPosition();
  }

  public void setMotionMagicPosition(double setpoint){
    mElevatorTalon.set(ControlMode.MotionMagic, setpoint);
  }

  public double getElevatorClosedLoopTarget(){
    return mElevatorTalon.getClosedLoopTarget();
  }

  public boolean isMMDone(){
    return Math.abs(getElevatorClosedLoopTarget() - getElevatorEncoderDistance()) < 5;
  }

  public void stopMM(){
    mElevatorTalon.stopMotor();
  }
  
  // public void adjustRight(double speed){
  //   mAdjustmentTalon.set(ControlMode.PercentOutput, speed);
  // }

  // public void adjustLeft(double speed){
  //   mAdjustmentTalon.set(ControlMode.PercentOutput, -speed);
  // }
  
  // public void stopAdjustment(){
  //   mAdjustmentTalon.stopMotor();
  // }

  public void lockElevatorClamp(){
    mElevatorSolenoid.set(true);
    isClamped = false;
  }

  public void unlockElevatorClamp(){
    mElevatorSolenoid.set(false);
    isClamped = true;
  }

  public void invertEncoder(){
    mElevatorTalon.setSensorPhase(true);
  }

  public void outputTelemetry(){
    SmartDashboard.putNumber("Elevator Encoder Ticks", getElevatorEncoderDistance());
    SmartDashboard.putNumber("Elevator Encoder Distance (in)", (getElevatorEncoderDistance() / ElevatorSensorConstants.kTicksPerInch));
    SmartDashboard.putBoolean("IS Clamped?", isClamped);
  }
}
