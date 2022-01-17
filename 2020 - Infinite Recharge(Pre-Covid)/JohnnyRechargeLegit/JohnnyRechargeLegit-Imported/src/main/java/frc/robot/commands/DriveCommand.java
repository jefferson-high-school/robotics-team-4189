/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase {
  /**
   * Creates a new DriveCommand.
   */
  private final DriveTrain mDriveTrain;
  private final XboxController mDriveController;
  public DriveCommand(DriveTrain driveTrain, XboxController driveController) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.mDriveTrain = driveTrain;
    this.mDriveController = driveController;
    addRequirements(this.mDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.initOLC();    
    mDriveTrain.leftSlaveFollowMaster();
    mDriveTrain.rightSLaveFollowMaster();
    mDriveTrain.setFollowersDeadband();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //mDriveTrain.setRightToBrake();
    mDriveTrain.outputTelemetry();
    double yAxis = this.mDriveController.getY(Hand.kLeft);
    double xAxis = this.mDriveController.getX(Hand.kRight);
    ///double yAxis2 = this.mDriveController.getY(Hand.kRight);
    mDriveTrain.openLoopControl(-yAxis*.8, xAxis*.7);
    //mDriveTrain.openLoopControl(-yAxis*2, xAxis*.4);
    //mDriveTrain.tankDrive(yAxis * Math.abs(yAxis), yAxis2 * Math.abs(yAxis2));
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
