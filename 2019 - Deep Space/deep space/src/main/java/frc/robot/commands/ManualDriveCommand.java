/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualDriveCommand extends Command {
  public double distance;
  public ManualDriveCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
//Austin 1.0 helped make this
//Trevor with the EPIC memes

double y_input = OI.driveStick.getY(Hand.kLeft);
double x_input = OI.driveStick.getX(Hand.kLeft);
double z_input = OI.driveStick.getX(Hand.kRight);

//Robot.driveSubsystem.manualDrive(OI.primaryStick.getX(), OI.primaryStick.getY()*-1, OI.primaryStick.getZ());
//Robot.driveSubsystem.manualDrive(OI.driveController.getX(Hand.kLeft), OI.driveController.getY(Hand.kLeft)*-1, OI.driveController.getX(Hand.kRight));
  Robot.driveSubsystem.manualDrive(Math.pow(x_input, 2) * Math.signum(x_input), Math.pow(y_input*-1, 2) * Math.signum(y_input*-1), Math.pow(z_input, 2) * Math.signum(z_input));

  Robot.driveSubsystem.onBoarderSetSpeed(OI.secondaryStick.getY() * -1);

  if(OI.driveStick.getBumper(Hand.kLeft)) {
    Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(1);
  }
  else if(OI.driveStick.getBumper(Hand.kRight))  {
    Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(-1);
  }
  else {
    Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(0);
  }

    //Use Motors
    // if(OI.onBoarderFoward.get()) {
    //   Robot.driveSubsystem.onBoarderSetSpeed(1);
    // }
    // else if(OI.onBoarderReverse.get()) {
    //   Robot.driveSubsystem.onBoarderSetSpeed(-1);
    // }
    // else {
    //   Robot.driveSubsystem.onBoarderSetSpeed(0);
    // }

    // if(OI.driveForwards1.get() || OI.driveForwards2.get() || OI.driveForwards3.get()) {
    //   Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(1);
    // }
    // else if(OI.climbWheelsBackwards.get())  {
    //   Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(-1);
    // }
    // else if(OI.driveBackwards1.get() || OI.driveBackwards2.get() || OI.driveBackwards3.get()) {
    //   Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(-1);
    // }
    // else {
    //   Robot.driveSubsystem.frontClimbWheelDriveSetSpeed(0);
    // }

    //Use Solenoids
    if(OI.openHatch.get())  {
      Robot.driveSubsystem.controlHatchO_C.set(true);
    }
    else if(OI.closeHatch.get())  {
      Robot.driveSubsystem.controlHatchO_C.set(false);
    }

    if(OI.shootBallLauncher.get()) {
      Robot.driveSubsystem.controlLauncher.set(true);
    }
    else if(OI.retractBallLauncher.get()) {
      Robot.driveSubsystem.controlLauncher.set(false);
    }

    if(OI.raiseLauncher.get()) {
      Robot.driveSubsystem.controlHatchO_C.set(false);
      Robot.driveSubsystem.controlOnboarder.set(true);
      Timer.delay(.2);
      Robot.driveSubsystem.controlLauncherHeight.set(true);
    }
    else if(OI.lowerLauncher.get()) {
      Robot.driveSubsystem.controlLauncherHeight.set(false);
    }

    // if(OI.grabHatch.get()) {
    //    //get panel
    //   Robot.driveSubsystem.controlHatchO_C.set(false);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchF_B.set(true);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchO_C.set(true);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchF_B.set(false);
    //  }
    // else if(OI.placeHatch.get()) {
    //   //put panel
    //   Robot.driveSubsystem.controlHatchF_B.set(true);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchO_C.set(false);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchF_B.set(false);
    //  }

    if(OI.driveStick.getAButton())  {
      //Place Hatch
      Robot.driveSubsystem.controlHatchF_B.set(true);
      Timer.delay(.4);
      Robot.driveSubsystem.controlHatchO_C.set(false);
      Timer.delay(.4);
      Robot.driveSubsystem.controlHatchF_B.set(false);
    }
    else if (OI.driveStick.getBButton())  {
      //Grab Hatch
      Robot.driveSubsystem.controlHatchO_C.set(false);
      Timer.delay(.2);
      Robot.driveSubsystem.controlHatchF_B.set(true);
      Timer.delay(.4);
      Robot.driveSubsystem.controlHatchO_C.set(true);
      Timer.delay(.4);
      Robot.driveSubsystem.controlHatchF_B.set(false);
    }

  //    if(OI.driveController.getBButton()) {
  //     //get panel
  //    Robot.driveSubsystem.controlHatchO_C.set(false);
  //    Timer.delay(1);
  //    Robot.driveSubsystem.controlHatchF_B.set(true);
  //    Timer.delay(1);
  //    Robot.driveSubsystem.controlHatchO_C.set(true);
  //    Timer.delay(1);
  //    Robot.driveSubsystem.controlHatchF_B.set(false);
  //   }
  //  else if(OI.driveController.getAButton()) {
  //    //put panel
  //    Robot.driveSubsystem.controlHatchF_B.set(true);
  //    Timer.delay(1);
  //    Robot.driveSubsystem.controlHatchO_C.set(false);
  //    Timer.delay(1);
  //    Robot.driveSubsystem.controlHatchF_B.set(false);
  //   }


    // if(OI.hatchFoward.get()) {
    //   Robot.driveSubsystem.controlHatchF_B.set(true);
    // }
    // if(OI.hatchBack.get()) {
    //   //put panel
    //   Robot.driveSubsystem.controlHatchF_B.set(true);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchO_C.set(false);
    //   Timer.delay(1);
    //   Robot.driveSubsystem.controlHatchF_B.set(false);
    // }

    if(OI.onBoarderDown.get()) {
      Robot.driveSubsystem.controlOnboarder.set(true);
    }
    else if(OI.onBoarderUp.get()) {
      Robot.driveSubsystem.controlOnboarder.set(false);
    }

    if(OI.climbDown.get()) {
      Robot.driveSubsystem.controlClimbFront.set(true);
      Robot.driveSubsystem.controlClimbRear.set(true);
      Timer.delay(.7);
    }
    // if(OI.climbFrontDown.get()) {
    //   Robot.driveSubsystem.controlClimbFront.set(true);
    //   Timer.delay(.7);
    // }
    else if(OI.climbFrontUp.get()) {
      Robot.driveSubsystem.controlClimbFront.set(false);
    }
    // if(OI.climbRearDown.get()) {
    //   Robot.driveSubsystem.controlClimbRear.set(true);
    //}
    else if(OI.climbRearUp.get()) {
      Robot.driveSubsystem.controlClimbRear.set(false);
    }
    
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
