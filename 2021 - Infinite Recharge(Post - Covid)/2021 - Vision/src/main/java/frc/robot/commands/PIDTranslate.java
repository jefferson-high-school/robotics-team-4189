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
import frc.robot.subsystems.DriveTrain;

public class PIDTranslate extends CommandBase {
  /**
   * Creates a new PIDTranslate.
   */
  private final DriveTrain driveTrain;
  private final Camera2 cam;
  private double distance;
  private double kP = .00007; //.00004
  private double kD = .00000;//.000004
  private double kI = .0000; //0.00002
  private double iLimit = 1000;
  private double error = 0;
  private double lastError = 0;
  private boolean isDone = false;
  private double lastTimeStamp = 0;
  private double dt = 0;
  private double errorRate = 0;
  private double errorSum = 0;
  private int c = 0;
  public PIDTranslate(DriveTrain driveTrainInput, Camera2 input) {
    driveTrain = driveTrainInput;
    cam = input;
    distance = (11822.6 * (Math.sqrt((1296 + cam.readCenterPixels() * cam.readCenterPixels())) / 12));
    //distance = 2 * 11822.6;
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.resetDisplacement(0);
    distance = (11822.6 * (Math.sqrt((1296 + cam.readCenterPixels() * cam.readCenterPixels())) / 12));
    //System.out.println(distance);
    error = distance - Math.abs(driveTrain.getDisplacement());
    isDone = false;
    dt = 0;
    errorRate = 0;
    errorSum = 0;
    errorSum = 0;
    error = 0;
    lastError = 0;
    lastTimeStamp = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    error = distance - Math.abs(driveTrain.getDisplacement());
    dt = Timer.getFPGATimestamp() - lastTimeStamp;
    errorRate = (error - lastError) / dt;
    if(Math.abs(error) < iLimit){
      errorSum += error * dt;
    }
    if(Math.abs(error) < 50)
      isDone = true;
    else{
      isDone = false;
    }
    driveTrain.drive(.4 * ((error * kP) + (errorRate * kD) + (errorSum * kI)), 0);
    lastError = error;
    lastTimeStamp = Timer.getFPGATimestamp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isDone){
      c++;
      if(c > 100){
        return true;
      }
    }
    else{
      c = 0;
      return false;
    }
    return false;
  }
}
