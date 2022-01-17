/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.VideoCamera;
//import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Autofix;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //Joysticks
  //public static Joystick primaryStick = new Joystick(RobotMap.primaryJoystickPort);
  public static Joystick secondaryStick = new Joystick(RobotMap.secondaryJoystickPort);
  
  //Cameras
  public static VideoCamera camera;
  
  //+public static SerialPort Usb = new SerialPort(115200, SerialPort.Port.kUSB1);;
  //public static SerialPort Usb = new SerialPort(115200, SerialPort.Port.kUSB1);

  public static XboxController driveStick = new XboxController(RobotMap.controllerPort);


  //Primary Stick Buttons
  //public static Button cameraAlign = new JoystickButton(primaryStick, 2);
  // public static Button onBoarderFoward = new JoystickButton(primaryStick, 1);
  // public static Button onBoarderReverse = new JoystickButton(primaryStick, 12);
  // public static Button grabHatch = new JoystickButton(primaryStick, 5);
  // public static Button placeHatch = new JoystickButton(primaryStick, 6);
  // public static Button onBoarder = new JoystickButton(primaryStick, 1);


  //Secondary Stick Buttons
  public static Button climbDown = new JoystickButton(secondaryStick, 7);
  public static Button openHatch = new JoystickButton(secondaryStick, 10);
  public static Button climbFrontUp = new JoystickButton(secondaryStick, 9);
  //public static Button climbRearDown = new JoystickButton(secondaryStick, 8);
  public static Button climbRearUp = new JoystickButton(secondaryStick, 11);
  // public static POVButton driveForwards1 = new POVButton(secondaryStick, 0);
  // public static POVButton driveForwards2 = new POVButton(secondaryStick, 45);
  // public static POVButton driveForwards3 = new POVButton(secondaryStick, 315);
  public static Button closeHatch = new JoystickButton(secondaryStick, 12);
  public static Button shootBallLauncher = new JoystickButton(secondaryStick, 1);
  public static Button retractBallLauncher = new JoystickButton(secondaryStick, 2);
  public static Button onBoarderDown = new JoystickButton(secondaryStick, 4);
  public static Button onBoarderUp = new JoystickButton(secondaryStick, 6);
  public static Button raiseLauncher = new JoystickButton(secondaryStick, 5);
  public static Button lowerLauncher = new JoystickButton(secondaryStick, 3);
 // public static Button cameraAlign = new JoystickButton(secondaryStick, 8);

  
  //Gyro
  public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
  // private void createSerial(){
  //   try {
  //     Usb = new SerialPort(115200, SerialPort.Port.kUSB1);
  //   } catch (UncleanStatusException exception) {
  //     // Birds are good!
  //     System.out.println("jokes on you we dont have camera");
  //     SmartDashboard.getBoolean("Camera",false);
  //   }
  // }
  public OI(){
   // cameraAlign.toggleWhenPressed(new Autofix());
    //createSerial();
  }
  
}
