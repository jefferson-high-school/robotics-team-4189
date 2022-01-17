/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrain;

public class DriveWithJoySticks extends Command {
  private DriveTrain driveTrain;
  // private Joystick leftStick;
  // private Joystick rightStick;
  private XboxController driveController;
  public DriveWithJoySticks(DriveTrain driveTrain, XboxController driveController) {
    this.driveTrain = driveTrain;
    this.driveController = driveController;
    // this.leftStick = leftStick;
    // this.rightStick = rightStick;

    requires(this.driveTrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    // double left = leftStick.getY();
    // double right = rightStick.getY();
    // driveTrain.setSpeed(left, right);
    double yAxis = driveController.getY(Hand.kLeft);
    double xAxis = driveController.getX(Hand.kRight);
    driveTrain.setSpeed(yAxis * .6, xAxis * .3);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
