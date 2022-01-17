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

public class WallRight extends CommandBase {
  /**
   * Creates a new Wallfollow.
   */
  private DriveTrain drivetrain;
  private double p = .4;
  //private double i = 0.0;
  //private double d = 0.0;
  private double error = 0;
  private double setpoint = 2;

  private double p1 = .025;//0.0116 //.02
  private double i1 = 0.0; //0.03
  private double d1 = 0.0; //0.00003;
  private double setpoint1 = 0;
  private double error1 = 0;
  private double lastTimestamp = 0;
  private double lastError1 = 0;
  private double errorSum1 = 0;
  private double iLimit1 = 10;
  private boolean lineUp = false;
  
  public WallRight(DriveTrain input) {
    drivetrain = input;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lineUp = false;
    lastTimestamp = Timer.getFPGATimestamp();
    lastError1 = 0;
    errorSum1 = 0;
    drivetrain.resetNavx();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    error = setpoint - drivetrain.getRightBottomUltraDistance();

    error1 = setpoint1 - drivetrain.getGyroAngle();
    double dt = Timer.getFPGATimestamp() - lastTimestamp;
    double errorRate = (error1 - lastError1) / dt;

    if(Math.abs(error1) < iLimit1){
      errorSum1 = errorSum1 + (error1 * dt);
    }

    //System.out.println("Gyro" + ultrasonic.getAngleGyro() + " " + error);
    System.out.println(drivetrain.getRightBottomUltraDistance());
    drivetrain.openLoopControl(.5, ((error1 * p1) + (d1 * errorRate) + (i1 * errorSum1)) + (-error * p));
    lastTimestamp = Timer.getFPGATimestamp();
    lastError1 = error1;
    if((error1 < setpoint + .2 && error1 > setpoint + .2) && (error1 < 2 && error1 > -2)){
      lineUp = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.openLoopControl(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //if(lineUp = true && (drivetrain.getFrontUltraDistance() < 1.2)){
      //return true;
    //}else{
      return false;
    //}
  }
}
