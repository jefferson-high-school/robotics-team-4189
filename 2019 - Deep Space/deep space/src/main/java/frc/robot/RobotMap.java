/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
   
  public static int pneumaticOnBoarderSolePort = 3;
  public static int pneumaticHatchO_CSolePort = 4;
  public static int pneumaticHatchF_BSolePort = 1;
  public static int pneumaticFrontClimbSolePort = 2;
  public static int pneumaticBackClimbSolePort = 0;
  public static int pneumaticLauncher = 6;
  public static int pneumaticLauncherHeight = 5;

  //USB 
  public static int controllerPort = 0;
  //public static int primaryJoystickPort = 2;
  public static int secondaryJoystickPort = 1;
  //Analog
  

  //DIO
  // public static int limitSwitchPort = 0;
  // public static int opticDevicePort = 1;
  // public static int encA1Port = 0;
  // public static int encA2Port = 1;
  // public static int encB1Port = 4;
  // public static int encB2Port = 5;
}