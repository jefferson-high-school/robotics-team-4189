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
import frc.robot.subsystems.Shooter;

public class TiltShooter extends CommandBase {
  /**
   * Creates a new TiltShooter.
   */
  private boolean done = false;
  private Shooter flywheel;
  private double getDisplacement;
  private double startAngle = 0;
  private double targetAngle = 0;
  private Camera2 cam;
  public TiltShooter(Shooter input1, Camera2 input2) {
    //Go to distance command for the Tilt mechanism's angle using encoders and a given value from the camera.
    //The camera outputs which zone we are in, and we can set the angle we need our tilt at using that value.

    flywheel = input1;
    done = false;
    cam = input2;
    startAngle = flywheel.getTiltSpeed();
    targetAngle = cam.readAngle();
    targetAngle = (targetAngle - startAngle);
    getDisplacement = 0;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("State", "Tilt");
    //flywheel.setShooterSpeed(1);
    done = false;
    startAngle = flywheel.getTiltSpeed();
    targetAngle = (cam.readAngle());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    getDisplacement = flywheel.getTiltSpeed();
    if(Math.abs(getDisplacement) > Math.abs(targetAngle)) {
      done = true;
    }
    if(targetAngle > 0){
      flywheel.tilt(-1);
    }
    else{
      flywheel.tilt(1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flywheel.tilt(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
