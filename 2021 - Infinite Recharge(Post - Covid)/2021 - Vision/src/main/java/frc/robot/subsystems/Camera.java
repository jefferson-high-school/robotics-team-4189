/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {
  /**
   * Creates a new Camera.
   */

  boolean direction = false;
  //SerialPort cam = new SerialPort(115200, SerialPort.Port.kUSB);
  String alaa = "";
  public Camera() {

  }

  public boolean direction(){
    if(alaa.equals("1")){
      return true;
    }
    else if(alaa.equals("0")){
      return false;
    }
    return false;
  }
  @Override
  public void periodic() {
    //alaa = cam.readString();
    if(alaa.length() != 0){
      alaa = alaa.substring(0, 1);
    }
    // This method will be called once per scheduler run
  }
}
