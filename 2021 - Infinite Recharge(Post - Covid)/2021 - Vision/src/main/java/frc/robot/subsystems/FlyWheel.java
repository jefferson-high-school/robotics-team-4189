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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlyWheel extends SubsystemBase {
  /**
   * Creates a new FlyWheel.
   */
  private double currentTime = Timer.getFPGATimestamp();
  private double stopTime = Timer.getFPGATimestamp() + .1;
  private double velocityOne;
  private double velocityTwo;
  private double shooterSpeed = 0;
  private boolean isConstant = false;
  private final TalonSRX flyWheelTurretMotor = new TalonSRX(Constants.flyWheelTurretMotorPort);
  private final WPI_TalonFX flyWheelShooterMotor = new WPI_TalonFX(Constants.flyWheelShooterMotorPort);
  private final TalonSRX flyWheelTiltMotor = new TalonSRX(Constants.flyWheelTiltMotorPort);
  private final TalonSRX flyWheeltLoadMotor = new TalonSRX(Constants.flyWheelLoadMotorPort);
  private final Encoder tiltEncoder = new Encoder(0,1);
  public FlyWheel() {
    flyWheeltLoadMotor.setNeutralMode(NeutralMode.Brake);
    initTelemetery();
    velocityOne = flyWheelShooterMotor.getSelectedSensorVelocity();
  }
  public double getVelocity(){
    return flyWheelShooterMotor.getSelectedSensorVelocity();
  }
  public void initTelemetery(){
    SmartDashboard.putNumber("kP", 0.005);
    SmartDashboard.putNumber("kI", 0.003);
    SmartDashboard.putNumber("kD", 0);
    SmartDashboard.putNumber("Timer", 35);
  }

  public void outputTelemetery(){
    SmartDashboard.putNumber("shootVeloMotorExact", this.getVelocity());
    SmartDashboard.putNumber("Tilt encoder Value", tiltEncoder.getDistance());
  }

  public boolean isConstantVelocity(){
    return isConstant;
  }
  private void resetConstant(){
    currentTime = Timer.getFPGATimestamp();
    stopTime = Timer.getFPGATimestamp() + 1; //seconds for every check
    velocityOne = flyWheelShooterMotor.getSelectedSensorVelocity();
  }
  private void checkIfConstantVelocity(){
    //Checks if the shooter speed is constant before we shoot
    currentTime = Timer.getFPGATimestamp();
    if(stopTime < currentTime){
      velocityTwo = flyWheelShooterMotor.getSelectedSensorVelocity();
      if(Math.abs(velocityOne - velocityTwo) < 100){
        isConstant = true;
        resetConstant();
      }
      else{
        isConstant = false;
        resetConstant();
      }
    }
  }
  public void setTurretSpeed(double speed){
    flyWheelTurretMotor.set(ControlMode.PercentOutput, speed);
  }
  public void setShooterSpeed(double speed){
    //shooterSpeed = speed;
    flyWheelShooterMotor.set(speed);
  }
  public void setTiltSpeed(double speed){
    flyWheelTiltMotor.set(ControlMode.PercentOutput, speed);
  }
  public void setLoadSpeed(double speed){
    flyWheeltLoadMotor.set(ControlMode.PercentOutput, speed);
  }
  public double getTiltEncoder(){
    return tiltEncoder.get(); 
    }
  public double getAngle(){
    return getTiltEncoder();
  }
  @Override
  public void periodic() {
    this.checkIfConstantVelocity();
    //flyWheelShooterMotor.set(shooterSpeed);
    outputTelemetery();
    // This method will be called once per scheduler run
  }
}
