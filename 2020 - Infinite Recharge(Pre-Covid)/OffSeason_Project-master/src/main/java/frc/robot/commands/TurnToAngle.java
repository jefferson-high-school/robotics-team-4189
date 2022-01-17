/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrain;

public class TurnToAngle extends Command {
  private DriveTrain driveTrain;
  private double angle;
  boolean isFinished = false;
  boolean inErrorZone = false;
  public int count;

  public TurnToAngle(DriveTrain driveTrain, double angle) {
    this.driveTrain = driveTrain;
    this.angle = angle;
    requires(this.driveTrain);
  }

  @Override
  protected void initialize() {
    driveTrain.rotateDegrees(angle);
  }

  @Override
  protected void execute() {
    double error = driveTrain.getTurnControllerError();
    inErrorZone = Math.abs(error) < 2.5;
    if(inErrorZone){
      count++;
      isFinished = count >= 10;
    }
    else{
      count = 0;
    }
  }

  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  @Override
  protected void end() {
    driveTrain.disableTurnController();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
