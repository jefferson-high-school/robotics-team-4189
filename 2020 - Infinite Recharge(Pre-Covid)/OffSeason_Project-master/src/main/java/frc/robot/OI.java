/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.ElevatorState;
import frc.robot.commands.MoveToSetpoint;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;

public class OI {
  public XboxController driveController = new XboxController(Constants.kDriveControllerPort); 
  // public Joystick leftStick = new Joystick(Constants.kLeftStickPort);
  // public Joystick rightStick = new Joystick(Constants.kRightStickPort);
  public Button turn = new JoystickButton(driveController, Constants.kTurnToAngle);
  public Button zeroElevator = new JoystickButton(driveController, Constants.kZeroElevator);
  public Button level1Elevator = new JoystickButton(driveController, Constants.kLevel1Elevator);
  public Button level2Elevator = new JoystickButton(driveController, Constants.kLevel2Elevator);
  public Button level3Elevator = new JoystickButton(driveController, Constants.kLevel3Elevator);

  public OI(DriveTrain driveTrain, Elevator elevator){
    turn.whenPressed(new TurnToAngle(driveTrain, Constants.kTurnDegrees));
    zeroElevator.whenPressed(new MoveToSetpoint(elevator, ElevatorState.ZERO));
    level1Elevator.whenPressed(new MoveToSetpoint(elevator, ElevatorState.LEVEL_1));
    level2Elevator.whenPressed(new MoveToSetpoint(elevator, ElevatorState.LEVEL_2));
    level3Elevator.whenPressed(new MoveToSetpoint(elevator, ElevatorState.LEVEL_3));
  }
}
