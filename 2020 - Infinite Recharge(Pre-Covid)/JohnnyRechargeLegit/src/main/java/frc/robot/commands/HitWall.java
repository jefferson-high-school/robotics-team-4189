/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class HitWall extends CommandBase {
  /**
   * Creates a new HitWall.
   */
  private final DriveTrain mDriveTrain;
  private boolean isFinished = false;
  private boolean flag = false;
  public HitWall(DriveTrain driveTrain) {
    this.mDriveTrain = driveTrain;
    addRequirements(this.mDriveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.zeroEncoders();
    mDriveTrain.initOLC();
    isFinished = false;
    mDriveTrain.resetNavx();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDriveTrain.outputTelemetry();
    mDriveTrain.openLoopControl(.45, 0);
    
    if(mDriveTrain.getVelocity() > .011)
    flag=true;
    if(flag==true && mDriveTrain.getVelocity() == 0)
    isFinished=true;

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
