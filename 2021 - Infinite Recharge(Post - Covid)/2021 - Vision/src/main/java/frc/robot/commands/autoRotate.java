/*----------------------------------------------------------------------------*/
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;


public class autoRotate extends CommandBase {
  /** Creates a new autoTranslate. */
  private boolean done = false;
  private DriveTrain drive;
  private double angle;
  private double currentAngle;
  private double speed = 0;
  int target = 0;
  private Camera2 cam;
  public autoRotate(DriveTrain drive2, Camera2 input2,int targetZone) {
    target = targetZone;
    cam = input2;
    drive = drive2;
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    cam.setMoving(true);
    //angle = cam.readCenterPixels();
    //System.out.println(cam.readCenterPixels());
    SmartDashboard.putString("State", "Rotate");
    angle = Math.atan(cam.readZoneDistance() / cam.readCenterPixels());
    drive.resetAngle();
    currentAngle = 0;
    done = false;
    if(angle >= 0){
      speed = -.4;
    }
    else if(angle < 0){
      speed = .4;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println(cam.readCenterPixels());
    drive.drive(0, speed);
    currentAngle = drive.getAngle();
    if(Math.abs(currentAngle + 2) > Math.abs(angle))
      done = true;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.drive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}