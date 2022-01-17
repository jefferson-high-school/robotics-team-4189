/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SaveTilt extends CommandBase {
  /**
   * Creates a new saveTilt.
   */
  private final Shooter shooter;
  private PrintWriter writer;
  private boolean isDone;
  
  public SaveTilt(Shooter shoot) {
    shooter = shoot;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    new File("/home/lvuser/tilt.csv");
    try {
      writer = new PrintWriter("/home/lvuser/tilt.csv");
    } catch (IOException e) { 
      e.printStackTrace();
    }
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    writer.write(""+shooter.getTiltSpeed());
    isDone=true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    writer.flush();
    writer.close();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
