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
import frc.robot.subsystems.ClimbElevator;

public class AdjustBalance extends CommandBase {
  /**
   * Creates a new AdjustBalance.
   */
  private final ClimbElevator mElevator;
  private final XboxController mDriveController;
  public AdjustBalance(ClimbElevator elevator, XboxController driveController) {
  this.mElevator = elevator;
  this.mDriveController = driveController;
  addRequirements(this.mElevator);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mElevator.initAdjustmentOLC();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     double leftTrigger = mDriveController.getTriggerAxis(Hand.kLeft);
     double rightTrigger = mDriveController.getTriggerAxis(Hand.kRight);
     mElevator.adjustLeft(leftTrigger);
     mElevator.adjustRight(rightTrigger);
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
