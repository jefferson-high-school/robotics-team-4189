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

public class Camera2 extends SubsystemBase {
  /**
   * Creates a new Camera2.
   */
  SerialPort cam;
  String readString;
  double zone;
  int skip;
  double angle;
  double distanceX;
  double distanceY;
  double offsetPixels;
  boolean isMoving = false;
  public Camera2() {
    cam = new SerialPort(9600, SerialPort.Port.kUSB1);
    readString = "";
    zone = 0;
    skip = 0;
    angle = 0;
    distanceX = -1;
    distanceY = -1;
    offsetPixels = -1;
  }

  private double readValues(char startCharacter, char endCharacter){
    //The readString method from the SerialPort requires clean up in order for us to use the values
    //Sometimes the values come in as nothing so we much check for an empty string
    //You can remove the spaces between each character by using String methods
    //Convert the String to doubles once clean up is complete
    if(readString.length() > 0 && readString.charAt(0) != '$'){
      readString = cam.readString();
    }
    readString = readString.replaceAll("\\s", "");
    int start = readString.indexOf(startCharacter);
    int cutOff = readString.indexOf(endCharacter);
    if(cutOff != -1) 
      return Double.parseDouble(readString.substring(start + 1, cutOff));
    return -12345;
  }

  public double readCenterPixels(){
    double input = this.readValues('$','%');
    if(input == -12345){
      return distanceX;
    }
    distanceX = input;
    return distanceX;
  }
  public double readOffsetPixels(){
    double input = this.readValues('&','*');
    if(input == -12345){
      return offsetPixels;
    }
    offsetPixels = input;
    return distanceX;
  }
  public double readZoneDistance(){
    double input = this.readValues('!','^');
    if(input == -12345){
      return distanceY;
    }
    if(zone == 0)
      distanceY = input - 242.14;
    else if(zone == 1)
      distanceY = input - 242.14;
    else if(zone == 2)
      distanceY = input - 185.4;
    else if(zone == 3)
      distanceY = input - 125;
    else if(zone == 4)
      distanceY = input - 86;
    return distanceY;
  }
  /*public double readZone(){
    double input = this.readValues('#','@');
    if(input == -12345){
      return zone;
    }
    zone = input;
    return zone;
  }*/
  public double readAngle(){
    //These set the different encoder ticks for each zone.
    if(zone == 0)
      angle = -4000;
    else if(zone == 1)
      angle = -4000;
    else if(zone == 2)
      angle = -7000;
    else if(zone == 3)
      angle = -13000;
    else if(zone == 4)
      angle = -23000;
    return angle;
  }
  public void setZone(int targetZone){
    zone = targetZone;
  }
  public void setMoving(boolean moving){
    isMoving = moving;
  }

  @Override

  public void periodic() {
    readString = cam.readString();
    SmartDashboard.putString("String From Camera", readString);
    SmartDashboard.putNumber("DistanceX", this.readCenterPixels());
    SmartDashboard.putNumber("DistanceY", this.readZoneDistance());
    //SmartDashboard.putNumber("Zone", this.readZone());
    SmartDashboard.putNumber("Angle From Camera", this.readAngle());
System.out.println(readString);
  }
}
