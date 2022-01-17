/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveWithJoy extends CommandBase {
  /**
   * Creates a new DriveWithJoy.
   */
  private final DriveTrain mDriveTrain;
  private final Joystick mJoystick1;
  private final Joystick mJoystick2;
  public DriveWithJoy(DriveTrain driveTrain, Joystick joy1, Joystick joy2) {
    // Use addRequirements() here to declare subsystem dependencies.
    mDriveTrain = driveTrain;
    mJoystick1 = joy1;
    mJoystick2 = joy2;
    addRequirements(mDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.initOLC();
    mDriveTrain.outputTelemetry();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double y1 = mJoystick1.getY();
    double y2 = mJoystick2.getY();
    mDriveTrain.tankDrive(y1, y2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
