/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class PIDTurn extends CommandBase {
  /**
   * Creates a new PIDTurn.
   */
  DriveTrain drivetrain;

  private int counter = 0;

  private double p = .750; //.033 .02
  private double i = 0.0; //.008 .008
  private double d = 0.205;//.0025 .01
  private double error = 0; 
  private double errorSum = 0;
  private double lastError = 0;
  private double iLimit = 25;
  private double setpoint = 180;

  private double lastTimestamp = 0;

  public PIDTurn(DriveTrain driveTrain) {
    drivetrain = driveTrain;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    error = 0;
    errorSum = 0;
    lastError = 0;
    lastTimestamp = Timer.getFPGATimestamp();
    drivetrain.initOLC();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Gyro: "+ drivetrain.getGyroAngle());
    System.out.println("Error: " + error);

    error = setpoint - drivetrain.getGyroAngle();
    double dt = Timer.getFPGATimestamp() - lastTimestamp;
    lastTimestamp = Timer.getFPGATimestamp();
    
    if(Math.abs(error) < iLimit){
      errorSum += error * dt;
    }
    //(error * p) + (i * errorSum));

    double errorRate = (error - lastError) / dt;
   // ((error * p) + (i * errorSum) + (d * errorRate))
   if(((error * p) + (i * errorSum) + (d * errorRate)) < .500 && error > 0){
    drivetrain.openLoopControl(0,.420 );
   }
   else if(((error * p) + (i * errorSum) + (d * errorRate)) > -.500 && error < 0){
    drivetrain.openLoopControl(0,-.420 );
   }
   else{
    drivetrain.openLoopControl(0,((error * p) + (i * errorSum) + (d * errorRate)));
   }
    if(error < 3 && error > -3){
      counter++;
    }
    else{
      counter = 0;
    }
    lastError = error;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.openLoopControl(0, 0);
    drivetrain.resetNavx();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(counter > 50 ){
      return true;
    }
    return false;
  }
}
