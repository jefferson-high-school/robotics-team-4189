/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbElevator;
import frc.robot.subsystems.DumpMechanism;

public class RaiseToSetPoint extends CommandBase {
  /**
   * Creates a new RaiseToSetPoint.
   */
  private final ClimbElevator mElevator;
  private final DumpMechanism mDUmpMechanism;
  private double setpoint = 0;
  private boolean isFinished;
  public RaiseToSetPoint(ClimbElevator elevator, double setpoint, DumpMechanism dumper) {
    this.mElevator = elevator;
    this.mDUmpMechanism = dumper;
    this.setpoint = setpoint;
    addRequirements(this.mElevator, this.mDUmpMechanism);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mElevator.zeroElevatorEncoder();
    mElevator.initOLC();
    isFinished = false;
    mDUmpMechanism.dumpCells(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mElevator.outputTelemetry();
    if(mElevator.getElevatorEncoderDistance() < setpoint){
      mElevator.openLoopControl(.8);
    }
    else{
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mElevator.openLoopControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
