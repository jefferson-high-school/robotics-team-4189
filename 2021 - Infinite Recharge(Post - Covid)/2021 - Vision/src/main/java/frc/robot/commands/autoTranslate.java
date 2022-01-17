// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;


public class autoTranslate extends CommandBase {
  /** Creates a new autoTranslate. */
  private boolean done = false;
  private DriveTrain drive;
  private double getDisplacement;
  private double convert;
  private Camera2 cam;
  public autoTranslate(DriveTrain train, Camera2 input1) {
    drive = train;
    done = false;
    cam = input1;
    convert = (10503.282 * (Math.sqrt(((cam.readZoneDistance() * cam.readZoneDistance()) + cam.readCenterPixels() * cam.readCenterPixels())) / 12)); //- 5251.641;
    getDisplacement = 0;
    System.out.println(convert);
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putString("State", "Translate");
    drive.resetDisplacement(0);
    done = false;
    convert = (11822.6 * (Math.sqrt(((cam.readZoneDistance() * cam.readZoneDistance()) + cam.readCenterPixels() * cam.readCenterPixels())) / 12)) - 15502; //- 5251.641;
    getDisplacement = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(convert);
    getDisplacement = Math.abs(drive.getDisplacement());
    drive.drive(.5, 0);
    //10503.282 ticks per ft - base
    //11822.6 ticks per ft - with shooter mounted on
    if(getDisplacement > convert) {
      done = true;
    }
  }

  // Called once .the command ends or is interrupted.
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
