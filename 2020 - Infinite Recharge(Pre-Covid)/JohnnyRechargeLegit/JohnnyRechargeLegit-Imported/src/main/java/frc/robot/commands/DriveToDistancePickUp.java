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

public class DriveToDistancePickUp extends CommandBase {
  private final DriveTrain mDriveTrain;
  private final DumpMechanism mDumpMechanism;
  private double distance = 0;
  private boolean isFinished = false;
  public DriveToDistancePickUp(DriveTrain driveTrain, double setpoint, DumpMechanism dumper) {
    this.mDriveTrain = driveTrain;
    this.mDumpMechanism = dumper;
    this.distance = setpoint;
    addRequirements(this.mDriveTrain, this.mDumpMechanism);
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDriveTrain.zeroEncoders();
    mDriveTrain.initOLC();
    isFinished = false;
    mDriveTrain.resetNavx();
    mDumpMechanism.spinRollersOLC(1);
    //mDriveTrain.openLoopControl(.5, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDriveTrain.outputTelemetry();
    if(-mDriveTrain.getRightDriveEncoderDistanceInches() < distance || mDriveTrain.getLeftDriveEncoderDistanceInches() < distance){
      mDriveTrain.openLoopControl(.45, 0);
    }
    else{
      isFinished = true;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mDriveTrain.openLoopControl(0, 0);
    mDumpMechanism.spinRollersOLC(0);
    //mDriveTrain.initOLC();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
