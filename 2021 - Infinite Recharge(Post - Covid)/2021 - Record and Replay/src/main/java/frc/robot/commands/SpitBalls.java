/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Onboarder;

public class SpitBalls extends CommandBase {
  /**
   * Creates a new SpitBalls.
   */
  private final Onboarder onboarder;
  private boolean end;
  private int time;
  public SpitBalls(Onboarder onboard) {
    onboarder = onboard;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(onboarder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = (int)Timer.getFPGATimestamp();
    end = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    onboarder.onboard(-1);
    if(Timer.getFPGATimestamp() > time+ 10)
      end = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    onboarder.onboard(0);
    end = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
