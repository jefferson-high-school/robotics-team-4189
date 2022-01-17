/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveToDistanceBack extends CommandBase {
  /**
   * Creates a new DriveToDistanceBack.
   */
  private final DriveTrain mDriveTrain;
  private double distance = 0;
  private boolean isFinished = false;
  public DriveToDistanceBack(DriveTrain driveTrain, double setpoint) {
    this.mDriveTrain = driveTrain;
    this.distance = setpoint;
    addRequirements(this.mDriveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.zeroEncoders();
    mDriveTrain.initOLC();
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDriveTrain.outputTelemetry();
    if(-mDriveTrain.getRightDriveEncoderDistanceInches() > distance || mDriveTrain.getLeftDriveEncoderDistanceInches() > distance){
      mDriveTrain.openLoopControl(-.5, 0);
    }
    else{
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mDriveTrain.openLoopControl(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
