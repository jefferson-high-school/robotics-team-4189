/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterToVelocity extends CommandBase {
  /**
   * Creates a new ShooterToVelocity.
   */
  private final Shooter shooter;
  private double shootVelocity;
  private double shootSpeed;
  private double shootSpeedAdjustment;
  private int targetSpeed;
  private boolean end;
  private int c;

  public ShooterToVelocity(Shooter shoot) {
    shooter = shoot;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shootVelocity = shooter.getShootVelocity();
    // if(!SmartDashboard.containsKey("Shoot Velocity"));
    //   SmartDashboard.putNumber("Shoot Velocity", 0);
    SmartDashboard.putNumber("Timer", 300);
    shootSpeed = 1;
    shootSpeedAdjustment = .001;
    end = false;
    c = 0;
    targetSpeed = 16000;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("shooter auto speed", shootSpeed);
    // shooter.load(-controller.getTriggerAxis(Hand.kRight));
    shootVelocity = shooter.getShootVelocity();
    shooter.shoot(shootSpeed);
    if(targetSpeed > shootVelocity){
      shootSpeed += shootSpeedAdjustment;
    }
    if(targetSpeed < shootVelocity){
      shootSpeed -= shootSpeedAdjustment;
    }
    if(targetSpeed >= shootVelocity - 200 && targetSpeed <= shootVelocity + 200 ){
      SmartDashboard.putBoolean("PERFECTish VELO", true);
      SmartDashboard.putNumber("c", c);
      c++;
      if(c >= SmartDashboard.getNumber("Timer", 100) && !(c <=1));
        shooter.load(-.75);
    }
    else{
      SmartDashboard.putBoolean("PERFECTish VELO", false);
      shooter.load(0);
      c = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.delete("c");
    SmartDashboard.delete("PERFECTish VELO");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
