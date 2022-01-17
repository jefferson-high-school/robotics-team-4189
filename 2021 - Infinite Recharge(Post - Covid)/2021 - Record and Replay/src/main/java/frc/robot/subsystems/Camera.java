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
  SerialPort cam;
  String alaa = "";
  private int alaaNum;
  public Camera() {
    try {
      // cam = new SerialPort(115200, SerialPort.Port.kUSB);
    } catch (Exception e) {

    }
  }
  public void outputTelemetry(){
    SmartDashboard.getString("alaa", alaa);
    SmartDashboard.putNumber("direction", alaaNum);
  }

  public boolean direction(){
    if(alaaNum == 1)
      return true;
    if(alaaNum == 0)
      return false;
    return true;
  }
  public String initAlaa(){
    try {
      // alaa = cam.readString();
    } catch (Exception e) {
      alaa = "0";
    }
    if(alaa.length() != 0 && !alaa.equals("")){
      alaa = alaa.substring(0, 1);
      if(alaa.equals("1") || alaa.equals("0"))
        alaaNum = Integer.valueOf(alaa);
    }
    return alaa;
  }
  @Override
  public void periodic() {
    this.outputTelemetry();
    alaa = this.initAlaa();
    // This method will be called once per scheduler run
  }
}
