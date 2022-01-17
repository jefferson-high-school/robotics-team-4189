/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.io.PrintWriter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */
  private final WPI_TalonFX leftMaster = new WPI_TalonFX(1);
  private final WPI_TalonFX rightMaster = new WPI_TalonFX(2);
  private final WPI_TalonFX leftSlave = new WPI_TalonFX(7);
  private final WPI_TalonFX rightSlave = new WPI_TalonFX(5);

  private final DifferentialDrive differentialDrive;
  

  public DriveTrain() {
    initTelemetry();
    rightSlave.follow(rightMaster);
    leftSlave.follow(leftMaster);
    differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.configOpenloopRamp(1.5);
    leftMaster.configOpenloopRamp(1.5);
    rightMaster.configClosedloopRamp(1.5);
    leftMaster.configClosedloopRamp(1.5);
    


    this.resetEncoder();
  }

  public void drive(double driveSpeed, double rotationSpeed){
    differentialDrive.arcadeDrive(driveSpeed* .6, rotationSpeed* .7);
  }

  public void initTelemetry(){
    // if(!SmartDashboard.containsKey("GamePieceAuto"))
      SmartDashboard.putString("GamePieceAuto", "Output");
    if(!SmartDashboard.containsKey("kPDrive"))
      SmartDashboard.putNumber("kPDrive", 0);
    if(!SmartDashboard.containsKey("kIDrive"))
      SmartDashboard.putNumber("kIDrive", 0);
    if(!SmartDashboard.containsKey("kDDrive"))
      SmartDashboard.putNumber("kDDrive", 0);
    if(!SmartDashboard.containsKey("Feet to Translate"))
      SmartDashboard.putNumber("Feet to Translate", 0);
    if(!SmartDashboard.containsKey("peed limit"))
      SmartDashboard.putNumber("speed limit", 1);


    
  }

  public void outputTelemetry(){
      this.initTelemetry();
    SmartDashboard.putNumber("encoder left", this.getEncoderLeft());
    SmartDashboard.putNumber("encoder right", this.getEncoderRight());
    // SmartDashboard.putNumber("velocity left", this.getVelocityLeft());
    // SmartDashboard.putNumber("velocity right", this.getVelocityRight());

  }
  // public void setAuto(boolean b){
  //   SmartDashboard.putBoolean("on", b);
  // }

  public void resetEncoder(){
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  public int getEncoderLeft(){
    return leftMaster.getSelectedSensorPosition();
  }

  public int getEncoderRight(){
    return rightMaster.getSelectedSensorPosition();
  }

  public int getVelocityRight(){
    return rightMaster.getSelectedSensorVelocity();
  }

  public int getVelocityLeft(){
    return leftMaster.getSelectedSensorVelocity();
  }

  public PrintWriter initWriter(PrintWriter writer, String name) {
    try {
      writer = new PrintWriter(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return writer;
  }

  @Override
  public void periodic() {
    this.outputTelemetry();
  
    // This method will be called once per scheduler run
  }
}
