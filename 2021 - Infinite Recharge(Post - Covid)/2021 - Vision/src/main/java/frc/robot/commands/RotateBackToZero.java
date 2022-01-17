/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;

public class RotateBackToZero extends CommandBase {
  /** Creates a new autoTranslate. */
  private boolean done = false;
  private DriveTrain drive;
  private Camera2 cam;
  private double angle;
  private double currentAngle;
  private double speed = 0;

  public RotateBackToZero(DriveTrain drive2,Camera2 inpit) {
    drive = drive2;
    angle = drive.getAngle();
    cam = inpit;
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("State", "RotateBack");
    angle = drive.getAngle();
    System.out.println(angle);
    currentAngle = 0;
    done = false;
    if(angle <= 0)
      speed = .38;
    else if(angle > 0)
      speed = -.38;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(0, speed);
    currentAngle = drive.getAngle();
    if(Math.abs(currentAngle) < 3)
      done = true;
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.drive(0,0);
    cam.setMoving(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}