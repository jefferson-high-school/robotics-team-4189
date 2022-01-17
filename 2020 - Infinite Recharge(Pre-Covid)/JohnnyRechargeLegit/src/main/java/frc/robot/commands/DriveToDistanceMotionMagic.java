/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveSensorConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DumpMechanism;

public class DriveToDistanceMotionMagic extends CommandBase {
  /**
   * Creates a new DriveToDistanceMotionMagic.
   */
  private final DriveTrain mDriveTrain;
  private final DumpMechanism mDumpMechanism;

  private double distance = 0;
  public DriveToDistanceMotionMagic(DriveTrain driveTrain, double distanceToDrive, DumpMechanism dumper) {

    // Use addRequirements() here to declare subsystem dependencies.
    this.mDriveTrain = driveTrain;
    this.distance = distanceToDrive;
    this.mDumpMechanism = dumper;
    addRequirements(this.mDriveTrain, this.mDumpMechanism);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //mDumpMechanism.spinRollersForwards(1);
    mDriveTrain.zeroEncoders();
    mDriveTrain.initMM();
    mDriveTrain.outputTelemetry();
    mDriveTrain.zeroEncoders();
    mDriveTrain.leftSlaveFollowMaster();
    //mDriveTrain.rightSLaveFollowMaster();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDriveTrain.setMotionMagicPosition((distance / DriveSensorConstants.kWheelCir) * DriveSensorConstants.kTicksPerRev);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mDriveTrain.stopMM();
    mDriveTrain.initOLC();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mDriveTrain.isMMDone();
  }
}
