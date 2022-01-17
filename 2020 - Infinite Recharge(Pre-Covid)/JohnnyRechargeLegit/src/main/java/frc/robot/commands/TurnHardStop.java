/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TurnHardStop extends CommandBase {
  /**
   * Creates a new TurnHardStop.
   */
  DriveTrain driveTrain;
  boolean finished = false;
  double angle = 0;
  public TurnHardStop(DriveTrain drivetrain) {
    driveTrain = drivetrain;
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.resetNavx();
    driveTrain.initOLC();
    finished = false;
    angle = driveTrain.getGyroAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override

  public void execute() {
    angle = driveTrain.getGyroAngle();
    System.out.println(angle);
    //driveTrain.openLoopControl(0, .535); / -15
    driveTrain.tankDrive(0, .7);
    if(angle < -90 + 8){

      finished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.openLoopControl(0, 0);
    angle = driveTrain.getGyroAngle();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
