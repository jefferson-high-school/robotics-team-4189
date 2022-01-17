// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraJuice extends SubsystemBase {
  //private SerialPort juicy = new SerialPort(9600, SerialPort.Port.kUSB1);
  private int cummies = 0;
  private int centerInt = 0;
  

  /** Creates a new CameraJuice. */
  public CameraJuice() {

  }
  public int returnCummies(){

    return cummies;
  }
  @Override
  public void periodic() {
    String centerValue = "stirng";
    int start = centerValue.indexOf('~');
    int cutOff = centerValue.indexOf('$');
    if(cutOff != -1) 
    centerInt = Integer.parseInt(centerValue.substring(start + 2, cutOff - 1));
    // This method will be called once per scheduler run
    cummies = centerInt;
  }
  
}
