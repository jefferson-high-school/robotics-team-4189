/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;

public class MoveToSetpoint extends Command {
  private Elevator elevator;
  private ElevatorState level;

  public MoveToSetpoint(Elevator elevator, ElevatorState level) {
    this.elevator = elevator;
    this.level = level;
    requires(this.elevator);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    int setpoint = elevator.getSetpoint(level);
    elevator.setMotionMagicPosition(setpoint);
  }

  @Override
  protected boolean isFinished() {
      return elevator.isMotionMagicDone();
  }

  @Override
  protected void end() {
    elevator.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
