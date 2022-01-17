/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {
  /**
   * Creates a new TurnToAngle.
   */
  private final DriveTrain mDriveTrain;
  private double angle;
  private static boolean isFinished = false;
  private static boolean inErrorZone = false;
  private static int count = 0;

  public TurnToAngle(DriveTrain driveTrain, double turnAngle) {
    this.mDriveTrain = driveTrain;
    this.angle = turnAngle;
    addRequirements(this.mDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.initOLC();
    count = 0;
    isFinished = false;
    mDriveTrain.rotateDegrees(angle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDriveTrain.outputTelemetry();
    double error = mDriveTrain.getTurnControllerError();
    inErrorZone = Math.abs(error) < 2.5;
    if (inErrorZone) {
      count++;
      isFinished = count >= 10;
    } else {
      count = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mDriveTrain.disableTurnController();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
