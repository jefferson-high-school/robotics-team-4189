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

public class ControlElevatorHeight extends CommandBase {
  /**
   * Creates a new LowerElevator.
   */
  private final ClimbElevator mElevator;
  private final XboxController mSecondaryController;
  public ControlElevatorHeight(ClimbElevator elevator, XboxController secondaryController) {
    this.mElevator = elevator;
    this.mSecondaryController = secondaryController;
    addRequirements(this.mElevator);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mElevator.initOLC();
    mElevator.zeroElevatorEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //mElevator.invertEncoder();
    mElevator.outputTelemetry();
    double yAxisRight = mSecondaryController.getY(Hand.kRight);
    mElevator.openLoopControl(yAxisRight);
    double rightTrigger = mSecondaryController.getTriggerAxis(Hand.kRight);
    double leftTrigger = mSecondaryController.getTriggerAxis(Hand.kLeft);
    if(rightTrigger > .1)
    mElevator.adjustRight(rightTrigger);
    if(leftTrigger > .1)
    mElevator.adjustLeft(leftTrigger);
    if(rightTrigger < .1 && leftTrigger < .1){
      mElevator.adjustLeft(0);
      mElevator.adjustRight(0);
    }
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
