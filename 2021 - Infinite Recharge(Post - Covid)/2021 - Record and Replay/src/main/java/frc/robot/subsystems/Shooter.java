/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private final TalonSRX tiltMotor;
  private final TalonSRX loadMotor;
  private final TalonSRX turretMotor;
  private final WPI_TalonFX shooterMotor;
  private final Encoder tiltEncoder;
  public Shooter() {

    tiltMotor = new TalonSRX(12);
    loadMotor = new TalonSRX(10);
    turretMotor = new TalonSRX(11);
    shooterMotor = new WPI_TalonFX(3);
    tiltEncoder = new Encoder(0, 1);
    initTelemetery();
    loadMotor.setNeutralMode(NeutralMode.Brake);
  }
  
  public void initTelemetery(){
    // if(!SmartDashboard.containsKey("kP"))
    //   SmartDashboard.putNumber("kP", 0.005);
    // if(!SmartDashboard.containsKey("kI"))
    //   SmartDashboard.putNumber("kI", 0.003);
    // if(!SmartDashboard.containsKey("kD"))
    //   SmartDashboard.putNumber("kD", 0);
    // if(!SmartDashboard.containsKey("Timer"))
    //   SmartDashboard.putNumber("Timer", 8);
      if(!SmartDashboard.containsKey("targetAngle"))
      SmartDashboard.putNumber("targetAngle", 0);
  }

  public void outputTelemetery(){
    this.initTelemetery();
    SmartDashboard.putNumber("shootVeloMotorExact", this.getShootVelocity());
    SmartDashboard.putNumber("Tilt encoder Value", tiltEncoder.get());
  }

  public void tilt(double speed){
    tiltMotor.set(ControlMode.PercentOutput, speed);
  }

  public double getTiltSpeed(){
    return this.tiltEncoder.get();
  }
  public void setInitTilt(String s){
this.tiltEncoder.reset();
  }

  public void load(double speed){
    loadMotor.set(ControlMode.PercentOutput, speed);
  }

  public void swivel(double speed){
    turretMotor.set(ControlMode.PercentOutput, speed);
  }

  public void shoot(double speed){
    shooterMotor.set(speed);
  }
  public int getShootVelocity(){
    return shooterMotor.getSelectedSensorVelocity();
  }

  @Override
  public void periodic() {
    outputTelemetery();
    
    // This method will be called once per scheduler run
  }
}
