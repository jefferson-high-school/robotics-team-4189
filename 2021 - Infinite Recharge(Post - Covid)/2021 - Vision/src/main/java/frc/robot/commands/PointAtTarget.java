/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FlyWheel;

public class PointAtTarget extends CommandBase {
  /**
   * Creates a new PointAtTarget.
   */
  FlyWheel flywheel;
  Camera2 cam;
  int center = 120;
  double pixels;
  boolean isDone = false;
private double pixelsOffset = 12;
  public PointAtTarget(FlyWheel input1, Camera2 input2) {
    flywheel = input1;
    cam = input2;
    addRequirements(flywheel,cam);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(pixels);
    if((-8 >= pixels && -10<= pixels)){
      isDone = true;
      flywheel.setTurretSpeed(0);
      System.out.println("not spinning");
    }
    else if(-8 < pixels){
      flywheel.setTurretSpeed(.3);
      System.out.println("right");
    }
    else if(-10 > pixels){
      flywheel.setTurretSpeed(-.3);
      System.out.println("left");
    }
    pixels = cam.readOffsetPixels();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isDone = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
