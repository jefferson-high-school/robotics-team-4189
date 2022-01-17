/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.FlyWheel;

public class ShootBalls extends CommandBase {
  /**
   * Creates a new ShootBalls.
   */
  private boolean isDone;
  private double startTime = 0;
  private double endTime = 0;
  private FlyWheel flywheel;

  public ShootBalls(FlyWheel input1) {
    flywheel = input1;
    addRequirements(flywheel);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
    endTime = Timer.getFPGATimestamp() + 3;
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    startTime = Timer.getFPGATimestamp();
    flywheel.setLoadSpeed(-.7);
    if(startTime > endTime){
      isDone = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flywheel.setLoadSpeed(0);
    flywheel.setShooterSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
