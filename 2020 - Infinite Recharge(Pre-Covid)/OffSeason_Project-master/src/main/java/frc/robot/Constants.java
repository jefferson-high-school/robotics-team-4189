/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {
  /////////// JoyStick Ports ////////////
  public static int kLeftStickPort = 0;
  public static int kRightStickPort = 1;
  public static int kDriveControllerPort = 0;

  /////////// Device IDs ////////////
  public static int kLeftDriveMasterID = 1;
  public static int kLeftDriveSlaveID = 4;
  public static int kRightDriveMasterID = 2;
  public static int kRightDriveSlaveID = 3;
  public static int kElevatorID = 2;

  /////////// Data ////////////
  public static int kLeftEncoderPortA = 0;
  public static int kLeftEncoderPortB = 1;
  public static int kRightEncoderPortA = 2;
  public static int kRightEncoderPortB = 3;

  /////////// Auto ////////////
  public static final double kWheelDiameter = 0.0;
  public static final double kMaxVelocity = 0.0;
  public static final double kTicksPerRev = 0.0;
  public static final String kPathName = "CargoFront";

  /////////// Buttons ////////////
  public static final int kTurnToAngle = 1;
  public static final int kZeroElevator = 2;
  public static final int kLevel1Elevator = 3;
  public static final int kLevel2Elevator = 4;
  public static final int kLevel3Elevator = 5;

  /////////// DriveTrain ////////////
  public static final int kTurnDegrees = 180;

  /////////// Elevator ////////////
  public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public static final int kCruiseVelocity = 1500;
  public static final int kAcceleration = 3000;
  public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);
  public static final int kLevel1 = 4000;
  public static final int kLevel2 = 10000;
  public static final int kLevel3 = 16000;
  
}
