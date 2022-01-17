/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
  }
  
  public void initTelemetery(){
    SmartDashboard.putNumber("kP", 0.005);
    SmartDashboard.putNumber("kI", 0.003);
    SmartDashboard.putNumber("kD", 0);
    SmartDashboard.putNumber("Timer", 30);
  }

  public void outputTelemetery(){
    SmartDashboard.putNumber("shootVeloMotorExact", this.getShootVelocity());
    SmartDashboard.putNumber("Tilt encoder Value", tiltEncoder.getDistance());
  }

  public void tilt(double speed){
    tiltMotor.set(ControlMode.PercentOutput, speed);
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
