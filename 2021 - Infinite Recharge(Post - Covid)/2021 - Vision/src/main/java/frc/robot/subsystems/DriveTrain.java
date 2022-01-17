// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

  

public class DriveTrain extends SubsystemBase {
  /** Creates a new DriveTrain. */
  
  private final WPI_TalonFX leftMaster;
  private final WPI_TalonFX rightMaster;
  private final WPI_TalonFX leftSlave;
  private final WPI_TalonFX rightSlave;
  private final DifferentialDrive diffDrive;
  private final XboxController controller;
  private final AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

  public DriveTrain() {
    leftMaster = new WPI_TalonFX(1);
    rightMaster = new WPI_TalonFX(2);
    leftSlave = new WPI_TalonFX(7);
    rightSlave = new WPI_TalonFX(5);
    diffDrive = new DifferentialDrive(leftMaster, rightMaster);
    controller = new XboxController(0);

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);
    
  }

  public double getDisplacement() {
    return leftMaster.getSelectedSensorPosition();

  }
  public double getAngle(){
    return ahrs.getAngle();
  }
  public void resetAngle(){
    ahrs.reset();
  }

  public void resetDisplacement(int input1)  {
    leftMaster.setSelectedSensorPosition(input1);
  }
  
  public void drive(double input1, double input2) {
    diffDrive.arcadeDrive(input1, input2);
  }



  @Override
  public void periodic() {
    //System.out.println(getDisplacement());
    // This method will be called once per scheduler run
  }
}
