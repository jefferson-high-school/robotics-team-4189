/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DumpMechanism;

public class FollowWall extends CommandBase {
  /**
   * Creates a new FollowWall.
   */
  private final DriveTrain mDriveTrain;
  private final DumpMechanism mDumpMechanism;
  private static boolean linedUp = false;
  public FollowWall(DriveTrain driveTrain, DumpMechanism dumper) {
    this.mDriveTrain = driveTrain;
    this.mDumpMechanism = dumper;
    addRequirements(this.mDriveTrain, this.mDumpMechanism);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.resetNavx();
    linedUp = false;
    mDumpMechanism.spinRollersForwards(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(mDriveTrain.getRightBottomUltraDistance() > 1.5){
      mDriveTrain.openLoopControl(-.6, .5);
    }
    else if(mDriveTrain.getRightBottomUltraDistance() < 1.1){
      mDriveTrain.openLoopControl(-.6, -.5);
    }
    else if(mDriveTrain.getRightBottomUltraDistance() > 1.1 && mDriveTrain.getRightBottomUltraDistance() < 1.5 && mDriveTrain.getGyroAngle() > 2){
      mDriveTrain.openLoopControl(-.6, -.6);
    }
    else if(mDriveTrain.getRightBottomUltraDistance() > 1.1 && mDriveTrain.getRightBottomUltraDistance() < 1.5 && mDriveTrain.getGyroAngle() < -2){
      mDriveTrain.openLoopControl(-.6, .6);
    }
    else if(mDriveTrain.getRightBottomUltraDistance() > 1.1 && mDriveTrain.getRightBottomUltraDistance() < 1.5 && mDriveTrain.getGyroAngle() > -2 && mDriveTrain.getGyroAngle() < 2){
      linedUp = true;
      mDriveTrain.openLoopControl(-.6, 0);
    }
    else{
      mDriveTrain.openLoopControl(-.6, 0);
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
    if(mDriveTrain.getFrontUltraDistance() < 1.7 && linedUp){
      return true;
    }else{
      return false;
    }
  }
}
