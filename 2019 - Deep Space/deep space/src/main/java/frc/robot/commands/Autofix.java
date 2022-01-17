/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Camera3;


public class Autofix extends Command {
  
  public Autofix() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.camera3);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // if (OI.Usb.getBytesReceived() > 0) {
    //   String recvstr = OI.Usb.readString(); 
    //   Camera3 Camera3 = new Camera3();
    //   Camera3.doStuff(recvstr);
    //   System.out.println(recvstr);
    //   //Nightcore is cool      
    //   }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
